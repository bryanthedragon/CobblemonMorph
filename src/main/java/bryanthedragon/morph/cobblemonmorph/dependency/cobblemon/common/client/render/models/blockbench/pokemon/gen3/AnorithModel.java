/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3.AnorithModel;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b-\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010c\u001a\u00020(\u00a2\u0006\u0004\bd\u0010eJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R2\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\"\u0010\u0015\u001a\u00020\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\"\u0010\"\u001a\u00020\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0018\"\u0004\b$\u0010\u001aR\"\u0010%\u001a\u00020\u001b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u001d\u001a\u0004\b&\u0010\u001f\"\u0004\b'\u0010!R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R2\u0010-\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b-\u0010\n\u001a\u0004\b.\u0010\f\"\u0004\b/\u0010\u000eR\u001a\u00101\u001a\u0002008\u0006X\u0086D\u00a2\u0006\f\n\u0004\b1\u00102\u001a\u0004\b3\u00104R2\u00105\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b5\u0010\n\u001a\u0004\b6\u0010\f\"\u0004\b7\u0010\u000eR2\u00108\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u0010\n\u001a\u0004\b9\u0010\f\"\u0004\b:\u0010\u000eR2\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b;\u0010\n\u001a\u0004\b<\u0010\f\"\u0004\b=\u0010\u000eR2\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010\n\u001a\u0004\b?\u0010\f\"\u0004\b@\u0010\u000eR2\u0010A\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u0010\n\u001a\u0004\bB\u0010\f\"\u0004\bC\u0010\u000eR2\u0010D\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bD\u0010\n\u001a\u0004\bE\u0010\f\"\u0004\bF\u0010\u000eR2\u0010G\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bG\u0010\n\u001a\u0004\bH\u0010\f\"\u0004\bI\u0010\u000eR2\u0010J\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bJ\u0010\n\u001a\u0004\bK\u0010\f\"\u0004\bL\u0010\u000eR2\u0010M\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bM\u0010\n\u001a\u0004\bN\u0010\f\"\u0004\bO\u0010\u000eR2\u0010P\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bP\u0010\n\u001a\u0004\bQ\u0010\f\"\u0004\bR\u0010\u000eR2\u0010S\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bS\u0010\n\u001a\u0004\bT\u0010\f\"\u0004\bU\u0010\u000eR2\u0010V\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bV\u0010\n\u001a\u0004\bW\u0010\f\"\u0004\bX\u0010\u000eR2\u0010Y\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bY\u0010\n\u001a\u0004\bZ\u0010\f\"\u0004\b[\u0010\u000eR\u001a\u0010\\\u001a\u0002008\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\\\u00102\u001a\u0004\b]\u00104R\u001a\u0010_\u001a\u00020^8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b_\u0010`\u001a\u0004\ba\u0010b\u00a8\u0006f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen3/AnorithModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "shoulderLeft", "getShoulderLeft", "setShoulderLeft", "", "shoulderOffset", "D", "getShoulderOffset", "()D", "shoulderRight", "getShoulderRight", "setShoulderRight", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "ui_poses", "getUi_poses", "setUi_poses", "walking", "getWalking", "setWalking", "water_battleidle", "getWater_battleidle", "setWater_battleidle", "water_idle", "getWater_idle", "setWater_idle", "water_sleep", "getWater_sleep", "setWater_sleep", "water_surface_battleidle", "getWater_surface_battleidle", "setWater_surface_battleidle", "water_surface_idle", "getWater_surface_idle", "setWater_surface_idle", "water_surface_sleep", "getWater_surface_sleep", "setWater_surface_sleep", "water_surface_swim", "getWater_surface_swim", "setWater_surface_swim", "water_swim", "getWater_swim", "setWater_swim", "wateroffset", "getWateroffset", "", "watersurfaceoffset", "I", "getWatersurfaceoffset", "()I", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class AnorithModel
extends PokemonPoseableModel {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private Vec3 portraitTranslation;
    private float portraitScale;
    @NotNull
    private Vec3 profileTranslation;
    private float profileScale;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walking;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> ui_poses;
    public Pose<PokemonEntity, ModelFrame> battleidle;
    public Pose<PokemonEntity, ModelFrame> water_surface_sleep;
    public Pose<PokemonEntity, ModelFrame> water_sleep;
    public Pose<PokemonEntity, ModelFrame> water_surface_idle;
    public Pose<PokemonEntity, ModelFrame> water_idle;
    public Pose<PokemonEntity, ModelFrame> water_surface_battleidle;
    public Pose<PokemonEntity, ModelFrame> water_battleidle;
    public Pose<PokemonEntity, ModelFrame> water_surface_swim;
    public Pose<PokemonEntity, ModelFrame> water_swim;
    public Pose<PokemonEntity, ModelFrame> shoulderLeft;
    public Pose<PokemonEntity, ModelFrame> shoulderRight;
    private final double shoulderOffset;
    private final double wateroffset;
    private final int watersurfaceoffset;
    @NotNull
    private final CryProvider cryAnimation;

    public AnorithModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "anorith");
        this.portraitTranslation = new Vec3(-0.14, -1.56, 0.0);
        this.portraitScale = 1.8f;
        this.profileTranslation = new Vec3(-0.05, -0.19, 0.0);
        this.profileScale = 1.03f;
        this.shoulderOffset = 5.5;
        this.wateroffset = -4.5;
        this.watersurfaceoffset = 1;
        this.cryAnimation = (arg_0, arg_1) -> AnorithModel.cryAnimation$lambda$0(this, arg_0, arg_1);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
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

    public final double getShoulderOffset() {
        return this.shoulderOffset;
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
            final /* synthetic */ AnorithModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "anorith", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.Companion.getUI_POSES();
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "summary_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue(object, (String)"UI_POSES");
        this.setUi_poses(PoseableEntityModel.registerPose$default(poseableEntityModel, "ui_poses", (Set)object, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", PoseType.SLEEP, (Function1)registerPoses.1.INSTANCE, 0, null, null, (StatelessAnimation[])object, null, null, 440, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_sleep", null, 4, null)};
        StatelessAnimation[] statelessAnimationArray = object;
        object = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        this.setWater_surface_sleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_surface_sleep", PoseType.SLEEP, (Function1)registerPoses.2.INSTANCE, 0, null, null, statelessAnimationArray, (ModelPartTransformation[])object, null, 312, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_sleep", null, 4, null)};
        Object[] objectArray4 = object;
        object = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        this.setWater_sleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_sleep", PoseType.SLEEP, (Function1)registerPoses.3.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray4, (ModelPartTransformation[])object, null, 312, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel2, "standing", (Set)object, (Function1)registerPoses.4.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel3 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_surface_idle(PoseableEntityModel.registerPose$default(poseableEntityModel3, "water_surface_idle", (Set)object, (Function1)registerPoses.5.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel4 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_idle(PoseableEntityModel.registerPose$default(poseableEntityModel4, "water_idle", (Set)object, (Function1)registerPoses.6.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "battle_idle", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel5 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel5, "battle_idle", (Set)object, (Function1)registerPoses.7.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_battle_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel6 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_surface_battleidle(PoseableEntityModel.registerPose$default(poseableEntityModel6, "surface_battle_idle", (Set)object, (Function1)registerPoses.8.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_battle_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel7 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_battleidle(PoseableEntityModel.registerPose$default(poseableEntityModel7, "water_battle_idle", (Set)object, (Function1)registerPoses.9.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "ground_walk", null, 4, null)};
        objectArray = objectArray3;
        PoseableEntityModel poseableEntityModel8 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalking(PoseableEntityModel.registerPose$default(poseableEntityModel8, "walking", (Set)object, (Function1)registerPoses.10.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.watersurfaceoffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel9 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWater_surface_swim(PoseableEntityModel.registerPose$default(poseableEntityModel9, "surface_swim", (Set)object, (Function1)registerPoses.11.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel10 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWater_swim(PoseableEntityModel.registerPose$default(poseableEntityModel10, "water_swim", (Set)object, (Function1)registerPoses.12.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.SHOULDER_LEFT;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "shoulder_left", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, this.shoulderOffset), ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, 2), ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(2, 2)};
        objectArray3 = modelPartTransformationArray;
        this.setShoulderLeft(PoseableEntityModel.registerPose$default(this, (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 30, null));
        object = PoseType.SHOULDER_RIGHT;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "anorith", "shoulder_right", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(0, -this.shoulderOffset), ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, 2), ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(2, 2)};
        objectArray3 = modelPartTransformationArray;
        this.setShoulderRight(PoseableEntityModel.registerPose$default(this, (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 30, null));
    }

    private static final StatefulAnimation cryAnimation$lambda$0(AnorithModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "anorith", "cry", null, 4, null);
    }
}

