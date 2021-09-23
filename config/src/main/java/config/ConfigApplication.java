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

            ctx.start();

            var message = ctx.getRequiredProperty("app.message", String.class);
            log.info(message);
        }
    }
}
