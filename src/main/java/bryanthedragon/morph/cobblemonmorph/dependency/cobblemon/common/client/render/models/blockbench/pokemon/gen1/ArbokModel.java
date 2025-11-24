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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0019\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010V\u001a\u00020\u0012\u00a2\u0006\u0004\bW\u0010XJ-\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00072\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\"\u0010%\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\u0019\u001a\u0004\b&\u0010\u001b\"\u0004\b'\u0010\u001dR\"\u0010(\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010 \u001a\u0004\b)\u0010\"\"\u0004\b*\u0010$R\u001a\u0010+\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b+\u0010\u0014\u001a\u0004\b,\u0010\u0016R2\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u00103\"\u0004\b4\u00105R2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u00101\u001a\u0004\b7\u00103\"\u0004\b8\u00105R2\u00109\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b9\u00101\u001a\u0004\b:\u00103\"\u0004\b;\u00105R\u0017\u0010<\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b<\u0010\u0014\u001a\u0004\b=\u0010\u0016R\u0017\u0010>\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b>\u0010\u0014\u001a\u0004\b?\u0010\u0016R\u0017\u0010A\u001a\u00020@8\u0006\u00a2\u0006\f\n\u0004\bA\u0010B\u001a\u0004\bC\u0010DR\u0017\u0010E\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\bE\u0010\u0014\u001a\u0004\bF\u0010\u0016R\u0017\u0010G\u001a\u00020@8\u0006\u00a2\u0006\f\n\u0004\bG\u0010B\u001a\u0004\bH\u0010DR\u0017\u0010I\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\bI\u0010\u0014\u001a\u0004\bJ\u0010\u0016R\u0017\u0010K\u001a\u00020@8\u0006\u00a2\u0006\f\n\u0004\bK\u0010B\u001a\u0004\bL\u0010DR\u0017\u0010M\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\bM\u0010\u0014\u001a\u0004\bN\u0010\u0016R\u0017\u0010O\u001a\u00020@8\u0006\u00a2\u0006\f\n\u0004\bO\u0010B\u001a\u0004\bP\u0010DR\u0017\u0010Q\u001a\u00020@8\u0006\u00a2\u0006\f\n\u0004\bQ\u0010B\u001a\u0004\bR\u0010DR2\u0010S\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020.0-j\u0002`/8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bS\u00101\u001a\u0004\bT\u00103\"\u0004\bU\u00105\u00a8\u0006Y"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/ArbokModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "sleep", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getSleep", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setSleep", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "standing", "getStanding", "setStanding", "summary", "getSummary", "setSummary", "tail", "getTail", "tail2", "getTail2", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "tail2WaveSegment", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "getTail2WaveSegment", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "tail3", "getTail3", "tail3WaveSegment", "getTail3WaveSegment", "tail4", "getTail4", "tail4WaveSegment", "getTail4WaveSegment", "tail5", "getTail5", "tail5WaveSegment", "getTail5WaveSegment", "tailWaveSegment", "getTailWaveSegment", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class ArbokModel
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
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> summary;
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
    private final WaveSegment tailWaveSegment;
    @NotNull
    private final WaveSegment tail2WaveSegment;
    @NotNull
    private final WaveSegment tail3WaveSegment;
    @NotNull
    private final WaveSegment tail4WaveSegment;
    @NotNull
    private final WaveSegment tail5WaveSegment;
    @NotNull
    private final CryProvider cryAnimation;

    public ArbokModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "arbok");
        this.head = this.getPart("head_ai");
        this.portraitScale = 1.5f;
        this.portraitTranslation = new Vec3(-0.4, 1.0, 0.0);
        this.profileScale = 0.67f;
        this.profileTranslation = new Vec3(0.0, 0.7, 0.0);
        this.tail = this.getPart("tail");
        this.tail2 = this.getPart("tail2");
        this.tail3 = this.getPart("tail3");
        this.tail4 = this.getPart("tail4");
        this.tail5 = this.getPart("tail5");
        this.tailWaveSegment = new WaveSegment(this.tail, 11.0f);
        this.tail2WaveSegment = new WaveSegment(this.tail2, 11.0f);
        this.tail3WaveSegment = new WaveSegment(this.tail3, 11.0f);
        this.tail4WaveSegment = new WaveSegment(this.tail4, 11.0f);
        this.tail5WaveSegment = new WaveSegment(this.tail5, 11.0f);
        this.cryAnimation = (arg_0, arg_1) -> ArbokModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public final Pose<PokemonEntity, ModelFrame> getSummary() {
        Pose<PokemonEntity, ModelFrame> pose = this.summary;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"summary");
        return null;
    }

    public final void setSummary(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.summary = pose;
    }

    @NotNull
    public final ModelPart getTail() {
        return this.tail;
    }

    @NotNull
    public final ModelPart getTail2() {
        return this.tail2;
    }

    @NotNull
    public final ModelPart getTail3() {
        return this.tail3;
    }

    @NotNull
    public final ModelPart getTail4() {
        return this.tail4;
    }

    @NotNull
    public final ModelPart getTail5() {
        return this.tail5;
    }

    @NotNull
    public final WaveSegment getTailWaveSegment() {
        return this.tailWaveSegment;
    }

    @NotNull
    public final WaveSegment getTail2WaveSegment() {
        return this.tail2WaveSegment;
    }

    @NotNull
    public final WaveSegment getTail3WaveSegment() {
        return this.tail3WaveSegment;
    }

    @NotNull
    public final WaveSegment getTail4WaveSegment() {
        return this.tail4WaveSegment;
    }

    @NotNull
    public final WaveSegment getTail5WaveSegment() {
        return this.tail5WaveSegment;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ ArbokModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "arbok", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = WaveFunctionKt.sineFunction$default(0.5f, 10.0f, 0.0f, 0.0f, 12, null);
        Object[] objectArray = this.tail;
        Object[] objectArray2 = new WaveSegment[]{this.tailWaveSegment, this.tail2WaveSegment, this.tail3WaveSegment, this.tail4WaveSegment, this.tail5WaveSegment};
        Object[] objectArray3 = objectArray2;
        WaveAnimation wave = new WaveAnimation(this, (Function1)object, 8.0f, (ModelPart)objectArray, 0.1f, false, 1, 0, true, (WaveSegment[])objectArray3, 32, null);
        object = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "arbok", "sleep", null, 4, null)};
        this.setSleep(PoseableEntityModel.registerPose$default(this, PoseType.SLEEP, null, 0, null, null, object, null, null, 222, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "arbok", "summary_idle", null, 4, null), wave};
        objectArray3 = objectArray2;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setStanding(PoseableEntityModel.registerPose$default(poseableEntityModel, "standing", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray3, null, (ModelQuirk[])objectArray, 180, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "arbok", "ground_walk", null, 4, null), wave};
        objectArray3 = objectArray2;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel2, "walk", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray3, null, (ModelQuirk[])objectArray, 180, null));
        object = PoseType.Companion.getUI_POSES();
        objectArray3 = new ModelQuirk[]{blink7};
        objectArray = objectArray3;
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "arbok", "summary_idle", null, 4, null)};
        objectArray3 = objectArray2;
        PoseableEntityModel poseableEntityModel3 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"UI_POSES");
        this.setSummary(PoseableEntityModel.registerPose$default(poseableEntityModel3, "summary", (Set)object, null, 0, null, null, (StatelessAnimation[])objectArray3, null, (ModelQuirk[])objectArray, 188, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getStanding(), this.getWalk(), this.getSleep()};
        return state.isPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "arbok", "faint", null, 4, null) : null;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(ArbokModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "arbok", "cry", null, 4, null);
    }
}

