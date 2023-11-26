plugins {
    kotlin("jvm") version "1.9.0"
}

group = "com.resnik"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.dom4j:dom4j:2.1.4")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}