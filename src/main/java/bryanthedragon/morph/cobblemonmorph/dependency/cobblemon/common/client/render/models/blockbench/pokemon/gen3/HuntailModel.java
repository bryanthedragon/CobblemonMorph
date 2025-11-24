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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen3;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010F\u001a\u00020\u0010\u00a2\u0006\u0004\bG\u0010HJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R2\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u00108\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\u0016\u001a\u00020\u00158\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010#\u001a\u00020\u00158\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\u0017\u001a\u0004\b$\u0010\u0019\"\u0004\b%\u0010\u001bR\"\u0010&\u001a\u00020\u001c8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010\u001e\u001a\u0004\b'\u0010 \"\u0004\b(\u0010\"R\u001a\u0010)\u001a\u00020\u00108\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010\u0012\u001a\u0004\b*\u0010\u0014R2\u0010+\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b+\u0010\u000b\u001a\u0004\b,\u0010\r\"\u0004\b-\u0010\u000fR2\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b.\u0010\u000b\u001a\u0004\b/\u0010\r\"\u0004\b0\u0010\u000fR\u0014\u00101\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b1\u0010\u0012R\u0014\u00102\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b2\u0010\u0012R\u0017\u00104\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107R\u0014\u00108\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b8\u0010\u0012R\u0017\u00109\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b9\u00105\u001a\u0004\b:\u00107R\u0014\u0010;\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010\u0012R\u0017\u0010<\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b<\u00105\u001a\u0004\b=\u00107R\u0014\u0010>\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010\u0012R\u0017\u0010?\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b?\u00105\u001a\u0004\b@\u00107R\u0014\u0010A\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010\u0012R\u0017\u0010B\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\bB\u00105\u001a\u0004\bC\u00107R\u0017\u0010D\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\bD\u00105\u001a\u0004\bE\u00107\u00a8\u0006I"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen3/HuntailModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "floating", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getFloating", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setFloating", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "standing", "getStanding", "setStanding", "swimming", "getSwimming", "setSwimming", "tail", "tail2", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "tail2Segment", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "getTail2Segment", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "tail3", "tail3Segment", "getTail3Segment", "tail4", "tail4Segment", "getTail4Segment", "tail5", "tail5Segment", "getTail5Segment", "tail6", "tail6Segment", "getTail6Segment", "tailSegment", "getTailSegment", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class HuntailModel
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
    public Pose<PokemonEntity, ModelFrame> floating;
    public Pose<PokemonEntity, ModelFrame> swimming;
    @NotNull
    private final ModelPart tail;
    @NotNull
    private final ModelPart tail2;
    @NotNull
    private final ModelPart tail3;
    @NotNull
    private final ModelPart tail4;
    @NotNull
    private final ModelPart tail5;
    @NotNull
    private final ModelPart tail6;
    @NotNull
    private final WaveSegment tailSegment;
    @NotNull
    private final WaveSegment tail2Segment;
    @NotNull
    private final WaveSegment tail3Segment;
    @NotNull
    private final WaveSegment tail4Segment;
    @NotNull
    private final WaveSegment tail5Segment;
    @NotNull
    private final WaveSegment tail6Segment;

    public HuntailModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "huntail");
        this.head = this.getPart("head");
        this.portraitScale = 2.6f;
        this.portraitTranslation = new Vec3(-1.3, -2.5, 0.0);
        this.profileScale = 0.9f;
        this.profileTranslation = new Vec3(0.0, 0.0, 0.0);
        this.tail = this.getPart("tail");
        this.tail2 = this.getPart("tail2");
        this.tail3 = this.getPart("tail3");
        this.tail4 = this.getPart("tail4");
        this.tail5 = this.getPart("tail5");
        this.tail6 = this.getPart("tail6");
        this.tailSegment = new WaveSegment(this.tail, 5.0f);
        this.tail2Segment = new WaveSegment(this.tail2, 5.0f);
        this.tail3Segment = new WaveSegment(this.tail3, 5.0f);
        this.tail4Segment = new WaveSegment(this.tail4, 5.0f);
        this.tail5Segment = new WaveSegment(this.tail5, 5.0f);
        this.tail6Segment = new WaveSegment(this.tail6, 5.0f);
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
    public final WaveSegment getTailSegment() {
        return this.tailSegment;
    }

    @NotNull
    public final WaveSegment getTail2Segment() {
        return this.tail2Segment;
    }

    @NotNull
    public final WaveSegment getTail3Segment() {
        return this.tail3Segment;
    }

    @NotNull
    public final WaveSegment getTail4Segment() {
        return this.tail4Segment;
    }

    @NotNull
    public final WaveSegment getTail5Segment() {
        return this.tail5Segment;
    }

    @NotNull
    public final WaveSegment getTail6Segment() {
        return this.tail6Segment;
    }

    @Override
    public void registerPoses() {
        PoseableEntityModel poseableEntityModel = this;
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTANDING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STANDING_POSES");
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[3];
        statelessAnimationArray[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        statelessAnimationArray[1] = PoseableEntityModel.bedrock$default(this, "huntail", "ground_idle", null, 4, null);
        Function1 function1 = WaveFunctionKt.sineFunction$default(0.8f, 8.0f, 0.0f, 0.0f, 12, null);
        ModelPart modelPart = this.getHead();
        WaveSegment[] waveSegmentArray = new WaveSegment[]{this.tailSegment, this.tail2Segment, this.tail3Segment, this.tail4Segment, this.tail5Segment, this.tail6Segment};
        WaveSegment[] waveSegmentArray2 = waveSegmentArray;
        statelessAnimationArray[2] = new WaveAnimation(this, (Function1<? super Float, Float>)function1, 5.0f, modelPart, 16.0f, false, 1, 0, true, waveSegmentArray2);
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel, "standing", enumSet, null, 0, null, null, statelessAnimationArray, null, null, 444, null));
        PoseableEntityModel poseableEntityModel2 = this;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "huntail", "water_idle", null, 4, null)};
        this.setFloating(PoseableEntityModel.registerPose$default(poseableEntityModel2, "floating", SetsKt.plus((Set)enumSet2, (Object)((Object)PoseType.FLOAT)), null, 0, null, null, statelessAnimationArray, null, null, 444, null));
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "huntail", "water_swim", null, 4, null)};
        this.setSwimming(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "swimming", PoseType.SWIM, null, 0, null, null, statelessAnimationArray, null, null, 444, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

