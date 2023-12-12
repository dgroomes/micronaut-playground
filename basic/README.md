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
     ```text
     $ ./build/install/basic/bin/basic
     00:36:42.830 [main] INFO  i.m.c.DefaultApplicationContext$RuntimeConfiguredEnvironment - Established active environments: [cli]
     00:36:42.875 [main] INFO  d.basic.BasicApplication$MyFactory - Hello world!
     ```


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Convert to Gradle Kotlin DSL
* [x] DONE Remove web server and just make the app say "hello world"
* [x] DONE Upgrade to Java 11
* [ ] GitHub Actions CI build
* [x] DONE Upgrade to Micronaut 3
* [x] DONE Upgrade to Micronaut 4
   * See [migration guide](https://micronaut.io/2023/05/09/upgrade-to-micronaut-framework-4-0-0/)
   * See the [OpenRewrite *Migrate to Micronaut 4 from Micronaut 3* recipe](https://docs.openrewrite.org/running-recipes/popular-recipe-guides/migrate-to-micronaut-4-from-micronaut-3) 
   * Unfortunately, the OpenRewrite plugin only removed the "jansi" part but didn't get the SnakeYAML dependency, or the
     Micronaut Validation change. I suppose it would be hard to do non-actual-code changes like that.


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
