plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
    alias(libs.plugins.detekt)
}

group = "org.quiltmc"
version = project.version

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
    apply(plugin=rootProject.libs.plugins.detekt.get().pluginId)

    detekt {
        config = files("${rootProject.projectDir}/codeformat/detekt.yml")
    }

    dependencies {
        minecraft(rootProject.libs.minecraft)
        mappings(loom.layered {
            addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
        })

        modImplementation(rootProject.libs.quilt.loader)

        modImplementation(rootProject.libs.qsl)
    }
    tasks.processResources {
        inputs.property("version", version)
        filesMatching("quilt.mod.json") {
            expand(Pair("version", version))
        }
    }
}