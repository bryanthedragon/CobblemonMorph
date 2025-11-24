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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7.DecidueyeHisuianModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001d\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\u0006\u0010G\u001a\u00020\b\u00a2\u0006\u0004\bH\u0010IJ\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R2\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u0002`\u00158\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\n\u001a\u0004\b\u001d\u0010\fR2\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u0002`\u00158\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0017\u001a\u0004\b\u001f\u0010\u0019\"\u0004\b \u0010\u001bR\u0014\u0010!\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\nR\u001a\u0010\"\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010\n\u001a\u0004\b#\u0010\fR\u001a\u0010$\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010\n\u001a\u0004\b%\u0010\fR\"\u0010'\u001a\u00020&8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\"\u0010.\u001a\u00020-8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\"\u00104\u001a\u00020&8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010(\u001a\u0004\b5\u0010*\"\u0004\b6\u0010,R\"\u00107\u001a\u00020-8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b7\u0010/\u001a\u0004\b8\u00101\"\u0004\b9\u00103R\u0014\u0010:\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b:\u0010\nR\u001a\u0010;\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b;\u0010\n\u001a\u0004\b<\u0010\fR\u001a\u0010=\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b=\u0010\n\u001a\u0004\b>\u0010\fR\u001a\u0010?\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b?\u0010\n\u001a\u0004\b@\u0010\fR2\u0010A\u001a\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u0002`\u00158\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u0010\u0017\u001a\u0004\bB\u0010\u0019\"\u0004\bC\u0010\u001bR2\u0010D\u001a\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u0002`\u00158\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bD\u0010\u0017\u001a\u0004\bE\u0010\u0019\"\u0004\bF\u0010\u001b\u00a8\u0006J"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/DecidueyeHisuianModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;", "", "registerPoses", "()V", "Lnet/minecraft/client/model/geom/ModelPart;", "arrow", "Lnet/minecraft/client/model/geom/ModelPart;", "getArrow", "()Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "fly", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFly", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFly", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "head", "getHead", "hover", "getHover", "setHover", "leftClosedWing", "leftLeg", "getLeftLeg", "leftWing", "getLeftWing", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightClosedWing", "rightLeg", "getRightLeg", "rightWing", "getRightWing", "rootPart", "getRootPart", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class DecidueyeHisuianModel
extends PokemonPoseableModel
implements HeadedFrame,
BipedFrame,
BiWingedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart leftClosedWing;
    @NotNull
    private final ModelPart rightClosedWing;
    @NotNull
    private final ModelPart leftWing;
    @NotNull
    private final ModelPart rightWing;
    @NotNull
    private final ModelPart leftLeg;
    @NotNull
    private final ModelPart rightLeg;
    @NotNull
    private final ModelPart arrow;
    @NotNull
    private Vec3 portraitTranslation;
    private float portraitScale;
    @NotNull
    private Vec3 profileTranslation;
    private float profileScale;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> hover;
    public Pose<PokemonEntity, ModelFrame> fly;
    @NotNull
    private final CryProvider cryAnimation;

    public DecidueyeHisuianModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "decidueye_hisui");
        this.head = this.getPart("head");
        this.leftClosedWing = this.getPart("wing_closed_left1");
        this.rightClosedWing = this.getPart("wing_closed_right1");
        this.leftWing = this.getPart("wing_open_left1");
        this.rightWing = this.getPart("wing_open_right1");
        this.leftLeg = this.getPart("thigh_left");
        this.rightLeg = this.getPart("thigh_right");
        this.arrow = this.getPart("arrow");
        this.portraitTranslation = new Vec3(-0.28, 2.5300000000000047, 0.0);
        this.portraitScale = 1.5200002f;
        this.profileTranslation = new Vec3(0.0, 1.0299999999999998, 0.0);
        this.profileScale = 0.46999997f;
        this.cryAnimation = (arg_0, arg_1) -> DecidueyeHisuianModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public ModelPart getLeftWing() {
        return this.leftWing;
    }

    @Override
    @NotNull
    public ModelPart getRightWing() {
        return this.rightWing;
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

    @NotNull
    public final ModelPart getArrow() {
        return this.arrow;
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
    public float getPortraitScale() {
        return this.portraitScale;
    }

    @Override
    public void setPortraitScale(float f) {
        this.portraitScale = f;
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

    @Override
    public float getProfileScale() {
        return this.profileScale;
    }

    @Override
    public void setProfileScale(float f) {
        this.profileScale = f;
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
            final /* synthetic */ DecidueyeHisuianModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "decidueye", "blink", null, 4, null);
            }
        }, 7, null);
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = SetsKt.minus((Set)enumSet, (Object)((Object)PoseType.HOVER));
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        Object object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        Object[] objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "decidueye", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", object, null, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray2, (ModelQuirk[])objectArray, 60, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        object = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.FLY));
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)};
        objectArray2 = objectArray;
        objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), new BipedWalkAnimation(this, 0.0f, 0.0f, 6, null), PoseableEntityModel.bedrock$default(this, "decidueye", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walk", object, null, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray2, (ModelQuirk[])objectArray, 60, null));
        object = PoseType.HOVER;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)};
        objectArray2 = objectArray;
        objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[2];
        statelessAnimationArray[0] = PoseableEntityModel.bedrock$default(this, "decidueye", "air_idle", null, 4, null);
        float f = -AngleExtensionsKt.toRadians(Float.valueOf(10.0f));
        statelessAnimationArray[1] = new WingFlapIdleAnimation(this, (Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.6f, 0.9f, 0.0f, f, 4, null), registerPoses.1.INSTANCE, 1);
        objectArray3 = statelessAnimationArray;
        this.setHover(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hover", (PoseType)((Object)object), null, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray2, (ModelQuirk[])objectArray, 52, null));
        object = PoseType.FLY;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.leftClosedWing).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.rightClosedWing).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.arrow).withVisibility(false)};
        objectArray2 = objectArray;
        objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[2];
        statelessAnimationArray[0] = PoseableEntityModel.bedrock$default(this, "decidueye", "air_fly", null, 4, null);
        f = -AngleExtensionsKt.toRadians(Float.valueOf(14.0f));
        statelessAnimationArray[1] = new WingFlapIdleAnimation(this, (Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.9f, 0.9f, 0.0f, f, 4, null), registerPoses.2.INSTANCE, 1);
        objectArray3 = statelessAnimationArray;
        this.setFly(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "fly", (PoseType)((Object)object), null, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray2, (ModelQuirk[])objectArray, 52, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    @Override
    @NotNull
    public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(@NotNull Function1<? super Float, Float> flapFunction, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
        return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(DecidueyeHisuianModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "decidueye", "hisuian_cry", null, 4, null);
    }
}

