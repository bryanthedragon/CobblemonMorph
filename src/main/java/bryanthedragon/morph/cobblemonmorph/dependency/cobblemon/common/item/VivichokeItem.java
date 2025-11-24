/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemNameBlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.PlaceOnWaterBlockItem
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.VivichokeBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ3\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/item/VivichokeItem;", "Lnet/minecraft/world/item/ItemNameBlockItem;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResultHolder;", "Lnet/minecraft/world/item/ItemStack;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lcom/cobblemon/mod/common/block/VivichokeBlock;", "block", "<init>", "(Lcom/cobblemon/mod/common/block/VivichokeBlock;)V", "common"})
public final class VivichokeItem
extends ItemNameBlockItem {
    public VivichokeItem(@NotNull VivichokeBlock block) {
        Intrinsics.checkNotNullParameter((Object)((Object)block), (String)"block");
        super((Block)block, new Item.Properties());
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@Nullable Level world, @NotNull Player user, @Nullable InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        BlockHitResult blockHitResult = PlaceOnWaterBlockItem.m_41435_((Level)world, (Player)user, (ClipContext.Fluid)ClipContext.Fluid.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.m_82430_(blockHitResult.m_82425_().m_7494_());
        InteractionResult interactionResult = super.m_6225_(new UseOnContext(user, hand, blockHitResult2));
        Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"super.useOnBlock(ItemUsa\u2026, hand, blockHitResult2))");
        InteractionResult actionResult = interactionResult;
        return new InteractionResultHolder(actionResult, (Object)user.m_21120_(hand));
    }
}

