import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    // Micronaut Gradle plugin releases: https://github.com/micronaut-projects/micronaut-gradle-plugin/releases
    id("io.micronaut.application") version "3.2.2"
}

repositories {
    mavenCentral()
}

micronaut {
    version("3.3.4") // Micronaut releases: https://github.com/micronaut-projects/micronaut-core/releases
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dgroomes.server.*")
    }
}

val zstdJniVersion = "1.5.2-1" // zstd-jni releases: https://github.com/luben/zstd-jni/tags
val brotliVersion = "1.6.0" // Brotli4j releases: https://github.com/hyperxpro/Brotli4j/releases
val operatingSystem: OperatingSystem = DefaultNativePlatform.getCurrentOperatingSystem()

dependencies {
    // Micronaut projects like this one will print warnings during compilation like
    //
    //   warning: unknown enum constant When.MAYBE
    //     reason: class file for javax.annotation.meta.When not found
    //
    // These warnings started with the release of Micronaut 3.0.0. To suppress the warnings you need to add a compile-only
    // dependency on findbugs. See the note in the "Nullable Annotations" section under the "3.0.0" section under the
    // "20.5 Breaking Changes" section in the Micronaut docs: https://docs.micronaut.io/latest/guide/#breaks
    compileOnly("com.google.code.findbugs:jsr305")
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
