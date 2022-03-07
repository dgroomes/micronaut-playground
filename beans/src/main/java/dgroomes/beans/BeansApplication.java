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

        // Try "refreshing" the bean. The effect I'm looking for is that the "archived time" that's printed is now a later
        // time. Unfortunately, this seems to have no effect.
        ctx.publishEvent(RefreshEvent.class);
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
            System.out.println("[BeansFactory#timeArchiverSingleton] pre-construct");
            var timeArchiver = new TimeArchiver();
            System.out.printf("[BeansFactory#timeArchiverPrototype] [timeArchiver identity=%s type=%s]%n", System.identityHashCode(timeArchiver), timeArchiver.getClass());
            System.out.println("[BeansFactory#timeArchiverSingleton] post-construct");
            return timeArchiver;
        }

        // This bean DOES NOT actually refresh. I don't known why this doesn't work.
        @Named("timeArchiverRefreshable")
        @Refreshable
        public TimeArchiver timeArchiverRefreshable() {
            System.out.println("[BeansFactory#timeArchiverSingleton] pre-construct");
            var timeArchiver = new TimeArchiver();
            System.out.printf("[BeansFactory#timeArchiverRefreshable] [timeArchiver identity=%s type=%s]%n", System.identityHashCode(timeArchiver), timeArchiver.getClass());
            System.out.println("[BeansFactory#timeArchiverSingleton] post-construct");
            return timeArchiver;
        }
    }
}
