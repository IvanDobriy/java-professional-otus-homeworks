allprojects {
    apply { plugin("java") }
    repositories {
        mavenCentral()
        mavenLocal()
    }


    /**
     * fat jart task for all subprojects
     * WARNING!!! path of main class must be same for all subprojects
     */
    val mainClass = "ru.otus.danilchenko.App"
    val ext = extensions.getByType<JavaPluginExtension>()
    val runtimeClasspath = configurations["runtimeClasspath"]
    plugins.withType(JavaPlugin::class.java) {
        tasks.register<Jar>("fatJar") {
            description = "to create fat jar"
            group = "project_build"

            archiveClassifier = "application"
            archiveBaseName = project.name
            archiveVersion = project.version.toString()

            dependsOn(runtimeClasspath)
            from(ext.sourceSets["main"].output)
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
    }
}