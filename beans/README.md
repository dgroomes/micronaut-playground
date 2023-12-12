# beans

This project illustrates Micronaut bean concepts like lifecycle scopes and the `BeanContext`.


## Instructions

Follow these instructions to build and run the program.

1. Use Java 21
2. Build and run the app:
   * ```shell
     ./gradlew run
     ```
   * Notice how the program behavior is different for `Singleton`-scoped bean than for the `Prototype`-scoped bean. The
     output will be something like this:
     ```text
     [TimeArchiver#constructor] [identity=570794077 type=class dgroomes.beans.TimeArchiver]
     [BeansApplication#run] [timeArchiver identity=570794077 type=class dgroomes.beans.TimeArchiver] Found time archiver: TimeArchive{archivedTime=12:07:32AM} [identity=570794077 type=class dgroomes.beans.TimeArchiver]
     [BeansApplication#run] [timeArchiver identity=570794077 type=class dgroomes.beans.TimeArchiver] Found time archiver: TimeArchive{archivedTime=12:07:32AM} [identity=570794077 type=class dgroomes.beans.TimeArchiver]
     [BeansApplication#run] [timeArchiver identity=570794077 type=class dgroomes.beans.TimeArchiver] Found time archiver: TimeArchive{archivedTime=12:07:32AM} [identity=570794077 type=class dgroomes.beans.TimeArchiver]
     
     ... omitted ...
     
     [TimeArchiver#constructor] [identity=1107985860 type=class dgroomes.beans.TimeArchiver]
     [BeansApplication#run] [timeArchiver identity=1107985860 type=class dgroomes.beans.TimeArchiver] Found time archiver: TimeArchive{archivedTime=12:07:35AM} [identity=1107985860 type=class dgroomes.beans.TimeArchiver]
     [TimeArchiver#constructor] [identity=473524237 type=class dgroomes.beans.TimeArchiver]
     [BeansApplication#run] [timeArchiver identity=473524237 type=class dgroomes.beans.TimeArchiver] Found time archiver: TimeArchive{archivedTime=12:07:36AM} [identity=473524237 type=class dgroomes.beans.TimeArchiver]
     [TimeArchiver#constructor] [identity=1652764753 type=class dgroomes.beans.TimeArchiver]
     [BeansApplication#run] [timeArchiver identity=1652764753 type=class dgroomes.beans.TimeArchiver] Found time archiver: TimeArchive{archivedTime=12:07:37AM} [identity=1652764753 type=class dgroomes.beans.TimeArchiver]
     ```
  

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Use `BeanContext`
* [x] DONE Can I strip out most of the Micronaut framework-iness (micronaut-cli.yml, Gradle plugin) and just use Micronaut
  as a library (including its required annotation processing)? With this "beans" sub-project I want to focus on just
  that. All other cruft detracts from the illustration.
* [x] DONE Compare and contrast beans with different life-cycle scopes: Singleton (easy!), Prototype, and the RefreshableScope (bewildering).
