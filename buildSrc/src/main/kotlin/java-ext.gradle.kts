plugins {
    java
}


repositories{
    mavenCentral()
}



java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

val mainClass = "ru.otus.danilchenko.App"
val runtimeClasspath = configurations["runtimeClasspath"]

tasks.register<Jar>("fatJar") {
    description = "to create fat jar"
    group = "project_build"

    archiveClassifier = "application"
    archiveBaseName = project.name
    archiveVersion = project.version.toString()

    dependsOn(runtimeClasspath)
    from(sourceSets["main"].output)
    from({
        runtimeClasspath.filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes("Main-Class" to mainClass)
        attributes("Implementation-Title" to  project.name)
        attributes("Implementation-Version" to project.version)
        attributes("Author" to project.property("author").toString())
    }
}
