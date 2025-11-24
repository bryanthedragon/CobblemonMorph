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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen9.CeruledgeModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001f\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\u0006\u0010Q\u001a\u00020\u0018\u00a2\u0006\u0004\bR\u0010SJ-\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t2\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR2\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fj\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001e\u0010\u001cR\u001a\u0010 \u001a\u00020\u001f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010\u001a\u001a\u0004\b%\u0010\u001cR\u001a\u0010&\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010\u001a\u001a\u0004\b'\u0010\u001cR\u001a\u0010(\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b(\u0010\u001a\u001a\u0004\b)\u0010\u001cR\u0017\u0010*\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b*\u0010\u001a\u001a\u0004\b+\u0010\u001cR\u0017\u0010,\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b,\u0010\u001a\u001a\u0004\b-\u0010\u001cR\"\u0010/\u001a\u00020.8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b/\u00100\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\"\u00106\u001a\u0002058\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b6\u00107\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R\"\u0010<\u001a\u00020.8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b<\u00100\u001a\u0004\b=\u00102\"\u0004\b>\u00104R\"\u0010?\u001a\u0002058\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b?\u00107\u001a\u0004\b@\u00109\"\u0004\bA\u0010;R\u001a\u0010B\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bB\u0010\u001a\u001a\u0004\bC\u0010\u001cR\u001a\u0010D\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bD\u0010\u001a\u001a\u0004\bE\u0010\u001cR\u001a\u0010F\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bF\u0010\u001a\u001a\u0004\bG\u0010\u001cR2\u0010H\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fj\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010\u0013\u001a\u0004\bI\u0010\u0015\"\u0004\bJ\u0010\u0017R2\u0010K\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fj\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bK\u0010\u0013\u001a\u0004\bL\u0010\u0015\"\u0004\bM\u0010\u0017R2\u0010N\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fj\u0002`\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bN\u0010\u0013\u001a\u0004\bO\u0010\u0015\"\u0004\bP\u0010\u0017\u00a8\u0006T"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen9/CeruledgeModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "bladeleft", "Lnet/minecraft/client/model/geom/ModelPart;", "getBladeleft", "()Lnet/minecraft/client/model/geom/ModelPart;", "bladeright", "getBladeright", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "head", "getHead", "leftArm", "getLeftArm", "leftLeg", "getLeftLeg", "passivebladeleft", "getPassivebladeleft", "passivebladeright", "getPassivebladeright", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightArm", "getRightArm", "rightLeg", "getRightLeg", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walking", "getWalking", "setWalking", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class CeruledgeModel
extends PokemonPoseableModel
implements HeadedFrame,
BipedFrame,
BimanualFrame {
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
    private final ModelPart bladeright;
    @NotNull
    private final ModelPart bladeleft;
    @NotNull
    private final ModelPart passivebladeright;
    @NotNull
    private final ModelPart passivebladeleft;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walking;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> battleidle;
    @NotNull
    private final CryProvider cryAnimation;

    public CeruledgeModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "ceruledge");
        this.head = this.getPart("head");
        this.rightArm = this.getPart("arm_right");
        this.leftArm = this.getPart("arm_left");
        this.rightLeg = this.getPart("leg_right");
        this.leftLeg = this.getPart("leg_left");
        this.bladeright = this.getPart("blade_right");
        this.bladeleft = this.getPart("blade_left");
        this.passivebladeright = this.getPart("passive_blade_right");
        this.passivebladeleft = this.getPart("passive_blade_left");
        this.portraitScale = 1.8f;
        this.portraitTranslation = new Vec3(-0.2, 1.9, 0.0);
        this.profileScale = 0.5f;
        this.profileTranslation = new Vec3(0.0, 1.0, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> CeruledgeModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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

    @NotNull
    public final ModelPart getBladeright() {
        return this.bladeright;
    }

    @NotNull
    public final ModelPart getBladeleft() {
        return this.bladeleft;
    }

    @NotNull
    public final ModelPart getPassivebladeright() {
        return this.passivebladeright;
    }

    @NotNull
    public final ModelPart getPassivebladeleft() {
        return this.passivebladeleft;
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
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ CeruledgeModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "ceruledge", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object[] objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.bladeright).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.bladeleft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.passivebladeright).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.passivebladeleft).withVisibility(true)};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "ceruledge", "sleep", null, 4, null)};
        objectArray = objectArray3;
        this.setSleep(PoseableEntityModel.registerPose$default(this, object, null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray2, null, 158, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.bladeright).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.bladeleft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.passivebladeright).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.passivebladeleft).withVisibility(true)};
        objectArray = objectArray3;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "ceruledge", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.1.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.bladeright).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.bladeleft).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.passivebladeright).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.passivebladeleft).withVisibility(true)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "ceruledge", "ground_walk", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalking(PoseableEntityModel.registerPose$default(poseableEntityModel, "walking", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 52, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.bladeright).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.bladeleft).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.passivebladeright).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.passivebladeleft).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "ceruledge", "battle_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel2, "battle_idle", (Set)object, (Function1)registerPoses.2.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getStanding(), this.getWalking(), this.getBattleidle(), this.getSleep()};
        return state.isPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "ceruledge", "faint2", null, 4, null) : null;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(CeruledgeModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "ceruledge", "cry", null, 4, null);
    }
}

