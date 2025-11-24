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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001f\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010@\u001a\u00020\u0015\u00a2\u0006\u0004\bA\u0010BJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR2\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R2\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u0010\u001a\u0004\b\u001b\u0010\u0012\"\u0004\b\u001c\u0010\u0014R\"\u0010\u001e\u001a\u00020\u001d8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010%\u001a\u00020$8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\"\u0010+\u001a\u00020\u001d8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010\u001f\u001a\u0004\b,\u0010!\"\u0004\b-\u0010#R\"\u0010.\u001a\u00020$8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010&\u001a\u0004\b/\u0010(\"\u0004\b0\u0010*R\u001a\u00101\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b1\u0010\u0017\u001a\u0004\b2\u0010\u0019R2\u00103\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b3\u0010\u0010\u001a\u0004\b4\u0010\u0012\"\u0004\b5\u0010\u0014R2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u0010\u0010\u001a\u0004\b7\u0010\u0012\"\u0004\b8\u0010\u0014R2\u00109\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b9\u0010\u0010\u001a\u0004\b:\u0010\u0012\"\u0004\b;\u0010\u0014R\u0014\u0010<\u001a\u00020\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010\u0017R\u0014\u0010=\u001a\u00020\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010\u0017R\u0014\u0010>\u001a\u00020\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010\u0017R\u0014\u0010?\u001a\u00020\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010\u0017\u00a8\u0006C"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/PidgeyModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "fly", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFly", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFly", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "hover", "getHover", "setHover", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "stand", "getStand", "setStand", "walk", "getWalk", "setWalk", "wingClosedLeft", "wingClosedRight", "wingOpenLeft", "wingOpenRight", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class PidgeyModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart wingOpenRight;
    @NotNull
    private final ModelPart wingOpenLeft;
    @NotNull
    private final ModelPart wingClosedRight;
    @NotNull
    private final ModelPart wingClosedLeft;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> stand;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> hover;
    public Pose<PokemonEntity, ModelFrame> fly;
    @NotNull
    private final CryProvider cryAnimation;

    public PidgeyModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "pidgey");
        this.head = this.getPart("head");
        this.wingOpenRight = this.getPart("wing_open_right");
        this.wingOpenLeft = this.getPart("wing_open_left");
        this.wingClosedRight = this.getPart("wing_closed_right");
        this.wingClosedLeft = this.getPart("wing_closed_left");
        this.portraitScale = 3.5f;
        this.portraitTranslation = new Vec3(-0.1, -2.1, 0.0);
        this.profileScale = 1.2f;
        this.profileTranslation = new Vec3(0.0, -0.01, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> PidgeyModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public final Pose<PokemonEntity, ModelFrame> getStand() {
        Pose<PokemonEntity, ModelFrame> pose = this.stand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"stand");
        return null;
    }

    public final void setStand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.stand = pose;
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
    public final Pose<PokemonEntity, ModelFrame> getHover() {
        Pose<PokemonEntity, ModelFrame> pose = this.hover;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"hover");
        return null;
    }

    public final void setHover(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.hover = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getFly() {
        Pose<PokemonEntity, ModelFrame> pose = this.fly;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"fly");
        return null;
    }

    public final void setFly(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.fly = pose;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ PidgeyModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "pidgey", "blink", null, 4, null);
            }
        }, 7, null);
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)};
        Object object = modelPartTransformationArray;
        modelPartTransformationArray = PoseType.SLEEP;
        Object[] objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "pidgey", "sleep_PLACEHOLDER", null, 4, null)};
        Object[] objectArray2 = objectArray;
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleeping", (PoseType)modelPartTransformationArray, null, 0, null, null, (StatelessAnimation[])objectArray2, object, null, 316, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSHOULDER_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"SHOULDER_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        Set set3 = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.STATIONARY_POSES");
        object = SetsKt.minus((Set)SetsKt.plus((Set)set3, (Iterable)enumSet3), (Object)((Object)PoseType.HOVER));
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)};
        modelPartTransformationArray = objectArray2;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "pidgey", "ground_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setStand(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray, modelPartTransformationArray, (ModelQuirk[])objectArray2, 52, null));
        object = PoseType.HOVER;
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)};
        modelPartTransformationArray = objectArray2;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "pidgey", "air_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setHover(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hover", (PoseType)((Object)object), null, 10, null, null, (StatelessAnimation[])objectArray, modelPartTransformationArray, (ModelQuirk[])objectArray2, 52, null));
        object = PoseType.FLY;
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(true)};
        modelPartTransformationArray = objectArray2;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "pidgey", "air_fly", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setFly(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "fly", (PoseType)((Object)object), null, 10, null, null, (StatelessAnimation[])objectArray, modelPartTransformationArray, (ModelQuirk[])objectArray2, 52, null));
        EnumSet<PoseType> enumSet4 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet4, (String)"PoseType.MOVING_POSES");
        object = SetsKt.minus((Set)enumSet4, (Object)((Object)PoseType.FLY));
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wingClosedLeft).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingClosedRight).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.wingOpenLeft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wingOpenRight).withVisibility(false)};
        modelPartTransformationArray = objectArray2;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "pidgey", "ground_walk", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray, modelPartTransformationArray, (ModelQuirk[])objectArray2, 52, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(PidgeyModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "pidgey", "cry", null, 4, null);
    }
}

