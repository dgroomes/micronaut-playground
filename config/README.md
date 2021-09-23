# config

NOT YET FULLY IMPLEMENTED

This sub-project showcases configuration features of Micronaut like environments and externalized configuration.

## Instructions

* Use Java 11
* Build and run the app:
    * `./gradlew run`

## Referenced materials

* [Micronaut docs: "Externalized Configuration with PropertySources"](https://docs.micronaut.io/2.3.0/guide/index.html#propertySource)

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* IN PROGRESS Set a Java system property in the main method before starting the Micronaut app ("context" object). The Micronaut app
  should reference an application property that gets its value from the system property.
* Similar to the Java system property idea, do something with environment variables.
