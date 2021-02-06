# basic

WORK IN PROGRESS

This sub-project is a "hello world" Micronaut application.

## Description

I scaffolded this with the Micronaut [`mn` CLI](https://docs.micronaut.io/latest/guide/index.html#buildCLI).

## Instructions

* Use Java 11
* Build and run the app:
  * `./gradlew run`
* Build and run the tests:
  * `./gradlew test`
* ? How to build the application for distribution? Is there an 'install' task? Why use the shadow plugin? 

## Referenced materials

* [Micronaut docs: *User Guide*](https://docs.micronaut.io/2.3.0/guide/index.html)
* [Micronaut docs: *API Reference*](https://docs.micronaut.io/2.3.0/api/index.html)
* [Micronaut docs: *Configuration Reference*](https://docs.micronaut.io/2.3.0/guide/configurationreference.html)
* [Micronaut docs: *Micronaut Guides*](https://guides.micronaut.io/index.html)
* [Micronaut docs: *HTTP Client documentation*](https://docs.micronaut.io/latest/guide/index.html#httpClient)
* [StackOverflow question/answer: Micronaut features lists cannot be changed by the CLI](https://stackoverflow.com/q/53116799)
  * Will I screw something up if I remove the 'netty-server' feature from my feature list? 

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Convert to Gradle Kotlin DSL
* Remove web server and just make the app say "hello world"
* DONE Upgrade to Java 11
