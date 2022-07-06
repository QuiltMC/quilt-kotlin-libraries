package org.quiltmc.qkl.wrapper.minecraft.brigadier

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext

/**
 * Accessors are defined as extensions methods on the reader
 * allowing reading of arguments with specific [descriptors][argumentDescriptor]
 * from [context]s with specific source types [S]
 */
public class ArgumentReader<S, out D : ArgumentDescriptor<*>>(
    public val context: CommandContext<S>,
    public val name: String,
    public val argumentDescriptor: D
)

public interface ArgumentDescriptor<A : ArgumentType<*>>

public class DefaultArgumentDescriptor<T : ArgumentType<*>> : ArgumentDescriptor<T>


/**
 * Casts the context to a different source, assuming that
 * the source is not used, so the source object itself
 * will never be cast and invalid casts will not throw.
 *
 * This is needed due to MC arg types requiring
 * a specific command source to resolve, even though
 * they have no need for it at all.
 *
 * @see net.minecraft.command.argument.NumberRangeArgumentType.FloatRangeArgumentType.getRangeArgument
 *
 * @author Cypher121
 */
@Suppress("UNCHECKED_CAST")
@PublishedApi
internal fun <S> CommandContext<*>.assumeSourceNotUsed(): CommandContext<S> =
    this as CommandContext<S>