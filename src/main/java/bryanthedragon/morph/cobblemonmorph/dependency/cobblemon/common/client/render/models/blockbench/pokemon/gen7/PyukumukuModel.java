/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010(\u001a\u00020\u0019\u00a2\u0006\u0004\b)\u0010*J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\"\u0010\u0006\u001a\u00020\u00058\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\"\u0010\r\u001a\u00020\f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\"\u0010\u0013\u001a\u00020\u00058\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0007\u001a\u0004\b\u0014\u0010\t\"\u0004\b\u0015\u0010\u000bR\"\u0010\u0016\u001a\u00020\f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u000e\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001a\u0010\u001a\u001a\u00020\u00198\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR2\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001ej\u0002`!8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/PyukumukuModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "", "registerPoses", "()V", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "standing", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getStanding", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setStanding", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPyukumukuModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PyukumukuModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/PyukumukuModel\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,45:1\n26#2:46\n*S KotlinDebug\n*F\n+ 1 PyukumukuModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/PyukumukuModel\n*L\n37#1:46\n*E\n"})
public final class PyukumukuModel
extends PokemonPoseableModel {
    @NotNull
    private final ModelPart rootPart;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;

    public PyukumukuModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "pyukumuku");
        this.portraitScale = 1.65f;
        this.portraitTranslation = new Vec3(-0.1, -0.8, 0.0);
        this.profileScale = 1.0f;
        this.profileTranslation = new Vec3(0.0, 0.2, 0.0);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
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

    @Override
    public void registerPoses() {
        PoseableEntityModel poseableEntityModel = this;
        EnumSet<PoseType> enumSet = PoseType.Companion.getALL_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.ALL_POSES");
        boolean $i$f$emptyArray = false;
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel, "standing", enumSet, null, 0, null, null, new StatelessAnimation[0], null, null, 444, null));
    }
}

