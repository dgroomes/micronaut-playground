package dgroomes.server;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/message")
public class MessageController {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello from a Micronaut web server application!";
    }
}
