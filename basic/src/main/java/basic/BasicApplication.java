package basic;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple "hello world" Micronaut program in the CLI style (i.e. it is *not* running a server).
 */
public class BasicApplication {

    public static void main(String[] args) {

        // Is this the correct boilerplate for running a vanilla (i.e. non-Picocli) command line Micronaut program?
        var builder = ApplicationContext.builder(BasicApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {

            ctx.start();
            var runner = ctx.getBean(Runnable.class);
            runner.run();
        }
    }

    @Factory
    static class MyFactory {

        private static final Logger log = LoggerFactory.getLogger(MyFactory.class);

        @Singleton
        String myMessage() {
            return "Hello world!";
        }

        @Singleton
        Runnable myRunner() {
            return () -> log.info(myMessage());
        }
    }
}
