/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.GildedChestBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.blockentity.BlockEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BlockEntityModelRepository;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013J?\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/render/block/GildedChestBlockRenderer;", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", "Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity;", "entity", "", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrices", "Lnet/minecraft/client/renderer/MultiBufferSource;", "vertexConsumers", "", "light", "overlay", "", "render", "(Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;", "context", "<init>", "(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V", "common"})
public final class GildedChestBlockRenderer
implements BlockEntityRenderer<GildedChestBlockEntity> {
    public GildedChestBlockRenderer(@NotNull BlockEntityRendererProvider.Context context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
    }

    public void render(@NotNull GildedChestBlockEntity entity2, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay2) {
        Intrinsics.checkNotNullParameter((Object)((Object)entity2), (String)"entity");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        Set aspects = SetsKt.emptySet();
        GildedState state = entity2.getPoseableState();
        state.updatePartialTicks(tickDelta);
        ResourceLocation poserId = entity2.getType().getPoserId();
        BlockEntityModel model = (BlockEntityModel)BlockEntityModelRepository.INSTANCE.getPoser(poserId, aspects);
        ResourceLocation texture = BlockEntityModelRepository.INSTANCE.getTexture(poserId, aspects, state.getAnimationSeconds());
        VertexConsumer vertexConsumer = vertexConsumers.m_6299_(model.m_103119_(texture));
        model.setBufferProvider(vertexConsumers);
        state.setCurrentModel(model);
        matrices.m_85836_();
        matrices.m_252781_(Axis.f_252403_.m_252977_(180.0f));
        matrices.m_85837_(-0.5, 0.0, 0.5);
        matrices.m_252781_(Axis.f_252436_.m_252977_(((Direction)entity2.m_58900_().m_61143_((Property)BlockStateProperties.f_61374_)).m_122435_()));
        matrices.m_252781_(Axis.f_252436_.m_252977_(180.0f));
        float f = state.getAnimationSeconds() * (float)20;
        model.setupAnimStateful(null, state, 0.0f, 0.0f, f, 0.0f, 0.0f);
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
        model.m_7695_(matrices, vertexConsumer, light, overlay2, 1.0f, 1.0f, 1.0f, 1.0f);
        model.withLayerContext(vertexConsumers, state, BlockEntityModelRepository.INSTANCE.getLayers(poserId, aspects), (Function0<Unit>)((Function0)new Function0<Unit>(model, matrices, vertexConsumer, light){
            final /* synthetic */ BlockEntityModel $model;
            final /* synthetic */ PoseStack $matrices;
            final /* synthetic */ VertexConsumer $vertexConsumer;
            final /* synthetic */ int $light;
            {
                this.$model = $model;
                this.$matrices = $matrices;
                this.$vertexConsumer = $vertexConsumer;
                this.$light = $light;
                super(0);
            }

            public final void invoke() {
                VertexConsumer vertexConsumer = this.$vertexConsumer;
                Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
                this.$model.m_7695_(this.$matrices, vertexConsumer, this.$light, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }));
        model.setDefault();
        matrices.m_85849_();
    }
}

