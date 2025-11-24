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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen7.WishiwashiSoloModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u00104\u001a\u00020#\u00a2\u0006\u0004\b5\u00106J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R2\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0010\u001a\u00020\u000f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u00020\u00168\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001d\u001a\u00020\u000f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u0011\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010\u0015R\"\u0010 \u001a\u00020\u00168\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b \u0010\u0018\u001a\u0004\b!\u0010\u001a\"\u0004\b\"\u0010\u001cR\u001a\u0010$\u001a\u00020#8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R2\u0010(\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b(\u0010\n\u001a\u0004\b)\u0010\f\"\u0004\b*\u0010\u000eR2\u0010+\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b+\u0010\n\u001a\u0004\b,\u0010\f\"\u0004\b-\u0010\u000eR2\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b.\u0010\n\u001a\u0004\b/\u0010\f\"\u0004\b0\u0010\u000eR2\u00101\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0002`\b8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b1\u0010\n\u001a\u0004\b2\u0010\f\"\u0004\b3\u0010\u000e\u00a8\u00067"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen7/WishiwashiSoloModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "floating", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFloating", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFloating", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "Lnet/minecraft/client/model/geom/ModelPart;", "rootPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getRootPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "standing", "getStanding", "setStanding", "swimming", "getSwimming", "setSwimming", "walk", "getWalk", "setWalk", "watersleep", "getWatersleep", "setWatersleep", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class WishiwashiSoloModel
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
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> floating;
    public Pose<PokemonEntity, ModelFrame> swimming;
    public Pose<PokemonEntity, ModelFrame> watersleep;

    public WishiwashiSoloModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "wishiwashi_solo");
        this.portraitScale = 3.0f;
        this.portraitTranslation = new Vec3(-0.4, -3.1, 0.0);
        this.profileScale = 0.8f;
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
    public final Pose<PokemonEntity, ModelFrame> getFloating() {
        Pose<PokemonEntity, ModelFrame> pose = this.floating;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"floating");
        return null;
    }

    public final void setFloating(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.floating = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSwimming() {
        Pose<PokemonEntity, ModelFrame> pose = this.swimming;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"swimming");
        return null;
    }

    public final void setSwimming(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.swimming = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWatersleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.watersleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"watersleep");
        return null;
    }

    public final void setWatersleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.watersleep = pose;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ WishiwashiSoloModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "wishiwashi_solo", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_solo", "water_sleep", null, 4, null)};
        this.setWatersleep(PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, registerPoses.1.INSTANCE, 0, null, null, object, null, null, 220, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTANDING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STANDING_POSES");
        object = SetsKt.minus((Set)enumSet, (Object)((Object)PoseType.FLOAT));
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_solo", "ground_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.MOVING_POSES");
        object = SetsKt.minus((Set)enumSet2, (Object)((Object)PoseType.SWIM));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_solo", "ground_walk", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)object, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.UI_POSES");
        object = SetsKt.plus((Set)enumSet3, (Object)((Object)PoseType.FLOAT));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_solo", "water_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setFloating(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "floating", (Set)object, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        object = PoseType.SWIM;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wishiwashi_solo", "water_swim", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setSwimming(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "swimming", (PoseType)((Object)object), null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
    }
}

