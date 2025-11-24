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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1.LickitungModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001c\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\u0006\u0010;\u001a\u00020\u0012\u00a2\u0006\u0004\b<\u0010=J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R2\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0018\u0010\u0016R\u001a\u0010\u0019\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u001a\u0010\u0016R\"\u0010\u001c\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010#\u001a\u00020\"8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\"\u0010)\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010\u001d\u001a\u0004\b*\u0010\u001f\"\u0004\b+\u0010!R\"\u0010,\u001a\u00020\"8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010$\u001a\u0004\b-\u0010&\"\u0004\b.\u0010(R\u001a\u0010/\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u0010\u0014\u001a\u0004\b0\u0010\u0016R\u001a\u00101\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b1\u0010\u0014\u001a\u0004\b2\u0010\u0016R\u001a\u00103\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b3\u0010\u0014\u001a\u0004\b4\u0010\u0016R2\u00105\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b5\u0010\r\u001a\u0004\b6\u0010\u000f\"\u0004\b7\u0010\u0011R2\u00108\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u0010\r\u001a\u0004\b9\u0010\u000f\"\u0004\b:\u0010\u0011\u00a8\u0006>"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/LickitungModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftArm", "getLeftArm", "leftLeg", "getLeftLeg", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightArm", "getRightArm", "rightLeg", "getRightLeg", "rootPart", "getRootPart", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class LickitungModel
extends PokemonPoseableModel
implements HeadedFrame,
BipedFrame,
BimanualFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart leftLeg;
    @NotNull
    private final ModelPart rightLeg;
    @NotNull
    private final ModelPart leftArm;
    @NotNull
    private final ModelPart rightArm;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> battleidle;

    public LickitungModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "lickitung");
        this.head = this.getPart("head");
        this.leftLeg = this.getPart("left_leg");
        this.rightLeg = this.getPart("right_leg");
        this.leftArm = this.getPart("left_upper_arm");
        this.rightArm = this.getPart("right_upper_arm");
        this.portraitScale = 2.1f;
        this.portraitTranslation = new Vec3(-0.1, 0.0, 0.0);
        this.profileScale = 0.8f;
        this.profileTranslation = new Vec3(0.0, 0.55, 0.0);
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
    @NotNull
    public ModelPart getLeftLeg() {
        return this.leftLeg;
    }

    @Override
    @NotNull
    public ModelPart getRightLeg() {
        return this.rightLeg;
    }

    @Override
    @NotNull
    public ModelPart getLeftArm() {
        return this.leftArm;
    }

    @Override
    @NotNull
    public ModelPart getRightArm() {
        return this.rightArm;
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
    public final Pose<PokemonEntity, ModelFrame> getBattleidle() {
        Pose<PokemonEntity, ModelFrame> pose = this.battleidle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battleidle");
        return null;
    }

    public final void setBattleidle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.battleidle = pose;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ LickitungModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "lickitung", "blink", null, 4, null);
            }
        }, 7, null);
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        EnumSet<PoseType> enumSet3 = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "lickitung", "ground_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", enumSet3, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        enumSet3 = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "lickitung", "ground_walk", null, 4, null)};
        objectArray = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel, "walk", enumSet3, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        enumSet3 = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "lickitung", "battle_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel2, "battle_idle", enumSet3, (Function1)registerPoses.1.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

