package org.quiltmc.qkl.wrapper.minecraft.text.mixin;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Style.class)
public interface StyleAccessor {
    @Invoker("<init>")
    static Style create(
            @Nullable TextColor textColor,
            @Nullable Boolean boolean_,
            @Nullable Boolean boolean2,
            @Nullable Boolean boolean3,
            @Nullable Boolean boolean4,
            @Nullable Boolean boolean5,
            @Nullable ClickEvent clickEvent,
            @Nullable HoverEvent hoverEvent,
            @Nullable String string,
            @Nullable Identifier identifier
    ) {
        throw new UnsupportedOperationException();
    }
}
