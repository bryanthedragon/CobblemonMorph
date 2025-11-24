/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexBuffer
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Matrix4fc
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthPoint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.BerryBlockEntityRenderState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.CobblemonRenderLayers;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BerryModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Axis;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4fc;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ?\u0010\u0014\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J-\u0010\u0018\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001b\u001a\u00020\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/client/render/block/BerryBlockRenderer;", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "blockEntity", "Lnet/minecraft/world/phys/Vec3;", "pos", "", "isInRenderDistance", "(Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;Lnet/minecraft/world/phys/Vec3;)Z", "entity", "", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "Lcom/mojang/blaze3d/vertex/VertexBuffer;", "buffer", "renderToBuffer", "(Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;IILcom/mojang/blaze3d/vertex/VertexBuffer;)V", "Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;", "context", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;", "<init>", "(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V", "common"})
public final class BerryBlockRenderer
implements BlockEntityRenderer<BerryBlockEntity> {
    @NotNull
    private final BlockEntityRendererProvider.Context context;

    public BerryBlockRenderer(@NotNull BlockEntityRendererProvider.Context context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.context = context;
    }

    public boolean isInRenderDistance(@NotNull BerryBlockEntity blockEntity, @NotNull Vec3 pos) {
        Intrinsics.checkNotNullParameter((Object)((Object)blockEntity), (String)"blockEntity");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return super.m_142756_((BlockEntity)blockEntity, pos) && Minecraft.m_91087_().f_91060_.f_172938_.m_113029_(AABB.m_165882_((Vec3)pos, (double)2.0, (double)4.0, (double)2.0));
    }

    public void render(@NotNull BerryBlockEntity entity2, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay2) {
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        BlockPos blockPos2 = entity2.m_58899_();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"entity.pos");
        if (!this.isInRenderDistance(entity2, BlockPosExtensionsKt.toVec3d(blockPos2))) {
            return;
        }
        BlockState blockState = entity2.m_58900_();
        Integer age = (Integer)blockState.m_61143_((Property)BerryBlock.Companion.getAGE());
        Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
        if (age <= 3) {
            return;
        }
        if (entity2.getRenderState() == null) {
            entity2.setRenderState(new BerryBlockEntityRenderState());
        }
        BerryBlockEntity.RenderState renderState = entity2.getRenderState();
        Intrinsics.checkNotNull((Object)renderState, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.BerryBlockEntityRenderState");
        BerryBlockEntityRenderState renderState2 = (BerryBlockEntityRenderState)renderState;
        if (renderState2.getNeedsRebuild() || renderState2.getVboLightLevel() != light) {
            this.renderToBuffer(entity2, light, overlay2, renderState2.getVbo());
            renderState2.setVboLightLevel(light);
            BerryBlockEntity.RenderState renderState3 = entity2.getRenderState();
            Intrinsics.checkNotNull((Object)renderState3, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.BerryBlockEntityRenderState");
            ((BerryBlockEntityRenderState)renderState3).setNeedsRebuild(false);
        }
        matrices.m_85836_();
        CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_110185_();
        renderState2.getVbo().m_85921_();
        renderState2.getVbo().m_253207_(matrices.m_85850_().m_252922_().mul((Matrix4fc)RenderSystem.getModelViewMatrix()), RenderSystem.getProjectionMatrix(), GameRenderer.m_172646_());
        VertexBuffer.m_85931_();
        CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_110188_();
        matrices.m_85849_();
    }

    public final void renderToBuffer(@NotNull BerryBlockEntity entity2, int light, int overlay2, @NotNull VertexBuffer buffer) {
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        BlockState blockState = entity2.m_58900_();
        Integer age = (Integer)blockState.m_61143_((Property)BerryBlock.Companion.getAGE());
        Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
        if (age <= 3) {
            return;
        }
        int n = 4;
        boolean isFlower = age == n;
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_173186_(), CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_110508_());
        for (Pair<Berry, GrowthPoint> pair : entity2.berryAndGrowthPoint$common()) {
            ModelPart model;
            Berry berry = (Berry)pair.component1();
            GrowthPoint growthPoint = (GrowthPoint)pair.component2();
            if ((isFlower ? BerryModelRepository.INSTANCE.modelOf(berry.getFlowerModelIdentifier()) : BerryModelRepository.INSTANCE.modelOf(berry.getFruitModelIdentifier())) == null) continue;
            model = model;
            model.m_171327_((float)Math.toRadians(180.0 - growthPoint.getRotation().f_82479_), (float)Math.toRadians(180.0 + growthPoint.getRotation().f_82480_), (float)Math.toRadians(growthPoint.getRotation().f_82481_));
            ModelPartExtensionsKt.setPosition(model, Axis.X_AXIS.ordinal(), (float)growthPoint.getPosition().f_82479_);
            ModelPartExtensionsKt.setPosition(model, Axis.Y_AXIS.ordinal(), (float)growthPoint.getPosition().f_82480_);
            ModelPartExtensionsKt.setPosition(model, Axis.Z_AXIS.ordinal(), (float)growthPoint.getPosition().f_82481_);
            model.m_104301_(new PoseStack(), (VertexConsumer)bufferBuilder, light, overlay2);
        }
        BufferBuilder.RenderedBuffer bufferBuilderFinal = bufferBuilder.m_231175_();
        buffer.m_85921_();
        buffer.m_231221_(bufferBuilderFinal);
        VertexBuffer.m_85931_();
    }
}

