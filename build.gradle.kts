plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
    alias(libs.plugins.detekt)
}

group = "org.quiltmc"

kotlin {
    // Enable explicit API mode, as this is a library
    explicitApi()
}

detekt {
    config = files("./detekt.yml")
}

repositories {
    mavenCentral()
}

// TODO: Move this logic to subprojects
dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${libs.versions.quilt.mappings.get()}:v2"))
    })

    modImplementation(libs.quilt.loader)

    modImplementation(libs.qsl)
}