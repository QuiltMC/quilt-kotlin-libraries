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

### AbstractEncoder and AbstractDecoder

`DynamicEncoder` and `DynamicDecoder` inherit from their respective abstract classes,
as the interfaces are not considered safe for direct inheritance at current stage of
custom format support. While QKL has full control of the ktx.serialization version it
bundles and would catch any changes at compile-time, using abstract versions should
give more freedom on implementing new experimental features or leaving them to defaults.

The abstract base classes also share logic between top-level and structure element encoding,
significantly reducing code duplication. As a negative effect, they require passing
state between shared and element-specific methods. Element-specific methods are mapped to equivalent
methods in `SerializationState` subclasses:

* `AbstractEncoder#encodeElement(descriptor): Boolean` => 
    `StructuredEncoderState#beforeStructureElement(descriptor): ElementOptions?` 
    (`null` return interpreted as false, i.e. skip encoding element)
* `AbstractDecoder#decodeElementIndex(descriptor): Int` => 
  `DecoderState#getNextIndex(descriptor): Int`
  * Magic number `CompositeDecoder.DECODE_DONE` used identically
  * Magic number `CompositeDecoder.UNKNOWN_NAME` returned only when no additional debug info
    can be added to the exception, as the default exception does not include any useful data

Both methods throw exceptions (causing the codec to immediately return a failure `DataResult`)
if called on a non-structural state.

Individual states must store their own data between element-specific calls and encoding calls,
with the exception of `ElementOptions` returned by encoder states.

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

* A serializer cannot call `decodeNotNullMark`, `decodeNull`, and their encoding counterparts unless
  `descriptor.isNullable` is true for that serializer.

* Calls to nullable encoding methods follow the following structure, as specified in documentation
  for `Decoder#decodeNotNullMark` and `Encoder#encodeNotNullMark`:
```kotlin
//decoding
if (decodeNotNullMark()) {
    //decode underlying value here
} else {
    decodeNull()
}

//encoding
if (value != null) {
  encodeNotNullMark()
  //encode underlying value here
} else {
    encodeNull()
}
```

### Debug traces

Decoders and encoders attempt to track the position in the object where the error occurred
to provide better debugging info if an error occurs. To preserve accuracy, each state
should keep track of the element currently being serialized (e.g. current index in list or key in map),
and only modify it once the next element is selected. Usually this is done in `DecoderState#getNextIndex`
or `StructuredEncoderState#beforeStructureElement`, with the exception of collection states (see below).

Nullable states do not provide a trace element, as their structure is Ops implementation-specific.

### Collection states

Collection decoder states make use of the (currently experimental) `decodeSequentially` method,
which allows serializers to bypass checking indices of every list and map element, instead only
calling `decodeCollectionSize` and assuming the elements are returned in order. However, this 
leads to an undesired interaction with `Decoder#decodeElementIndex` and therefore `DecoderState#getNextIndex`:

* If sequential decoding is used, calls to `getNextIndex` are skipped entirely
* But, there is no strong guarantee that returning `true` from `decodeSequentially` will enable
  sequential decoding

Due to the above, collection states should always return the correct index from
`getNextIndex` but modify it at the start of `getElement` instead. That way both
approaches are supported while preserving the correct debug trace.
