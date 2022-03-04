package dgroomes.serversiderendering;

import io.micronaut.runtime.Micronaut;

/**
 * A Micronaut application that renders HTML on the server-side using the Micronaut "Views" sub-project and Thymeleaf.
 */
public class ServerSideRenderingApplication {

    public static void main(String[] args) {
        Micronaut.run(args);
    }
}
