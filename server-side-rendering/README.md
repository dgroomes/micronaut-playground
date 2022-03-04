# server-side-rendering

A Micronaut application that renders HTML on the server-side using the Micronaut "Views" sub-project and Thymeleaf.

## Instructions

Follow these instructions to build and run the program.

1. Use Java 17
2. Build and start the web app:
   * `./gradlew run`
3. Open your web browser:
   * <http://[::1]:8080/messages>
   * You should notice that the page rendered a friendly message in HTML!
4. Add a query parameter
   * <http://[::1]:8080/messages?repetitions=4>
   * The page repeated the message four times!

## Reference

* [Thymeleaf](https://www.thymeleaf.org/)
  > Thymeleaf is a modern server-side Java template engine for both web and standalone environments.
* [Micronaut Views](https://micronaut-projects.github.io/micronaut-views/latest/guide/index.html)
* [Google Developers article: "Rendering on the Web"](https://developers.google.com/web/updates/2019/02/rendering-on-the-web)
  > Server rendering generates the full HTML for a page on the server in response to navigation.
