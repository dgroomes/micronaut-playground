import okhttp3.internal.cacheGet

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
        annotations("dgroomes.basic.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("dgroomes.basic.BasicApplication")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
