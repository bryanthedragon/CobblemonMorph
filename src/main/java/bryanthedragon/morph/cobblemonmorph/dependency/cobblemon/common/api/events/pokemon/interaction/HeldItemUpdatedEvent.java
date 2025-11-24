/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0011\u001a\u00020\u0005\u0012\u0006\u0010\u0012\u001a\u00020\b\u0012\u0006\u0010\u0013\u001a\u00020\u000b\u0012\u0006\u0010\u0014\u001a\u00020\b\u0012\u0006\u0010\u0015\u001a\u00020\b\u00a2\u0006\u0004\b,\u0010-J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000e\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\u000e\u0010\nJ\u0010\u0010\u000f\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\u000f\u0010\nJN\u0010\u0016\u001a\u00020\u00002\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\b2\b\b\u0002\u0010\u0013\u001a\u00020\u000b2\b\b\u0002\u0010\u0014\u001a\u00020\b2\b\b\u0002\u0010\u0015\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u001a\u001a\u00020\u000b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u00d6\u0003\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u001d\u001a\u00020\u001cH\u00d6\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0010\u0010 \u001a\u00020\u001fH\u00d6\u0001\u00a2\u0006\u0004\b \u0010!R\u0019\u0010\u0010\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\"\u001a\u0004\b#\u0010\u0004R\u0017\u0010\u0013\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010$\u001a\u0004\b%\u0010\rR\u0017\u0010\u0015\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010&\u001a\u0004\b'\u0010\nR\u0017\u0010\u0014\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010&\u001a\u0004\b(\u0010\nR\u0017\u0010\u0012\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010&\u001a\u0004\b)\u0010\nR\u0017\u0010\u0011\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010*\u001a\u0004\b+\u0010\u0007\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/interaction/HeldItemUpdatedEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/world/entity/LivingEntity;", "component1", "()Lnet/minecraft/world/entity/LivingEntity;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component2", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lnet/minecraft/world/item/ItemStack;", "component3", "()Lnet/minecraft/world/item/ItemStack;", "", "component4", "()Z", "component5", "component6", "cause", "pokemon", "originalStack", "decrement", "oldItem", "newItem", "copy", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/ItemStack;ZLnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Lcom/cobblemon/mod/common/api/events/pokemon/interaction/HeldItemUpdatedEvent;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/LivingEntity;", "getCause", "Z", "getDecrement", "Lnet/minecraft/world/item/ItemStack;", "getNewItem", "getOldItem", "getOriginalStack", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lnet/minecraft/world/entity/LivingEntity;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/item/ItemStack;ZLnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", "common"})
public final class HeldItemUpdatedEvent
extends Cancelable {
    @Nullable
    private final LivingEntity cause;
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final ItemStack originalStack;
    private final boolean decrement;
    @NotNull
    private final ItemStack oldItem;
    @NotNull
    private final ItemStack newItem;

    public HeldItemUpdatedEvent(@Nullable LivingEntity cause, @NotNull Pokemon pokemon, @NotNull ItemStack originalStack, boolean decrement, @NotNull ItemStack oldItem, @NotNull ItemStack newItem) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)originalStack, (String)"originalStack");
        Intrinsics.checkNotNullParameter((Object)oldItem, (String)"oldItem");
        Intrinsics.checkNotNullParameter((Object)newItem, (String)"newItem");
        this.cause = cause;
        this.pokemon = pokemon;
        this.originalStack = originalStack;
        this.decrement = decrement;
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    @Nullable
    public final LivingEntity getCause() {
        return this.cause;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final ItemStack getOriginalStack() {
        return this.originalStack;
    }

    public final boolean getDecrement() {
        return this.decrement;
    }

    @NotNull
    public final ItemStack getOldItem() {
        return this.oldItem;
    }

    @NotNull
    public final ItemStack getNewItem() {
        return this.newItem;
    }

    @Nullable
    public final LivingEntity component1() {
        return this.cause;
    }

    @NotNull
    public final Pokemon component2() {
        return this.pokemon;
    }

    @NotNull
    public final ItemStack component3() {
        return this.originalStack;
    }

    public final boolean component4() {
        return this.decrement;
    }

    @NotNull
    public final ItemStack component5() {
        return this.oldItem;
    }

    @NotNull
    public final ItemStack component6() {
        return this.newItem;
    }

    @NotNull
    public final HeldItemUpdatedEvent copy(@Nullable LivingEntity cause, @NotNull Pokemon pokemon, @NotNull ItemStack originalStack, boolean decrement, @NotNull ItemStack oldItem, @NotNull ItemStack newItem) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)originalStack, (String)"originalStack");
        Intrinsics.checkNotNullParameter((Object)oldItem, (String)"oldItem");
        Intrinsics.checkNotNullParameter((Object)newItem, (String)"newItem");
        return new HeldItemUpdatedEvent(cause, pokemon, originalStack, decrement, oldItem, newItem);
    }

    public static /* synthetic */ HeldItemUpdatedEvent copy$default(HeldItemUpdatedEvent heldItemUpdatedEvent, LivingEntity livingEntity, Pokemon pokemon, ItemStack itemStack, boolean bl, ItemStack itemStack2, ItemStack itemStack3, int n, Object object) {
        if ((n & 1) != 0) {
            livingEntity = heldItemUpdatedEvent.cause;
        }
        if ((n & 2) != 0) {
            pokemon = heldItemUpdatedEvent.pokemon;
        }
        if ((n & 4) != 0) {
            itemStack = heldItemUpdatedEvent.originalStack;
        }
        if ((n & 8) != 0) {
            bl = heldItemUpdatedEvent.decrement;
        }
        if ((n & 0x10) != 0) {
            itemStack2 = heldItemUpdatedEvent.oldItem;
        }
        if ((n & 0x20) != 0) {
            itemStack3 = heldItemUpdatedEvent.newItem;
        }
        return heldItemUpdatedEvent.copy(livingEntity, pokemon, itemStack, bl, itemStack2, itemStack3);
    }

    @NotNull
    public String toString() {
        return "HeldItemUpdatedEvent(cause=" + this.cause + ", pokemon=" + this.pokemon + ", originalStack=" + this.originalStack + ", decrement=" + this.decrement + ", oldItem=" + this.oldItem + ", newItem=" + this.newItem + ")";
    }

    public int hashCode() {
        int result = this.cause == null ? 0 : this.cause.hashCode();
        result = result * 31 + this.pokemon.hashCode();
        result = result * 31 + this.originalStack.hashCode();
        int n = this.decrement ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        result = result * 31 + this.oldItem.hashCode();
        result = result * 31 + this.newItem.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HeldItemUpdatedEvent)) {
            return false;
        }
        HeldItemUpdatedEvent heldItemUpdatedEvent = (HeldItemUpdatedEvent)other;
        if (!Intrinsics.areEqual((Object)this.cause, (Object)heldItemUpdatedEvent.cause)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)heldItemUpdatedEvent.pokemon)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.originalStack, (Object)heldItemUpdatedEvent.originalStack)) {
            return false;
        }
        if (this.decrement != heldItemUpdatedEvent.decrement) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.oldItem, (Object)heldItemUpdatedEvent.oldItem)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.newItem, (Object)heldItemUpdatedEvent.newItem);
    }
}

