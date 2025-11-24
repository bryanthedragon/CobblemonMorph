/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.EnumSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010?\u001a\u00020 \u00a2\u0006\u0004\b@\u0010AJ-\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR\"\u0010\r\u001a\u00020\f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\"\u0010\u0014\u001a\u00020\u00138\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\"\u0010\u001a\u001a\u00020\f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u001b\u0010\u0010\"\u0004\b\u001c\u0010\u0012R\"\u0010\u001d\u001a\u00020\u00138\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u0015\u001a\u0004\b\u001e\u0010\u0017\"\u0004\b\u001f\u0010\u0019R\u001a\u0010!\u001a\u00020 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R2\u0010(\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020&0%j\u0002`'8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001a\u0010/\u001a\u00020.8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R2\u00103\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020&0%j\u0002`'8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b3\u0010)\u001a\u0004\b4\u0010+\"\u0004\b5\u0010-R2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020&0%j\u0002`'8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u0010)\u001a\u0004\b7\u0010+\"\u0004\b8\u0010-R2\u00109\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020&0%j\u0002`'8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b9\u0010)\u001a\u0004\b:\u0010+\"\u0004\b;\u0010-R2\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020&0%j\u0002`'8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b<\u0010)\u001a\u0004\b=\u0010+\"\u0004\b>\u0010-\u00a8\u0006B"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/CutieflyModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "", "registerPoses", "()V", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "shoulderLeft", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getShoulderLeft", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setShoulderLeft", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "", "shoulderOffset", "D", "getShoulderOffset", "()D", "shoulderRight", "getShoulderRight", "setShoulderRight", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class CutieflyModel
extends PokemonPoseableModel {
    @NotNull
    private final ModelPart rootPart;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> shoulderLeft;
    public Pose<PokemonEntity, ModelFrame> shoulderRight;
    private final double shoulderOffset;

    public CutieflyModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "cutiefly");
        this.portraitScale = 2.4f;
        this.portraitTranslation = new Vec3(-0.3, 1.5, 0.0);
        this.profileScale = 0.7f;
        this.profileTranslation = new Vec3(0.0, 1.2, 0.0);
        this.shoulderOffset = 12.5;
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @Override
    public float getPortraitScale() {
        return this.portraitScale;
    }

    @Override
    public void setPortraitScale(float f) {
        this.portraitScale = f;
    }

    @Override
    @NotNull
    public Vec3 getPortraitTranslation() {
        return this.portraitTranslation;
    }

    @Override
    public void setPortraitTranslation(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
        this.portraitTranslation = vec3;
    }

    @Override
    public float getProfileScale() {
        return this.profileScale;
    }

    @Override
    public void setProfileScale(float f) {
        this.profileScale = f;
    }

    @Override
    @NotNull
    public Vec3 getProfileTranslation() {
        return this.profileTranslation;
    }

    @Override
    public void setProfileTranslation(@NotNull Vec3 vec3) {
        Intrinsics.checkNotNullParameter((Object)vec3, (String)"<set-?>");
        this.profileTranslation = vec3;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getStanding() {
        Pose<PokemonEntity, ModelFrame> pose = this.standing;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"standing");
        return null;
    }

    public final void setStanding(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.standing = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWalk() {
        Pose<PokemonEntity, ModelFrame> pose = this.walk;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"walk");
        return null;
    }

    public final void setWalk(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.walk = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.sleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"sleep");
        return null;
    }

    public final void setSleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.sleep = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getShoulderLeft() {
        Pose<PokemonEntity, ModelFrame> pose = this.shoulderLeft;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"shoulderLeft");
        return null;
    }

    public final void setShoulderLeft(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.shoulderLeft = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getShoulderRight() {
        Pose<PokemonEntity, ModelFrame> pose = this.shoulderRight;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"shoulderRight");
        return null;
    }

    public final void setShoulderRight(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.shoulderRight = pose;
    }

    public final double getShoulderOffset() {
        return this.shoulderOffset;
    }

    @Override
    public void registerPoses() {
        Object[] objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cutiefly", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, null, 0, null, null, (StatelessAnimation[])objectArray, null, null, 222, null));
        PoseableEntityModel poseableEntityModel = this;
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cutiefly", "ground_idle", null, 4, null)};
        StatelessAnimation[] statelessAnimationArray = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, Float.valueOf(-12.0f))};
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel, "standing", SetsKt.plus((Set)set2, (Iterable)enumSet2), null, 0, null, null, statelessAnimationArray, (ModelPartTransformation[])objectArray, null, 316, null));
        PoseableEntityModel poseableEntityModel2 = this;
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cutiefly", "ground_walk", null, 4, null)};
        Object[] objectArray2 = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, Float.valueOf(-12.0f))};
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel2, "walk", enumSet3, null, 0, null, null, (StatelessAnimation[])objectArray2, (ModelPartTransformation[])objectArray, null, 316, null));
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cutiefly", "ground_idle", null, 4, null)};
        Object[] objectArray3 = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(this.shoulderOffset, -4, 0)};
        this.setShoulderLeft(PoseableEntityModel.registerPose$default(this, PoseType.SHOULDER_LEFT, null, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, null, 158, null));
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "cutiefly", "ground_idle", null, 4, null)};
        Object[] objectArray4 = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(-this.shoulderOffset, -4, 0)};
        this.setShoulderRight(PoseableEntityModel.registerPose$default(this, PoseType.SHOULDER_RIGHT, null, 0, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])objectArray, null, 158, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getStanding(), this.getWalk(), this.getSleep()};
        return state.isPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "cutiefly", "faint", null, 4, null) : null;
    }
}

