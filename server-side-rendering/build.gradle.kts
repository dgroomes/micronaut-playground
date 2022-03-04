plugins {
    // Micronaut Gradle plugin releases: https://github.com/micronaut-projects/micronaut-gradle-plugin/releases
    id("io.micronaut.application") version "3.2.2"
}

repositories {
    mavenCentral()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dgroomes.serversiderendering.*")
    }
}

dependencies {
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

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
