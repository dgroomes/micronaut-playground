package dgroomes.beans;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BeansApplication {

    private static final Logger log = LoggerFactory.getLogger(BeansApplication.class);

    private final TimeArchiver timeArchiver;
    private final ApplicationContext ctx;

    public BeansApplication(TimeArchiver timeArchiver, ApplicationContext ctx) {
        this.timeArchiver = timeArchiver;
        this.ctx = ctx;
    }

    public static void main(String[] args) throws InterruptedException {
        var builder = ApplicationContext.builder(BeansApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {
            ctx.start();

//            for (int i = 0; i < 3; i++) {
//                var timeArchiver = ctx.getBean(TimeArchiver.class);
//                log.info("Found current time: {}", timeArchiver.getArchivedTime());
//                ctx.publishEvent(new RefreshEvent());
//                Thread.sleep(1_000);
//            }
            var app = ctx.getBean(BeansApplication.class);
            app.run();
        }
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            log.info("Self: {}", this.hashCode());
//            var timeArchiver = ctx.getBean(TimeArchiver.class);
            log.info("Found current time: {}", timeArchiver.getArchivedTime());
            ctx.publishEvent(new RefreshEvent());
            Thread.sleep(1_000);
        }
    }
}
