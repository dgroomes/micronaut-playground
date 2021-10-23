plugins {
    id("io.micronaut.application") version "2.0.3"
}

repositories {
    mavenCentral()
}

micronaut {
    processing {
        incremental(true)
        annotations("dgroomes.beans.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("dgroomes.beans.BeansApplication")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
