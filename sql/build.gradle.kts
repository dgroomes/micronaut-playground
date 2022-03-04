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
        annotations("dgroomes.sql.*")
    }
}

dependencies {
    // Micronaut core stuff
    implementation("io.micronaut:micronaut-runtime")
    runtimeOnly("ch.qos.logback:logback-classic")

    // Micronaut Data and Micronaut JDBC stuff
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.postgresql:postgresql")
}

application {
    mainClass.set("dgroomes.sql.SqlApplication")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
