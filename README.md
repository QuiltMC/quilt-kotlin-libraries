# Quilt Kotlin Libraries

#### Note: At the moment, [Quilt Standard Libraries](https://github.com/QuiltMC/quilt-standard-libraries), and as such Quilt Kotlin Libraries, are not read for general use. Please make an issue or talk to the QKL team on discord before writing any PRs.


### What are the Quilt Kotlin Libraries?

Quilt Kotlin Libraries is a wrapper and API provider for Minecraft written in Kotlin, and as such allows modders to 
write their own mods in Kotlin, making use of these libraries.

We provide wrappers for various Minecraft features, for example Registries and Math functions. On top of this we provide
Kotlin wrappers for [QSL](https://github.com/QuiltMC/quilt-standard-libraries) APIs, allowing better Kotlin integration
with QSL features.


### Repository Structure
* The `core` folder. This contains all the core functionality for the Quilt Kotlin Libraries.
* The `fatjar` folder. This contains the means for creating one Quilt Kotlin Libraries Jar that can be added to mods
folders and development environments.
* The `wrapper` folder. This contains all the Kotlin wrappers to both QSL and Minecraft features/functions.