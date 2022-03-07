package dgroomes.beans;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.env.Environment;
import io.micronaut.inject.BeanIdentifier;
import io.micronaut.runtime.context.scope.Refreshable;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Singleton
public class BeansApplication {

    private final Provider<TimeArchiver> timeArchiverSingleton;
    private final Provider<TimeArchiver> timeArchiverPrototype;
    private final Provider<TimeArchiver> timeArchiverRefreshable;
    private final ApplicationContext ctx;

    public BeansApplication(@Named("timeArchiverSingleton") Provider<TimeArchiver> timeArchiverSingletonProvider,
                            @Named("timeArchiverPrototype") Provider<TimeArchiver> timeArchiverPrototypeProvider,
                            @Named("timeArchiverRefreshable") Provider<TimeArchiver> timeArchiverRefreshable,
                            ApplicationContext ctx) {
        this.timeArchiverSingleton = timeArchiverSingletonProvider;
        this.timeArchiverPrototype = timeArchiverPrototypeProvider;
        this.timeArchiverRefreshable = timeArchiverRefreshable;
        this.ctx = ctx;
    }

    public static void main(String[] args) throws InterruptedException {
        var builder = ApplicationContext.builder(BeansApplication.class, Environment.CLI);
        var ctx = builder.build();
        ctx.start();
        var app = ctx.getBean(BeansApplication.class);
        app.run();
    }

    public void run() throws InterruptedException {
        lookAtTheTime(timeArchiverSingleton, "SINGLETON");
        lookAtTheTime(timeArchiverPrototype, "PROTOTYPE");
        lookAtTheTime(timeArchiverRefreshable, "REFRESHABLE");

        // "Refresh" the bean. The effect I'm looking for is that the "archived time" that's printed is now a later
        // time. I think how this works is that Micronaut marks the bean as stale, and only the next time one of the
        // bean's methods are de-referenced, like TimeArchiver#toString, then Micronaut actually constructs a new "fresh"
        // instance of TimeArchiver. In my opinion, this is on the high level of complicated for end-users to understand.
        // In my opinion, end-users need to a mental of how the bean lifecycle works to be able to use it effectively and
        // safely. In other words, I don't think the framework has created an abstraction, but instead a leaky abstraction.
        ctx.getEventPublisher(RefreshEvent.class).publishEvent(new RefreshEvent());
        ctx.refreshBean(BeanIdentifier.of("timeArchiverRefreshable"));
        lookAtTheTime(timeArchiverRefreshable, "REFRESHABLE");
    }

    private void lookAtTheTime(Provider<TimeArchiver> provider, String beanDescriptor) throws InterruptedException {
        System.out.printf("Looking up the time on a '%s' bean%n", beanDescriptor);
        for (int i = 0; i < 3; i++) {
            System.out.println("[BeansApplication#run] pre-get");
            var timeArchiver = provider.get();
            System.out.println("[BeansApplication#run] post-get");
            System.out.printf("[BeansApplication#run] [timeArchiver identity=%s type=%s] Found time archiver: %s%n", System.identityHashCode(timeArchiver), timeArchiver.getClass(), timeArchiver);
            Thread.sleep(1_000);
        }
        System.out.printf("%n%n");
    }

    @Factory
    static class BeansFactory {

        @Bean
        @Singleton
        @Named("timeArchiverSingleton")
        public TimeArchiver timeArchiverSingleton() {
            System.out.println("[BeansFactory#timeArchiverSingleton] pre-construct");
            var timeArchiver = new TimeArchiver();
            System.out.printf("[BeansFactory#timeArchiverSingleton] [timeArchiver identity=%s type=%s]%n", System.identityHashCode(timeArchiver), timeArchiver.getClass());
            System.out.println("[BeansFactory#timeArchiverSingleton] post-construct");
            return timeArchiver;
        }

        @Named("timeArchiverPrototype")
        @Prototype
        public TimeArchiver timeArchiverPrototype() {
            System.out.println("[BeansFactory#timeArchiverPrototype] pre-construct");
            var timeArchiver = new TimeArchiver();
            System.out.printf("[BeansFactory#timeArchiverPrototype] [timeArchiver identity=%s type=%s]%n", System.identityHashCode(timeArchiver), timeArchiver.getClass());
            System.out.println("[BeansFactory#timeArchiverPrototype] post-construct");
            return timeArchiver;
        }

        @Named("timeArchiverRefreshable")
        @Refreshable
        public TimeArchiver timeArchiverRefreshable() {
            System.out.println("[BeansFactory#timeArchiverRefreshable] pre-construct");
            var timeArchiver = new TimeArchiver();
            System.out.printf("[BeansFactory#timeArchiverRefreshable] [timeArchiver identity=%s type=%s]%n", System.identityHashCode(timeArchiver), timeArchiver.getClass());
            System.out.println("[BeansFactory#timeArchiverRefreshable] post-construct");
            return timeArchiver;
        }
    }
}
