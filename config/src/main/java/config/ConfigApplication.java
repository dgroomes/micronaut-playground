package config;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * A simple "hello world" Micronaut program
 */
public class ConfigApplication {

    public static void main(String[] args) {
        var builder = ApplicationContext.build(ConfigApplication.class, Environment.CLI);
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
