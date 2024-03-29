# config

This showcases configuration features of Micronaut like environments and externalized configuration.


## Instructions

Follow these instructions to build and run the program.

1. Use Java 21
2. Build and run the program:
    * ```shell
      ./gradlew run
      ```
    * Notice the output. It prints application properties retrieved from various places. Study the code and the
      `application.yml` file to understand where the property values are coming from. The output will look something
      like the following.
    * ```text
      00:15:00.526 [main] INFO  dgroomes.config.MessageVisitor - Hello from the 'application.yml' file!
      00:15:00.527 [main] INFO  dgroomes.config.MessageVisitor - Hello from a system property!
      00:15:00.527 [main] INFO  dgroomes.config.MessageVisitor - Hello from the 'application.yml' file!
      00:15:00.527 [main] INFO  dgroomes.config.MessageVisitor - Hello from the 'HARDCODED_PROPERTIES'!
      ```
3. Try overriding a variable
    * Run the app but also override one of the Micronaut app properties by declaring an environment variable of the
      given name. In this case the property name is `app.message-1`, but we have to use a slightly different name than
      that because `app.message-1` is not a legal environment variable name. Instead, we use `app_message_1`. Micronaut
      is smart enough to realize that this name is a synonym for `app.message-1`. Use the following command:
    * ```shell
      app_message_1="Hello from an environment variable!" ./gradlew run
      ```
    * Notice the output. The environment variable overrode the original value!
4. Try activating a Micronaut *environment*
    * ```shell
      MICRONAUT_ENVIRONMENTS=happy ./gradlew run
      ```
    * Notice the special message!

Tip: to build and run the program in debug suspending mode, use this:

```shell
alias go="./gradlew :installDist && CONFIG_OPTS='-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005' build/install/config/bin/config"
go
```


## Reference

* [Micronaut docs: "The Environment"](https://docs.micronaut.io/latest/guide/index.html#environments)
* [Micronaut docs: "Externalized Configuration with PropertySources"](https://docs.micronaut.io/latest/guide/index.html#propertySource)


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Set a Java system property in the main method before starting the Micronaut app ("context" object). The Micronaut
  app should reference an application property that gets its value from the system property.
* [x] DONE Similar to the Java system property idea, do something with environment variables.
* [x] DONE Do something with Micronaut "environments"
