plugins {
    java
    application
}

repositories {
    mavenCentral()
}

val micronautVersion = "3.3.4" // Micronaut releases: https://github.com/micronaut-projects/micronaut-core/releases
val logbackVersion = "1.2.11" // Logback releases: http://logback.qos.ch/news.html

dependencies {
    annotationProcessor("io.micronaut:micronaut-inject-java:$micronautVersion")
    implementation("io.micronaut:micronaut-inject:$micronautVersion")
    implementation("io.micronaut:micronaut-runtime:$micronautVersion")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
}

application {
    mainClass.set("dgroomes.beans.BeansApplication")
}
