package dgroomes.client;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class ClientMain {

  public static void main(String[] args) {

    var builder = ApplicationContext.builder(ClientMain.class, Environment.CLI);
    try (var ctx = builder.build()) {

      ctx.start();
      var runner = ctx.getBean(ClientProgram.class);
      runner.run();
    }
  }
}
