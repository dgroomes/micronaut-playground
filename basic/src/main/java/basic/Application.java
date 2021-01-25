package basic;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        var app = Micronaut.build(args).classes(Application.class);
        app.start();
    }
}
