/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen5.VolcaronaModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.EnumSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010;\u001a\u00020\u000b\u00a2\u0006\u0004\b<\u0010=J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u001a\u0010\f\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000fR\"\u0010\u0015\u001a\u00020\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010\"\u001a\u00020\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0018\"\u0004\b$\u0010\u001aR\"\u0010%\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u001d\u001a\u0004\b&\u0010\u001f\"\u0004\b'\u0010!R\u0017\u0010(\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b(\u0010\r\u001a\u0004\b)\u0010\u000fR\u0017\u0010*\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b*\u0010\r\u001a\u0004\b+\u0010\u000fR\u001a\u0010,\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010\r\u001a\u0004\b-\u0010\u000fR2\u00102\u001a\u0012\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002000.j\u0002`18\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107R2\u00108\u001a\u0012\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u0002000.j\u0002`18\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u00103\u001a\u0004\b9\u00105\"\u0004\b:\u00107\u00a8\u0006>"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen5/VolcaronaModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftfluff", "getLeftfluff", "leftwings", "getLeftwings", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightfluff", "getRightfluff", "rightwings", "getRightwings", "rootPart", "getRootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "standing", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getStanding", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setStanding", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class VolcaronaModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart rightwings;
    @NotNull
    private final ModelPart leftwings;
    @NotNull
    private final ModelPart rightfluff;
    @NotNull
    private final ModelPart leftfluff;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    @NotNull
    private final CryProvider cryAnimation;

    public VolcaronaModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "volcarona");
        this.head = this.getPart("head");
        this.rightwings = this.getPart("wings_right");
        this.leftwings = this.getPart("wings_left");
        this.rightfluff = this.getPart("wing_base_right");
        this.leftfluff = this.getPart("wing_base_left");
        this.portraitScale = 1.83f;
        this.portraitTranslation = new Vec3(-0.62, 1.89, 0.0);
        this.profileScale = 0.46f;
        this.profileTranslation = new Vec3(0.0, 1.06, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> VolcaronaModel.cryAnimation$lambda$0(this, arg_0, arg_1);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @NotNull
    public ModelPart getHead() {
        return this.head;
    }

    @NotNull
    public final ModelPart getRightwings() {
        return this.rightwings;
    }

    @NotNull
    public final ModelPart getLeftwings() {
        return this.leftwings;
    }

    @NotNull
    public final ModelPart getRightfluff() {
        return this.rightfluff;
    }

    @NotNull
    public final ModelPart getLeftfluff() {
        return this.leftfluff;
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

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        var wingFrame4 = new BiWingedFrame(this){
            @NotNull
            private final ModelPart rootPart;
            @NotNull
            private final ModelPart leftWing;
            @NotNull
            private final ModelPart rightWing;
            {
                this.rootPart = $receiver.getRootPart();
                this.leftWing = $receiver.getPart("wings_left");
                this.rightWing = $receiver.getPart("wings_right");
            }

            @NotNull
            public ModelPart getRootPart() {
                return this.rootPart;
            }

            @NotNull
            public ModelPart getLeftWing() {
                return this.leftWing;
            }

            @NotNull
            public ModelPart getRightWing() {
                return this.rightWing;
            }

            @NotNull
            public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(@NotNull Function1<? super Float, Float> flapFunction, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
                return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
            }
        };
        var fluffFrame2 = new BiWingedFrame(this){
            @NotNull
            private final ModelPart rootPart;
            @NotNull
            private final ModelPart leftWing;
            @NotNull
            private final ModelPart rightWing;
            {
                this.rootPart = $receiver.getRootPart();
                this.leftWing = $receiver.getPart("wing_base_left");
                this.rightWing = $receiver.getPart("wing_base_right");
            }

            @NotNull
            public ModelPart getRootPart() {
                return this.rootPart;
            }

            @NotNull
            public ModelPart getLeftWing() {
                return this.leftWing;
            }

            @NotNull
            public ModelPart getRightWing() {
                return this.rightWing;
            }

            @NotNull
            public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(@NotNull Function1<? super Float, Float> flapFunction, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
                return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
            }
        };
        EnumSet<PoseType> enumSet = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.UI_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.STATIONARY_POSES");
        EnumSet<PoseType> enumSet3 = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        Object[] objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, Float.valueOf(0.2f), Float.valueOf(0.3f), null, null, null, null, 975, null), PoseableEntityModel.bedrock$default(this, "volcarona", "ground_idle", null, 4, null), wingFrame4.wingFlap((Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.25f, 1.0f, 0.0f, 0.0f, 12, null), registerPoses.1.INSTANCE, 1), fluffFrame2.wingFlap((Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.3f, 1.0f, 0.0f, 0.0f, 12, null), registerPoses.2.INSTANCE, 1)};
        Object[] objectArray2 = objectArray;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.rightwings).addRotationDegrees(1, 5), ModelPartExtensionsKt.createTransformation(this.leftwings).addRotationDegrees(1, -5), ModelPartExtensionsKt.createTransformation(this.rightfluff).addRotationDegrees(1, 17), ModelPartExtensionsKt.createTransformation(this.leftfluff).addRotationDegrees(1, -17)};
        objectArray = modelPartTransformationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", enumSet3, null, 20, null, null, (StatelessAnimation[])objectArray2, (ModelPartTransformation[])objectArray, null, 308, null));
        enumSet3 = PoseType.Companion.getMOVING_POSES();
        objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, Float.valueOf(0.2f), Float.valueOf(0.3f), null, null, null, null, 975, null), PoseableEntityModel.bedrock$default(this, "volcarona", "ground_idle", null, 4, null), wingFrame4.wingFlap((Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.25f, 0.8f, 0.0f, 0.0f, 12, null), registerPoses.3.INSTANCE, 1), fluffFrame2.wingFlap((Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.3f, 0.8f, 0.0f, 0.0f, 12, null), registerPoses.4.INSTANCE, 1)};
        objectArray2 = objectArray;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.rightwings).addRotationDegrees(1, 5), ModelPartExtensionsKt.createTransformation(this.leftwings).addRotationDegrees(1, -5), ModelPartExtensionsKt.createTransformation(this.rightfluff).addRotationDegrees(1, 17), ModelPartExtensionsKt.createTransformation(this.leftfluff).addRotationDegrees(1, -17), ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, -5)};
        objectArray = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel, "walk", enumSet3, null, 20, null, null, (StatelessAnimation[])objectArray2, (ModelPartTransformation[])objectArray, null, 308, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(VolcaronaModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "volcarona", "cry", null, 4, null);
    }
}

