fun DependencyHandlerScope.includeApi(dependency: Any) {
    modApi(dependency)
    include(dependency)
}

dependencies {
    includeApi(rootProject.libs.fabric.kotlin)
}