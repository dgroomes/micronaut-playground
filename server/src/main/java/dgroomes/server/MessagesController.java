package dgroomes.server;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Return friendly messages to the client.
 */
@Controller("/messages")
public class MessagesController {

    private static final String FRIENDLY_MESSAGE = "Hello from a Micronaut web server application!";
    private final CsvMapper csvMapper = new CsvMapper();

    /**
     * Return friendly messages to the client.
     *
     * @param repetitions Optional query parameter. When present, the message will be repeated this many times.
     * @return a friendly message
     */
    @Get(produces = {MediaType.TEXT_PLAIN, MediaType.ALL})
    public String messagesPlainText(@Nullable @Parameter Integer repetitions) {
        if (repetitions == null) {
            repetitions = 1;
        }

        return (FRIENDLY_MESSAGE + "\n").repeat(repetitions);
    }

    /**
     * Return friendly messages to the caller in CSV format.
     * <p>
     * It will look something like this:
     * <pre>
     * message_number,message
     * 1,"Hello from a Micronaut web server application!"
     * 2,"Hello from a Micronaut web server application!"
     * 3,"Hello from a Micronaut web server application!"
     * </pre>
     *
     * @param repetitions the message will be repeated this many times.
     * @return friendly messages in CSV.
     */
    @Get(produces = {MediaType.TEXT_CSV})
    public String messagesCsv(@Nullable @Parameter Integer repetitions) {
        if (repetitions == null) {
            repetitions = 1;
        }

        CsvSchema schema = new CsvSchema.Builder()
                .addColumn("message_number")
                .addColumn("message")
                .setUseHeader(true)
                .build();

        try (var byteStream = new ByteArrayOutputStream();
             var writer = csvMapper.writer(schema).writeValues(byteStream)) {

            for (int i = 0; i < repetitions; i++) {
                writer.write(new Object[]{i + 1, FRIENDLY_MESSAGE});
            }

            return byteStream.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong while serializing messages to CSV");
        }
    }
}
