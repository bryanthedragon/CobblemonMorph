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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7.WishiwashiSchoolingModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u00109\u001a\u00020(\u00a2\u0006\u0004\b:\u0010;J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R2\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\"\u0010\u0015\u001a\u00020\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010\"\u001a\u00020\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0018\"\u0004\b$\u0010\u001aR\"\u0010%\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u001d\u001a\u0004\b&\u0010\u001f\"\u0004\b'\u0010!R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R2\u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b-\u0010\n\u001a\u0004\b.\u0010\f\"\u0004\b/\u0010\u000eR2\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b0\u0010\n\u001a\u0004\b1\u0010\f\"\u0004\b2\u0010\u000eR2\u00103\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b3\u0010\n\u001a\u0004\b4\u0010\f\"\u0004\b5\u0010\u000eR2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u0010\n\u001a\u0004\b7\u0010\f\"\u0004\b8\u0010\u000e\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/WishiwashiSchoolingModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "floating", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFloating", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFloating", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "", "offsetY", "D", "getOffsetY", "()D", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "standing", "getStanding", "setStanding", "swimming", "getSwimming", "setSwimming", "walk", "getWalk", "setWalk", "watersleep", "getWatersleep", "setWatersleep", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class WishiwashiSchoolingModel
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
    public Pose<PokemonEntity, ModelFrame> floating;
    public Pose<PokemonEntity, ModelFrame> swimming;
    public Pose<PokemonEntity, ModelFrame> watersleep;
    private final double offsetY;

    public WishiwashiSchoolingModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "wishiwashi_school");
        this.portraitScale = 0.5f;
        this.portraitTranslation = new Vec3(-0.4, 0.8, 0.0);
        this.profileScale = 0.2f;
        this.profileTranslation = new Vec3(0.0, 1.0, 0.0);
        this.offsetY = -8.0;
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
    public final Pose<PokemonEntity, ModelFrame> getFloating() {
        Pose<PokemonEntity, ModelFrame> pose = this.floating;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"floating");
        return null;
    }

    public final void setFloating(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.floating = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSwimming() {
        Pose<PokemonEntity, ModelFrame> pose = this.swimming;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"swimming");
        return null;
    }

    public final void setSwimming(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.swimming = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWatersleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.watersleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"watersleep");
        return null;
    }

    public final void setWatersleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.watersleep = pose;
    }

    public final double getOffsetY() {
        return this.offsetY;
    }

    @Override
    public void registerPoses() {
        Object[] objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_school", "water_sleep", null, 4, null)};
        this.setWatersleep(PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, registerPoses.1.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray, null, null, 220, null));
        PoseableEntityModel poseableEntityModel = this;
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTANDING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STANDING_POSES");
        Set set2 = SetsKt.minus((Set)enumSet, (Object)((Object)PoseType.FLOAT));
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_school", "water_idle", null, 4, null)};
        StatelessAnimation[] statelessAnimationArray = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0.0, this.offsetY, 0.0)};
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel, "standing", SetsKt.plus((Set)set2, (Iterable)enumSet2), null, 0, null, null, statelessAnimationArray, (ModelPartTransformation[])objectArray, null, 316, null));
        PoseableEntityModel poseableEntityModel2 = this;
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.MOVING_POSES");
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_school", "water_swim", null, 4, null)};
        Object[] objectArray2 = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0.0, this.offsetY, 0.0)};
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel2, "walking", SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.SWIM)), null, 0, null, null, (StatelessAnimation[])objectArray2, (ModelPartTransformation[])objectArray, null, 316, null));
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_school", "water_idle", null, 4, null)};
        this.setFloating(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "floating", PoseType.FLOAT, null, 0, null, null, (StatelessAnimation[])objectArray, null, null, 444, null));
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_school", "water_swim", null, 4, null)};
        this.setSwimming(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "swimming", PoseType.SWIM, null, 0, null, null, (StatelessAnimation[])objectArray, null, null, 444, null));
    }
}

