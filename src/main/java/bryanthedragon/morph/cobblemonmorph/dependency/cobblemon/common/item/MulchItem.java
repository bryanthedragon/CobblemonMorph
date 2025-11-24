/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.Mulchable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/item/MulchItem;", "Lcom/cobblemon/mod/common/item/CobblemonItem;", "Lnet/minecraft/world/item/context/UseOnContext;", "context", "Lnet/minecraft/world/InteractionResult;", "useOnBlock", "(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "", "useOnMulchAble", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "variant", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "getVariant", "()Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "<init>", "(Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)V", "common"})
public final class MulchItem
extends CobblemonItem {
    @NotNull
    private final MulchVariant variant;

    public MulchItem(@NotNull MulchVariant variant) {
        Intrinsics.checkNotNullParameter((Object)((Object)variant), (String)"variant");
        super(new Item.Properties());
        this.variant = variant;
    }

    @NotNull
    public final MulchVariant getVariant() {
        return this.variant;
    }

    @NotNull
    public InteractionResult m_6225_(@NotNull UseOnContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        ItemStack itemStack = context.m_43722_();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"context.stack");
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
        if (this.useOnMulchAble(itemStack, world, pos)) {
            if (!world.f_46443_) {
                world.m_46796_(1505, pos, 0);
            }
            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)true);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(true)");
            return interactionResult;
        }
        return InteractionResult.PASS;
    }

    private final boolean useOnMulchAble(ItemStack stack, Level world, BlockPos pos) {
        BlockState state = world.m_8055_(pos);
        if (state.m_60734_() instanceof Mulchable) {
            Block block = state.m_60734_();
            Mulchable mulchable = block instanceof Mulchable ? (Mulchable)block : null;
            if (mulchable == null) {
                return false;
            }
            Mulchable mulchAble = mulchable;
            if (world instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel)world;
                Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
                if (mulchAble.canHaveMulchApplied(serverLevel, pos, state, this.variant)) {
                    ServerLevel serverLevel2 = (ServerLevel)world;
                    RandomSource randomSource = world.f_46441_;
                    Intrinsics.checkNotNullExpressionValue((Object)randomSource, (String)"world.random");
                    mulchAble.applyMulch(serverLevel2, randomSource, pos, state, this.variant);
                    stack.m_41774_(1);
                    return true;
                }
            }
        }
        return false;
    }
}

