plugins {
    // Micronaut Gradle plugin releases: https://github.com/micronaut-projects/micronaut-gradle-plugin/releases
    id("io.micronaut.application") version "3.2.1"
}

repositories {
    mavenCentral()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dgroomes.config.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("dgroomes.config.ConfigApplication")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    named<JavaExec>("run") {
        // For some reason, the "run" task is being skipped on subsequent executions. Gradle's caching capability knows
        // to skip tasks when their inputs haven't changed, like the dependency resolution and compile tasks. This is a
        // good feature in general. But the "run" task is not a "building" task, it's a "running" task. It doesn't make
        // sense for the run task to be "cached". When we want to run our program, we should be able to run our program.
        // A workaround is to use the "outputs.upToDateWhen" configuration to force Gradle to never cache this task.
        outputs.upToDateWhen { false }
    }
}
