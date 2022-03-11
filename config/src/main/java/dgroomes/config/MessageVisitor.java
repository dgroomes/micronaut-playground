package dgroomes.config;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log some messages.
 */
@Singleton
public record MessageVisitor(
        @Value("${app.message-1}") String message1,
        @Value("${app.message-2}") String message2,
        @Value("${app.message-3}") String message3,
        @Value("${app.message-4}") String message4) {

    private static final Logger log = LoggerFactory.getLogger(MessageVisitor.class);

    public void visitMessages() {
        log.info(message1);
        log.info(message2);
        log.info(message3);
        log.info(message4);
    }
}
