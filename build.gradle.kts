
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.AbstractDokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.Year

@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.quilt.loom)
    alias(libs.plugins.detekt)
    alias(libs.plugins.licenser)
    alias(libs.plugins.git.hooks)
    alias(libs.plugins.dokka)
    `maven-publish`
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

group = "org.quiltmc"
val rootVersion = project.version
val flk_version: String by project
version = project.version.toString() + "+kt." + project.libs.versions.kotlin.orNull + "+flk." + flk_version
val projectVersion = project.version as String + if (System.getenv("SNAPSHOTS_URL") != null && System.getenv("MAVEN_URL") == null) "-SNAPSHOT" else ""

val javaVersion = 17 // The current version of Java used by Minecraft

repositories {
    mavenCentral()
}

fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { api(it) }
}

dependencies {
    includeApi(project(":core"))
    includeApi(project(":library"))
}

allprojects {
    apply(plugin=rootProject.libs.plugins.kotlin.get().pluginId)
    apply(plugin=rootProject.libs.plugins.detekt.get().pluginId)
    apply(plugin=rootProject.libs.plugins.licenser.get().pluginId)
    apply(plugin=rootProject.libs.plugins.dokka.get().pluginId)
    apply(plugin="maven-publish")
    apply(plugin=rootProject.libs.plugins.quilt.loom.get().pluginId)

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
            inputs.property("version", rootVersion)
            filesMatching("quilt.mod.json") {
                expand(
                    "version" to rootVersion,
                    "flk_version" to "$flk_version+kotlin.${project.libs.versions.kotlin.orNull}"
                )
            }
        }

        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = javaVersion.toString()
                languageVersion = rootProject.libs.plugins.kotlin.get().version.strictVersion
            }
        }

        // Every dokka task
        withType<AbstractDokkaTask> {
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                val rootPath = "${rootProject.projectDir.absolutePath}/codeformat/dokka"
                customStyleSheets = file("$rootPath/styles").listFiles()!!.toList()
                customAssets = file("$rootPath/assets").listFiles()!!.toList()
                templatesDir = file("$rootPath/templates")

                footerMessage = "© ${Year.now().value} QuiltMC"
            }

            doLast {
                // Script overriding does not work, so we have to do it manually
                val scriptsOut = outputDirectory.get().resolve("scripts")
                val scriptsIn = file("${rootProject.projectDir}/codeformat/dokka/scripts")
                if (project != rootProject) return@doLast
                scriptsIn.listFiles()!!.forEach {
                    it.copyTo(scriptsOut.resolve(it.name), overwrite = true)
                }
            }
        }

        // Every `dokkaType` and `dokkaTypePartial` task
        withType<AbstractDokkaLeafTask> {
            dokkaSourceSets.configureEach {
                val quiltMaven = "https://maven.quiltmc.org/repository/release/org/quiltmc"

                // QSL
                val qslBaseLink = "$quiltMaven/qsl"
                val qslVersion = rootProject.libs.versions.qsl.get()
                val qslLink = "$qslBaseLink/$qslVersion/qsl-$qslVersion-fat-javadoc.jar"
                externalDocumentationLink("$qslLink/", "$qslLink/element-list")

                // Minecraft (mapped with Quilt mappings)
                val mappingBaseLink = "$quiltMaven/quilt-mappings"
                val mappingVersion = rootProject.libs.versions.quilt.mappings.get()
                val mappingLink = "$mappingBaseLink/$mappingVersion/quilt-mappings-$mappingVersion-javadoc.jar"
                externalDocumentationLink("$mappingLink/", "$mappingLink/element-list")
            }
        }
    }

    group = "org.quiltmc.quilt-kotlin-libraries"
    version = projectVersion

    dependencies {
        minecraft(rootProject.libs.minecraft)
        mappings(
            variantOf(rootProject.libs.quilt.mappings) {
                classifier("intermediary-v2")
            }
        )

        modImplementation(rootProject.libs.quilt.loader)

        modImplementation(rootProject.libs.qsl)
    }

    tasks {
        processResources {
            inputs.property("version", rootProject.version)

            filesMatching("quilt.mod.json") {
                expand(Pair("version", rootProject.version))
            }
        }

        remapJar {
            dependsOn(remapSourcesJar)
        }

        val dokkaJavadocJar by creating(Jar::class.java) {
            group = "documentation"
            archiveClassifier.set("javadoc")
            from(dokkaJavadoc)
        }
    }

    java {
        withSourcesJar()

        sourceCompatibility = JavaVersion.toVersion(javaVersion)
        targetCompatibility = JavaVersion.toVersion(javaVersion)
    }

    publishing {
        publications {
            create<MavenPublication>("Maven") {
                artifactId = project.name
                version = projectVersion

                artifact(tasks.remapSourcesJar.get().archiveFile) {
                    builtBy(tasks.remapSourcesJar)
                    this.classifier = "sources"
                }
                artifact(tasks.remapJar.get().archiveFile) {
                    builtBy(tasks.remapJar)
                }
                artifact(tasks.getByName<Jar>("dokkaJavadocJar").archiveFile) {
                    builtBy(tasks.getByName("dokkaJavadocJar"))
                    this.classifier = "javadoc"
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

subprojects {
    tasks {
        remapJar {
            archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
            dependsOn(remapSourcesJar)
        }

        remapSourcesJar {
            archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
        }

        named<Jar>("dokkaJavadocJar") {
            archiveBaseName.set("quilt-kotlin-libraries-${project.name}")
        }
    }
}

tasks {
    create("dokkaHtmlJar", Jar::class.java) {
        group = "documentation"
        archiveBaseName.set("quilt-kotlin-libraries")
        archiveClassifier.set("dokka")
        from(dokkaHtmlMultiModule.get().outputDirectory)
        duplicatesStrategy = DuplicatesStrategy.FAIL
    }
}

publishing {
    publications {
        getByName<MavenPublication>("Maven") {
            val dokka = rootProject.tasks.getByName<Jar>("dokkaHtmlJar")
            artifact(dokka.archiveFile) {
                builtBy(dokka)
                classifier = "dokka"
            }
        }
    }
}

gitHooks {
    // Before committing, check that licenses are all ready and the detekt checks have passed.
    setHooks(mapOf("pre-commit" to "checkLicenses apiCheck detekt"))
}
