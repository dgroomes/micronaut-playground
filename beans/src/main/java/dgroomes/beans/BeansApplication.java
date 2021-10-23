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

        ctx.publishEvent(RefreshEvent.class);
        ctx.refreshBean(BeanIdentifier.of("timeArchiverRefreshable"));
        lookAtTheTime(timeArchiverRefreshable, "REFRESHABLE");
    }

    private void lookAtTheTime(Provider<TimeArchiver> provider, String beanDescriptor) throws InterruptedException {
        System.out.printf("Looking up the time on a '%s' bean%n", beanDescriptor);
        for (int i = 0; i < 3; i++) {
            var archivedTime = provider.get().archivedTime;
            System.out.printf("[BeansApplication#run] Found archived time: %s%n", archivedTime);
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
            return new TimeArchiver();
        }

        @Named("timeArchiverPrototype")
        @Prototype
        public TimeArchiver timeArchiverPrototype() {
            return new TimeArchiver();
        }

        // This bean DOES NOT actually refresh. I don't known why this doesn't work.
        @Named("timeArchiverRefreshable")
        @Refreshable
        public TimeArchiver timeArchiverRefreshable() {
            return new TimeArchiver();
        }
    }
}
