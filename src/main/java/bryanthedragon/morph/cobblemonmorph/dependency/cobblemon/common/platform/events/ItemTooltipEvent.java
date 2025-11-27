/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemTooltipEvent {
    @NotNull
    private final ItemStack stack;
    @NotNull
    private final TooltipFlag context;
    @NotNull
    private final List<Component> lines;

    public ItemTooltipEvent(@NotNull ItemStack stack, @NotNull TooltipFlag context, @NotNull List<Component> lines) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter(lines, (String)"lines");
        this.stack = stack;
        this.context = context;
        this.lines = lines;
    }

    @NotNull
    public final ItemStack getStack() {
        return this.stack;
    }

    @NotNull
    public final TooltipFlag getContext() {
        return this.context;
    }

    @NotNull
    public final List<Component> getLines() {
        return this.lines;
    }

    @NotNull
    public final ItemStack component1() {
        return this.stack;
    }

    @NotNull
    public final TooltipFlag component2() {
        return this.context;
    }

    @NotNull
    public final List<Component> component3() {
        return this.lines;
    }

    @NotNull
    public final ItemTooltipEvent copy(@NotNull ItemStack stack, @NotNull TooltipFlag context, @NotNull List<Component> lines) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter(lines, (String)"lines");
        return new ItemTooltipEvent(stack, context, lines);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static /* synthetic */ ItemTooltipEvent copy$default(ItemTooltipEvent itemTooltipEvent, ItemStack itemStack, TooltipFlag tooltipFlag, List list, int n, Object object) {
        if ((n & 1) != 0) {
            itemStack = itemTooltipEvent.stack;
        }
        if ((n & 2) != 0) {
            tooltipFlag = itemTooltipEvent.context;
        }
        if ((n & 4) != 0) {
            list = itemTooltipEvent.lines;
        }
        return itemTooltipEvent.copy(itemStack, tooltipFlag, list);
    }

    @NotNull
    public String toString() {
        return "ItemTooltipEvent(stack=" + this.stack + ", context=" + this.context + ", lines=" + this.lines + ")";
    }

    public int hashCode() {
        int result = this.stack.hashCode();
        result = result * 31 + this.context.hashCode();
        result = result * 31 + ((Object)this.lines).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ItemTooltipEvent)) {
            return false;
        }
        ItemTooltipEvent itemTooltipEvent = (ItemTooltipEvent)other;
        if (!Intrinsics.areEqual((Object)this.stack, (Object)itemTooltipEvent.stack)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.context, (Object)itemTooltipEvent.context)) {
            return false;
        }
        return Intrinsics.areEqual(this.lines, itemTooltipEvent.lines);
    }
}

