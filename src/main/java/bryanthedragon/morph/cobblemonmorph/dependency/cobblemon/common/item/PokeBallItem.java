/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ-\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/item/PokeBallItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "", "isFireproof", "()Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "throwPokeBall", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/server/level/ServerPlayer;)V", "Lnet/minecraft/world/entity/player/Player;", "Lnet/minecraft/world/InteractionHand;", "usedHand", "Lnet/minecraft/world/InteractionResultHolder;", "Lnet/minecraft/world/item/ItemStack;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "pokeBall", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "getPokeBall", "()Lcom/cobblemon/mod/common/pokeball/PokeBall;", "<init>", "(Lcom/cobblemon/mod/common/pokeball/PokeBall;)V", "common"})
public final class PokeBallItem
extends CobblemonItem {
    @NotNull
    private final PokeBall pokeBall;

    public PokeBallItem(@NotNull PokeBall pokeBall) {
        Intrinsics.checkNotNullParameter((Object)pokeBall, (String)"pokeBall");
        super(new Item.Properties());
        this.pokeBall = pokeBall;
    }

    @NotNull
    public final PokeBall getPokeBall() {
        return this.pokeBall;
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand usedHand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)usedHand, (String)"usedHand");
        ItemStack itemStack = player.m_21120_(usedHand);
        if (DistributionUtilsKt.isServerSide(world)) {
            this.throwPokeBall(world, (ServerPlayer)player);
        }
        if (!player.m_150110_().f_35937_) {
            itemStack.m_41774_(1);
        }
        InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19092_((Object)itemStack, (boolean)world.f_46443_);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"success(itemStack, world.isClient)");
        return interactionResultHolder;
    }

    private final void throwPokeBall(Level world, ServerPlayer player) {
        EmptyPokeBallEntity emptyPokeBallEntity;
        Level level = player.m_9236_();
        Intrinsics.checkNotNullExpressionValue((Object)level, (String)"player.world");
        EmptyPokeBallEntity $this$throwPokeBall_u24lambda_u240 = emptyPokeBallEntity = new EmptyPokeBallEntity(this.pokeBall, level, (LivingEntity)player, null, 8, null);
        boolean bl = false;
        float overhandFactor = player.m_146909_() < 0.0f ? 5.0f * (float)Math.cos(AngleExtensionsKt.toRadians(Float.valueOf(player.m_146909_()))) : 5.0f;
        $this$throwPokeBall_u24lambda_u240.m_37251_((Entity)player, player.m_146909_() - overhandFactor, player.m_146908_(), 0.0f, $this$throwPokeBall_u24lambda_u240.getPokeBall().getThrowPower(), 1.0f);
        $this$throwPokeBall_u24lambda_u240.m_146884_($this$throwPokeBall_u24lambda_u240.m_20182_().m_82549_($this$throwPokeBall_u24lambda_u240.m_20184_().m_82541_().m_82490_(1.0)));
        $this$throwPokeBall_u24lambda_u240.m_5602_((Entity)player);
        EmptyPokeBallEntity pokeBallEntity = emptyPokeBallEntity;
        world.m_7967_((Entity)pokeBallEntity);
    }

    public boolean m_41475_() {
        return Intrinsics.areEqual((Object)this.pokeBall.getName(), (Object)PokeBalls.INSTANCE.getMASTER_BALL().getName()) || super.m_41475_();
    }
}

