/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.generic;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.GenericBedrockEntityModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J?\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/render/generic/GenericBedrockRenderer;", "Lnet/minecraft/client/renderer/entity/EntityRenderer;", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "entity", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "(Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;)Lnet/minecraft/resources/ResourceLocation;", "", "yaw", "partialTicks", "Lcom/mojang/blaze3d/vertex/PoseStack;", "poseStack", "Lnet/minecraft/client/renderer/MultiBufferSource;", "buffer", "", "packedLight", "", "render", "(Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", "Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;", "context", "<init>", "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", "common"})
public final class GenericBedrockRenderer
extends EntityRenderer<GenericBedrockEntity> {
    public GenericBedrockRenderer(@NotNull EntityRendererProvider.Context context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        super(context);
    }

    @NotNull
    public ResourceLocation getTexture(@NotNull GenericBedrockEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        ResourceLocation resourceLocation = entity2.getCategory();
        Set<String> set2 = entity2.getAspects();
        EntitySideDelegate<GenericBedrockEntity> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate");
        return GenericBedrockEntityModelRepository.INSTANCE.getTexture(resourceLocation, set2, ((GenericBedrockClientDelegate)entitySideDelegate).getAnimationSeconds());
    }

    public void render(@NotNull GenericBedrockEntity entity2, float yaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)poseStack, (String)"poseStack");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        if (entity2.m_20145_()) {
            return;
        }
        Object model = GenericBedrockEntityModelRepository.INSTANCE.getPoser(entity2.getCategory(), entity2.getAspects());
        poseStack.m_85836_();
        poseStack.m_85841_(1.0f, -1.0f, 1.0f);
        poseStack.m_85841_(entity2.getScale(), entity2.getScale(), entity2.getScale());
        poseStack.m_252781_(Axis.f_252436_.m_252977_(yaw));
        VertexConsumer vertexConsumer = buffer.m_6299_(model.m_103119_(this.getTexture(entity2)));
        EntitySideDelegate<GenericBedrockEntity> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate");
        GenericBedrockClientDelegate state = (GenericBedrockClientDelegate)entitySideDelegate;
        state.updatePartialTicks(partialTicks);
        ((PoseableEntityModel)model).setLayerContext(buffer, state, PokemonModelRepository.INSTANCE.getLayers(entity2.getCategory(), entity2.getAspects()));
        ((PoseableEntityModel)model).m_6973_((Entity)entity2, 0.0f, 0.0f, (float)entity2.f_19797_ + partialTicks, 0.0f, 0.0f);
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
        ((PoseableEntityModel)model).m_7695_(poseStack, vertexConsumer, packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        ((PoseableEntityModel)model).setGreen(1.0f);
        ((PoseableEntityModel)model).setBlue(1.0f);
        ((PoseableEntityModel)model).setRed(1.0f);
        ((PoseableEntityModel)model).resetLayerContext();
        poseStack.m_85849_();
        super.m_7392_((Entity)entity2, yaw, partialTicks, poseStack, buffer, packedLight);
    }
}

