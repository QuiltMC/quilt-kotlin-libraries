plugins {
    id("org.quiltmc.loom")
}

fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { api(it) }
}

dependencies {
    includeApi(project(":core"))
    includeApi(project(":library"))
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}

