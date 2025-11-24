/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b;\u0010<J3\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ3\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016\u00a2\u0006\u0004\b\n\u0010\tJ\u0019\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012JM\u0010\u001b\u001a\u00020\u001a\"\b\b\u0000\u0010\u0013*\u00020\u00072\b\b\u0002\u0010\u0014\u001a\u00020\r2\u001a\u0010\u0017\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\b\u0001\u0012\u00028\u00000\u00160\u00152\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0015\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0019\u0010\u001d\u001a\u00020\u001a2\b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u001a\u0010 \u001a\u00020\u001f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u001a\u0010%\u001a\u00020$8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b%\u0010'R\"\u0010)\u001a\u00020(8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010\u0013\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\"\u0010/\u001a\u00020.8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b/\u00100\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\"\u00105\u001a\u00020(8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b5\u0010\u0013\u001a\u0004\b6\u0010+\"\u0004\b7\u0010-R\"\u00108\u001a\u00020.8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b8\u00100\u001a\u0004\b9\u00102\"\u0004\b:\u00104\u00a8\u0006="}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "getEatAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "getFaintAnimation", "Lnet/minecraft/world/entity/Entity;", "entity", "", "getOverlayTexture", "(Lnet/minecraft/world/entity/Entity;)Ljava/lang/Integer;", "Lcom/cobblemon/mod/common/client/entity/PokemonClientDelegate;", "getState", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Lcom/cobblemon/mod/common/client/entity/PokemonClientDelegate;", "F", "transformTicks", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "idleAnimations", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "transformedParts", "", "registerShoulderPoses", "(I[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;[Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;)V", "setupEntityTypeContext", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "", "isForLivingEntityRenderer", "Z", "()Z", "", "portraitScale", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonPoseableModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,106:1\n26#2:107\n1#3:108\n*S KotlinDebug\n*F\n+ 1 PokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel\n*L\n41#1:107\n*E\n"})
public abstract class PokemonPoseableModel
extends PoseableEntityModel<PokemonEntity> {
    private final boolean isForLivingEntityRenderer;
    private transient float portraitScale = 1.0f;
    @NotNull
    private transient Vec3 portraitTranslation;
    private transient float profileScale;
    @NotNull
    private transient Vec3 profileTranslation;
    @NotNull
    private final CryProvider cryAnimation;

    public PokemonPoseableModel() {
        super(null, 1, null);
        this.isForLivingEntityRenderer = true;
        Vec3 vec3 = Vec3.f_82478_;
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"ZERO");
        this.portraitTranslation = vec3;
        this.profileScale = 1.0f;
        Vec3 vec32 = Vec3.f_82478_;
        Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"ZERO");
        this.profileTranslation = vec32;
        this.cryAnimation = PokemonPoseableModel::cryAnimation$lambda$0;
    }

    @Override
    public boolean isForLivingEntityRenderer() {
        return this.isForLivingEntityRenderer;
    }

    @NotNull
    public PokemonClientDelegate getState(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        PokemonSideDelegate pokemonSideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull((Object)pokemonSideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate");
        return (PokemonClientDelegate)pokemonSideDelegate;
    }

    public final <F extends ModelFrame> void registerShoulderPoses(int transformTicks, @NotNull StatelessAnimation<PokemonEntity, ? extends F>[] idleAnimations2, @NotNull ModelPartTransformation[] transformedParts) {
        Intrinsics.checkNotNullParameter(idleAnimations2, (String)"idleAnimations");
        Intrinsics.checkNotNullParameter((Object)transformedParts, (String)"transformedParts");
        PoseableEntityModel.registerPose$default(this, PoseType.SHOULDER_LEFT, null, transformTicks, null, null, idleAnimations2, transformedParts, null, 154, null);
        PoseableEntityModel.registerPose$default(this, PoseType.SHOULDER_RIGHT, null, transformTicks, null, null, idleAnimations2, transformedParts, null, 154, null);
    }

    public static /* synthetic */ void registerShoulderPoses$default(PokemonPoseableModel pokemonPoseableModel, int n, StatelessAnimation[] statelessAnimationArray, ModelPartTransformation[] modelPartTransformationArray, int n2, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerShoulderPoses");
        }
        if ((n2 & 1) != 0) {
            n = 30;
        }
        if ((n2 & 4) != 0) {
            boolean $i$f$emptyArray = false;
            modelPartTransformationArray = new ModelPartTransformation[]{};
        }
        pokemonPoseableModel.registerShoulderPoses(n, statelessAnimationArray, modelPartTransformationArray);
    }

    public float getPortraitScale() {
        return this.portraitScale;
    }

    public void setPortraitScale(float f) {
        this.portraitScale = f;
    }

    @NotNull
    public Vec3 getPortraitTranslation() {
        return this.portraitTranslation;
    }

    public void setPortraitTranslation(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
        this.portraitTranslation = vec3;
    }

    public float getProfileScale() {
        return this.profileScale;
    }

    public void setProfileScale(float f) {
        this.profileScale = f;
    }

    @NotNull
    public Vec3 getProfileTranslation() {
        return this.profileTranslation;
    }

    public void setProfileTranslation(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
        this.profileTranslation = vec3;
    }

    @Nullable
    public StatefulAnimation<PokemonEntity, ModelFrame> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        return null;
    }

    @Nullable
    public StatefulAnimation<PokemonEntity, ModelFrame> getEatAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        return null;
    }

    @Override
    @NotNull
    public Integer getOverlayTexture(@Nullable Entity entity2) {
        return entity2 instanceof PokemonEntity ? OverlayTexture.m_118093_((int)OverlayTexture.m_118088_((float)0.0f), (int)OverlayTexture.m_118096_((((PokemonEntity)entity2).f_20916_ > 0 ? 1 : 0) != 0)) : OverlayTexture.f_118083_;
    }

    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void setupEntityTypeContext(@Nullable PokemonEntity entity2) {
        block0: {
            PokemonEntity pokemonEntity = entity2;
            if (pokemonEntity == null) break block0;
            PokemonEntity it = pokemonEntity;
            boolean bl = false;
            this.getContext().put(RenderContext.Companion.getSCALE(), Float.valueOf(it.getPokemon().getForm().getBaseScale()));
            this.getContext().put(RenderContext.Companion.getSPECIES(), it.getPokemon().getSpecies().getResourceIdentifier());
            this.getContext().put(RenderContext.Companion.getASPECTS(), it.getPokemon().getAspects());
            ResourceLocation texture = PokemonModelRepository.INSTANCE.getTexture(it.getPokemon().getSpecies().getResourceIdentifier(), it.getPokemon().getAspects(), 0.0f);
            boolean bl2 = false;
            this.getContext().put(RenderContext.Companion.getTEXTURE(), texture);
        }
    }

    private static final StatefulAnimation cryAnimation$lambda$0(PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return null;
    }
}

