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
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J?\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/client/render/pokeball/PokeBallRenderer;", "Lnet/minecraft/client/renderer/entity/EntityRenderer;", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "pEntity", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;)Lnet/minecraft/resources/ResourceLocation;", "entity", "", "yaw", "partialTicks", "Lcom/mojang/blaze3d/vertex/PoseStack;", "poseStack", "Lnet/minecraft/client/renderer/MultiBufferSource;", "buffer", "", "packedLight", "", "render", "(Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", "Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;", "context", "<init>", "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", "common"})
public final class PokeBallRenderer
extends EntityRenderer<EmptyPokeBallEntity> {
    public PokeBallRenderer(@NotNull EntityRendererProvider.Context context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        super(context);
    }

    @NotNull
    public ResourceLocation getTexture(@NotNull EmptyPokeBallEntity pEntity) {
        Intrinsics.checkNotNullParameter((Object)pEntity, (String)"pEntity");
        ResourceLocation resourceLocation = pEntity.getPokeBall().getName();
        Set<String> set2 = pEntity.getAspects();
        EntitySideDelegate<EmptyPokeBallEntity> entitySideDelegate = pEntity.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate");
        return PokeBallModelRepository.INSTANCE.getTexture(resourceLocation, set2, ((EmptyPokeBallClientDelegate)entitySideDelegate).getAnimationSeconds());
    }

    public void render(@NotNull EmptyPokeBallEntity entity2, float yaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)poseStack, (String)"poseStack");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        PokeBallModel model = (PokeBallModel)PokeBallModelRepository.INSTANCE.getPoser(entity2.getPokeBall().getName(), entity2.getAspects());
        poseStack.m_85836_();
        poseStack.m_252781_(Axis.f_252436_.m_252977_(yaw));
        poseStack.m_85841_(0.7f, -0.7f, -0.7f);
        VertexConsumer vertexConsumer = ItemRenderer.m_115222_((MultiBufferSource)buffer, (RenderType)model.m_103119_(this.getTexture(entity2)), (boolean)false, (boolean)false);
        EntitySideDelegate<EmptyPokeBallEntity> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate");
        EmptyPokeBallClientDelegate state = (EmptyPokeBallClientDelegate)entitySideDelegate;
        state.updatePartialTicks(partialTicks);
        model.setLayerContext(buffer, state, PokemonModelRepository.INSTANCE.getLayers(entity2.getPokeBall().getName(), entity2.getAspects()));
        model.m_6973_((Entity)entity2, 0.0f, 0.0f, (float)entity2.f_19797_ + partialTicks, 0.0f, 0.0f);
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"vertexConsumer");
        model.m_7695_(poseStack, vertexConsumer, packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        model.setGreen(1.0f);
        model.setBlue(1.0f);
        model.setRed(1.0f);
        model.resetLayerContext();
        poseStack.m_85849_();
        super.m_7392_((Entity)entity2, yaw, partialTicks, poseStack, buffer, packedLight);
    }
}

