# Quilt Kotlin Libraries

### What are the Quilt Kotlin Libraries?

Quilt Kotlin Libraries is a wrapper and API provider for Minecraft written in Kotlin, and as such allows modders to 
write their own mods in Kotlin, making use of these libraries.

We provide wrappers for various Minecraft features, for example Registries and Math functions. On top of this we provide
Kotlin wrappers for [QSL](https://github.com/QuiltMC/quilt-standard-libraries) APIs, allowing better Kotlin integration
with QSL features.


### Repository Structure
* `core` contains the language adapter, kotlin standard library and kotlin reflect library
* `library` contains everything else including the rest of the kotlinx stuff brought in by FKL
* The root project is responsible for bundling both the submodules and some required dependencies into a single jar