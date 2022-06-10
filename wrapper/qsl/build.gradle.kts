plugins {
    id("org.quiltmc.loom")
}

dependencies {
    minecraft(rootProject.libs.minecraft)
    mappings(loom.layered {
        addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
    })

    modImplementation(rootProject.libs.quilt.loader)

    modImplementation(rootProject.libs.qsl)

    implementation(project(":core"))
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries-${name}")
}