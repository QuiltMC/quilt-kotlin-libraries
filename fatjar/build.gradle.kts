plugins {
    id("org.quiltmc.loom")
}

dependencies {
    include(project(":core"))
    include(project(":wrapper:qsl"))
    include(project(":wrapper:minecraft"))
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}