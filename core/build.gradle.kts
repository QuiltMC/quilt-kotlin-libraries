fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { api(it) }
}

dependencies {
    val kotlinVersion = rootProject.libs.versions.kotlin.get()
    includeApi(kotlin("stdlib", kotlinVersion))
    includeApi(kotlin("stdlib-jdk7", kotlinVersion))
    includeApi(kotlin("stdlib-jdk8", kotlinVersion))
    includeApi(kotlin("reflect", kotlinVersion))
}