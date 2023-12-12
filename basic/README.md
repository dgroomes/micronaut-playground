# basic

This is a "Hello World" Micronaut CLI program.


## Overview

An effective way to start learning a new framework is with a basic example. This is a so-called "Hello World" program
implemented in Java and the [Micronaut](https://micronaut.io/). I scaffolded this with the Micronaut [`mn` CLI](https://docs.micronaut.io/latest/guide/index.html#buildCLI)
and adapted it from there.


## Instructions

Follow these instructions to build and run the program.

1. Use Java 21
2. Build and run the app:
   * ```shell
     ./gradlew run
     ```
3. Build and run the tests:
   * ```shell
     ./gradlew test
     ```
4. Build the app distribution and then run it:
   * ```shell
     ./gradlew installDist
     ```
   * ```shell
     ./build/install/basic/bin/basic
     ``` 
   * The output should look like this:
     ```shell
     $ ./build/install/basic/bin/basic
     00:05:16.725 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [cli]
     00:05:16.804 [main] INFO  d.basic.BasicApplication$MyFactory - Hello world!
     ```


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Convert to Gradle Kotlin DSL
* [x] DONE Remove web server and just make the app say "hello world"
* [x] DONE Upgrade to Java 11
* [ ] GitHub Actions CI build
* [x] DONE Upgrade to Micronaut 3
* [ ] Upgrade to Micronaut 4


## Reference

* [Micronaut docs: *User Guide*](https://docs.micronaut.io/latest/guide/)
* [Micronaut docs: *API Reference*](https://docs.micronaut.io/2.3.0/api/index.html)
* [Micronaut docs: *Configuration Reference*](https://docs.micronaut.io/2.3.0/guide/configurationreference.html)
* [Micronaut docs: *Micronaut Guides*](https://guides.micronaut.io/index.html)
* [Micronaut docs: *HTTP Client documentation*](https://docs.micronaut.io/latest/guide/index.html#httpClient)
* [Micronaut docs: *Upgrading to Micronaut 3.x*](https://docs.micronaut.io/latest/guide/#upgrading)
* [micronaut-gradle-plugin](https://github.com/micronaut-projects/micronaut-gradle-plugin)
* [StackOverflow question/answer: Micronaut features lists cannot be changed by the CLI](https://stackoverflow.com/q/53116799)
    * Will I screw something up if I remove the 'netty-server' feature from my feature list? UPDATE: well I did it, and the `mn`
      Micronaut command line still seems to work.
