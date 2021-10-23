plugins {
    java
    application
}

repositories {
    mavenCentral()
}

val micronautVersion = "3.1.1" // Micronaut releases: https://github.com/micronaut-projects/micronaut-core/releases
val logbackVersion = "1.2.6" // Logback releases: http://logback.qos.ch/news.html

dependencies {
    annotationProcessor("io.micronaut:micronaut-inject-java:$micronautVersion")
    implementation("io.micronaut:micronaut-inject:$micronautVersion")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
}

application {
    mainClass.set("dgroomes.beans.BeansApplication")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
