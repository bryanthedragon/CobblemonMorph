/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1.DratiniModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.EnumSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001c\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010=\u001a\u00020\u0006\u00a2\u0006\u0004\b>\u0010?J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\"\u0010\f\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\"\u0010\u0013\u001a\u00020\u00128\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\r\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\"\u0010\u001c\u001a\u00020\u00128\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u0014\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u0018R\u001a\u0010\u001f\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\b\u001a\u0004\b \u0010\nR2\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R2\u0010+\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b+\u0010&\u001a\u0004\b,\u0010(\"\u0004\b-\u0010*R2\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b.\u0010&\u001a\u0004\b/\u0010(\"\u0004\b0\u0010*R2\u00101\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b1\u0010&\u001a\u0004\b2\u0010(\"\u0004\b3\u0010*R2\u00104\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b4\u0010&\u001a\u0004\b5\u0010(\"\u0004\b6\u0010*R2\u00107\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b7\u0010&\u001a\u0004\b8\u0010(\"\u0004\b9\u0010*R2\u0010:\u001a\u0012\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u0002`$8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b:\u0010&\u001a\u0004\b;\u0010(\"\u0004\b<\u0010*\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/DratiniModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "sleep", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getSleep", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setSleep", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "standing", "getStanding", "setStanding", "surface_swim", "getSurface_swim", "setSurface_swim", "walking", "getWalking", "setWalking", "water_idle", "getWater_idle", "setWater_idle", "water_sleep", "getWater_sleep", "setWater_sleep", "water_swim", "getWater_swim", "setWater_swim", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class DratiniModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walking;
    public Pose<PokemonEntity, ModelFrame> water_idle;
    public Pose<PokemonEntity, ModelFrame> water_swim;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> water_sleep;
    public Pose<PokemonEntity, ModelFrame> surface_swim;

    public DratiniModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "dratini");
        this.head = this.getPart("head");
        this.portraitScale = 1.66f;
        this.portraitTranslation = new Vec3(-0.48, 0.35, 0.0);
        this.profileScale = 0.58f;
        this.profileTranslation = new Vec3(0.14, 0.83, 0.0);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @NotNull
    public ModelPart getHead() {
        return this.head;
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
    public final Pose<PokemonEntity, ModelFrame> getWalking() {
        Pose<PokemonEntity, ModelFrame> pose = this.walking;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"walking");
        return null;
    }

    public final void setWalking(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.walking = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_idle() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_idle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_idle");
        return null;
    }

    public final void setWater_idle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_idle = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_swim() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_swim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_swim");
        return null;
    }

    public final void setWater_swim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_swim = pose;
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
    public final Pose<PokemonEntity, ModelFrame> getWater_sleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_sleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_sleep");
        return null;
    }

    public final void setWater_sleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_sleep = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSurface_swim() {
        Pose<PokemonEntity, ModelFrame> pose = this.surface_swim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"surface_swim");
        return null;
    }

    public final void setSurface_swim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.surface_swim = pose;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ DratiniModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "dratini", "blink", null, 4, null);
            }
        }, 7, null);
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dratini", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", PoseType.SLEEP, (Function1)registerPoses.1.INSTANCE, 0, null, null, statelessAnimationArray, null, null, 440, null));
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dratini", "water_sleep", null, 4, null)};
        this.setWater_sleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_sleep", PoseType.SLEEP, (Function1)registerPoses.2.INSTANCE, 0, null, null, statelessAnimationArray, null, null, 440, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        statelessAnimationArray = SetsKt.minus((Set)SetsKt.plus((Set)set2, (Iterable)enumSet2), (Object)((Object)PoseType.FLOAT));
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        StatelessAnimation[] statelessAnimationArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dratini", "ground_idle", null, 4, null)};
        objectArray = statelessAnimationArray2;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)statelessAnimationArray, null, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 180, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        statelessAnimationArray = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.SWIM));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray2 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dratini", "ground_walk", null, 4, null)};
        objectArray = statelessAnimationArray2;
        this.setWalking(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)statelessAnimationArray, null, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 180, null));
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dratini", "water_idle", null, 4, null)};
        this.setWater_idle(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_idle", PoseType.FLOAT, null, 10, null, null, statelessAnimationArray, null, null, 436, null));
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dratini", "water_swim", null, 4, null)};
        this.setWater_swim(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_swim", PoseType.SWIM, null, 10, null, null, statelessAnimationArray, null, null, 436, null));
        PoseableEntityModel poseableEntityModel = this;
        EnumSet<PoseType> enumSet4 = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet4, (String)"PoseType.STATIONARY_POSES");
        Set set3 = enumSet4;
        EnumSet<PoseType> enumSet5 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet5, (String)"PoseType.MOVING_POSES");
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dratini", "surfacewater_swim", null, 4, null)};
        this.setSurface_swim(PoseableEntityModel.registerPose$default(poseableEntityModel, "surface_swim", SetsKt.plus((Set)set3, (Iterable)enumSet5), (Function1)registerPoses.3.INSTANCE, 10, null, null, statelessAnimationArray, null, null, 432, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

