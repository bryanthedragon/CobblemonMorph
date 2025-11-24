/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0018\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010H\u001a\u00020+\u00a2\u0006\u0004\bI\u0010JJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0006\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR2\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R2\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R\"\u0010\u0018\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\"\u0010%\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u0019\u001a\u0004\b&\u0010\u001b\"\u0004\b'\u0010\u001dR\"\u0010(\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010 \u001a\u0004\b)\u0010\"\"\u0004\b*\u0010$R\u001a\u0010,\u001a\u00020+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R2\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b0\u0010\u000f\u001a\u0004\b1\u0010\u0011\"\u0004\b2\u0010\u0013R\u001a\u00104\u001a\u0002038\u0006X\u0086D\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107R2\u00108\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u0010\u000f\u001a\u0004\b9\u0010\u0011\"\u0004\b:\u0010\u0013R2\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b;\u0010\u000f\u001a\u0004\b<\u0010\u0011\"\u0004\b=\u0010\u0013R2\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010\u000f\u001a\u0004\b?\u0010\u0011\"\u0004\b@\u0010\u0013R2\u0010A\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\r8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u0010\u000f\u001a\u0004\bB\u0010\u0011\"\u0004\bC\u0010\u0013R\u0017\u0010D\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\bD\u0010-\u001a\u0004\bE\u0010/R\u0017\u0010F\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\bF\u0010-\u001a\u0004\bG\u0010/\u00a8\u0006K"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/ZubatModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "fly", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFly", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFly", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "hover", "getHover", "setHover", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "shoulderLeft", "getShoulderLeft", "setShoulderLeft", "", "shoulderOffset", "I", "getShoulderOffset", "()I", "shoulderRight", "getShoulderRight", "setShoulderRight", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "wings_folded", "getWings_folded", "wings_open", "getWings_open", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class ZubatModel
extends PokemonPoseableModel {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart wings_folded;
    @NotNull
    private final ModelPart wings_open;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> hover;
    public Pose<PokemonEntity, ModelFrame> fly;
    public Pose<PokemonEntity, ModelFrame> shoulderLeft;
    public Pose<PokemonEntity, ModelFrame> shoulderRight;
    private final int shoulderOffset;
    @NotNull
    private final CryProvider cryAnimation;

    public ZubatModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "zubat");
        this.wings_folded = this.getPart("wings_folded");
        this.wings_open = this.getPart("wings_open");
        this.portraitScale = 1.7f;
        this.portraitTranslation = new Vec3(0.0, 0.0, 0.0);
        this.profileScale = 0.7f;
        this.profileTranslation = new Vec3(0.0, 0.7, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> ZubatModel.cryAnimation$lambda$0(this, arg_0, arg_1);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @NotNull
    public final ModelPart getWings_folded() {
        return this.wings_folded;
    }

    @NotNull
    public final ModelPart getWings_open() {
        return this.wings_open;
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

    public final int getShoulderOffset() {
        return this.shoulderOffset;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk twitch7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ ZubatModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "zubat", "eartwitch", null, 4, null);
            }
        }, 7, null);
        Object[] objectArray = PoseType.SLEEP;
        Object[] objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wings_folded).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wings_open).withVisibility(true)};
        Object[] objectArray3 = objectArray2;
        Object[] objectArray4 = new ModelQuirk[]{twitch7};
        objectArray2 = objectArray4;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "sleep", null, 4, null)};
        objectArray4 = statelessAnimationArray;
        this.setSleep(PoseableEntityModel.registerPose$default(this, (PoseType)objectArray, null, 0, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 30, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        objectArray = SetsKt.minus((Set)SetsKt.plus((Set)set2, (Iterable)enumSet2), (Object)((Object)PoseType.HOVER));
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wings_folded).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wings_open).withVisibility(true)};
        objectArray3 = objectArray2;
        objectArray4 = new ModelQuirk[]{twitch7};
        objectArray2 = objectArray4;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "ground_idle", null, 4, null)};
        objectArray4 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)objectArray, null, 10, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 52, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.MOVING_POSES");
        objectArray = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.FLY));
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wings_folded).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wings_open).withVisibility(true)};
        objectArray3 = objectArray2;
        objectArray4 = new ModelQuirk[]{twitch7};
        objectArray2 = objectArray4;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "ground_walk", null, 4, null)};
        objectArray4 = statelessAnimationArray;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walk", (Set)objectArray, null, 10, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 52, null));
        objectArray = PoseType.HOVER;
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wings_folded).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wings_open).withVisibility(true)};
        objectArray3 = objectArray2;
        objectArray4 = new ModelQuirk[]{twitch7};
        objectArray2 = objectArray4;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "air_idle", null, 4, null)};
        objectArray4 = statelessAnimationArray;
        this.setHover(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hover", (PoseType)objectArray, null, 10, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 52, null));
        objectArray = PoseType.FLY;
        objectArray2 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.wings_folded).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.wings_open).withVisibility(true)};
        objectArray3 = objectArray2;
        objectArray4 = new ModelQuirk[]{twitch7};
        objectArray2 = objectArray4;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "air_fly", null, 4, null)};
        objectArray4 = statelessAnimationArray;
        this.setFly(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "fly", (PoseType)objectArray, null, 10, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 52, null));
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "shoulder_left", null, 4, null)};
        StatelessAnimation[] statelessAnimationArray2 = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, this.shoulderOffset)};
        this.setShoulderLeft(PoseableEntityModel.registerPose$default(this, PoseType.SHOULDER_LEFT, null, 0, null, null, statelessAnimationArray2, (ModelPartTransformation[])objectArray, null, 158, null));
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "zubat", "shoulder_right", null, 4, null)};
        Object[] objectArray5 = objectArray;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -this.shoulderOffset)};
        this.setShoulderRight(PoseableEntityModel.registerPose$default(this, PoseType.SHOULDER_RIGHT, null, 0, null, null, (StatelessAnimation[])objectArray5, (ModelPartTransformation[])objectArray, null, 158, null));
    }

    private static final StatefulAnimation cryAnimation$lambda$0(ZubatModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "zubat", "cry", null, 4, null);
    }
}

