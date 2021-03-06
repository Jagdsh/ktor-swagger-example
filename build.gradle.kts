buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11")
    }
}

val jUnitVersion = "5.3.2"
val mockKVersion = "1.9.3"

plugins {
    kotlin("jvm") version "1.3.11"
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.0.0"
    application
}

tasks.withType<Jar> {
    archiveBaseName.set("ktor-sample-service")
    manifest {
        attributes["Main-Class"] = "example.JsonApplicationKt"
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            events("passed", "failed", "skipped")
        }
    }
}

repositories {
    mavenLocal()
    maven { setUrl("https://dl.bintray.com/kotlin/ktor") }
    jcenter()
}

fun DependencyHandler.ktor(name: String) =
        create(group = "io.ktor", name = name, version = "1.1.1")

dependencies {
    implementation(kotlin("stdlib-jdk8", property("kotlin.version") as String))
    implementation(kotlin("reflect", property("kotlin.version") as String))
    implementation(ktor("ktor-server-netty"))
    implementation(ktor("ktor-locations"))
    implementation(ktor("ktor-server-core"))
    implementation(ktor("ktor-gson"))
    implementation(group = "com.github.ajalt", name = "clikt", version = "1.3.0")
    implementation("de.nielsfalk.ktor:ktor-swagger:0.5.0")
    implementation("net.logstash.log4j:jsonevent-layout:1.7")
    implementation("org.slf4j:slf4j-log4j12:1.7.26")
    implementation("org.slf4j:slf4j-api:1.7.26")

    /* Junit Platform */
    testImplementation(kotlin("test", property("kotlin.version") as String))
    testImplementation(kotlin("test-junit", property("kotlin.version") as String))
    testImplementation(ktor("ktor-server-test-host"))
    testImplementation(ktor("ktor-gson"))
    testImplementation("com.winterbe:expekt:0.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
    testImplementation("io.mockk:mockk:$mockKVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "example.PetsApplicationKt"
}
