# beans

NOT YET FULLY IMPLEMENTED

This project illustrates Micronaut bean concepts like lifecycle scopes and the `BeanContext`.

* Use Java 11
* Have Java 17 installed
  * This version of Java will be used via Gradle's [Toolchains for JVM projects](https://docs.gradle.org/current/userguide/toolchains.html).  
* Build and run the app:
  * `./gradlew run`
  
## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Use `BeanContext`
* DONE Can I strip out most of the Micronaut framework-iness (micronaut-cli.yml, Gradle plugin) and just use Micronaut as a
  library (including its required annotation processing)? With this "beans" sub-project I want to focus on just that. All
  other cruft detracts from the illustration.
* Compare and constrast beans with different life-cycle scopes: Singleton (easy!), Prototype, and the 
  RefreshableScope (bewildering).
