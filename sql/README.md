# sql

An example Micronaut application that accesses a SQL database using *Micronaut Data*.

## Instructions

Requires: Java 17, Docker

Follow these instructions to build and run the program:

1. Start the Postgres database:
    * `docker-compose up --detach --force-recreate --renew-anon-volumes`
    * Why is this `up` command so complicated? The combination of `--force-recreate` and `--renew-anon-volumes` has the 
      effect of starting the Postgres database from a clean slate. This is convenient for quick development. You can use
      a shortened version like `docker-compose up -d --force-recreate -V` but that's still long. Consider creating a shell
      alias like `alias dcuf="docker-compose up --detach --force-recreate --renew-anon-volumes"`.
1. Use Java 17
1. Build and run the app:
    * `./gradlew run`
    * Observe program output. It reads from and writes to the Postgres database!
1. Stop the database:
    * `docker-compose down`

## Reference

* [Micronaut Data](https://micronaut-projects.github.io/micronaut-data/latest/guide/)
  > In addition to JPA, Micronaut Data supports the generation of repositories that use native SQL. The implementation is general enough that any transport can use be used for executing the SQL queries, as of this writing JDBC is the only supported implementation for executing SQL queries, however this may change in the future.
* [H2 Database settings](https://www.h2database.com/javadoc/org/h2/engine/DbSettings.html)
  * While H2 is not used in this project. I started with it because the Micronaut Data docs do this.
* [StackOverflow: H2 uppercase/lowercase naming problem resulting in 'Table not found' error](https://stackoverflow.com/a/17925668) 
* [dgroomes/jdbc-playground](https://github.com/dgroomes/jdbc-playground)
  * I incorporated some code into this project from another project of mine: my "jdbc-playground".  

## Notes

This was hard. The Micronaut docs have a good volume of content and really nice features like the code snippets, and even
going so far as to offer alternative code snippets for Gradle vs. Maven, and Java vs. Groovy vs. Kotlin. That is much
appreciated. However, after following the directions precisely, copying code snippets judiciously, reviewing the docs
multiple times, and experimenting with changes here and there, I still could not get up and running with a non-JPA
Micronaut Data "Hello world" example. After some deeper diving, (Micronaut DEBUG logging, breakpoints in the Micronaut code,
executing handwritten SQL statements from the `main` method) I figured out that H2 is changing the case of the table name
and it disagrees with the casing Micronaut is using. See this [StackOverflow answer](https://stackoverflow.com/a/17925668).


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Remove the ID from the observations table. I often use tables without IDs. What changes do I have to make to the app
  code to support a Micronaut Data integration to a table without IDs? I already know this will be a bit awkward because
  I tried it, found [this GitHub issue](https://github.com/micronaut-projects/micronaut-data/issues/1001) and then gave up.
* Write a hand-written SQL query. I often write hand-written SQL queries to do aggregations and sometimes complex operations
  which are not feasible (or even remotely recommended to try) in the ORM layer (I know Micronaut Data says "it is not an ORM"
  but what is it if not an ORM?).
* DONE Clean up the build.gradle.kts file. The explicit version numbers should not be needed.
