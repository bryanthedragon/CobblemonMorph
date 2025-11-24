/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BimanualFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3.ArmaldoModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b6\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u000f\u0012\u0006\u0010h\u001a\u00020\u0017\u00a2\u0006\u0004\bi\u0010jJ\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R2\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0019\u001a\u0004\b\u001d\u0010\u001bR\u001a\u0010\u001e\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001bR\"\u0010!\u001a\u00020 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\"\u0010(\u001a\u00020'8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\"\u0010.\u001a\u00020 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010\"\u001a\u0004\b/\u0010$\"\u0004\b0\u0010&R\"\u00101\u001a\u00020'8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010)\u001a\u0004\b2\u0010+\"\u0004\b3\u0010-R\u001a\u00104\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u0010\u0019\u001a\u0004\b5\u0010\u001bR\u001a\u00106\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b6\u0010\u0019\u001a\u0004\b7\u0010\u001bR\u001a\u00108\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b8\u0010\u0019\u001a\u0004\b9\u0010\u001bR2\u0010:\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b:\u0010\r\u001a\u0004\b;\u0010\u000f\"\u0004\b<\u0010\u0011R2\u0010=\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b=\u0010\r\u001a\u0004\b>\u0010\u000f\"\u0004\b?\u0010\u0011R2\u0010@\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b@\u0010\r\u001a\u0004\bA\u0010\u000f\"\u0004\bB\u0010\u0011R2\u0010C\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bC\u0010\r\u001a\u0004\bD\u0010\u000f\"\u0004\bE\u0010\u0011R2\u0010F\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bF\u0010\r\u001a\u0004\bG\u0010\u000f\"\u0004\bH\u0010\u0011R2\u0010I\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bI\u0010\r\u001a\u0004\bJ\u0010\u000f\"\u0004\bK\u0010\u0011R2\u0010L\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bL\u0010\r\u001a\u0004\bM\u0010\u000f\"\u0004\bN\u0010\u0011R2\u0010O\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bO\u0010\r\u001a\u0004\bP\u0010\u000f\"\u0004\bQ\u0010\u0011R2\u0010R\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bR\u0010\r\u001a\u0004\bS\u0010\u000f\"\u0004\bT\u0010\u0011R2\u0010U\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bU\u0010\r\u001a\u0004\bV\u0010\u000f\"\u0004\bW\u0010\u0011R2\u0010X\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bX\u0010\r\u001a\u0004\bY\u0010\u000f\"\u0004\bZ\u0010\u0011R2\u0010[\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u0002`\u000b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b[\u0010\r\u001a\u0004\b\\\u0010\u000f\"\u0004\b]\u0010\u0011R\u001a\u0010_\u001a\u00020^8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b_\u0010`\u001a\u0004\ba\u0010bR\u001a\u0010d\u001a\u00020c8\u0006X\u0086D\u00a2\u0006\f\n\u0004\bd\u0010e\u001a\u0004\bf\u0010g\u00a8\u0006k"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen3/ArmaldoModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BimanualFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftArm", "getLeftArm", "leftLeg", "getLeftLeg", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightArm", "getRightArm", "rightLeg", "getRightLeg", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "ui_poses", "getUi_poses", "setUi_poses", "walking", "getWalking", "setWalking", "water_battleidle", "getWater_battleidle", "setWater_battleidle", "water_idle", "getWater_idle", "setWater_idle", "water_sleep", "getWater_sleep", "setWater_sleep", "water_surface_battleidle", "getWater_surface_battleidle", "setWater_surface_battleidle", "water_surface_idle", "getWater_surface_idle", "setWater_surface_idle", "water_surface_sleep", "getWater_surface_sleep", "setWater_surface_sleep", "water_surface_swim", "getWater_surface_swim", "setWater_surface_swim", "water_swim", "getWater_swim", "setWater_swim", "", "wateroffset", "D", "getWateroffset", "()D", "", "watersurfaceoffset", "I", "getWatersurfaceoffset", "()I", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class ArmaldoModel
extends PokemonPoseableModel
implements HeadedFrame,
BimanualFrame,
BipedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart leftArm;
    @NotNull
    private final ModelPart rightArm;
    @NotNull
    private final ModelPart leftLeg;
    @NotNull
    private final ModelPart rightLeg;
    @NotNull
    private Vec3 portraitTranslation;
    private float portraitScale;
    @NotNull
    private Vec3 profileTranslation;
    private float profileScale;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walking;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> battleidle;
    public Pose<PokemonEntity, ModelFrame> water_surface_sleep;
    public Pose<PokemonEntity, ModelFrame> water_sleep;
    public Pose<PokemonEntity, ModelFrame> water_surface_idle;
    public Pose<PokemonEntity, ModelFrame> water_idle;
    public Pose<PokemonEntity, ModelFrame> water_surface_battleidle;
    public Pose<PokemonEntity, ModelFrame> water_battleidle;
    public Pose<PokemonEntity, ModelFrame> water_surface_swim;
    public Pose<PokemonEntity, ModelFrame> water_swim;
    public Pose<PokemonEntity, ModelFrame> ui_poses;
    private final double wateroffset;
    private final int watersurfaceoffset;
    @NotNull
    private final CryProvider cryAnimation;

    public ArmaldoModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "armaldo");
        this.head = this.getPart("head");
        this.leftArm = this.getPart("arm1_left");
        this.rightArm = this.getPart("arm1_right");
        this.leftLeg = this.getPart("left_leg");
        this.rightLeg = this.getPart("right_leg");
        this.portraitTranslation = new Vec3(-0.46, 1.5, 0.0);
        this.portraitScale = 1.73f;
        this.profileTranslation = new Vec3(0.0, 0.77, 0.0);
        this.profileScale = 0.63f;
        this.wateroffset = -4.5;
        this.watersurfaceoffset = 10;
        this.cryAnimation = (arg_0, arg_1) -> ArmaldoModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public final Pose<PokemonEntity, ModelFrame> getWater_surface_sleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_surface_sleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_surface_sleep");
        return null;
    }

    public final void setWater_surface_sleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_surface_sleep = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_sleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_sleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_sleep");
        return null;
    }

    public final void setWater_sleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_sleep = pose;
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
    public final Pose<PokemonEntity, ModelFrame> getWater_idle() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_idle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_idle");
        return null;
    }

    public final void setWater_idle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_idle = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_surface_battleidle() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_surface_battleidle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_surface_battleidle");
        return null;
    }

    public final void setWater_surface_battleidle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_surface_battleidle = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_battleidle() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_battleidle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_battleidle");
        return null;
    }

    public final void setWater_battleidle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_battleidle = pose;
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

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWater_swim() {
        Pose<PokemonEntity, ModelFrame> pose = this.water_swim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"water_swim");
        return null;
    }

    public final void setWater_swim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.water_swim = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getUi_poses() {
        Pose<PokemonEntity, ModelFrame> pose = this.ui_poses;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"ui_poses");
        return null;
    }

    public final void setUi_poses(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.ui_poses = pose;
    }

    public final double getWateroffset() {
        return this.wateroffset;
    }

    public final int getWatersurfaceoffset() {
        return this.watersurfaceoffset;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ ArmaldoModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "armaldo", "blink", null, 4, null);
            }
        }, 7, null);
        SimpleQuirk look2 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ ArmaldoModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "armaldo", "look_quirk", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.Companion.getUI_POSES();
        Object[] objectArray = new ModelQuirk[]{blink7, look2};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue(object, (String)"UI_POSES");
        this.setUi_poses(PoseableEntityModel.registerPose$default(poseableEntityModel, "ui_poses", (Set)object, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", PoseType.SLEEP, (Function1)registerPoses.1.INSTANCE, 0, null, null, (StatelessAnimation[])object, null, null, 440, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_sleep", null, 4, null)};
        StatelessAnimation[] statelessAnimationArray = object;
        object = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        this.setWater_surface_sleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_surface_sleep", PoseType.SLEEP, (Function1)registerPoses.2.INSTANCE, 0, null, null, statelessAnimationArray, (ModelPartTransformation[])object, null, 312, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_sleep", null, 4, null)};
        Object[] objectArray4 = object;
        object = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        this.setWater_sleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_sleep", PoseType.SLEEP, (Function1)registerPoses.3.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])object, null, 312, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel2, "standing", (Set)object, (Function1)registerPoses.4.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel3 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_surface_idle(PoseableEntityModel.registerPose$default(poseableEntityModel3, "water_surface_idle", (Set)object, (Function1)registerPoses.5.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel4 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_idle(PoseableEntityModel.registerPose$default(poseableEntityModel4, "water_idle", (Set)object, (Function1)registerPoses.6.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "battle_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel5 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel5, "battle_idle", (Set)object, (Function1)registerPoses.7.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_battle_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel6 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_surface_battleidle(PoseableEntityModel.registerPose$default(poseableEntityModel6, "surface_battle_idle", (Set)object, (Function1)registerPoses.8.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_battle_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel7 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_battleidle(PoseableEntityModel.registerPose$default(poseableEntityModel7, "water_battle_idle", (Set)object, (Function1)registerPoses.9.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "ground_walk", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel8 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalking(PoseableEntityModel.registerPose$default(poseableEntityModel8, "walking", (Set)object, (Function1)registerPoses.10.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel9 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWater_surface_swim(PoseableEntityModel.registerPose$default(poseableEntityModel9, "surface_swim", (Set)object, (Function1)registerPoses.11.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7, look2};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "armaldo", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel10 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWater_swim(PoseableEntityModel.registerPose$default(poseableEntityModel10, "water_swim", (Set)object, (Function1)registerPoses.12.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(ArmaldoModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "armaldo", "cry", null, 4, null);
    }
}

