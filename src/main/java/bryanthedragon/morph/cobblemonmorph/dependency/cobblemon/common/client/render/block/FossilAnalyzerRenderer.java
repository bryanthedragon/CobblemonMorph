/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013JA\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/block/FossilAnalyzerRenderer;", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", "Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;", "entity", "", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;", "ctx", "<init>", "(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V", "common"})
@SourceDebugExtension(value={"SMAP\nFossilAnalyzerRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilAnalyzerRenderer.kt\ncom/cobblemon/mod/common/client/render/block/FossilAnalyzerRenderer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n1864#2,3:69\n*S KotlinDebug\n*F\n+ 1 FossilAnalyzerRenderer.kt\ncom/cobblemon/mod/common/client/render/block/FossilAnalyzerRenderer\n*L\n47#1:69,3\n*E\n"})
public final class FossilAnalyzerRenderer
implements BlockEntityRenderer<FossilAnalyzerBlockEntity> {
    public FossilAnalyzerRenderer(@NotNull BlockEntityRendererProvider.Context ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
    }

    /*
     * WARNING - void declaration
     */
    public void render(@NotNull FossilAnalyzerBlockEntity entity2, float tickDelta, @NotNull PoseStack matrices, @Nullable MultiBufferSource vertexConsumers, int light, int overlay2) {
        BlockState blockState;
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        if (entity2.m_58904_() != null) {
            v0 = entity2.m_58900_();
        } else {
            Object object = CobblemonBlocks.FOSSIL_ANALYZER.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.SOUTH);
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            v0 = blockState = (BlockState)object;
        }
        if (entity2.getMultiblockStructure() == null) {
            return;
        }
        Direction direction = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        float yRot = direction.m_122435_() + (direction == Direction.WEST || direction == Direction.EAST ? 180.0f : 0.0f);
        MultiblockStructure multiblockStructure = entity2.getMultiblockStructure();
        Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
        FossilMultiblockStructure struct2 = (FossilMultiblockStructure)multiblockStructure;
        Iterable $this$forEachIndexed$iv = struct2.getFossilInventory();
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void fossilStack;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ItemStack itemStack = (ItemStack)item$iv;
            int index = n;
            boolean bl = false;
            matrices.m_85836_();
            Direction direction2 = direction;
            Vec3 dirOffset = switch (direction2 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction2.ordinal()]) {
                case 1 -> new Vec3(0.0, 0.0, 0.05);
                case 2 -> new Vec3(0.0, 0.0, -0.05);
                case 3 -> new Vec3(-0.05, 0.0, 0.0);
                case 4 -> new Vec3(0.05, 0.0, 0.0);
                default -> Vec3.f_82478_;
            };
            matrices.m_85837_(0.5 + dirOffset.f_82479_, 0.4 + (double)index * 0.1 + dirOffset.f_82480_, 0.5 + dirOffset.f_82481_);
            matrices.m_252781_(Axis.f_252436_.m_252977_(yRot));
            matrices.m_252781_(Axis.f_252403_.m_252977_(180.0f));
            matrices.m_252781_(Axis.f_252529_.m_252977_(90.0f));
            matrices.m_85841_(0.7f, 0.7f, 0.7f);
            Minecraft.m_91087_().m_91291_().m_269128_((ItemStack)fossilStack, ItemDisplayContext.NONE, light, overlay2, matrices, vertexConsumers, entity2.m_58904_(), 0);
            matrices.m_85849_();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

