# server

A "Hello World" Micronaut HTTP web server application that also showcases compression (gzip) and content-negotiation.


## Instructions

Follow these instructions to build and run the program.

1. Use Java 21
2. Build and start the web app:
   * ```shell
     ./gradlew run
     ```
3. Make an HTTP request:
   * ```shell
     curl --request GET http://[::1]:8080/messages
     ```
   * You should notice that the server responded with a friendly message! Altogether, it should look something like this:
     ```text
     $ curl --request GET http://[::1]:8080/messages
     Hello from a Micronaut web server application!
     ```
4. Add a query parameter
   * ```shell
     curl --request GET http://[::1]:8080/messages?repetitions=3
     ```
   * The server repeated the friendly message three times! Altogether, it should look something like this:
     ```text
     curl --request GET http://[::1]:8080/messages?repetitions=3
     Hello from a Micronaut web server application!
     Hello from a Micronaut web server application!
     Hello from a Micronaut web server application!
     ```
5. Request the response in CSV format
    * ```shell
      curl --request GET --header 'Accept: text/csv' http://[::1]:8080/messages?repetitions=3
      ```
    * The server formatted the messages in a CSV format, including a special "message_number" column. The CSV support
      required custom application code. Altogether, it should look something like this:
      ```text
      curl --request GET --header 'Accept: text/csv' http://[::1]:8080/messages?repetitions=3
      message_number,message
      1,"Hello from a Micronaut web server application!"
      2,"Hello from a Micronaut web server application!"
      3,"Hello from a Micronaut web server application!"
      ```
6. Inspect the HTTP response header
   * ```shell
     curl --request GET --head http://[::1]:8080/messages?repetitions=3
     ```
   * Notice the new `--head` option we passed to `curl`. This will print the HTTP response headers and status line only.
   * Altogether, it should look something like this:
     ```text
     $ curl --request GET --head http://[::1]:8080/messages?repetitions=3
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
7. Experiment with compression encodings
   * First, try a request without compression but with lots of repetitions.
   * ```shell
     curl --request GET --head http://[::1]:8080/messages?repetitions=100
     ```
   * The response body is 4,700 bytes.
   * Allow [gzip](https://developer.mozilla.org/en-US/docs/Glossary/GZip_compression) compression via an [Accept-Encoding](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Encoding)
     header.
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: gzip' http://[::1]:8080/messages?repetitions=100
     ```
   * The response body is only 100 bytes! The compression was highly effective because the content is so repetitive.
   * Experiment with other encodings like [Brotli](https://en.wikipedia.org/wiki/Brotli), [Zstandard](https://en.wikipedia.org/wiki/Zstd),
     and [DEFLATE](https://en.wikipedia.org/wiki/Deflate).
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: br' http://[::1]:8080/messages?repetitions=100
     ```
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: zstd' http://[::1]:8080/messages?repetitions=100
     ```
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: deflate' http://[::1]:8080/messages?repetitions=100
     ```
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: *' http://[::1]:8080/messages?repetitions=100
     ```
8. Find the compression lower-limit
   * Look at the output of the following commands. Why does the first one have a larger response size (`Content-Length`
     bytes) than the second one, despite it having fewer repetitions?
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: *' http://[::1]:8080/messages?repetitions=21
     ```
   * ```shell
     curl --request GET --head --header 'Accept-Encoding: *' http://[::1]:8080/messages?repetitions=22
     ```
   * The reason is the configuration of the "content size threshold" given to `io.netty.handler.codec.http.HttpContentCompressor`.
     We don't want to bother compressing small payloads. The compute overhead is not worth it.
9. Try running a Micronaut-based client instead of `curl`:
   * ```shell
     ./gradlew :client:run
     ```
   * The output will look something like this:
     ```text
     13:21:03 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [cli]
     13:21:03 [main] INFO  dgroomes.client.ClientProgram - Sending a request to the server...
     13:21:03 [main] INFO  dgroomes.client.ClientProgram - Got a response. Content length: 47
     13:21:03 [main] INFO  dgroomes.client.ClientProgram - Sending a request with 'repetitions' equal to 10,000 ...
     13:21:03 [main] INFO  dgroomes.client.ClientProgram - Got a response. Content length: 538,917
     13:21:03 [main] INFO  dgroomes.client.ClientProgram - Sending a request with 'repetitions' equal to 1,000,000 ...
     13:21:04 [main] INFO  dgroomes.client.ClientProgram - Got a response. Content length: 55,888,919
     13:21:04 [main] INFO  dgroomes.client.ClientProgram - Sending a request with 'repetitions' equal to 2,000,000. This should fail because the size of the response is larger than 'micronaut.http.client.max-content-length' configuration ...
     Exception in thread "main" io.micronaut.http.client.exceptions.ContentLengthExceededException: The received length exceeds the maximum allowed content length [104857600]
             at io.micronaut.http.client.netty.DefaultHttpClient$11.exceptionCaught(DefaultHttpClient.java:2608)
             ... snip ...
             at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:496)
             at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:986)
             at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
             at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
             at java.base/java.lang.Thread.run(Thread.java:833)
             Suppressed: java.lang.Exception: #block terminated with an error
                     at reactor.core.publisher.BlockingSingleSubscriber.blockingGet(BlockingSingleSubscriber.java:99)
                     at reactor.core.publisher.Flux.blockFirst(Flux.java:2600)
                     at io.micronaut.http.client.netty.DefaultHttpClient$3.exchange(DefaultHttpClient.java:612)
                     at io.micronaut.http.client.BlockingHttpClient.exchange(BlockingHttpClient.java:77)
                     at io.micronaut.http.client.BlockingHttpClient.exchange(BlockingHttpClient.java:106)
                     at dgroomes.client.ClientProgram.run(ClientProgram.java:63)
                     at dgroomes.client.ClientMain.main(ClientMain.java:15)
     ```
   * The moral of the story is that you, as a programmer, need to think about the size of the response data and how it
     effects the behavior of the server (e.g. compression) and how it effects the behavior of the client (e.g. too much
     data causes a failure)

Tip: to build and run the program in debug suspending mode, use this:

```shell
alias go="./gradlew :installDist && SERVER_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005' build/install/server/bin/server"
go
```


## Reference

* ["Creating your first Micronaut application"](https://guides.micronaut.io/latest/creating-your-first-micronaut-app-gradle-java.html)
* [Micronaut User Guide: "6.29.2 Configuring the Netty Pipeline"](https://docs.micronaut.io/latest/guide/#nettyPipeline)
* [MDN Web Docs: "Accept"](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept)
  > The "Accept" request HTTP header indicates which content types, expressed as MIME types, the client is able to understand.
