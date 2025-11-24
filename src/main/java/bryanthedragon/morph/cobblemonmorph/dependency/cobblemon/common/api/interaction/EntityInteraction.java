/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J)\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/interaction/EntityInteraction;", "Lnet/minecraft/world/entity/Entity;", "T", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "amount", "", "consumeItem", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;I)V", "entity", "", "onInteraction", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Z", "common"})
public interface EntityInteraction<T extends Entity> {
    public boolean onInteraction(@NotNull ServerPlayer var1, @NotNull T var2, @NotNull ItemStack var3);

    public void consumeItem(@NotNull ServerPlayer var1, @NotNull ItemStack var2, int var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T extends Entity> void consumeItem(@NotNull EntityInteraction<T> $this, @NotNull ServerPlayer player, @NotNull ItemStack stack, int amount) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
            if (!player.m_7500_()) {
                stack.m_41774_(amount);
            }
        }

        public static /* synthetic */ void consumeItem$default(EntityInteraction entityInteraction, ServerPlayer serverPlayer, ItemStack itemStack, int n, int n2, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: consumeItem");
            }
            if ((n2 & 4) != 0) {
                n = 1;
            }
            entityInteraction.consumeItem(serverPlayer, itemStack, n);
        }
    }
}

