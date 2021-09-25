plugins {
    id("io.micronaut.application") version "2.0.3"
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
    annotationProcessor("io.micronaut.data:micronaut-data-processor:3.0.1")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut:micronaut-runtime")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("dgroomes.sql.SqlApplication")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
