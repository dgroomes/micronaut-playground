package dgroomes.beans;

import io.micronaut.context.BeanContext;
import jakarta.inject.Singleton;

@Singleton
public class BeansApplication {

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
            System.out.printf("[BeansApplication#run] Found archived time: %s%n", timeArchiver.getArchivedTime());
            Thread.sleep(1_000);
        }
    }
}
