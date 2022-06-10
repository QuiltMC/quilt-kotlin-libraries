plugins {
    id("org.quiltmc.loom")
}

fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { modApi(it) }
}

dependencies {
    minecraft(rootProject.libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
    })

    modImplementation(rootProject.libs.quilt.loader)

    modImplementation(rootProject.libs.qsl)

    val kotlinVersion = rootProject.libs.versions.kotlin.get()
    includeApi(kotlin("stdlib", kotlinVersion))
    includeApi(kotlin("stdlib-jdk8", kotlinVersion))
    includeApi(kotlin("stdlib-jdk7", kotlinVersion))
    includeApi(kotlin("reflect", kotlinVersion))
    includeApi(rootProject.libs.coroutines.core.get())
    includeApi(rootProject.libs.coroutines.jvm.get())
    includeApi(rootProject.libs.coroutines.jdk8.get())
    includeApi(rootProject.libs.serialization.core.get())
    includeApi(rootProject.libs.serialization.json.get())
    includeApi(rootProject.libs.serialization.cbor.get())
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries-${name}")
}