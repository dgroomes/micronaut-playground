plugins {
    // Micronaut Gradle plugin releases: https://github.com/micronaut-projects/micronaut-gradle-plugin/releases
    id("io.micronaut.application") version "4.2.1"
}

repositories {
    mavenCentral()
}

micronaut {
    version("4.2.1") // Micronaut releases: https://github.com/micronaut-projects/micronaut-core/releases
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dgroomes.config.*")
    }
}

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("dgroomes.config.ConfigApplication")
}
