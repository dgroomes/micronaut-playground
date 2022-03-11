package dgroomes.config;

import io.micronaut.context.env.PropertySource;
import io.micronaut.runtime.Micronaut;

import java.util.Map;

/**
 * This showcases configuration features of Micronaut like environments and externalized configuration.
 */
public class ConfigApplication {

    private static final Map<String, Object> HARDCODED_PROPERTIES = Map.of("app.message-4", "Hello from the 'HARDCODED_PROPERTIES'!");

    public static void main(String[] args) {
        Micronaut micronaut = Micronaut
                .build((String) null)
                .propertySources(PropertySource.of(HARDCODED_PROPERTIES));

        try (var ctx = micronaut.build()) {

            System.setProperty("app.message-2", "Hello from a system property!");

            ctx.start();

            ctx.getBean(MessageVisitor.class).visitMessages();
        }
    }
}
