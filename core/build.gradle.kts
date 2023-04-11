fun DependencyHandlerScope.includeApi(dependency: Any) {
    api(dependency)
    include(dependency)
}

dependencies {
    val kotlinVersion = rootProject.libs.versions.kotlin.get()

    includeApi(kotlin("stdlib", kotlinVersion))
    includeApi(kotlin("stdlib-jdk7", kotlinVersion))
    includeApi(kotlin("stdlib-jdk8", kotlinVersion))
    includeApi(kotlin("reflect", kotlinVersion))

    includeApi(rootProject.libs.coroutines.core)
    includeApi(rootProject.libs.coroutines.jvm)
    includeApi(rootProject.libs.coroutines.jdk8)
    includeApi(rootProject.libs.serialization.core)
    includeApi(rootProject.libs.serialization.json)
    includeApi(rootProject.libs.serialization.cbor)
    includeApi(rootProject.libs.atomic)
    includeApi(rootProject.libs.datetime)
}