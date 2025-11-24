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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BimanualSwingAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.BipedWalkAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2.HeracrossModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001e\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\u0006\u0010@\u001a\u00020\u0015\u00a2\u0006\u0004\bA\u0010BJ\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R2\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R2\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0017\u001a\u0004\b\u001b\u0010\u0019R\u001a\u0010\u001c\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001d\u0010\u0019R\"\u0010\u001f\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\"\u0010&\u001a\u00020%8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\"\u0010,\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010 \u001a\u0004\b-\u0010\"\"\u0004\b.\u0010$R\"\u0010/\u001a\u00020%8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b/\u0010'\u001a\u0004\b0\u0010)\"\u0004\b1\u0010+R\u001a\u00102\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b2\u0010\u0017\u001a\u0004\b3\u0010\u0019R\u001a\u00104\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u0010\u0017\u001a\u0004\b5\u0010\u0019R\u001a\u00106\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b6\u0010\u0017\u001a\u0004\b7\u0010\u0019R\u001a\u00108\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b8\u0010\u0017\u001a\u0004\b9\u0010\u0019R2\u0010:\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b:\u0010\r\u001a\u0004\b;\u0010\u000f\"\u0004\b<\u0010\u0011R2\u0010=\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b=\u0010\r\u001a\u0004\b>\u0010\u000f\"\u0004\b?\u0010\u0011\u00a8\u0006C"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen2/HeracrossModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "flying", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFlying", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFlying", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "hovering", "getHovering", "setHovering", "Lnet/minecraft/client/model/geom/ModelPart;", "leftArm", "Lnet/minecraft/client/model/geom/ModelPart;", "getLeftArm", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftLeg", "getLeftLeg", "leftWing", "getLeftWing", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightArm", "getRightArm", "rightLeg", "getRightLeg", "rightWing", "getRightWing", "rootPart", "getRootPart", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class HeracrossModel
extends PokemonPoseableModel
implements BipedFrame,
BimanualFrame,
BiWingedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart leftArm;
    @NotNull
    private final ModelPart rightArm;
    @NotNull
    private final ModelPart leftLeg;
    @NotNull
    private final ModelPart rightLeg;
    @NotNull
    private final ModelPart leftWing;
    @NotNull
    private final ModelPart rightWing;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> hovering;
    public Pose<PokemonEntity, ModelFrame> flying;

    public HeracrossModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "heracross");
        this.leftArm = this.getPart("arm_right");
        this.rightArm = this.getPart("arm_left");
        this.leftLeg = this.getPart("leg_right");
        this.rightLeg = this.getPart("leg_left");
        this.leftWing = this.getPart("wing_right");
        this.rightWing = this.getPart("wing_left");
        this.portraitScale = 1.6f;
        this.portraitTranslation = new Vec3(-0.2, 0.37, 0.0);
        this.profileScale = 0.7f;
        this.profileTranslation = new Vec3(-0.02, 0.64, 0.0);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
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
    public ModelPart getLeftWing() {
        return this.leftWing;
    }

    @Override
    @NotNull
    public ModelPart getRightWing() {
        return this.rightWing;
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
    public final Pose<PokemonEntity, ModelFrame> getHovering() {
        Pose<PokemonEntity, ModelFrame> pose = this.hovering;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"hovering");
        return null;
    }

    public final void setHovering(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.hovering = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getFlying() {
        Pose<PokemonEntity, ModelFrame> pose = this.flying;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"flying");
        return null;
    }

    public final void setFlying(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.flying = pose;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ HeracrossModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "heracross", "blink", null, 4, null);
            }
        }, 7, null);
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        Object object = SetsKt.minus((Set)SetsKt.plus((Set)set2, (Iterable)enumSet2), (Object)((Object)PoseType.HOVER));
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getLeftWing()).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.getRightWing()).withVisibility(false)};
        objectArray = objectArray3;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "heracross", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", object, null, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 60, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.MOVING_POSES");
        object = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.FLY));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "heracross", "ground_idle", null, 4, null), new BipedWalkAnimation(this, 0.6f, 1.0f), new BimanualSwingAnimation(this, 0.6f, 1.0f)};
        objectArray = objectArray3;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walk", object, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        object = PoseType.HOVER;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[2];
        objectArray3[0] = PoseableEntityModel.bedrock$default(this, "heracross", "air_idle", null, 4, null);
        float f = AngleExtensionsKt.toRadians(Float.valueOf(35.0f));
        objectArray3[1] = new WingFlapIdleAnimation(this, (Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.6f, 0.1f, 0.0f, f, 4, null), registerPoses.1.INSTANCE, 1);
        objectArray = objectArray3;
        this.setHovering(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hovering", (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        object = PoseType.FLY;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[2];
        objectArray3[0] = PoseableEntityModel.bedrock$default(this, "heracross", "air_idle", null, 4, null);
        f = AngleExtensionsKt.toRadians(Float.valueOf(35.0f));
        objectArray3[1] = new WingFlapIdleAnimation(this, (Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.6f, 0.1f, 0.0f, f, 4, null), registerPoses.2.INSTANCE, 1);
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addRotationDegrees(0, 45)};
        objectArray3 = modelPartTransformationArray;
        this.setFlying(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "flying", (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 60, null));
    }

    @Override
    @NotNull
    public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(@NotNull Function1<? super Float, Float> flapFunction, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
        return BiWingedFrame.DefaultImpls.wingFlap(this, flapFunction, timeVariable, axis);
    }
}

