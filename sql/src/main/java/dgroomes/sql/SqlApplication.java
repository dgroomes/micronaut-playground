package dgroomes.sql;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple Micronaut program that connects to a SQL database
 */
public class SqlApplication {

    private static final Logger log = LoggerFactory.getLogger(SqlApplication.class);

    public static void main(String[] args) {
        var builder = ApplicationContext.builder(SqlApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {
            ctx.start();
            log.warn("Not yet implemented");
        }
    }
}
