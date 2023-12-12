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
        annotations("dgroomes.sql.*")
    }
}

dependencies {
    // Micronaut core stuff
    implementation("io.micronaut:micronaut-runtime")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")

    // Micronaut Data and Micronaut JDBC stuff
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.postgresql:postgresql")
}

application {
    mainClass.set("dgroomes.sql.SqlApplication")
}
