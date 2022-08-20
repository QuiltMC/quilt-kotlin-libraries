plugins {
    id("org.quiltmc.loom")
}

fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { implementation(it) }
}

dependencies {
    includeApi(project(":core"))
    includeApi(project(":wrapper:qsl"))
    includeApi(project(":wrapper:minecraft"))
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}

publishing {
    publications {
        getByName<MavenPublication>("Maven") {
            val dokka = rootProject.tasks.getByName<Jar>("dokkaHtmlJar")
            artifact(dokka.archiveFile) {
                builtBy(dokka)
                classifier = "dokka"
            }
        }
    }
}