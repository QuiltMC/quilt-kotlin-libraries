pluginManagement {
    repositories {
        maven {
            name = "Quilt"
            url = uri("https://maven.quiltmc.org/repository/release")
        }
        // Currently needed for Intermediary and other temporary dependencies
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}
rootProject.name = "quilt-kotlin-libraries"

include(":core")
include(":wrapper:main")
include(":wrapper:main:registry")
include(":wrapper:qsl")