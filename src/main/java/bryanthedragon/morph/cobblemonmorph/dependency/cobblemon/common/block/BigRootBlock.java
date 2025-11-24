/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BigRootBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RootBlock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000  2\u00020\u0001:\u0001 B\u000f\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001e\u0010\u001fJ/\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ?\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\u0017H\u0014\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u001a\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u001a\u0010\u001b\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/block/BigRootBlock;", "Lcom/cobblemon/mod/common/block/RootBlock;", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/Level;", "Lnet/minecraft/world/entity/player/Player;", "player", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/world/item/ItemStack;", "shearedDrop", "()Lnet/minecraft/world/item/ItemStack;", "shearedResultingState", "()Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
public final class BigRootBlock
extends RootBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final VoxelShape AABB = Shapes.m_83048_((double)0.2, (double)0.3, (double)0.2, (double)0.8, (double)1.0, (double)0.8);

    public BigRootBlock(@NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        VoxelShape voxelShape = AABB;
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"AABB");
        return voxelShape;
    }

    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_150930_(Items.f_42574_) && this.attemptShear(world, state, pos, (Function0<Unit>)((Function0)new Function0<Unit>(stack, player, hand){
            final /* synthetic */ ItemStack $stack;
            final /* synthetic */ Player $player;
            final /* synthetic */ InteractionHand $hand;
            {
                this.$stack = $stack;
                this.$player = $player;
                this.$hand = $hand;
                super(0);
            }

            public final void invoke() {
                this.$stack.m_41622_(1, (LivingEntity)this.$player, arg_0 -> onUse.1.invoke$lambda$0(this.$hand, arg_0));
            }

            private static final void invoke$lambda$0(InteractionHand $hand, Player affected) {
                Intrinsics.checkNotNullParameter((Object)$hand, (String)"$hand");
                affected.m_21190_($hand);
            }
        }))) {
            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)world.f_46443_);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
            return interactionResult;
        }
        InteractionResult interactionResult = super.m_6227_(state, world, pos, player, hand, hit);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"super.onUse(state, world, pos, player, hand, hit)");
        return interactionResult;
    }

    @Override
    @NotNull
    protected BlockState shearedResultingState() {
        BlockState blockState = Blocks.f_152548_.m_49966_();
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"HANGING_ROOTS.defaultState");
        return blockState;
    }

    @Override
    @NotNull
    protected ItemStack shearedDrop() {
        ItemStack itemStack = Items.f_42401_.m_7968_();
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"STRING.defaultStack");
        return itemStack;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u001c\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u0005\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/block/BigRootBlock$Companion;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "AABB", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

