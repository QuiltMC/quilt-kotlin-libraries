# QKL: Serialization

Author: June (Cypher121)

## Summary

The serialization package allows for minimum-boilerplate creation of codecs through use of the 
[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) library and its compiler plugin.
Additionally, it allows for extensive configuration of encoding logic, allowing the same
serializable class to produce codecs that prioritize ease of writing by hand or optimal space usage 
for purposes such as networking, save-data storage, or registry entry attachments.

## Usage

### Before use

The use of serialization requires applying the `kotlinx.serialization` Gradle plugin.
The library itself is bundled with QKL and does not need to be added manually.

For general instructions on using the serialization library, 
see [its documentation](https://kotlinlang.org/docs/serialization.html).
Creation of generated codecs follows the basic patterns, such as usage of 
Serializable, SerialName, Polymorphic, Contextual, and other annotations.

### Creating a CodecFactory

The starting-off point of QKL Serialization is `CodecFactory`, which can be used as follows:

```kotlin
import org.quiltmc.qkl.wrapper.minecraft.serialization.CodecFactory

@Serializable
data class ExampleClass(
    val exampleField: Int
)

//using the default settings
val codec1: Codec<ExampleClass> = CodecFactory.create()

//using custom settings
val customFactory = CodecFactory {
    encodeDefaults = true
    printErrorStackTraces = true
    
    polymorphism {
        classDiscriminator = "class"
    }
}

val codec2 = customFactory.create<ExampleClass>()

//overriding settings from an existing factory 
val otherFactory = CodecFactory(from = customFactory) {
    useClassPropertyIndices = true
}
```

The detailed descriptions of all available options can be found in 
the documentation for `CodecOptionsBuilder`.

### Using codecs in serializable classes

Often it is necessary or desirable to use a type that has an associated codec but no serializer.
This could be a Minecraft class like `Identifier` or even a type with an associated registry.
To do so, the type has to be marked as `@Contextual` in the serializable class, and the associated
codec must be added to the factory as follows:

```kotlin
@Serializable
data class CodecExampleClass(
    @Contextual val id: Identifier,
    val blockCounts: Map<@Contextual Block, Int>
)

val factoryWithCodecs = CodecFactory {
    codecs {
        //codecs can be named for better error messages
        named(Identifier.CODEC, "Identifier")
        
        //registry codecs are retrieved and named automatically
        registry(Registry.BLOCK)
    }
}

val codec = factoryWithCodecs.create<CodecExampleClass>()
```

### Serial annotations

Some factory options can be overridden for specific classes by use of annotations:

```kotlin
//CodecSerializable and CodecSerializable.Polymorphic 
//can be used instead of Serializable

//encodes the field regardless of factory settings
@CodecSerializable(
    encodeDefaults = TriState.TRUE
)
data class DefaultsExample(
    val field: Int = 0
)

//always encodes as a plain string
@CodecSerializable(
    useInlineWrapper = TriState.FALSE
)
@JvmInline
value class InlineExample(val value: String)

//always encodes as { "actual_class": "...", "value": { ... } }
@CodecSerializable.Polymorphic(
    classDiscriminator = "actual_class",
    flatten = TriState.FALSE
)
sealed class PolymorphicExample

@Serializable
class MapExample(
    //if the property is a map, always encodes it as a list of entries
    @CodecEntryListMap val map: Map<Pair<Int, Int>, String>
)
```

### Extended compatibility with custom DynamicOps

Codec serializers function with any instance of DynamicOps by default. However,
it can also make use of shortcuts and optimizations for specific formats, such as those
known to support numeric/boolean or even map/list keys, or formats (such as JSON) that
can properly encode a null value. To add extended support to custom instances
of DynamicOps (your own or, using mixin, other mods'), implement `ExtendedDynamicOps<T>`
on the correct instance of `DynamicOps<T>`.


## Implementation

Information below describes the internal implementation of the serialization package
and is intended for contributors and maintainers of the library.

### Codec adapters

Codecs used in a CodecFactory have to be wrapped as a contextual KSerializer.
The wrapper (`CodecSerializerAdapter`) simply casts the provided encoder to
`DynamicEncoder` (likewise for decoder) and extracts the underlying DynamicOps 
to invoke the codec directly.

The descriptor for codec serializers contains a marker annotation signaling the encoder/decoder
to not use it for type introspection, including not checking if its SerialKind is allowed as a map key.

### State stack

To reduce code duplication, encoding/decoding of individual values is separated from
traversal of the serialized type's structure as much as possible. This allows for
usage of the same primitive handling methods in any structure.

The separation is not perfect, mainly due to map keys only supporting strings by default,
as well as the need to pass some properties down into child states. To do so, states provide
`ElementOptions` instances. `DecoderState` instances simply provide a pair of an element and
options for its decoding, while `EncoderState` instances either provide one as a property
in case of `SingleValueState` as there is only one element, or as the return value of
`beforeStructureElement` in case of `StructuredEncoderState`.

### Wrapper states

Some states act as wrappers that represent a property of the serializer rather than
a structure in the encoded value. Those wrappers take `ElementOptions` and return them
later for their own elements:

* Nullable states: as it's unknown whether the value will be wrapped (e.g. NBT) or not (e.g. JSON),
  it's assumed that it is not wrapped and restrictions to the parent apply to the element.

* Polymorphic states: if flattened encoding is turned on, the value element is not wrapped and
  uses parent options.

* Inline states: if the inline wrapper is not used, the element uses parent options.

### Descriptor structure assumptions

Due to the experimental nature of custom formats, some descriptor info cannot be retrieved
without assuming a certain structure followed by those descriptors:

* `PolymorphicKind` descriptors are expected to have two elements: type name and value.
  If the descriptor for value (index 1) has any elements, those elements are known subclasses
  of the base polymorphic type (format used by standard serializers for sealed classes).

* `StructureKind.MAP` descriptors are expected to have two elements: key and value descriptors.
