# micronaut-playground

📚 Learning and experimenting with the Micronaut framework <https://micronaut.io/>.

> A modern, JVM-based, full-stack framework for building modular, easily testable microservice and serverless
> applications
>
> -- <cite>https://micronaut.io</cite>

## Standalone sub-projects

This repository illustrates different concepts, patterns and examples via standalone sub-projects. Each sub-project is
completely independent of the others and do not depend on the root project. This _standalone sub-project constraint_
forces the sub-projects to be complete and maximizes the reader's chances of successfully running, understanding, and
re-using the code.

The sub-projects include:

### `basic/`

This is a "Hello World" Micronaut CLI program.

See the README in [basic/](basic/).

### `server/`

A "Hello World" Micronaut HTTP web server application that also showcases compression (gzip) and content-negotiation.

See the README in [server/](server/).

### `beans/`

This project illustrates Micronaut bean concepts like lifecycle scopes and the `BeanContext`.

See the README in [beans/](beans/).

### `config/`

This showcases configuration features of Micronaut like environments and externalized configuration.

See the README in [config/](config/). 

### `sql/`

An example Micronaut application that accesses a SQL database using *Micronaut Data*.

See the README in [sql/](sql/). 

## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* DONE Add a server example. I want to showcase a "hello world" endpoint and do gzip compression.
* Track the issue about the Gradle "run" task being cached starting in version 3.0.0 of the Micronaut Gradle plugin.
  * Update: see <https://github.com/micronaut-projects/micronaut-gradle-plugin/issues/385>
