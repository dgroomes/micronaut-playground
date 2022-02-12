# beans

This project illustrates Micronaut bean concepts like lifecycle scopes and the `BeanContext`.

## Instructions

Follow these instructions to build and run the program.

1. Use Java 17
2. Build and run the app:
   * `./gradlew run`
   * Notice how the program behavior is different for `Singleton`-scoped bean than for the `Prototype`-scoped bean. The
     output will be something like this:
     ```
     [TimeArchiver#constructor] New instance!
     [BeansApplication#run] Found archived time: 01:52:01PM
     [BeansApplication#run] Found archived time: 01:52:01PM
     [BeansApplication#run] Found archived time: 01:52:01PM
     
     
     [TimeArchiver#constructor] New instance!
     [BeansApplication#run] Found archived time: 01:52:04PM
     [TimeArchiver#constructor] New instance!
     [BeansApplication#run] Found archived time: 01:52:05PM
     [TimeArchiver#constructor] New instance!
     [BeansApplication#run] Found archived time: 01:52:06PM
     ```
  
## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Use `BeanContext`
* DONE Can I strip out most of the Micronaut framework-iness (micronaut-cli.yml, Gradle plugin) and just use Micronaut
  as a library (including its required annotation processing)? With this "beans" sub-project I want to focus on just
  that. All other cruft detracts from the illustration.
* DONE (Unfortunately I can't do `Refreshable` easily because I would have to add `micronaut-runtime` and also
  `ApplicationContext` instead of `BeanContext` which I'm not interested in doing). Compare and contrast beans with
  different life-cycle scopes: Singleton (easy!), Prototype, and the RefreshableScope (bewildering).
