package dgroomes.beans;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Singleton
public class BeansApplication {

    private final Provider<TimeArchiver> timeArchiverSingleton;
    private final Provider<TimeArchiver> timeArchiverPrototype;

    public BeansApplication(@Named("timeArchiverSingleton") Provider<TimeArchiver> timeArchiverSingletonProvider,
                            @Named("timeArchiverPrototype") Provider<TimeArchiver> timeArchiverPrototypeProvider) {
        this.timeArchiverSingleton = timeArchiverSingletonProvider;
        this.timeArchiverPrototype = timeArchiverPrototypeProvider;
    }

    public static void main(String[] args) throws InterruptedException {
        var ctx = BeanContext.run();
        var app = ctx.getBean(BeansApplication.class);
        app.run();
    }

    public void run() throws InterruptedException {
        lookAtTheTime(timeArchiverSingleton);
        lookAtTheTime(timeArchiverPrototype);
    }

    private void lookAtTheTime(Provider<TimeArchiver> provider) throws InterruptedException {
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
    }
}
