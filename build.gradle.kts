import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
    alias(libs.plugins.detekt)
    alias(libs.plugins.licenser)
    alias(libs.plugins.git.hooks)
    `maven-publish`
}

group = "org.quiltmc"
version = project.version
val projectVersion = project.version as String + if (System.getenv("SNAPSHOTS_URL") != null && System.getenv("MAVEN_URL") == null) "-SNAPSHOT" else ""

val javaVersion = 17 // The current version of Java used by Minecraft

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

    tasks {
        processResources {
            inputs.property("version", version)
            filesMatching("quilt.mod.json") {
                expand(Pair("version", version))
            }
        }

        withType<KotlinCompile>() {
            kotlinOptions {
                jvmTarget = javaVersion.toString()
                languageVersion = rootProject.libs.plugins.kotlin.get().version.strictVersion
            }
        }
    }
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = rootProject.libs.plugins.quilt.loom.get().pluginId)

    group = "org.quiltmc.quilt-kotlin-libraries"
    version = projectVersion

    val samples by sourceSets.creating

    dependencies {
        minecraft(rootProject.libs.minecraft)
        mappings(loom.layered {
            addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${rootProject.libs.versions.quilt.mappings.get()}:v2"))
        })

        modImplementation(rootProject.libs.quilt.loader)

        modImplementation(rootProject.libs.qsl)

        // Get samples to depend on main and its dependencies
        val main = sourceSets.main.get()
        "samplesImplementation"(main.output)
        "samplesImplementation"(main.compileClasspath)
    }

    // Fix weird IDEA error when trying to work with samples
    afterEvaluate {
        if (System.getProperty("idea.sync.active") != null) {
            println("Creating sample source set folders to circumvent IDEA error")
            samples.allSource.srcDirs.forEach {
                it.mkdirs()
            }
        }
    }

    tasks {
        remapJar {
            archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
            dependsOn(remapSourcesJar)
        }

        remapSourcesJar {
            archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
        }
    }

    java {
        withSourcesJar()
        withJavadocJar()

        sourceCompatibility = JavaVersion.toVersion(javaVersion)
        targetCompatibility = JavaVersion.toVersion(javaVersion)
    }

    publishing {
        publications {
            if (project.name != "wrapper") {
                create<MavenPublication>("Maven") {
                    artifactId = project.name
                    if (project.name == "fatjar") {
                        artifactId = "quilt-kotlin-libraries"
                    }
                    version = projectVersion

                    artifact(tasks.remapSourcesJar.get().archiveFile) {
                        builtBy(tasks.remapSourcesJar)
                        this.classifier = "sources"
                    }
                    artifact(tasks.remapJar.get().archiveFile) {
                        builtBy(tasks.remapJar)
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

gitHooks {
    // Before committing, check that licenses are all ready and the detekt checks have passed.
    setHooks(mapOf("pre-commit" to "checkLicenses detekt"))
}
