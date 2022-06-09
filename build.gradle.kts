plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
}

group = "org.quiltmc"

kotlin {
    // Enable explicit API mode, as this is a library
    explicitApi()
}

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin=rootProject.libs.plugins.kotlin.get().pluginId)
    apply(plugin=rootProject.libs.plugins.quilt.loom.get().pluginId)
    dependencies {
        minecraft(rootProject.libs.minecraft)
        mappings(loom.layered {
            addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
        })

        modImplementation(rootProject.libs.quilt.loader)

        modImplementation(rootProject.libs.qsl)
    }
}