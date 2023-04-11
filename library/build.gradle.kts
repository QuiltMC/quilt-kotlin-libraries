@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(project(":core"))
    modImplementation(rootProject.libs.qsl)
}