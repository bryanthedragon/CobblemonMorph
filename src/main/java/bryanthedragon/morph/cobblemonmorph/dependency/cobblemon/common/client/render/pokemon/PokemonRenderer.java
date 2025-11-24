/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.entity.MobRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Math
 *  org.joml.Matrix4f
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBallDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings.ServerSettings;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.DoubleRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 A2\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00030\u0001:\u0001AB\u000f\u0012\u0006\u0010>\u001a\u00020=\u00a2\u0006\u0004\b?\u0010@J[\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0019\u0010\u0018\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u001d\u0010\u001eJ?\u0010\"\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\"\u0010#J=\u0010(\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010%\u001a\u00020$2\u0006\u0010!\u001a\u00020\r2\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b(\u0010)J7\u0010/\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\r2\u0006\u0010.\u001a\u00020\u000fH\u0014\u00a2\u0006\u0004\b/\u00100JS\u00106\u001a\u00020\u00142\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u0002012\u0006\u00103\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u00105\u001a\u000204\u00a2\u0006\u0004\b6\u00107J'\u0010\t\u001a\u00020\u00142\u0006\u00108\u001a\u00020\u00022\u0006\u00109\u001a\u00020\u00062\u0006\u0010:\u001a\u00020\bH\u0014\u00a2\u0006\u0004\b\t\u0010;J\u0017\u0010<\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b<\u0010\u001e\u00a8\u0006B"}, d2={"Lcom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer;", "Lnet/minecraft/client/renderer/entity/MobRenderer;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lnet/minecraft/client/model/EntityModel;", "Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;", "state", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "", "scale", "partialTicks", "", "reversed", "Lnet/minecraft/client/renderer/MultiBufferSource;", "buff", "", "packedLight", "Lcom/cobblemon/mod/common/pokeball/PokeBall;", "ball", "distance", "", "drawPokeBall", "(Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;Lcom/mojang/blaze3d/vertex/PoseStack;FFZLnet/minecraft/client/renderer/MultiBufferSource;ILcom/cobblemon/mod/common/pokeball/PokeBall;I)V", "entity", "getLyingAngle", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)F", "Lnet/minecraft/resources/ResourceLocation;", "getTexture", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lnet/minecraft/resources/ResourceLocation;", "hasLabel", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "entityYaw", "poseMatrix", "buffer", "render", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", "Lnet/minecraft/world/entity/Entity;", "beamTarget", "Lnet/minecraft/world/phys/Vec3;", "offset", "renderBeam", "(Lcom/mojang/blaze3d/vertex/PoseStack;FLcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/phys/Vec3;)V", "Lnet/minecraft/network/chat/Component;", "text", "matrices", "vertexConsumers", "light", "renderLabelIfPresent", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "modelNow", "beamMode", "Lcom/cobblemon/mod/common/client/entity/PokemonClientDelegate;", "clientDelegate", "renderTransition", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;ILcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILcom/cobblemon/mod/common/client/entity/PokemonClientDelegate;)V", "pEntity", "pMatrixStack", "pPartialTickTime", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", "shouldRenderLabel", "Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;", "context", "<init>", "(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonRenderer.kt\ncom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,391:1\n288#2,2:392\n1#3:394\n*S KotlinDebug\n*F\n+ 1 PokemonRenderer.kt\ncom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer\n*L\n198#1:392,2\n*E\n"})
public final class PokemonRenderer
extends MobRenderer<PokemonEntity, EntityModel<PokemonEntity>> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Vector4f recallBeamColour = new Vector4f(1.0f, 0.1f, 0.1f, 1.0f);

    public PokemonRenderer(@NotNull EntityRendererProvider.Context context) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        super(context, null, 0.5f);
    }

    @NotNull
    public ResourceLocation getTexture(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        ResourceLocation resourceLocation = entity2.getPokemon().getSpecies().getResourceIdentifier();
        Set<String> set2 = entity2.getAspects();
        PokemonSideDelegate pokemonSideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
        return PokemonModelRepository.INSTANCE.getTexture(resourceLocation, set2, ((PokemonClientDelegate)pokemonSideDelegate).getAnimationSeconds());
    }

    public void render(@NotNull PokemonEntity entity2, float entityYaw, float partialTicks, @NotNull PoseStack poseMatrix, @NotNull MultiBufferSource buffer, int packedLight) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)poseMatrix, (String)"poseMatrix");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        float f = (float)Math.min(entity2.m_20191_().f_82291_ - entity2.m_20191_().f_82288_, entity2.m_20191_().f_82293_ - entity2.m_20191_().f_82290_) / 1.5f;
        PokemonSideDelegate pokemonSideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
        this.f_114477_ = f * ((PokemonClientDelegate)pokemonSideDelegate).getEntityScaleModifier();
        this.f_115290_ = (EntityModel)PokemonModelRepository.INSTANCE.getPoser(entity2.getPokemon().getSpecies().getResourceIdentifier(), entity2.getAspects());
        PokemonSideDelegate pokemonSideDelegate2 = entity2.getDelegate();
        Intrinsics.checkNotNull((Object)pokemonSideDelegate2, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
        PokemonClientDelegate clientDelegate = (PokemonClientDelegate)pokemonSideDelegate2;
        EntityModel entityModel = this.f_115290_;
        Intrinsics.checkNotNull((Object)entityModel, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity>");
        PoseableEntityModel modelNow = (PoseableEntityModel)entityModel;
        clientDelegate.updatePartialTicks(partialTicks);
        if (entity2.getBeamMode() != 0) {
            this.renderTransition(modelNow, entity2.getBeamMode(), entity2, partialTicks, poseMatrix, buffer, packedLight, clientDelegate);
        }
        modelNow.setLayerContext(buffer, clientDelegate, PokemonModelRepository.INSTANCE.getLayers(entity2.getPokemon().getSpecies().getResourceIdentifier(), entity2.getAspects()));
        if (entity2.getTicksLived() < 10) {
            Object object = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getSPAWN_DIRECTION());
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"entity.dataTracker.get(SPAWN_DIRECTION)");
            entity2.f_20884_ = entity2.f_20883_ = ((Number)object).floatValue();
        }
        super.m_7392_((Mob)entity2, entityYaw, partialTicks, poseMatrix, buffer, packedLight);
        modelNow.setGreen(1.0f);
        modelNow.setBlue(1.0f);
        modelNow.resetLayerContext();
        if (this.shouldRenderLabel(entity2)) {
            Component component = entity2.m_5446_();
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"entity.displayName");
            this.renderLabelIfPresent(entity2, component, poseMatrix, buffer, packedLight);
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void renderTransition(@NotNull PoseableEntityModel<PokemonEntity> modelNow, int beamMode, @NotNull PokemonEntity entity2, float partialTicks, @NotNull PoseStack poseMatrix, @NotNull MultiBufferSource buffer, int packedLight, @NotNull PokemonClientDelegate clientDelegate) {
        Intrinsics.checkNotNullParameter(modelNow, (String)"modelNow");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)poseMatrix, (String)"poseMatrix");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)clientDelegate, (String)"clientDelegate");
        float s = clientDelegate.getSecondsSinceBeamEffectStarted();
        if (modelNow instanceof PokemonPoseableModel && beamMode == 3 && s > 0.2f) {
            float value2 = (s - 0.2f) / 0.4f;
            float colourValue = 1.0f - Math.min(0.6f, value2);
            modelNow.setGreen(colourValue);
            modelNow.setBlue(colourValue);
        }
        Entity entity3 = clientDelegate.getPhaseTarget();
        if (entity3 == null) {
            return;
        }
        Entity phaseTarget = entity3;
        poseMatrix.m_85836_();
        MatrixWrapper beamSourcePosition = null;
        if (phaseTarget instanceof EmptyPokeBallEntity) {
            EntitySideDelegate<EmptyPokeBallEntity> entitySideDelegate = ((EmptyPokeBallEntity)phaseTarget).getDelegate();
            Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState");
            v2 = ((PokeBallPoseableState)((Object)entitySideDelegate)).getLocatorStates().get("beam");
            if (v2 == null || (v2 = v2.getOrigin()) == null) {
                v2 = ((EmptyPokeBallEntity)phaseTarget).m_20182_();
            }
        } else {
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            if (Intrinsics.areEqual((Object)phaseTarget.m_20148_(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) {
                lookVec = phaseTarget.m_20154_().m_82524_(1.5707964f).m_82542_(1.0, 0.0, 1.0).m_82541_();
                v2 = phaseTarget.m_20299_(partialTicks).m_82492_(0.0, 0.4, 0.0).m_82546_(lookVec.m_82490_(0.3));
            } else {
                lookVec = phaseTarget.m_20154_().m_82524_(1.5707964f - AngleExtensionsKt.toRadians(Float.valueOf(phaseTarget.m_213816_() - phaseTarget.m_146909_()))).m_82542_(1.0, 0.0, 1.0).m_82541_();
                v2 = beamSourcePosition = phaseTarget.m_20299_(partialTicks).m_82492_(0.0, 0.7, 0.0).m_82546_(lookVec.m_82490_(0.4));
            }
        }
        if (clientDelegate.getSendOutPosition() == null && beamMode == 1) {
            clientDelegate.setSendOutPosition((Vec3)beamSourcePosition);
        } else if (beamMode == 1) {
            Vec3 vec3 = clientDelegate.getSendOutPosition();
            Intrinsics.checkNotNull((Object)vec3);
            clientDelegate.setSendOutPosition(vec3.m_82520_(0.0, 0.04, 0.0));
            Vec3 vec32 = clientDelegate.getSendOutPosition();
            Intrinsics.checkNotNull((Object)vec32);
            beamSourcePosition = vec32;
        }
        Vec3 offsetDirection = beamSourcePosition.m_82546_(entity2.m_20182_()).m_82541_().m_82490_(-((double)clientDelegate.getBallOffset()));
        Vec3 facingDir = null;
        Vec3 $this$renderTransition_u24lambda_u240 = beamSourcePosition.m_82546_(entity2.m_20182_());
        boolean bl = false;
        Vec3 newOffset = offsetDirection.m_82490_(2.0);
        double distance2 = beamSourcePosition.m_82554_(entity2.m_20182_());
        newOffset = newOffset.m_82490_(distance2 / 10.0 * (double)5);
        Vec3 vec3 = newOffset.m_82541_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"newOffset.normalize()");
        facingDir = vec3;
        clientDelegate.setSendOutOffset(newOffset);
        poseMatrix.m_85837_($this$renderTransition_u24lambda_u240.f_82479_ + newOffset.f_82479_, $this$renderTransition_u24lambda_u240.f_82480_ + newOffset.f_82480_, $this$renderTransition_u24lambda_u240.f_82481_ + newOffset.f_82481_);
        Vec3 dir = beamSourcePosition.m_82546_(entity2.m_20182_()).m_82541_();
        double angle = Mth.m_14136_((double)dir.f_82481_, (double)dir.f_82479_) - (double)1.5707964f;
        poseMatrix.m_252781_(Axis.f_252436_.m_252961_(-((float)angle) + (float)Math.PI));
        if (beamMode == 1 && !clientDelegate.getBallDone()) {
            Object object;
            Object var32_37;
            PokemonRenderer pokemonRenderer;
            ClientBallDisplay clientBallDisplay;
            PoseStack poseStack;
            float f;
            float f2;
            boolean bl2;
            MultiBufferSource multiBufferSource;
            int n;
            block15: {
                Object v9;
                void $this$firstOrNull$iv;
                String string = entity2.getPokemon().getCaughtBall().getName().toString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"entity.pokemon.caughtBall.name.toString()");
                if (StringsKt.contains$default((CharSequence)string, (CharSequence)"beast", (boolean)false, (int)2, null)) {
                    double xAngleFacingDir = Mth.m_14136_((double)facingDir.f_82480_, (double)Math.sqrt(facingDir.f_82479_ * facingDir.f_82479_ + facingDir.f_82481_ * facingDir.f_82481_));
                    poseMatrix.m_252781_(Axis.f_252529_.m_252961_(-((float)xAngleFacingDir)));
                }
                Iterable distance2 = CobblemonClient.INSTANCE.getStorage().getMyParty();
                n = packedLight;
                multiBufferSource = buffer;
                bl2 = false;
                f2 = partialTicks;
                f = clientDelegate.getBallOffset();
                poseStack = poseMatrix;
                clientBallDisplay = new ClientBallDisplay(entity2.getPokemon().getCaughtBall(), SetsKt.emptySet());
                pokemonRenderer = this;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    Pokemon it = (Pokemon)element$iv;
                    boolean bl3 = false;
                    Pokemon pokemon = it;
                    if (!Intrinsics.areEqual((Object)(pokemon != null ? pokemon.getUuid() : null), (Object)entity2.getPokemon().getUuid())) continue;
                    v9 = element$iv;
                    break block15;
                }
                v9 = var32_37 = null;
            }
            if ((object = (Pokemon)var32_37) == null || (object = ((Pokemon)object).getCaughtBall()) == null) {
                object = clientDelegate.getCurrentEntity().getPokemon().getCaughtBall();
            }
            PokemonRenderer.drawPokeBall$default(pokemonRenderer, clientBallDisplay, poseStack, f, f2, bl2, multiBufferSource, n, (PokeBall)object, (int)Math.ceil(beamSourcePosition.m_82554_(entity2.m_20182_()) / (double)4.0f), 16, null);
        }
        poseMatrix.m_85849_();
        if (beamMode == 3) {
            Intrinsics.checkNotNullExpressionValue((Object)offsetDirection, (String)"offsetDirection");
            this.renderBeam(poseMatrix, partialTicks, entity2, phaseTarget, buffer, offsetDirection);
        }
    }

    protected void scale(@NotNull PokemonEntity pEntity, @NotNull PoseStack pMatrixStack, float pPartialTickTime) {
        Intrinsics.checkNotNullParameter((Object)pEntity, (String)"pEntity");
        Intrinsics.checkNotNullParameter((Object)pMatrixStack, (String)"pMatrixStack");
        float f = pEntity.getPokemon().getForm().getBaseScale() * pEntity.getPokemon().getScaleModifier();
        PokemonSideDelegate pokemonSideDelegate = pEntity.getDelegate();
        Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
        float scale = f * ((PokemonClientDelegate)pokemonSideDelegate).getEntityScaleModifier();
        pMatrixStack.m_85841_(scale, scale, scale);
    }

    public final void renderBeam(@NotNull PoseStack matrixStack, float partialTicks, @NotNull PokemonEntity entity2, @NotNull Entity beamTarget, @NotNull MultiBufferSource buffer, @NotNull Vec3 offset) {
        MatrixWrapper beamSourcePosition;
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)beamTarget, (String)"beamTarget");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)offset, (String)"offset");
        PokemonSideDelegate pokemonSideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
        PokemonClientDelegate clientDelegate = (PokemonClientDelegate)pokemonSideDelegate;
        Vec3 pokemonPosition = entity2.m_20182_().m_82520_(0.0, (double)entity2.m_20206_() / 2.0 * (double)clientDelegate.getEntityScaleModifier(), 0.0);
        if (beamTarget instanceof EmptyPokeBallEntity) {
            EntitySideDelegate<EmptyPokeBallEntity> entitySideDelegate = ((EmptyPokeBallEntity)beamTarget).getDelegate();
            Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPoseableState");
            v2 = ((PokeBallPoseableState)((Object)entitySideDelegate)).getLocatorStates().get("beam");
            if (v2 == null || (v2 = v2.getOrigin()) == null) {
                v2 = ((EmptyPokeBallEntity)beamTarget).m_20182_();
            }
        } else {
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            if (Intrinsics.areEqual((Object)beamTarget.m_20148_(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) {
                lookVec = beamTarget.m_20154_().m_82524_(1.5707964f).m_82542_(1.0, 0.0, 1.0).m_82541_();
                v2 = beamTarget.m_20299_(partialTicks).m_82492_(0.0, 0.4, 0.0).m_82546_(lookVec.m_82490_(0.3));
            } else {
                lookVec = beamTarget.m_20154_().m_82524_(1.5707964f - AngleExtensionsKt.toRadians(Float.valueOf(beamTarget.m_213816_() - beamTarget.m_146909_()))).m_82542_(1.0, 0.0, 1.0).m_82541_();
                v2 = beamSourcePosition = beamTarget.m_20299_(partialTicks).m_82492_(0.0, 0.7, 0.0).m_82546_(lookVec.m_82490_(0.4));
            }
        }
        if (clientDelegate.getSendOutPosition() != null) {
            Vec3 vec3 = clientDelegate.getSendOutPosition();
            Intrinsics.checkNotNull((Object)vec3);
            beamSourcePosition = vec3;
        }
        if (beamSourcePosition.m_82554_(pokemonPosition) > 20.0) {
            return;
        }
        Vec3 newOffset = null;
        newOffset = offset.m_82490_(2.0);
        double distance = beamSourcePosition.m_82554_(entity2.m_20182_());
        newOffset = newOffset.m_82490_(distance / 10.0 * (double)5);
        newOffset = newOffset.m_82542_(0.0, 1.0 + Companion.ease(clientDelegate.getBallOffset()), 0.0);
        Vec3 it = pokemonPosition.m_82546_(beamSourcePosition.m_82549_(newOffset));
        boolean bl = false;
        Vector3f direction = new Vector3f((float)it.f_82479_, (float)it.f_82480_, (float)it.f_82481_);
        matrixStack.m_85836_();
        Vec3 $this$renderBeam_u24lambda_u243 = beamSourcePosition.m_82546_(entity2.m_20182_());
        boolean bl2 = false;
        matrixStack.m_85837_($this$renderBeam_u24lambda_u243.f_82479_ + newOffset.f_82479_, $this$renderBeam_u24lambda_u243.f_82480_ + newOffset.f_82480_, $this$renderBeam_u24lambda_u243.f_82481_ + newOffset.f_82481_);
        float s = clientDelegate.getSecondsSinceBeamEffectStarted();
        float ratio = s < 0.2f ? s / 0.2f : (s > 0.6f ? 1.0f - Math.min((s - 0.2f - 0.4f) / 0.2f, 1.0f) : 1.0f);
        direction.normalize();
        Vector3f yAxis = new Vector3f(0.0f, 1.0f, 0.0f);
        float dot = direction.dot((Vector3fc)yAxis);
        Vector3f cross = yAxis.cross((Vector3fc)direction);
        Quaternionf q = new Quaternionf(cross.x, cross.y, cross.z, 1.0f + dot).normalize();
        matrixStack.m_252781_(q);
        RenderHelperKt.renderBeaconBeam$default(matrixStack, buffer, null, partialTicks, entity2.m_9236_().m_46467_(), 0.0f, (float)pokemonPosition.m_82554_(beamSourcePosition.m_82549_(offset)) * ratio, PokemonRenderer.recallBeamColour.x, PokemonRenderer.recallBeamColour.y, PokemonRenderer.recallBeamColour.z, PokemonRenderer.recallBeamColour.w, 0.03f, 0.07f, 0.4f, 36, null);
        matrixStack.m_85849_();
    }

    protected float getLyingAngle(@Nullable PokemonEntity entity2) {
        return 0.0f;
    }

    protected boolean hasLabel(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        return false;
    }

    private final boolean shouldRenderLabel(PokemonEntity entity2) {
        if (!super.m_6512_((Mob)entity2)) {
            return false;
        }
        Object object = entity2.m_20088_().m_135370_(PokemonEntity.Companion.getHIDE_LABEL());
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"entity.dataTracker.get(PokemonEntity.HIDE_LABEL)");
        if (((Boolean)object).booleanValue()) {
            return false;
        }
        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        if (localPlayer == null) {
            return false;
        }
        LocalPlayer player = localPlayer;
        PokemonSideDelegate pokemonSideDelegate = entity2.getDelegate();
        PokemonClientDelegate pokemonClientDelegate = pokemonSideDelegate instanceof PokemonClientDelegate ? (PokemonClientDelegate)pokemonSideDelegate : null;
        if (pokemonClientDelegate == null) {
            return false;
        }
        PokemonClientDelegate delegate = pokemonClientDelegate;
        return PlayerExtensionsKt.isLookingAt$default((Entity)player, (Entity)entity2, 0.0f, 0.0f, 6, null) && delegate.getPhaseTarget() == null;
    }

    protected void renderLabelIfPresent(@NotNull PokemonEntity entity2, @NotNull Component text, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)vertexConsumers, (String)"vertexConsumers");
        if (entity2.m_20145_()) {
            return;
        }
        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        if (localPlayer == null) {
            return;
        }
        LocalPlayer player = localPlayer;
        double d = this.f_114476_.m_114471_((Entity)entity2);
        if (d <= 4096.0) {
            double scale = Math.min(1.5, Math.max(0.65, SimpleMathExtensionsKt.remap(d, new DoubleRange(-16.0, 96.0), new DoubleRange(0.0, 1.0))));
            double sizeScale = Mth.m_14139_((double)SimpleMathExtensionsKt.remap(scale, new DoubleRange(0.65, 1.5), new DoubleRange(0.0, 1.0)), (double)0.5, (double)1.0);
            double offsetScale = Mth.m_14139_((double)SimpleMathExtensionsKt.remap(scale, new DoubleRange(0.65, 1.5), new DoubleRange(0.0, 1.0)), (double)0.0, (double)1.0);
            double entityHeight = entity2.m_20191_().m_82376_() + (double)0.5f;
            matrices.m_85836_();
            matrices.m_85837_(0.0, entityHeight, 0.0);
            matrices.m_252781_(this.f_114476_.m_253208_());
            matrices.m_85837_(0.0, 0.0 + offsetScale / (double)2, -(scale + offsetScale));
            matrices.m_85841_((float)(-0.025 * sizeScale), (float)(-0.025 * sizeScale), 1.0f * (float)sizeScale);
            Matrix4f matrix4f = matrices.m_85850_().m_252922_();
            int opacity = (int)(Minecraft.m_91087_().f_91066_.m_92141_(0.25f) * 255.0f) << 24;
            MutableComponent label = entity2.m_7755_().m_6881_();
            if (ServerSettings.INSTANCE.getDisplayEntityLevelLabel()) {
                Integer n = entity2.labelLevel();
                Intrinsics.checkNotNullExpressionValue((Object)n, (String)"entity.labelLevel()");
                if (((Number)n).intValue() > 0) {
                    Object[] objectArray = new Object[1];
                    Intrinsics.checkNotNullExpressionValue((Object)entity2.labelLevel(), (String)"entity.labelLevel()");
                    MutableComponent levelLabel = LocalizationUtilsKt.lang("label.lv", objectArray);
                    MutableComponent mutableComponent = label;
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"label");
                    label = TextKt.add(mutableComponent, " ").m_7220_((Component)levelLabel);
                }
            }
            float h = -this.m_114481_().m_92852_((FormattedText)label) / 2;
            float y = 0.0f;
            int packedLight = LightTexture.m_109885_((int)15, (int)15);
            this.m_114481_().m_272077_((Component)label, h, y, 0x20FFFFFF, false, matrix4f, vertexConsumers, Font.DisplayMode.SEE_THROUGH, opacity, packedLight);
            this.m_114481_().m_272077_((Component)label, h, y, -1, false, matrix4f, vertexConsumers, Font.DisplayMode.NORMAL, 0, packedLight);
            if (entity2.canBattle((Player)player)) {
                Component sendOutBinding = CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_();
                Object[] objectArray = new Object[1];
                Intrinsics.checkNotNullExpressionValue((Object)sendOutBinding, (String)"sendOutBinding");
                objectArray[0] = sendOutBinding;
                MutableComponent battlePrompt = LocalizationUtilsKt.lang("challenge_label", objectArray);
                h = -this.m_114481_().m_92852_((FormattedText)battlePrompt) / 2;
                this.m_114481_().m_272077_((Component)battlePrompt, h, y + (float)10, 0x20FFFFFF, false, matrix4f, vertexConsumers, Font.DisplayMode.SEE_THROUGH, opacity, packedLight);
                this.m_114481_().m_272077_((Component)battlePrompt, h, y + (float)10, -1, false, matrix4f, vertexConsumers, Font.DisplayMode.NORMAL, 0, packedLight);
            }
            matrices.m_85849_();
        }
    }

    private final void drawPokeBall(ClientBallDisplay state, PoseStack matrixStack, float scale, float partialTicks, boolean reversed, MultiBufferSource buff, int packedLight, PokeBall ball2, int distance) {
        matrixStack.m_85836_();
        matrixStack.m_85841_(0.7f, -0.7f, -0.7f);
        PokeBallModel model = (PokeBallModel)PokeBallModelRepository.INSTANCE.getPoser(ball2.getName(), state.getAspects());
        ResourceLocation texture = PokeBallModelRepository.INSTANCE.getTexture(ball2.getName(), state.getAspects(), state.getAnimationSeconds());
        if (scale == 1.0f) {
            model.moveToPose(null, state, model.getOpen());
        } else {
            matrixStack.m_85837_(0.0, -0.2, 0.0);
            float rot = 360.0f * (float)distance;
            String string = ball2.getName().toString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"ball.name.toString()");
            if (StringsKt.contains$default((CharSequence)string, (CharSequence)"beast", (boolean)false, (int)2, null)) {
                matrixStack.m_252781_(Axis.f_252393_.m_252977_(org.joml.Math.lerp((float)0.0f, (float)rot, (float)scale)));
            } else {
                matrixStack.m_252781_(Axis.f_252495_.m_252977_(org.joml.Math.lerp((float)0.0f, (float)rot, (float)scale)));
            }
            matrixStack.m_85837_(0.0, 0.2, 0.0);
        }
        state.setTimeEnteredPose(0.0f);
        state.updatePartialTicks(partialTicks);
        model.setupAnimStateful(null, state, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        model.m_6839_(null, 0.0f, 0.0f, 0.0f);
        VertexConsumer buffer = ItemRenderer.m_115222_((MultiBufferSource)buff, (RenderType)model.m_103119_(texture), (boolean)false, (boolean)false);
        Intrinsics.checkNotNullExpressionValue((Object)buffer, (String)"buffer");
        model.m_7695_(matrixStack, buffer, packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        model.setGreen(1.0f);
        model.setBlue(1.0f);
        model.setRed(1.0f);
        model.resetLayerContext();
        matrixStack.m_85849_();
    }

    static /* synthetic */ void drawPokeBall$default(PokemonRenderer pokemonRenderer, ClientBallDisplay clientBallDisplay, PoseStack poseStack, float f, float f2, boolean bl, MultiBufferSource multiBufferSource, int n, PokeBall pokeBall, int n2, int n3, Object object) {
        if ((n3 & 4) != 0) {
            f = 5.0f;
        }
        if ((n3 & 0x10) != 0) {
            bl = false;
        }
        pokemonRenderer.drawPokeBall(clientBallDisplay, poseStack, f, f2, bl, multiBufferSource, n, pokeBall, n2);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0007\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/render/pokemon/PokemonRenderer$Companion;", "", "", "x", "ease", "(D)D", "Lorg/joml/Vector4f;", "recallBeamColour", "Lorg/joml/Vector4f;", "getRecallBeamColour", "()Lorg/joml/Vector4f;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Vector4f getRecallBeamColour() {
            return recallBeamColour;
        }

        public final double ease(double x) {
            return 1.0 - Math.pow(1.0 - x, 3);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

