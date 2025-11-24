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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9.SquawkabillyModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0012\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\u0006\u0010T\u001a\u00020\u0017\u00a2\u0006\u0004\bU\u0010VJ\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u001a\u0010\t\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR2\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR2\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001d\u0010\u0014\"\u0004\b\u001e\u0010\u0016R\u001a\u0010\u001f\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0019\u001a\u0004\b \u0010\u001bR\u001a\u0010!\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\u0019\u001a\u0004\b\"\u0010\u001bR\"\u0010$\u001a\u00020#8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\"\u0010+\u001a\u00020*8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\"\u00101\u001a\u00020#8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010%\u001a\u0004\b2\u0010'\"\u0004\b3\u0010)R\"\u00104\u001a\u00020*8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010,\u001a\u0004\b5\u0010.\"\u0004\b6\u00100R\u001a\u00107\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b7\u0010\u0019\u001a\u0004\b8\u0010\u001bR\u001a\u00109\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b9\u0010\u0019\u001a\u0004\b:\u0010\u001bR\u001a\u0010;\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b;\u0010\u0019\u001a\u0004\b<\u0010\u001bR2\u0010=\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b=\u0010\u0012\u001a\u0004\b>\u0010\u0014\"\u0004\b?\u0010\u0016R\u001a\u0010A\u001a\u00020@8\u0006X\u0086D\u00a2\u0006\f\n\u0004\bA\u0010B\u001a\u0004\bC\u0010DR\u001a\u0010F\u001a\u00020E8\u0006X\u0086D\u00a2\u0006\f\n\u0004\bF\u0010G\u001a\u0004\bH\u0010IR2\u0010J\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bJ\u0010\u0012\u001a\u0004\bK\u0010\u0014\"\u0004\bL\u0010\u0016R2\u0010M\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bM\u0010\u0012\u001a\u0004\bN\u0010\u0014\"\u0004\bO\u0010\u0016R\u0014\u0010P\u001a\u00020\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bP\u0010\u0019R2\u0010Q\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rj\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bQ\u0010\u0012\u001a\u0004\bR\u0010\u0014\"\u0004\bS\u0010\u0016\u00a8\u0006W"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen9/SquawkabillyModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "flying", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFlying", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFlying", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "hovering", "getHovering", "setHovering", "leftLeg", "getLeftLeg", "leftWing", "getLeftWing", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightLeg", "getRightLeg", "rightWing", "getRightWing", "rootPart", "getRootPart", "shoulderLeft", "getShoulderLeft", "setShoulderLeft", "", "shoulderOffsetX", "D", "getShoulderOffsetX", "()D", "", "shoulderOffsetY", "I", "getShoulderOffsetY", "()I", "shoulderRight", "getShoulderRight", "setShoulderRight", "standing", "getStanding", "setStanding", "tail", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class SquawkabillyModel
extends PokemonPoseableModel
implements HeadedFrame,
BipedFrame,
BiWingedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart leftWing;
    @NotNull
    private final ModelPart rightWing;
    @NotNull
    private final ModelPart leftLeg;
    @NotNull
    private final ModelPart rightLeg;
    @NotNull
    private final ModelPart tail;
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
    public Pose<PokemonEntity, ModelFrame> shoulderLeft;
    public Pose<PokemonEntity, ModelFrame> shoulderRight;
    @NotNull
    private final CryProvider cryAnimation;
    private final double shoulderOffsetX;
    private final int shoulderOffsetY;

    public SquawkabillyModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "squawkabilly");
        this.head = this.getPart("head");
        this.leftWing = this.getPart("wing_left");
        this.rightWing = this.getPart("wing_right");
        this.leftLeg = this.getPart("leg_left");
        this.rightLeg = this.getPart("leg_right");
        this.tail = this.getPart("tail");
        this.portraitScale = 2.0f;
        this.portraitTranslation = new Vec3(-0.2, -0.2, 0.0);
        this.profileScale = 0.85f;
        this.profileTranslation = new Vec3(0.0, 0.51, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> SquawkabillyModel.cryAnimation$lambda$0(this, arg_0, arg_1);
        this.shoulderOffsetX = -1.0;
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

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    public final double getShoulderOffsetX() {
        return this.shoulderOffsetX;
    }

    public final int getShoulderOffsetY() {
        return this.shoulderOffsetY;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ SquawkabillyModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "squawkabilly", "blink", null, 4, null);
            }
        }, 7, null);
        ModelQuirk[] modelQuirkArray = new ModelQuirk[]{blink7};
        Object object = modelQuirkArray;
        EnumSet<PoseType> enumSet = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.UI_POSES");
        modelQuirkArray = SetsKt.plus((Set)enumSet, (Object)((Object)PoseType.STAND));
        Object[] objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null)};
        Object[] objectArray2 = objectArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)modelQuirkArray, null, 10, null, null, objectArray2, null, object, 180, null));
        modelQuirkArray = new ModelQuirk[]{blink7};
        object = modelQuirkArray;
        modelQuirkArray = PoseType.WALK;
        objectArray = new StatelessAnimation[10];
        objectArray[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        objectArray[1] = PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null);
        ModelPart modelPart = this.getRootPart();
        Function1<Float, Float> function1 = WaveFunctionKt.parabolaFunction(-4.0f, 0.4f);
        objectArray[2] = this.translation(modelPart, function1, 1, registerPoses.1.INSTANCE);
        objectArray[3] = this.translation(this.getHead(), (Function1<Float, Float>)WaveFunctionKt.sineFunction$default(AngleExtensionsKt.toRadians(Float.valueOf(-20.0f)), 1.0f, 0.0f, AngleExtensionsKt.toRadians(Float.valueOf(-10.0f)), 4, null), 0, registerPoses.2.INSTANCE);
        objectArray[4] = this.rotation(this.getLeftLeg(), WaveFunctionKt.parabolaFunction(-20.0f, 0.0f, AngleExtensionsKt.toRadians(Float.valueOf(30.0f))), 0, registerPoses.3.INSTANCE);
        objectArray[5] = this.rotation(this.getRightLeg(), WaveFunctionKt.parabolaFunction(-20.0f, 0.0f, AngleExtensionsKt.toRadians(Float.valueOf(30.0f))), 0, registerPoses.4.INSTANCE);
        objectArray[6] = this.rotation(this.tail, (Function1<Float, Float>)WaveFunctionKt.sineFunction$default(AngleExtensionsKt.toRadians(Float.valueOf(-5.0f)), 1.0f, 0.0f, 0.0f, 12, null), 0, registerPoses.5.INSTANCE);
        objectArray[7] = this.wingFlap(WaveFunctionKt.sineFunction(AngleExtensionsKt.toRadians(Float.valueOf(-5.0f)), 0.4f, 0.0f, AngleExtensionsKt.toRadians(Float.valueOf(-20.0f))), registerPoses.6.INSTANCE, 2);
        objectArray[8] = this.translation(this.getRightWing(), WaveFunctionKt.parabolaFunction(-10.0f, 30.0f, AngleExtensionsKt.toRadians(Float.valueOf(25.0f))), 1, registerPoses.7.INSTANCE);
        objectArray[9] = this.translation(this.getLeftWing(), WaveFunctionKt.parabolaFunction(-10.0f, 30.0f, AngleExtensionsKt.toRadians(Float.valueOf(25.0f))), 1, registerPoses.8.INSTANCE);
        objectArray2 = objectArray;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walk", (PoseType)modelQuirkArray, null, 10, null, null, objectArray2, null, object, 180, null));
        modelQuirkArray = new ModelQuirk[]{blink7};
        object = modelQuirkArray;
        objectArray2 = new PoseType[]{PoseType.FLOAT, PoseType.HOVER};
        modelQuirkArray = SetsKt.setOf((Object[])objectArray2);
        objectArray = new StatelessAnimation[2];
        objectArray[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        float f = -AngleExtensionsKt.toRadians(Float.valueOf(10.0f));
        objectArray[1] = new WingFlapIdleAnimation(this, (Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.9f, 0.9f, 0.0f, f, 4, null), registerPoses.9.INSTANCE, 2);
        objectArray2 = objectArray;
        this.setHovering(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hovering", (Set)modelQuirkArray, null, 10, null, null, objectArray2, null, object, 180, null));
        modelQuirkArray = new ModelQuirk[]{blink7};
        object = modelQuirkArray;
        objectArray2 = new PoseType[]{PoseType.FLY, PoseType.SWIM};
        modelQuirkArray = SetsKt.setOf((Object[])objectArray2);
        objectArray = new StatelessAnimation[2];
        objectArray[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        f = -AngleExtensionsKt.toRadians(Float.valueOf(14.0f));
        objectArray[1] = new WingFlapIdleAnimation(this, (Function1<? super Float, Float>)WaveFunctionKt.sineFunction$default(0.9f, 0.9f, 0.0f, f, 4, null), registerPoses.10.INSTANCE, 2);
        objectArray2 = objectArray;
        this.setFlying(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "flying", (Set)modelQuirkArray, null, 10, null, null, objectArray2, null, object, 180, null));
        object = PoseType.SHOULDER_LEFT;
        objectArray2 = new ModelQuirk[]{blink7};
        modelQuirkArray = objectArray2;
        objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null)};
        objectArray2 = objectArray;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(this.shoulderOffsetX, this.shoulderOffsetY, 0.0)};
        objectArray = modelPartTransformationArray;
        this.setShoulderLeft(PoseableEntityModel.registerPose$default(this, (PoseType)((Object)object), null, 0, null, null, objectArray2, (ModelPartTransformation[])objectArray, modelQuirkArray, 30, null));
        object = PoseType.SHOULDER_RIGHT;
        objectArray2 = new ModelQuirk[]{blink7};
        modelQuirkArray = objectArray2;
        objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "squawkabilly", "ground_idle", null, 4, null)};
        objectArray2 = objectArray;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(-this.shoulderOffsetX, this.shoulderOffsetY, 0.0)};
        objectArray = modelPartTransformationArray;
        this.setShoulderRight(PoseableEntityModel.registerPose$default(this, (PoseType)((Object)object), null, 0, null, null, objectArray2, (ModelPartTransformation[])objectArray, modelQuirkArray, 30, null));
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

    private static final StatefulAnimation cryAnimation$lambda$0(SquawkabillyModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "squawkabilly", "cry", null, 4, null);
    }
}

