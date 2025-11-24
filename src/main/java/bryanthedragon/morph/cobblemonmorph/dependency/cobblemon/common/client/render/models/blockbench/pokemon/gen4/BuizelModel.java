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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4.BuizelModel;
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

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010M\u001a\u00020\u0015\u00a2\u0006\u0004\bN\u0010OJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR2\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\"\u0010\u001b\u001a\u00020\u001a8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\"\u0010\"\u001a\u00020!8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010(\u001a\u00020\u001a8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010\u001c\u001a\u0004\b)\u0010\u001e\"\u0004\b*\u0010 R\"\u0010+\u001a\u00020!8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010#\u001a\u0004\b,\u0010%\"\u0004\b-\u0010'R\u001a\u0010.\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b.\u0010\u0017\u001a\u0004\b/\u0010\u0019R2\u00100\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b0\u0010\u0010\u001a\u0004\b1\u0010\u0012\"\u0004\b2\u0010\u0014R2\u00103\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b3\u0010\u0010\u001a\u0004\b4\u0010\u0012\"\u0004\b5\u0010\u0014R2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u0010\u0010\u001a\u0004\b7\u0010\u0012\"\u0004\b8\u0010\u0014R2\u00109\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b9\u0010\u0010\u001a\u0004\b:\u0010\u0012\"\u0004\b;\u0010\u0014R2\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b<\u0010\u0010\u001a\u0004\b=\u0010\u0012\"\u0004\b>\u0010\u0014R2\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b?\u0010\u0010\u001a\u0004\b@\u0010\u0012\"\u0004\bA\u0010\u0014R2\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bB\u0010\u0010\u001a\u0004\bC\u0010\u0012\"\u0004\bD\u0010\u0014R2\u0010E\u001a\u0012\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bj\u0002`\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bE\u0010\u0010\u001a\u0004\bF\u0010\u0012\"\u0004\bG\u0010\u0014R\u001a\u0010I\u001a\u00020H8\u0006X\u0086D\u00a2\u0006\f\n\u0004\bI\u0010J\u001a\u0004\bK\u0010L\u00a8\u0006P"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen4/BuizelModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "float", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFloat", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFloat", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "surfaceWaterIdle", "getSurfaceWaterIdle", "setSurfaceWaterIdle", "surfaceWaterSleep", "getSurfaceWaterSleep", "setSurfaceWaterSleep", "surfaceWaterSwim", "getSurfaceWaterSwim", "setSurfaceWaterSwim", "swim", "getSwim", "setSwim", "walking", "getWalking", "setWalking", "waterSleep", "getWaterSleep", "setWaterSleep", "", "wateroffset", "I", "getWateroffset", "()I", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class BuizelModel
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
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> waterSleep;
    public Pose<PokemonEntity, ModelFrame> float;
    public Pose<PokemonEntity, ModelFrame> swim;
    public Pose<PokemonEntity, ModelFrame> surfaceWaterIdle;
    public Pose<PokemonEntity, ModelFrame> surfaceWaterSwim;
    public Pose<PokemonEntity, ModelFrame> surfaceWaterSleep;
    private final int wateroffset;
    @NotNull
    private final CryProvider cryAnimation;

    public BuizelModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "buizel");
        this.head = this.getPart("head");
        this.portraitScale = 2.3f;
        this.portraitTranslation = new Vec3(-0.2, 0.1, 0.0);
        this.profileScale = 0.7f;
        this.profileTranslation = new Vec3(0.0, 0.65, 0.0);
        this.wateroffset = -8;
        this.cryAnimation = (arg_0, arg_1) -> BuizelModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public final Pose<PokemonEntity, ModelFrame> getWaterSleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.waterSleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"waterSleep");
        return null;
    }

    public final void setWaterSleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.waterSleep = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getFloat() {
        Pose<PokemonEntity, ModelFrame> pose = this.float;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"float");
        return null;
    }

    public final void setFloat(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.float = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSwim() {
        Pose<PokemonEntity, ModelFrame> pose = this.swim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"swim");
        return null;
    }

    public final void setSwim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.swim = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSurfaceWaterIdle() {
        Pose<PokemonEntity, ModelFrame> pose = this.surfaceWaterIdle;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"surfaceWaterIdle");
        return null;
    }

    public final void setSurfaceWaterIdle(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.surfaceWaterIdle = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSurfaceWaterSwim() {
        Pose<PokemonEntity, ModelFrame> pose = this.surfaceWaterSwim;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"surfaceWaterSwim");
        return null;
    }

    public final void setSurfaceWaterSwim(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.surfaceWaterSwim = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSurfaceWaterSleep() {
        Pose<PokemonEntity, ModelFrame> pose = this.surfaceWaterSleep;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"surfaceWaterSleep");
        return null;
    }

    public final void setSurfaceWaterSleep(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.surfaceWaterSleep = pose;
    }

    public final int getWateroffset() {
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
            final /* synthetic */ BuizelModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "buizel", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object[] objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "buizel", "sleep", null, 4, null)};
        Object[] objectArray2 = objectArray;
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", object, (Function1)registerPoses.1.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray2, null, null, 440, null));
        object = PoseType.SLEEP;
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "buizel", "water_sleep", null, 4, null)};
        objectArray2 = objectArray;
        this.setWaterSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "water_sleep", object, (Function1)registerPoses.2.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray2, null, null, 440, null));
        object = PoseType.SLEEP;
        objectArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "buizel", "surfacewater_sleep", null, 4, null)};
        objectArray2 = objectArray;
        Object[] objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray = objectArray3;
        this.setSurfaceWaterSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "surface_water_sleep", object, (Function1)registerPoses.3.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray2, (ModelPartTransformation[])objectArray, null, 312, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        object = SetsKt.minus((Set)SetsKt.plus((Set)set2, (Iterable)enumSet2), (Object)((Object)PoseType.FLOAT));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "buizel", "ground_idle", null, 4, null)};
        objectArray = objectArray3;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.4.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.MOVING_POSES");
        object = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.SWIM));
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "buizel", "ground_walk", null, 4, null)};
        objectArray = objectArray3;
        this.setWalking(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walking", (Set)object, (Function1)registerPoses.5.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
        object = PoseType.FLOAT;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "buizel", "water_idle", null, 4, null)};
        objectArray = objectArray3;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        this.setFloat(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "float", object, null, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 52, null));
        object = PoseType.SWIM;
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "buizel", "water_swim", null, 4, null)};
        objectArray = objectArray3;
        this.setSwim(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "swim", object, null, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 180, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "buizel", "surfacewater_idle", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setSurfaceWaterIdle(PoseableEntityModel.registerPose$default(poseableEntityModel, "surface_water_idle", (Set)object, (Function1)registerPoses.6.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "buizel", "surfacewater_swim", null, 4, null)};
        objectArray = objectArray3;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray3 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setSurfaceWaterSwim(PoseableEntityModel.registerPose$default(poseableEntityModel2, "surface_water_swim", (Set)object, (Function1)registerPoses.7.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, (ModelQuirk[])objectArray2, 48, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(BuizelModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "buizel", "cry", null, 4, null);
    }
}

