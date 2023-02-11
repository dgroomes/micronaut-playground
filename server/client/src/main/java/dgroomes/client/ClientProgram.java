package dgroomes.client;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
class ClientProgram {

  private static final Logger log = LoggerFactory.getLogger(ClientProgram.class);

  private final BlockingHttpClient httpClient;

  public ClientProgram(@Client("http://[::1]:8080") HttpClient httpClient) {
    this.httpClient = httpClient.toBlocking();
  }

  public void run() {
    {
      log.info("Sending a request to the server...");

      MutableHttpRequest<Object> request = HttpRequest.GET("/messages").accept(MediaType.TEXT_PLAIN_TYPE);
      HttpResponse<String> response = httpClient.exchange(request, String.class);

      var contentLength = formatNumber(response.getContentLength());
      log.info("Got a response. Content length: {}", contentLength);
    }

    {
      log.info("Sending a request with 'repetitions' equal to 10,000 ...");

      MutableHttpRequest<Object> request = HttpRequest.GET("/messages?repetitions=10000")
              .accept(MediaType.TEXT_CSV_TYPE);
      HttpResponse<String> response = httpClient.exchange(request, String.class);

      var contentLength = formatNumber(response.getContentLength());
      log.info("Got a response. Content length: {}", contentLength);
    }

    {
      log.info("Sending a request with 'repetitions' equal to 1,000,000 ...");

      MutableHttpRequest<Object> request = HttpRequest.GET("/messages?repetitions=1000000")
              .accept(MediaType.TEXT_CSV_TYPE);
      HttpResponse<String> response = httpClient.exchange(request, String.class);

      var contentLength = formatNumber(response.getContentLength());
      log.info("Got a response. Content length: {}", contentLength);
    }

    {
      log.info("Sending a request with 'repetitions' equal to 2,000,000. This should fail because the size of the response is larger than 'micronaut.http.client.max-content-length' configuration ...");

      MutableHttpRequest<Object> request = HttpRequest.GET("/messages?repetitions=2000000")
              .accept(MediaType.TEXT_CSV_TYPE);
      HttpResponse<String> response = httpClient.exchange(request, String.class);

      var contentLength = formatNumber(response.getContentLength());
      log.info("Got a response. Content length: {}", contentLength);
    }
  }

  private static String formatNumber(long n) {
    return String.format("%,d", n);
  }
}
