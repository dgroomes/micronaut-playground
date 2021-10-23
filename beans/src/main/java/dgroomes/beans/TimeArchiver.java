package dgroomes.beans;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static java.time.temporal.ChronoField.*;

public class TimeArchiver {

    public final String archivedTime;

    public TimeArchiver() {
        System.out.println("[TimeArchiver#constructor] New instance!");
        var now = Instant.now();
        var localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        archivedTime = HOUR_MINUTE_FORMATTER.format(localDateTime);
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
