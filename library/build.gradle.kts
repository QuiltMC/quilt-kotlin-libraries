fun DependencyHandlerScope.includeApi(dependency: Any) {
    include(dependency)?.let { api(it) }
}

dependencies {
    implementation(project(":core"))
    includeApi(rootProject.libs.coroutines.core.get())
    includeApi(rootProject.libs.coroutines.jvm.get())
    includeApi(rootProject.libs.coroutines.jdk8.get())
    includeApi(rootProject.libs.serialization.core.get())
    includeApi(rootProject.libs.serialization.json.get())
    includeApi(rootProject.libs.serialization.cbor.get())
    includeApi(rootProject.libs.atomic.get())
    includeApi(rootProject.libs.datetime.get())
}