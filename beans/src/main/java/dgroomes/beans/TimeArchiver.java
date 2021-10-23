package dgroomes.beans;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static java.time.temporal.ChronoField.*;

@Singleton
public class TimeArchiver {

    private static final Logger log = LoggerFactory.getLogger(TimeArchiver.class);

    private Instant archivedTime;

    public TimeArchiver() {
        log.info("[Default constructor]. Self: {}", this.hashCode());
    }

    @PostConstruct
    public void init() {
        archivedTime = Instant.now();
        log.info("New archived time: {}", getArchivedTime());
        log.info("Self: {}", this.hashCode());
    }

    // This formats a time to just the hour and minute. For example, "09:59PM".
    private static final DateTimeFormatter HOUR_MINUTE_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(CLOCK_HOUR_OF_AMPM, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .appendText(ChronoField.AMPM_OF_DAY)
            .toFormatter();

    /**
     * Get the archived time. This is completely pointless.
     */
    public String getArchivedTime() {
        var localDateTime = LocalDateTime.ofInstant(archivedTime, ZoneId.systemDefault());
        return HOUR_MINUTE_FORMATTER.format(localDateTime);
    }
}
