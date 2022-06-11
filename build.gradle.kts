plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
    alias(libs.plugins.detekt)
    alias(libs.plugins.licenser)
    id("maven-publish")
}

group = "org.quiltmc"
version = project.version
val projectVersion = project.version as String

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
subprojects {
    apply(plugin="maven-publish")
    apply(plugin=rootProject.libs.plugins.quilt.loom.get().pluginId)

    dependencies {
        minecraft(rootProject.libs.minecraft)
        mappings(loom.layered {
            addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
        })

        modImplementation(rootProject.libs.quilt.loader)

        modImplementation(rootProject.libs.qsl)
    }

    tasks.remapJar {
        archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
        dependsOn(tasks.remapSourcesJar)
    }
    tasks.remapSourcesJar {
        archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
    }
    java {

        withSourcesJar()
    }

    publishing {
        publications {
            if (project.name != "wrapper") {
                create<MavenPublication>("Maven") {
                    groupId = "org.quiltmc.quilt-kotlin-libraries"
                    artifactId = "quilt-kotlin-libraries-${project.name}"
                    if (project.name == "fatjar") {
                        artifactId = "quilt-kotlin-libraries"
                    }
                    version = projectVersion
                    if (System.getenv("SNAPSHOTS_URL") != null && System.getenv("MAVEN_URL") == null) {
                        version += "-SNAPSHOT"
                    }
                    artifact(tasks.remapSourcesJar.get().archiveFile) {
                        builtBy(tasks.remapSourcesJar)
                    }
                }
            }
        }
        repositories {
            mavenLocal()
            if (System.getenv("MAVEN_URL") != null) {
                maven {
                    setUrl(System.getenv("MAVEN_URL"))
                    credentials {
                        username = System.getenv("MAVEN_USERNAME")
                        password = System.getenv("MAVEN_PASSWORD")
                    }
                    name = "Maven"
                }
            } else if (System.getenv("SNAPSHOTS_URL") != null) {
                maven {
                    setUrl(System.getenv("SNAPSHOTS_URL"))
                    credentials {
                        username = System.getenv("SNAPSHOTS_USERNAME")
                        password = System.getenv("SNAPSHOTS_PASSWORD")
                    }
                    name = "Maven"
                }
            }
        }
    }
}

tasks.remapJar {
    archiveBaseName.set("quilt-kotlin-libraries")
}