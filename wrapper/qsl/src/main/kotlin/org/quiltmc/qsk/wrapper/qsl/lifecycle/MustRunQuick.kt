package org.quiltmc.qsk.wrapper.qsl.lifecycle

/**
 * If an event is marked by this annotation, subscribers should
 * run their code in as little time as possible due to hot code paths.
 */
@RequiresOptIn(
    message = "This event is marked as hot, and should be run in a tight loop.",
    level = RequiresOptIn.Level.WARNING
)
public annotation class MustRunQuick
