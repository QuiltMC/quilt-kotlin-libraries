plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
    alias(libs.plugins.detekt)
    alias(libs.plugins.licenser)
}

group = "org.quiltmc"
version = project.version

repositories {
    mavenCentral()
}

dependencies {
    minecraft(rootProject.libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
    })

    modImplementation(rootProject.libs.quilt.loader)

    modImplementation(rootProject.libs.qsl)
}

allprojects {
    apply(plugin=rootProject.libs.plugins.kotlin.get().pluginId)
    apply(plugin=rootProject.libs.plugins.detekt.get().pluginId)
    apply(plugin=rootProject.libs.plugins.licenser.get().pluginId)

    repositories {
        mavenCentral()
    }
    detekt {
        config = files("${rootProject.projectDir}/codeformat/detekt.yml")
    }
    license {
        rule(file("${rootProject.projectDir}/codeformat/HEADER"))
        include("**/*.kt")
    }
    kotlin {
        // Enable explicit API mode, as this is a library
        explicitApi()
    }
    tasks.processResources {
        inputs.property("version", version)
        filesMatching("quilt.mod.json") {
            expand(Pair("version", version))
        }
    }
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}