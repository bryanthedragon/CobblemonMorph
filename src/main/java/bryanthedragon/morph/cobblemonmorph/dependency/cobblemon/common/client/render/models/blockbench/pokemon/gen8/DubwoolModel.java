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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.QuadrupedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen8.DubwoolModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\u0012\u0006\u0010S\u001a\u00020\u0014\u00a2\u0006\u0004\bT\u0010UJ-\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ-\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016\u00a2\u0006\u0004\b\u000b\u0010\nJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0015\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0019\u0010\u0016\u001a\u0004\b\u001a\u0010\u0018R\u001a\u0010\u001b\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0016\u001a\u0004\b\u001c\u0010\u0018R\u001a\u0010\u001d\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u0016\u001a\u0004\b\u001e\u0010\u0018R\u001a\u0010\u001f\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0016\u001a\u0004\b \u0010\u0018R\u0017\u0010!\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u0016\u001a\u0004\b\"\u0010\u0018R\"\u0010$\u001a\u00020#8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\"\u0010+\u001a\u00020*8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\"\u00101\u001a\u00020#8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010%\u001a\u0004\b2\u0010'\"\u0004\b3\u0010)R\"\u00104\u001a\u00020*8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010,\u001a\u0004\b5\u0010.\"\u0004\b6\u00100R\u001a\u00107\u001a\u00020\u00148\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b7\u0010\u0016\u001a\u0004\b8\u0010\u0018R2\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020:09j\u0002`;8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b<\u0010=\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR2\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020:09j\u0002`;8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bB\u0010=\u001a\u0004\bC\u0010?\"\u0004\bD\u0010AR2\u0010E\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020:09j\u0002`;8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bE\u0010=\u001a\u0004\bF\u0010?\"\u0004\bG\u0010AR2\u0010H\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020:09j\u0002`;8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010=\u001a\u0004\bI\u0010?\"\u0004\bJ\u0010AR2\u0010K\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020:09j\u0002`;8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bK\u0010=\u001a\u0004\bL\u0010?\"\u0004\bM\u0010AR2\u0010N\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020:09j\u0002`;8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bN\u0010=\u001a\u0004\bO\u0010?\"\u0004\bP\u0010AR\u0017\u0010Q\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\bQ\u0010\u0016\u001a\u0004\bR\u0010\u0018\u00a8\u0006V"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen8/DubwoolModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/QuadrupedFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getEatAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "foreLeftLeg", "Lnet/minecraft/client/model/geom/ModelPart;", "getForeLeftLeg", "()Lnet/minecraft/client/model/geom/ModelPart;", "foreRightLeg", "getForeRightLeg", "head", "getHead", "hindLeftLeg", "getHindLeftLeg", "hindRightLeg", "getHindRightLeg", "neckWool", "getNeckWool", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "shearedsleep", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getShearedsleep", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setShearedsleep", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "shearedstanding", "getShearedstanding", "setShearedstanding", "shearedwalk", "getShearedwalk", "setShearedwalk", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "wool", "getWool", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class DubwoolModel
extends PokemonPoseableModel
implements HeadedFrame,
QuadrupedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart foreLeftLeg;
    @NotNull
    private final ModelPart foreRightLeg;
    @NotNull
    private final ModelPart hindLeftLeg;
    @NotNull
    private final ModelPart hindRightLeg;
    @NotNull
    private final ModelPart wool;
    @NotNull
    private final ModelPart neckWool;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> shearedsleep;
    public Pose<PokemonEntity, ModelFrame> shearedstanding;
    public Pose<PokemonEntity, ModelFrame> shearedwalk;
    @NotNull
    private final CryProvider cryAnimation;

    public DubwoolModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "dubwool");
        this.head = this.getPart("head");
        this.foreLeftLeg = this.getPart("leg_front_left");
        this.foreRightLeg = this.getPart("leg_front_right");
        this.hindLeftLeg = this.getPart("leg_back_left");
        this.hindRightLeg = this.getPart("leg_back_right");
        this.wool = this.getPart("wool_shearable");
        this.neckWool = this.getPart("neck_wool_shearable");
        this.portraitScale = 3.1f;
        this.portraitTranslation = new Vec3(-1.2, -0.7, 0.0);
        this.profileScale = 0.9f;
        this.profileTranslation = new Vec3(0.0, 0.4, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> DubwoolModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public ModelPart getForeLeftLeg() {
        return this.foreLeftLeg;
    }

    @Override
    @NotNull
    public ModelPart getForeRightLeg() {
        return this.foreRightLeg;
    }

    @Override
    @NotNull
    public ModelPart getHindLeftLeg() {
        return this.hindLeftLeg;
    }

    @Override
    @NotNull
    public ModelPart getHindRightLeg() {
        return this.hindRightLeg;
    }

    @NotNull
    public final ModelPart getWool() {
        return this.wool;
    }

    @NotNull
    public final ModelPart getNeckWool() {
        return this.neckWool;
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
    public final Pose<PokemonEntity, ModelFrame> getShearedsleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.shearedsleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"shearedsleep");
        return null;
    }

    public final void setShearedsleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.shearedsleep = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getShearedstanding() {
        Pose<PokemonEntity, ModelFrame> pose = this.shearedstanding;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"shearedstanding");
        return null;
    }

    public final void setShearedstanding(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.shearedstanding = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getShearedwalk() {
        Pose<PokemonEntity, ModelFrame> pose = this.shearedwalk;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"shearedwalk");
        return null;
    }

    public final void setShearedwalk(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.shearedwalk = pose;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ DubwoolModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "dubwool", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object[] objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(true)};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dubwool", "sleep", null, 4, null)};
        objectArray = objectArray3;
        this.setSleep(PoseableEntityModel.registerPose$default(this, object, registerPoses.1.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, objectArray2, null, 152, null));
        objectArray2 = new PoseType[]{PoseType.NONE, PoseType.STAND, PoseType.PORTRAIT, PoseType.PROFILE};
        object = SetsKt.setOf((Object[])objectArray2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(true)};
        objectArray = objectArray3;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dubwool", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.2.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
        objectArray2 = new PoseType[]{PoseType.SWIM, PoseType.WALK};
        object = SetsKt.setOf((Object[])objectArray2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(true)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dubwool", "ground_walk", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)object, (Function1)registerPoses.3.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.SLEEP;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(false)};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dubwool", "sleep", null, 4, null)};
        objectArray = objectArray3;
        this.setShearedsleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "shearedsleep", object, (Function1)registerPoses.4.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, objectArray2, null, 304, null));
        objectArray2 = new PoseType[]{PoseType.NONE, PoseType.STAND, PoseType.PORTRAIT, PoseType.PROFILE};
        object = SetsKt.setOf((Object[])objectArray2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dubwool", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setShearedstanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "shearedstanding", (Set)object, (Function1)registerPoses.5.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
        objectArray2 = new PoseType[]{PoseType.SWIM, PoseType.WALK};
        object = SetsKt.setOf((Object[])objectArray2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wool).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.neckWool).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dubwool", "ground_walk", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setShearedwalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "shearedwalking", (Set)object, (Function1)registerPoses.6.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getStanding(), this.getWalk(), this.getShearedwalk(), this.getShearedstanding(), this.getSleep()};
        return state.isPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "dubwool", "faint", null, 4, null) : null;
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getEatAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getSleep(), this.getShearedsleep()};
        return state.isNotPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "dubwool", "eat", null, 4, null) : null;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(DubwoolModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "dubwool", "cry", null, 4, null);
    }
}

