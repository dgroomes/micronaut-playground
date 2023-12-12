plugins {
    // Micronaut Gradle plugin releases: https://github.com/micronaut-projects/micronaut-gradle-plugin/releases
    id("io.micronaut.application") version "3.2.2"
}

repositories {
    mavenCentral()
}

micronaut {
    version("3.3.4") // Micronaut releases: https://github.com/micronaut-projects/micronaut-core/releases
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dgroomes.serversiderendering.*")
    }
}

dependencies {
    // Micronaut projects like this one will print warnings during compilation like
    //
    //   warning: unknown enum constant When.MAYBE
    //     reason: class file for javax.annotation.meta.When not found
    //
    // These warnings started with the release of Micronaut 3.0.0. To suppress the warnings you need to add a compile-only
    // dependency on findbugs. See the note in the "Nullable Annotations" section under the "3.0.0" section under the
    // "20.5 Breaking Changes" section in the Micronaut docs: https://docs.micronaut.io/latest/guide/#breaks
    compileOnly("com.google.code.findbugs:jsr305")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("dgroomes.serversiderendering.ServerSideRenderingApplication")
}
