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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen6;

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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen6.AvaluggModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0010\u0006\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\u0012\u0006\u0010S\u001a\u00020\u001c\u00a2\u0006\u0004\bT\u0010UJ-\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR2\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\u001e\u001a\u0004\b\"\u0010 R\u001a\u0010#\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b#\u0010\u001e\u001a\u0004\b$\u0010 R\u001a\u0010%\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010\u001e\u001a\u0004\b&\u0010 R\u001a\u0010'\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b'\u0010\u001e\u001a\u0004\b(\u0010 R\"\u0010*\u001a\u00020)8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\"\u00101\u001a\u0002008\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u00102\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\"\u00107\u001a\u00020)8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b7\u0010+\u001a\u0004\b8\u0010-\"\u0004\b9\u0010/R\"\u0010:\u001a\u0002008\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b:\u00102\u001a\u0004\b;\u00104\"\u0004\b<\u00106R\u001a\u0010=\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b=\u0010\u001e\u001a\u0004\b>\u0010 R2\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b?\u0010\u0012\u001a\u0004\b@\u0010\u0014\"\u0004\bA\u0010\u0016R2\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bB\u0010\u0012\u001a\u0004\bC\u0010\u0014\"\u0004\bD\u0010\u0016R2\u0010E\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bE\u0010\u0012\u001a\u0004\bF\u0010\u0014\"\u0004\bG\u0010\u0016R2\u0010H\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010\u0012\u001a\u0004\bI\u0010\u0014\"\u0004\bJ\u0010\u0016R2\u0010K\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bK\u0010\u0012\u001a\u0004\bL\u0010\u0014\"\u0004\bM\u0010\u0016R\u001a\u0010O\u001a\u00020N8\u0006X\u0086D\u00a2\u0006\f\n\u0004\bO\u0010P\u001a\u0004\bQ\u0010R\u00a8\u0006V"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen6/AvaluggModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/QuadrupedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "foreLeftLeg", "Lnet/minecraft/client/model/geom/ModelPart;", "getForeLeftLeg", "()Lnet/minecraft/client/model/geom/ModelPart;", "foreRightLeg", "getForeRightLeg", "head", "getHead", "hindLeftLeg", "getHindLeftLeg", "hindRightLeg", "getHindRightLeg", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walking", "getWalking", "setWalking", "water_surface_idle", "getWater_surface_idle", "setWater_surface_idle", "water_surface_swim", "getWater_surface_swim", "setWater_surface_swim", "", "wateroffset", "D", "getWateroffset", "()D", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class AvaluggModel
extends PokemonPoseableModel
implements QuadrupedFrame,
HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart hindLeftLeg;
    @NotNull
    private final ModelPart hindRightLeg;
    @NotNull
    private final ModelPart foreLeftLeg;
    @NotNull
    private final ModelPart foreRightLeg;
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
    public Pose<PokemonEntity, ModelFrame> water_surface_idle;
    public Pose<PokemonEntity, ModelFrame> water_surface_swim;
    private final double wateroffset;
    @NotNull
    private final CryProvider cryAnimation;

    public AvaluggModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "avalugg");
        this.head = this.getPart("head");
        this.hindLeftLeg = this.getPart("leg_back_left");
        this.hindRightLeg = this.getPart("leg_back_right");
        this.foreLeftLeg = this.getPart("leg_front_left");
        this.foreRightLeg = this.getPart("leg_front_right");
        this.portraitScale = 1.0f;
        this.portraitTranslation = new Vec3(-1.35, 1.5, 0.0);
        this.profileScale = 0.27f;
        this.profileTranslation = new Vec3(-0.05, 1.2, 0.0);
        this.wateroffset = -3.5;
        this.cryAnimation = (arg_0, arg_1) -> AvaluggModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public ModelPart getHindLeftLeg() {
        return this.hindLeftLeg;
    }

    @Override
    @NotNull
    public ModelPart getHindRightLeg() {
        return this.hindRightLeg;
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

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_surface_idle() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_surface_idle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_surface_idle");
        return null;
    }

    public final void setWater_surface_idle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_surface_idle = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_surface_swim() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_surface_swim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_surface_swim");
        return null;
    }

    public final void setWater_surface_swim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_surface_swim = pose;
    }

    public final double getWateroffset() {
        return this.wateroffset;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ AvaluggModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "avalugg", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "avalugg", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, null, 0, null, null, object, null, null, 222, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        object = SetsKt.minus((Set)SetsKt.plus((Set)set2, (Iterable)enumSet2), (Object)((Object)PoseType.FLOAT));
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "avalugg", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.1.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.MOVING_POSES");
        object = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.SWIM));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "avalugg", "ground_walk", null, 4, null)};
        objectArray = objectArray3;
        this.setWalking(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)object, (Function1)registerPoses.2.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "avalugg", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_surface_idle(PoseableEntityModel.registerPose$default(poseableEntityModel, "surface_idle", (Set)object, (Function1)registerPoses.3.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "avalugg", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWater_surface_swim(PoseableEntityModel.registerPose$default(poseableEntityModel2, "surface_swim", (Set)object, (Function1)registerPoses.4.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "avalugg", "battle_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel3 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel3, "battle_idle", (Set)object, (Function1)registerPoses.5.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getStanding(), this.getWalking(), this.getBattleidle(), this.getSleep(), this.getWater_surface_idle(), this.getWater_surface_swim()};
        return state.isPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "avalugg", "faint", null, 4, null) : null;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(AvaluggModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "avalugg", "cry", null, 4, null);
    }
}

