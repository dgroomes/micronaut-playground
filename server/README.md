# server

This is a "Hello World" Micronaut HTTP web server application but with some compression flair.

## Instructions

1. Use Java 17
2. Build and start the web app:
   * `./gradlew run`
3. Make an HTTP request:
   * `curl --request GET http://[::1]:8080/message`
   * You should notice that the server responded with a friendly message! Altogether, it should look something like this:
     ```text
     $ curl --request GET http://[::1]:8080/message
     Hello from a Micronaut web server application!
     ```
4. Add a query parameter
   * `curl --request GET http://[::1]:8080/message?repetitions=3`
   * The server repeated the friendly message three times! Altogether, it should look something like this:
     ```text
     curl --request GET http://[::1]:8080/message?repetitions=3
     Hello from a Micronaut web server application!
     Hello from a Micronaut web server application!
     Hello from a Micronaut web server application!
     ```
5. Inspect the HTTP response header
   * `curl --request GET --head http://[::1]:8080/message?repetitions=3`
   * Notice the new `--head` option we passed to `curl`. This will print the HTTP response headers and status line only.
   * Altogether, it should look something like this:
     ```text
     $ curl --request GET --head http://[::1]:8080/message?repetitions=3
     HTTP/1.1 200 OK
     date: Sat, 12 Feb 2022 16:38:33 GMT
     Content-Type: text/plain
     content-length: 141
     connection: keep-alive
     ```
   * Notice the [Content-Length](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length) header has a
     value of `141`. This is equal to the number of characters in the body. Specifically:
     ```text
     Hello from a Micronaut web server application! (46 chars + 1 newline character)
     Hello from a Micronaut web server application! (46 chars + 1 newline character)
     Hello from a Micronaut web server application! (46 chars + 1 newline character)
     ```
     Which is `47 * 3 = 141`. Makes sense!
6. Experiment with compression encodings
   * First, try a request without compression but with lots of repetitions.
   * `curl --request GET --head http://[::1]:8080/message?repetitions=100`
   * The response body is 4700 bytes.
   * Allow [gzip](https://developer.mozilla.org/en-US/docs/Glossary/GZip_compression) compression via an [Accept-Encoding](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Encoding)
     header.
   * `curl --request GET --head --header 'Accept-Encoding: gzip' http://[::1]:8080/message?repetitions=100`
   * The response body is only 100 bytes! The compression was highly effective because the content is so repetitive.
   * Experiment with other encodings like [Brotli](https://en.wikipedia.org/wiki/Brotli), [Zstandard](https://en.wikipedia.org/wiki/Zstd),
     and [DEFLATE](https://en.wikipedia.org/wiki/Deflate).
   * `curl --request GET --head --header 'Accept-Encoding: br' http://[::1]:8080/message?repetitions=100`
   * `curl --request GET --head --header 'Accept-Encoding: zstd' http://[::1]:8080/message?repetitions=100`
   * `curl --request GET --head --header 'Accept-Encoding: deflate' http://[::1]:8080/message?repetitions=100`
   * `curl --request GET --head --header 'Accept-Encoding: *' http://[::1]:8080/message?repetitions=100`
7. Find the compression lower-limit
   * Look at the output of the following commands. Why does the first one have a larger response size (`Content-Length`
     bytes) than the second one, despite it having fewer repetitions?
   * `curl --request GET --head --header 'Accept-Encoding: *' http://[::1]:8080/message?repetitions=21`
   * `curl --request GET --head --header 'Accept-Encoding: *' http://[::1]:8080/message?repetitions=22`
   * The reason is the configuration of the "content size threshold" given to `io.netty.handler.codec.http.HttpContentCompressor`.
     We don't want to bother compressing small payloads. The compute overhead is not worth it.

Tip: to build and run the program in debug mode in suspending mode, use this:

```shell
alias go="./gradlew :installDist && SERVER_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005' build/install/server/bin/server"`
go
```
   

## Reference

* ["Creating your first Micronaut application"](https://guides.micronaut.io/latest/creating-your-first-micronaut-app-gradle-java.html)
* [Micronaut User Guide: "6.29.2 Configuring the Netty Pipeline"](https://docs.micronaut.io/latest/guide/#nettyPipeline)
