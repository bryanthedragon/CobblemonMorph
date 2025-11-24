/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1.CharizardModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b!\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005B\u000f\u0012\u0006\u0010K\u001a\u00020\u001b\u00a2\u0006\u0004\bL\u0010MJ\u000f\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\n\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR2\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000ej\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R2\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000ej\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0013\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010\u001d\u001a\u0004\b!\u0010\u001fR\u001a\u0010\"\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010\u001d\u001a\u0004\b#\u0010\u001fR\u001a\u0010$\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010\u001d\u001a\u0004\b%\u0010\u001fR\"\u0010'\u001a\u00020&8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\"\u0010.\u001a\u00020-8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\"\u00104\u001a\u00020&8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010(\u001a\u0004\b5\u0010*\"\u0004\b6\u0010,R\"\u00107\u001a\u00020-8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b7\u0010/\u001a\u0004\b8\u00101\"\u0004\b9\u00103R\u001a\u0010:\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b:\u0010\u001d\u001a\u0004\b;\u0010\u001fR\u001a\u0010<\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b<\u0010\u001d\u001a\u0004\b=\u0010\u001fR\u001a\u0010>\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b>\u0010\u001d\u001a\u0004\b?\u0010\u001fR\u001a\u0010@\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b@\u0010\u001d\u001a\u0004\bA\u0010\u001fR2\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000ej\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bB\u0010\u0013\u001a\u0004\bC\u0010\u0015\"\u0004\bD\u0010\u0017R2\u0010E\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000ej\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bE\u0010\u0013\u001a\u0004\bF\u0010\u0015\"\u0004\bG\u0010\u0017R2\u0010H\u001a\u0012\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000ej\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010\u0013\u001a\u0004\bI\u0010\u0015\"\u0004\bJ\u0010\u0017\u00a8\u0006N"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/CharizardModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "fly", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFly", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFly", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "flyIdle", "getFlyIdle", "setFlyIdle", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftArm", "getLeftArm", "leftLeg", "getLeftLeg", "leftWing", "getLeftWing", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightArm", "getRightArm", "rightLeg", "getRightLeg", "rightWing", "getRightWing", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class CharizardModel
extends PokemonPoseableModel
implements HeadedFrame,
BipedFrame,
BimanualFrame,
BiWingedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart rightArm;
    @NotNull
    private final ModelPart leftArm;
    @NotNull
    private final ModelPart rightLeg;
    @NotNull
    private final ModelPart leftLeg;
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
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> flyIdle;
    public Pose<PokemonEntity, ModelFrame> fly;
    @NotNull
    private final CryProvider cryAnimation;

    public CharizardModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "charizard");
        this.head = this.getPart("head_ai");
        this.rightArm = this.getPart("arm_right");
        this.leftArm = this.getPart("arm_left");
        this.rightLeg = this.getPart("leg_right");
        this.leftLeg = this.getPart("leg_left");
        this.leftWing = this.getPart("wing_left");
        this.rightWing = this.getPart("wing_right");
        this.portraitScale = 1.9f;
        this.portraitTranslation = new Vec3(-0.5, 1.4, 0.0);
        this.profileScale = 0.55f;
        this.profileTranslation = new Vec3(0.05, 0.93, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> CharizardModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    @NotNull
    public ModelPart getRightLeg() {
        return this.rightLeg;
    }

    @Override
    @NotNull
    public ModelPart getLeftLeg() {
        return this.leftLeg;
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
    public final Pose<PokemonEntity, ModelFrame> getFlyIdle() {
        Pose<PokemonEntity, ModelFrame> pose = this.flyIdle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"flyIdle");
        return null;
    }

    public final void setFlyIdle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.flyIdle = pose;
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
        this.getAnimations().put("physical", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('charizard', 'physical', 'look', q.curve('symmetrical_wide'))"));
        this.getAnimations().put("special", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('charizard', 'special', 'look', q.curve('symmetrical_wide'))"));
        this.getAnimations().put("status", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('charizard', 'status', q.curve('symmetrical_wide'))"));
        this.getAnimations().put("recoil", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('charizard', 'recoil')"));
        this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('charizard', 'cry')"));
        ExpressionLike faint = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('charizard', 'faint', q.curve('one'))");
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ CharizardModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "charizard", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object object2 = new ModelQuirk[]{blink7};
        ModelQuirk[] modelQuirkArray = object2;
        Object[] objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        Object[] objectArray2 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "charizard", "sleep", null, 4, null)};
        objectArray = objectArray2;
        this.setSleep(PoseableEntityModel.registerPose$default(this, object, null, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 86, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = SetsKt.minus((Set)enumSet, (Object)((Object)PoseType.HOVER));
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        object2 = new ModelQuirk[]{blink7};
        modelQuirkArray = object2;
        objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "charizard", "ground_idle", null, 4, null)};
        objectArray = objectArray2;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.1.INSTANCE, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 168, null));
        object = SetsKt.setOf((Object)((Object)PoseType.STAND));
        object2 = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        modelQuirkArray = MapsKt.mutableMapOf((Pair[])object2);
        objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "charizard", "battle_idle", null, 4, null)};
        object2 = objectArray;
        PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "battle_standing", (Set)object, (Function1)registerPoses.2.INSTANCE, 0, (Map)modelQuirkArray, null, (StatelessAnimation[])object2, null, null, 424, null);
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        object = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.FLY));
        object2 = new ModelQuirk[]{blink7};
        modelQuirkArray = object2;
        objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "charizard", "ground_idle", null, 4, null), PoseableEntityModel.bedrock$default(this, "charizard", "ground_walk", null, 4, null)};
        objectArray = objectArray2;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walk", (Set)object, null, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 172, null));
        object = PoseType.HOVER;
        object2 = new ModelQuirk[]{blink7};
        modelQuirkArray = object2;
        objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "charizard", "air_idle", null, 4, null)};
        object2 = objectArray;
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, Float.valueOf(-2.0f))};
        objectArray = objectArray2;
        this.setFlyIdle(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hover", object, null, 0, null, null, (StatelessAnimation[])object2, (ModelPartTransformation[])objectArray, modelQuirkArray, 60, null));
        object = PoseType.FLY;
        object2 = new ModelQuirk[]{blink7};
        modelQuirkArray = object2;
        objectArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "charizard", "air_fly", null, 4, null)};
        object2 = objectArray;
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, Float.valueOf(6.0f))};
        objectArray = objectArray2;
        this.setFly(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "fly", object, null, 0, null, null, (StatelessAnimation[])object2, (ModelPartTransformation[])objectArray, modelQuirkArray, 60, null));
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

    private static final StatefulAnimation cryAnimation$lambda$0(CharizardModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "charizard", "cry", null, 4, null);
    }
}

