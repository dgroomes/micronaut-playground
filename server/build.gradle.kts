import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

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
        annotations("dgroomes.basic.*")
    }
}

val zstdJniVersion = "1.5.2-1" // zstd-jni releases: https://github.com/luben/zstd-jni/tags
val brotliVersion = "1.6.0" // Brotli4j releases: https://github.com/hyperxpro/Brotli4j/releases
val operatingSystem: OperatingSystem = DefaultNativePlatform.getCurrentOperatingSystem()

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
    runtimeOnly("ch.qos.logback:logback-classic")

    // Netty does detection at bootstrap-time for the presence of these Zstandard and Brotli Java libraries. If they are
    // present, then they will be supported as compression options.
    runtimeOnly("com.github.luben:zstd-jni:$zstdJniVersion")
    runtimeOnly("com.aayushatharva.brotli4j:brotli4j:$brotliVersion")
    runtimeOnly("com.aayushatharva.brotli4j:${determineBrotliDependencyForPlatform()}:$brotliVersion")
}

application {
    mainClass.set("dgroomes.server.BasicApplication")
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

/**
 * @return the name of the platform-specific Brotli artifact. For example, "native-osx-x86_64", "native-windows-x86_64"
 */
fun determineBrotliDependencyForPlatform() = when {
    operatingSystem.isWindows -> "native-windows-x86_64"
    operatingSystem.isMacOsX -> "native-osx-x86_64"
    operatingSystem.isLinux -> when {
        DefaultNativePlatform.getCurrentArchitecture().isArm -> "native-linux-aarch64"
        else -> "native-linux-x86_64"
    }
    else -> throw GradleException("Platform not supported by Brotli: ${operatingSystem.displayName}")
}
