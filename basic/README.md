# basic

This is a "Hello World" Micronaut application.

## Description

An effective way to start learning a new framework is with a basic example. This is a so-called "Hello World" program
implemented in Java and the [Micronaut](https://micronaut.io/). I scaffolded this with the Micronaut [`mn` CLI](https://docs.micronaut.io/latest/guide/index.html#buildCLI)
and adapted it from there.

## Instructions

1. Use Java 17
2. Build and run the app:
   * `./gradlew run`
3. Build and run the tests:
   * `./gradlew test`
4. Build the app distribution and then run it:
   * `./gradlew installDist`
   * `./build/install/basic/bin/basic` 

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

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Convert to Gradle Kotlin DSL
* DONE Remove web server and just make the app say "hello world"
* DONE Upgrade to Java 11
* GitHub Actions CI build
* DONE Upgrade to Micronaut 3
