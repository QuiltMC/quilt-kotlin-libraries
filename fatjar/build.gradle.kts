plugins {
    id("org.quiltmc.loom")
}

fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { implementation(it) }
}

dependencies {
    include(modApi(project(":core",              configuration = "namedElements"))!!)
    include(modApi(project(":wrapper:qsl",       configuration = "namedElements"))!!)
    include(modApi(project(":wrapper:minecraft", configuration = "namedElements"))!!)
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}