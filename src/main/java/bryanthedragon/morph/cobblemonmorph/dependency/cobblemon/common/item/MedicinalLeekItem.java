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
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemNameBlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.PlaceOnWaterBlockItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MedicinalLeekBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J-\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/item/MedicinalLeekItem;", "Lnet/minecraft/world/item/ItemNameBlockItem;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/entity/player/Player;", "user", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResultHolder;", "Lnet/minecraft/world/item/ItemStack;", "use", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", "Lnet/minecraft/world/item/context/UseOnContext;", "context", "Lnet/minecraft/world/InteractionResult;", "useOnBlock", "(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", "Lcom/cobblemon/mod/common/block/MedicinalLeekBlock;", "block", "Lnet/minecraft/item/Item$Settings;", "settings", "<init>", "(Lcom/cobblemon/mod/common/block/MedicinalLeekBlock;Lnet/minecraft/world/item/Item$Properties;)V", "common"})
public final class MedicinalLeekItem
extends ItemNameBlockItem {
    public MedicinalLeekItem(@NotNull MedicinalLeekBlock block, @NotNull Item.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)((Object)block), (String)"block");
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super((Block)block, settings);
        Cobblemon.INSTANCE.getImplementation().registerCompostable((ItemLike)this, 0.65f);
    }

    @NotNull
    public InteractionResult m_6225_(@NotNull UseOnContext context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return InteractionResult.PASS;
    }

    @NotNull
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        BlockHitResult blockHitResult = PlaceOnWaterBlockItem.m_41435_((Level)world, (Player)user, (ClipContext.Fluid)ClipContext.Fluid.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.m_82430_(blockHitResult.m_82425_().m_7494_());
        InteractionResult placeResult = this.m_40576_(new BlockPlaceContext(new UseOnContext(user, hand, blockHitResult2)));
        ItemStack stack = user.m_21120_(hand);
        if (!placeResult.m_19077_() && this.m_41472_()) {
            FoodProperties foodProperties = this.m_41473_();
            boolean bl = foodProperties != null ? foodProperties.m_38747_() : false;
            if (user.m_36391_(bl)) {
                user.m_6672_(hand);
                InteractionResultHolder interactionResultHolder = InteractionResultHolder.m_19096_((Object)stack);
                Intrinsics.checkNotNullExpressionValue((Object)interactionResultHolder, (String)"consume(stack)");
                return interactionResultHolder;
            }
        }
        return new InteractionResultHolder(placeResult, (Object)stack);
    }
}

