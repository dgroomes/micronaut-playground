package dgroomes.beans;

import io.micronaut.context.BeanContext;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BeansApplication {

    private static final Logger log = LoggerFactory.getLogger(BeansApplication.class);

    private final TimeArchiver timeArchiver;

    public BeansApplication(TimeArchiver timeArchiver) {
        this.timeArchiver = timeArchiver;
    }

    public static void main(String[] args) throws InterruptedException {
        var ctx = BeanContext.run();
        var app = ctx.getBean(BeansApplication.class);
        app.run();
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            log.info("Self: {}", this.hashCode());
            log.info("Found current time: {}", timeArchiver.getArchivedTime());
            Thread.sleep(1_000);
        }
    }
}
