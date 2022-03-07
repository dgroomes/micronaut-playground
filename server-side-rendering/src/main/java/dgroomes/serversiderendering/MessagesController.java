package dgroomes.serversiderendering;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Render an HTML page with friendly messages.
 */
@Controller("/messages")
public class MessagesController {

    private static final String FRIENDLY_MESSAGE = "Hello from a Micronaut web server application!";

    public record Message(int number, String text) {
    }

    @Introspected
    public record Messages(List<Message> messages) {
    }

    /**
     * @param repetitions Optional query parameter. When present, the message will be repeated this many times.
     */
    @SuppressWarnings("unchecked")
    @Get
    public ModelAndView<Messages> messages(@Nullable @Parameter Integer repetitions) {
        if (repetitions == null) {
            repetitions = 1;
        }

        List<Message> messages = IntStream.range(0, repetitions).mapToObj(index -> new Message(index, FRIENDLY_MESSAGE)).toList();

        return new ModelAndView("messages", new Messages(messages));
    }
}
