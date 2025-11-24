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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockStatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BipedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.CryProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4.MonfernoModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u001a\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\u0012\u0006\u0010A\u001a\u00020\u001c\u00a2\u0006\u0004\bB\u0010CJ-\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\f\u0010\rR2\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\u001e\u001a\u0004\b\"\u0010 R\"\u0010$\u001a\u00020#8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\"\u0010+\u001a\u00020*8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\"\u00101\u001a\u00020#8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010%\u001a\u0004\b2\u0010'\"\u0004\b3\u0010)R\"\u00104\u001a\u00020*8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010,\u001a\u0004\b5\u0010.\"\u0004\b6\u00100R\u001a\u00107\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b7\u0010\u001e\u001a\u0004\b8\u0010 R\u001a\u00109\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b9\u0010\u001e\u001a\u0004\b:\u0010 R2\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b;\u0010\u0012\u001a\u0004\b<\u0010\u0014\"\u0004\b=\u0010\u0016R2\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000ej\u0002`\u00108\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010\u0012\u001a\u0004\b?\u0010\u0014\"\u0004\b@\u0010\u0016\u00a8\u0006D"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen4/MonfernoModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BipedFrame;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "getFaintAnimation", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;)Lcom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockStatefulAnimation;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftLeg", "getLeftLeg", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rightLeg", "getRightLeg", "rootPart", "getRootPart", "standing", "getStanding", "setStanding", "walk", "getWalk", "setWalk", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class MonfernoModel
extends PokemonPoseableModel
implements HeadedFrame,
BipedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart leftLeg;
    @NotNull
    private final ModelPart rightLeg;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> battleidle;
    @NotNull
    private final CryProvider cryAnimation;

    public MonfernoModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "monferno");
        this.head = this.getPart("head");
        this.leftLeg = this.getPart("leg_left");
        this.rightLeg = this.getPart("leg_right");
        this.portraitScale = 2.2f;
        this.portraitTranslation = new Vec3(-0.2, 0.4, 0.0);
        this.profileScale = 0.7f;
        this.profileTranslation = new Vec3(0.0, 0.6, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> MonfernoModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public ModelPart getLeftLeg() {
        return this.leftLeg;
    }

    @Override
    @NotNull
    public ModelPart getRightLeg() {
        return this.rightLeg;
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

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ MonfernoModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "monferno", "blink", null, 4, null);
            }
        }, 7, null);
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        EnumSet<PoseType> enumSet3 = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "monferno", "ground_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", enumSet3, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        enumSet3 = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "monferno", "ground_walk", null, 4, null)};
        objectArray = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"MOVING_POSES");
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel, "walk", enumSet3, null, 0, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 188, null));
        enumSet3 = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "monferno", "battle_idle", null, 4, null)};
        objectArray = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel2, "battle_idle", enumSet3, (Function1)registerPoses.1.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray, null, (ModelQuirk[])objectArray2, 176, null));
    }

    @Nullable
    public BedrockStatefulAnimation<PokemonEntity> getFaintAnimation(@NotNull PokemonEntity pokemonEntity, @NotNull PoseableEntityState<PokemonEntity> state) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        Pose[] poseArray = new Pose[]{this.getStanding(), this.getWalk(), this.getBattleidle()};
        return state.isPosedIn(poseArray) ? PoseableEntityModel.bedrockStateful$default(this, "monferno", "faint", null, 4, null) : null;
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(MonfernoModel this$0, PokemonEntity pokemonEntity, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return PoseableEntityModel.bedrockStateful$default(this$0, "monferno", "cry", null, 4, null);
    }
}

