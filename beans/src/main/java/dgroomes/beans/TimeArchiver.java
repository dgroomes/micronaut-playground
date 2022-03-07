package dgroomes.beans;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static java.time.temporal.ChronoField.*;

/**
 * Instances of this class hold on to an "archived time" object. This class is immutable.
 * <p>
 * This class helps illustrate Micronaut's bean lifecycles.
 */
public class TimeArchiver {

    public final LocalDateTime archivedTime;

    public TimeArchiver() {
        System.out.println("[TimeArchiver#constructor] New instance!");
        var now = Instant.now();
        archivedTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
    }

    @Override
    public String toString() {
        String archivedTimeReadable = HOUR_MINUTE_FORMATTER.format(archivedTime);
        return "TimeArchive{archivedTime=%s}".formatted(archivedTimeReadable);
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
}
