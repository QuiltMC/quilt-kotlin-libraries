plugins {
    id("org.quiltmc.loom")
}

fun DependencyHandlerScope.includeProject(projectId: String) {
    include(modApi(project(projectId, configuration = "namedElements"))!!)
}

dependencies {
    includeProject(":core")
    includeProject(":wrapper:qsl")
    includeProject(":wrapper:minecraft")
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}