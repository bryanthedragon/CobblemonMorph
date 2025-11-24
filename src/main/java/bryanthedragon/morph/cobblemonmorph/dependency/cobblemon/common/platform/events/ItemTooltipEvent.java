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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0004\b!\u0010\"J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ4\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u00052\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\r\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001b\u001a\u0004\b\u001c\u0010\u0007R\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001d\u001a\u0004\b\u001e\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001f\u001a\u0004\b \u0010\u0004\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/platform/events/ItemTooltipEvent;", "", "Lnet/minecraft/world/item/ItemStack;", "component1", "()Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/world/item/TooltipFlag;", "component2", "()Lnet/minecraft/world/item/TooltipFlag;", "", "Lnet/minecraft/network/chat/Component;", "component3", "()Ljava/util/List;", "stack", "context", "lines", "copy", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/TooltipFlag;Ljava/util/List;)Lcom/cobblemon/mod/common/platform/events/ItemTooltipEvent;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/item/TooltipFlag;", "getContext", "Ljava/util/List;", "getLines", "Lnet/minecraft/world/item/ItemStack;", "getStack", "<init>", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/TooltipFlag;Ljava/util/List;)V", "common"})
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

