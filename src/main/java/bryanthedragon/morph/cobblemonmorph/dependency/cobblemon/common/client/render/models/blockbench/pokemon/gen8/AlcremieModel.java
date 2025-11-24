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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0014\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\u0012\u0006\u0010G\u001a\u00020\u000e\u00a2\u0006\u0004\bH\u0010IJ-\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\u00020\u000e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0014\u0010\u0012R\"\u0010\u0016\u001a\u00020\u00158\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010#\u001a\u00020\u00158\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\u0017\u001a\u0004\b$\u0010\u0019\"\u0004\b%\u0010\u001bR\"\u0010&\u001a\u00020\u001c8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010\u001e\u001a\u0004\b'\u0010 \"\u0004\b(\u0010\"R\u001a\u0010)\u001a\u00020\u000e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010\u0010\u001a\u0004\b*\u0010\u0012R\u001a\u0010+\u001a\u00020\u000e8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b+\u0010\u0010\u001a\u0004\b,\u0010\u0012R2\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001a\u00107\u001a\u0002068\u0006X\u0086D\u00a2\u0006\f\n\u0004\b7\u00108\u001a\u0004\b9\u0010:R2\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b;\u00101\u001a\u0004\b<\u00103\"\u0004\b=\u00105R2\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u00101\u001a\u0004\b?\u00103\"\u0004\b@\u00105R2\u0010A\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u00101\u001a\u0004\bB\u00103\"\u0004\bC\u00105R2\u0010D\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bD\u00101\u001a\u0004\bE\u00103\"\u0004\bF\u00105\u00a8\u0006J"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen8/AlcremieModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "", "registerPoses", "()V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftArm", "getLeftArm", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightArm", "getRightArm", "rootPart", "getRootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "shoulderLeft", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getShoulderLeft", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setShoulderLeft", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "", "shoulderOffset", "D", "getShoulderOffset", "()D", "shoulderRight", "getShoulderRight", "setShoulderRight", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class AlcremieModel
extends PokemonPoseableModel
implements HeadedFrame,
BimanualFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart rightArm;
    @NotNull
    private final ModelPart leftArm;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> shoulderLeft;
    public Pose<PokemonEntity, ModelFrame> shoulderRight;
    private final double shoulderOffset;

    public AlcremieModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "alcremie");
        this.head = this.getPart("head");
        this.rightArm = this.getPart("arm_right");
        this.leftArm = this.getPart("arm_left");
        this.portraitScale = 2.14f;
        this.portraitTranslation = new Vec3(-0.25, -0.31, 0.0);
        this.profileScale = 0.54f;
        this.profileTranslation = new Vec3(0.0, 0.88, 0.0);
        this.shoulderOffset = 5.5;
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
    public ModelPart getRightArm() {
        return this.rightArm;
    }

    @Override
    @NotNull
    public ModelPart getLeftArm() {
        return this.leftArm;
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
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ AlcremieModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "alcremie", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "alcremie", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", PoseType.SLEEP, null, 0, null, null, object, null, null, 444, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"UI_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.STATIONARY_POSES");
        object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "alcremie", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 180, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "alcremie", "ground_walk", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel, "walk", (Set)object, null, 5, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 180, null));
        object = PoseType.SHOULDER_LEFT;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "alcremie", "shoulder_left", null, 4, null)};
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, this.shoulderOffset)};
        objectArray3 = modelPartTransformationArray;
        this.setShoulderLeft(PoseableEntityModel.registerPose$default(this, (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 30, null));
        object = PoseType.SHOULDER_RIGHT;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "alcremie", "shoulder_right", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -this.shoulderOffset)};
        objectArray3 = modelPartTransformationArray;
        this.setShoulderRight(PoseableEntityModel.registerPose$default(this, (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 30, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getSleep()};
        return state.isNotPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "alcremie", "faint", null, 4, null) : null;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

