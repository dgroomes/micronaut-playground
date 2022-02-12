package dgroomes.server;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/message")
public class MessageController {

    /**
     * Return a friendly message to the caller.
     *
     * @param repetitions Optional query parameter. When present, the message will be repeated this many times.
     * @return a friendly message
     */
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String message(@Nullable @Parameter Integer repetitions) {
        if (repetitions == null) {
            repetitions = 1;
        }

        return "Hello from a Micronaut web server application!\n".repeat(repetitions);
    }
}
