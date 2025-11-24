/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\u001aG\u0010\r\u001a\u00020\f2\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t\u00a2\u0006\u0004\b\r\u0010\u000e\u001aU\u0010\r\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t\u00a2\u0006\u0004\b\r\u0010\u0014\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "renderablePokemon", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "Lorg/joml/Quaternionf;", "rotation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "state", "", "partialTicks", "scale", "", "drawProfilePokemon", "(Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Quaternionf;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)V", "Lnet/minecraft/resources/ResourceLocation;", "species", "", "", "aspects", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Quaternionf;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FF)V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonGuiUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonGuiUtils.kt\ncom/cobblemon/mod/common/client/gui/PokemonGuiUtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,102:1\n1#2:103\n*E\n"})
public final class PokemonGuiUtilsKt {
    public static final void drawProfilePokemon(@NotNull RenderablePokemon renderablePokemon, @NotNull PoseStack matrixStack, @NotNull Quaternionf rotation, @Nullable PoseableEntityState<PokemonEntity> state, float partialTicks, float scale) {
        Intrinsics.checkNotNullParameter((Object)renderablePokemon, (String)"renderablePokemon");
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        PokemonGuiUtilsKt.drawProfilePokemon(renderablePokemon.getSpecies().getResourceIdentifier(), renderablePokemon.getAspects(), matrixStack, rotation, state, partialTicks, scale);
    }

    public static /* synthetic */ void drawProfilePokemon$default(RenderablePokemon renderablePokemon, PoseStack poseStack, Quaternionf quaternionf, PoseableEntityState poseableEntityState, float f, float f2, int n, Object object) {
        if ((n & 0x20) != 0) {
            f2 = 20.0f;
        }
        PokemonGuiUtilsKt.drawProfilePokemon(renderablePokemon, poseStack, quaternionf, poseableEntityState, f, f2);
    }

    public static final void drawProfilePokemon(@NotNull ResourceLocation species, @NotNull Set<String> aspects, @NotNull PoseStack matrixStack, @NotNull Quaternionf rotation, @Nullable PoseableEntityState<PokemonEntity> state, float partialTicks, float scale) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(species, aspects);
        PoseableEntityState<PokemonEntity> poseableEntityState = state;
        ResourceLocation texture = PokemonModelRepository.INSTANCE.getTexture(species, aspects, poseableEntityState != null ? poseableEntityState.getAnimationSeconds() : 0.0f);
        RenderContext context = new RenderContext();
        ResourceLocation it = PokemonModelRepository.INSTANCE.getTextureNoSubstitute(species, aspects, 0.0f);
        boolean bl = false;
        context.put(RenderContext.Companion.getTEXTURE(), it);
        RenderContext.Key<Float> key = RenderContext.Companion.getSCALE();
        Species species2 = PokemonSpecies.INSTANCE.getByIdentifier(species);
        Intrinsics.checkNotNull((Object)species2);
        context.put(key, Float.valueOf(species2.getForm(aspects).getBaseScale()));
        context.put(RenderContext.Companion.getSPECIES(), species);
        context.put(RenderContext.Companion.getASPECTS(), aspects);
        RenderType renderType = model.m_103119_(texture);
        RenderSystem.applyModelViewMatrix();
        matrixStack.m_85841_(scale, scale, -scale);
        if (state != null) {
            Pose pose = model.getPose(PoseType.PROFILE);
            if (pose != null) {
                Pose it2 = pose;
                boolean bl2 = false;
                state.setPose(it2.getPoseName());
            }
            state.setTimeEnteredPose(0.0f);
            state.updatePartialTicks(partialTicks);
            model.setupAnimStateful(null, state, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            PoseableEntityModel.setupAnimStateless$default((PoseableEntityModel)model, PoseType.PROFILE, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 62, null);
        }
        matrixStack.m_85837_(model.getProfileTranslation().f_82479_, model.getProfileTranslation().f_82480_, model.getProfileTranslation().f_82481_ - 4.0);
        matrixStack.m_85841_(model.getProfileScale(), model.getProfileScale(), 1.0f / model.getProfileScale());
        matrixStack.m_252781_(rotation);
        Lighting.m_166384_();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.m_91087_().m_91290_();
        rotation.conjugate();
        entityRenderDispatcher.m_252923_(rotation);
        entityRenderDispatcher.m_114468_(true);
        MultiBufferSource.BufferSource bufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
        VertexConsumer buffer = bufferSource.m_6299_(renderType);
        Vector3f light1 = new Vector3f(-1.0f, 1.0f, 1.0f);
        Vector3f light2 = new Vector3f(1.3f, -1.0f, 1.0f);
        RenderSystem.setShaderLights((Vector3f)light1, (Vector3f)light2);
        int packedLight = LightTexture.m_109885_((int)11, (int)7);
        Intrinsics.checkNotNullExpressionValue((Object)bufferSource, (String)"bufferSource");
        model.withLayerContext((MultiBufferSource)bufferSource, state, PokemonModelRepository.INSTANCE.getLayers(species, aspects), (Function0<Unit>)((Function0)new Function0<Unit>(model, context, matrixStack, buffer, packedLight, bufferSource){
            final /* synthetic */ PokemonPoseableModel $model;
            final /* synthetic */ RenderContext $context;
            final /* synthetic */ PoseStack $matrixStack;
            final /* synthetic */ VertexConsumer $buffer;
            final /* synthetic */ int $packedLight;
            final /* synthetic */ MultiBufferSource.BufferSource $bufferSource;
            {
                this.$model = $model;
                this.$context = $context;
                this.$matrixStack = $matrixStack;
                this.$buffer = $buffer;
                this.$packedLight = $packedLight;
                this.$bufferSource = $bufferSource;
                super(0);
            }

            public final void invoke() {
                VertexConsumer vertexConsumer = this.$buffer;
                Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"buffer");
                this.$model.render(this.$context, this.$matrixStack, vertexConsumer, this.$packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
                this.$bufferSource.m_109911_();
            }
        }));
        model.setDefault();
        entityRenderDispatcher.m_114468_(true);
        Lighting.m_84931_();
    }

    public static /* synthetic */ void drawProfilePokemon$default(ResourceLocation resourceLocation, Set set2, PoseStack poseStack, Quaternionf quaternionf, PoseableEntityState poseableEntityState, float f, float f2, int n, Object object) {
        if ((n & 0x40) != 0) {
            f2 = 20.0f;
        }
        PokemonGuiUtilsKt.drawProfilePokemon(resourceLocation, set2, poseStack, quaternionf, poseableEntityState, f, f2);
    }
}

