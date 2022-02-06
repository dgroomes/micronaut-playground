# server

This is a "Hello World" Micronaut HTTP web server application.

## Instructions

1. Use Java 17
2. Build and start the web app:
   * `./gradlew run`
3. Make an HTTP request:
   * `curl -X GET http://[::1]:8080/message`
   * You should notice that the server responded! Altogether, it should look something like this:
     ```text
     $ curl -X GET http://[::1]:8080/message
     Hello from a Micronaut web server application!
     ```

## Reference

* ["Creating your first Micronaut application"](https://guides.micronaut.io/latest/creating-your-first-micronaut-app-gradle-java.html)
