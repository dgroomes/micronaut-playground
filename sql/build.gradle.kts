plugins {
    id("io.micronaut.application") version "2.0.3"
}

val postgresVersion = "42.2.20" // releases: https://jdbc.postgresql.org/ and https://search.maven.org/artifact/org.postgresql/postgresql

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
    annotationProcessor("io.micronaut.data:micronaut-data-processor:3.0.1")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    compileOnly("jakarta.persistence:jakarta.persistence-api:2.2.2")
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
