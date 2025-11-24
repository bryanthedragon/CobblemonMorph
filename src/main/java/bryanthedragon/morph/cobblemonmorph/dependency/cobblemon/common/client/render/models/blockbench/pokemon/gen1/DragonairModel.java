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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1.DragonairModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b*\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010N\u001a\u00020\u0018\u00a2\u0006\u0004\bO\u0010PJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R2\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR2\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u001a\u0010\u0014\u001a\u00020\u00138\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR2\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\r\"\u0004\b\u001f\u0010\u000fR\"\u0010!\u001a\u00020 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\"\u0010(\u001a\u00020'8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\"\u0010.\u001a\u00020 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010\"\u001a\u0004\b/\u0010$\"\u0004\b0\u0010&R\"\u00101\u001a\u00020'8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010)\u001a\u0004\b2\u0010+\"\u0004\b3\u0010-R\u001a\u00104\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u0010\u001a\u001a\u0004\b5\u0010\u001cR2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u0010\u000b\u001a\u0004\b7\u0010\r\"\u0004\b8\u0010\u000fR2\u00109\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b9\u0010\u000b\u001a\u0004\b:\u0010\r\"\u0004\b;\u0010\u000fR2\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b<\u0010\u000b\u001a\u0004\b=\u0010\r\"\u0004\b>\u0010\u000fR2\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b?\u0010\u000b\u001a\u0004\b@\u0010\r\"\u0004\bA\u0010\u000fR2\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bB\u0010\u000b\u001a\u0004\bC\u0010\r\"\u0004\bD\u0010\u000fR2\u0010E\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bE\u0010\u000b\u001a\u0004\bF\u0010\r\"\u0004\bG\u0010\u000fR2\u0010H\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010\u000b\u001a\u0004\bI\u0010\r\"\u0004\bJ\u0010\u000fR2\u0010K\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bK\u0010\u000b\u001a\u0004\bL\u0010\r\"\u0004\bM\u0010\u000f\u00a8\u0006Q"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/DragonairModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battle_idle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattle_idle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattle_idle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "flying", "getFlying", "setFlying", "", "flyingoffset", "I", "getFlyingoffset", "()I", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "hovering", "getHovering", "setHovering", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "surface_float", "getSurface_float", "setSurface_float", "surface_swim", "getSurface_swim", "setSurface_swim", "walking", "getWalking", "setWalking", "water_idle", "getWater_idle", "setWater_idle", "water_sleep", "getWater_sleep", "setWater_sleep", "water_swim", "getWater_swim", "setWater_swim", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class DragonairModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walking;
    public Pose<PokemonEntity, ModelFrame> water_idle;
    public Pose<PokemonEntity, ModelFrame> water_swim;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> water_sleep;
    public Pose<PokemonEntity, ModelFrame> surface_swim;
    public Pose<PokemonEntity, ModelFrame> surface_float;
    public Pose<PokemonEntity, ModelFrame> battle_idle;
    public Pose<PokemonEntity, ModelFrame> hovering;
    public Pose<PokemonEntity, ModelFrame> flying;
    private final int flyingoffset;

    public DragonairModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "dragonair");
        this.head = this.getPart("head");
        this.portraitScale = 2.3f;
        this.portraitTranslation = new Vec3(-0.02, 1.58, 0.0);
        this.profileScale = 0.65f;
        this.profileTranslation = new Vec3(0.1, 0.9, 0.0);
        this.flyingoffset = -12;
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
    public final Pose<PokemonEntity, ModelFrame> getSurface_swim() {
        Pose<PokemonEntity, ModelFrame> pose = this.surface_swim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"surface_swim");
        return null;
    }

    public final void setSurface_swim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.surface_swim = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSurface_float() {
        Pose<PokemonEntity, ModelFrame> pose = this.surface_float;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"surface_float");
        return null;
    }

    public final void setSurface_float(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.surface_float = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getBattle_idle() {
        Pose<PokemonEntity, ModelFrame> pose = this.battle_idle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battle_idle");
        return null;
    }

    public final void setBattle_idle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.battle_idle = pose;
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

    public final int getFlyingoffset() {
        return this.flyingoffset;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ DragonairModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "dragonair", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", PoseType.SLEEP, (Function1)registerPoses.1.INSTANCE, 0, null, null, object, null, null, 440, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "water_sleep", null, 4, null)};
        this.setWater_sleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_sleep", PoseType.SLEEP, (Function1)registerPoses.2.INSTANCE, 0, null, null, object, null, null, 440, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        object = SetsKt.minus((Set)SetsKt.minus((Set)SetsKt.plus((Set)set2, (Iterable)enumSet2), (Object)((Object)PoseType.FLOAT)), (Object)((Object)PoseType.HOVER));
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dragonair", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.3.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        object = SetsKt.minus((Set)SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.SWIM)), (Object)((Object)PoseType.FLY));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "ground_walk", null, 4, null)};
        objectArray = objectArray3;
        this.setWalking(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)object, (Function1)registerPoses.4.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.HOVER;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dragonair", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.flyingoffset)};
        objectArray3 = modelPartTransformationArray;
        this.setHovering(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "hovering", (PoseType)((Object)object), (Function1)registerPoses.5.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.FLY;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "dragonair", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.flyingoffset)};
        objectArray3 = modelPartTransformationArray;
        this.setFlying(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "flying", (PoseType)((Object)object), (Function1)registerPoses.6.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "water_idle", null, 4, null)};
        this.setWater_idle(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_idle", PoseType.FLOAT, (Function1)registerPoses.7.INSTANCE, 10, null, null, object, null, null, 432, null));
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "water_swim", null, 4, null)};
        this.setWater_swim(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_swim", PoseType.SWIM, (Function1)registerPoses.8.INSTANCE, 10, null, null, object, null, null, 432, null));
        PoseableEntityModel poseableEntityModel = this;
        EnumSet<PoseType> enumSet4 = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet4, (String)"PoseType.STATIONARY_POSES");
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "surfacewater_idle", null, 4, null)};
        this.setSurface_float(PoseableEntityModel.registerPose$default(poseableEntityModel, "surface_float", enumSet4, (Function1)registerPoses.9.INSTANCE, 10, null, null, object, null, null, 432, null));
        PoseableEntityModel poseableEntityModel2 = this;
        EnumSet<PoseType> enumSet5 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet5, (String)"PoseType.MOVING_POSES");
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "surfacewater_swim", null, 4, null)};
        this.setSurface_swim(PoseableEntityModel.registerPose$default(poseableEntityModel2, "surface_swim", enumSet5, (Function1)registerPoses.10.INSTANCE, 10, null, null, object, null, null, 432, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "dragonair", "battle_idle", null, 4, null)};
        objectArray2 = objectArray;
        PoseableEntityModel poseableEntityModel3 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattle_idle(PoseableEntityModel.registerPose$default(poseableEntityModel3, "battle_idle", (Set)object, (Function1)registerPoses.11.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray2, null, null, 432, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

