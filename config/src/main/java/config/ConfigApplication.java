package config;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple "hello world" Micronaut program
 */
public class ConfigApplication {

    private static final Logger log = LoggerFactory.getLogger(ConfigApplication.class);

    public static void main(String[] args) {
        var builder = ApplicationContext.build(ConfigApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {

            System.setProperty("app.message-2", "Hello from a system property!");

            ctx.start();

            log.info(ctx.getRequiredProperty("app.message-1", String.class));
            log.info(ctx.getRequiredProperty("app.message-2", String.class));
            log.info(ctx.getRequiredProperty("app.message-3", String.class));
        }
    }
}
