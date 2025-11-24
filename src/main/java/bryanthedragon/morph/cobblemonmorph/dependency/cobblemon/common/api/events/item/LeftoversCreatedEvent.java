/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/events/item/LeftoversCreatedEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/world/item/ItemStack;", "leftovers", "Lnet/minecraft/world/item/ItemStack;", "getLeftovers", "()Lnet/minecraft/world/item/ItemStack;", "setLeftovers", "(Lnet/minecraft/world/item/ItemStack;)V", "Lnet/minecraft/server/level/ServerPlayer;", "playerEntity", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayerEntity", "()Lnet/minecraft/server/level/ServerPlayer;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;)V", "common"})
public final class LeftoversCreatedEvent
extends Cancelable {
    @NotNull
    private final ServerPlayer playerEntity;
    @NotNull
    private ItemStack leftovers;

    public LeftoversCreatedEvent(@NotNull ServerPlayer playerEntity, @NotNull ItemStack leftovers) {
        Intrinsics.checkNotNullParameter((Object)playerEntity, (String)"playerEntity");
        Intrinsics.checkNotNullParameter((Object)leftovers, (String)"leftovers");
        this.playerEntity = playerEntity;
        this.leftovers = leftovers;
    }

    @NotNull
    public final ServerPlayer getPlayerEntity() {
        return this.playerEntity;
    }

    @NotNull
    public final ItemStack getLeftovers() {
        return this.leftovers;
    }

    public final void setLeftovers(@NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
        this.leftovers = itemStack;
    }
}

