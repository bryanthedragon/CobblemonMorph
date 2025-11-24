/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive;

import kotlin.Metadata;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/item/interactive/InteractiveItem;", "Lnet/minecraft/world/entity/Entity;", "T", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "entity", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "onInteraction", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Z", "common"})
public interface InteractiveItem<T extends Entity> {
    public boolean onInteraction(@NotNull ServerPlayer var1, @NotNull T var2, @NotNull ItemStack var3);
}

