import com.github.rholder.gradle.task.OneJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.10"
    id("com.github.onslip.gradle-one-jar") version "1.0.5"
}

group = "com.tylerkindy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.google.guava:guava:27.0.1-jre")

    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testCompile("org.assertj:assertj-core:3.11.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.create("oneJar", OneJar::class.java) {
    mainClass = "com.tylerkindy.aoc2018.day1.Day1Kt"
}
