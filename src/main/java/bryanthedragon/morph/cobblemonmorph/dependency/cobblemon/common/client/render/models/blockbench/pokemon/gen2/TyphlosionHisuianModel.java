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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2;

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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen2.TyphlosionHisuianModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b#\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010A\u001a\u00020\u0015\u00a2\u0006\u0004\bB\u0010CJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R2\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u00108\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0016\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\"\u0010\u001b\u001a\u00020\u001a8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\"\u0010\"\u001a\u00020!8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010(\u001a\u00020\u001a8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010\u001c\u001a\u0004\b)\u0010\u001e\"\u0004\b*\u0010 R\"\u0010+\u001a\u00020!8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010#\u001a\u0004\b,\u0010%\"\u0004\b-\u0010'R\u001a\u0010.\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b.\u0010\u0017\u001a\u0004\b/\u0010\u0019R2\u00100\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b0\u0010\u000b\u001a\u0004\b1\u0010\r\"\u0004\b2\u0010\u000fR\u0017\u00103\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b3\u0010\u0017\u001a\u0004\b4\u0010\u0019R\u0017\u00105\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b5\u0010\u0017\u001a\u0004\b6\u0010\u0019R\u0017\u00107\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b7\u0010\u0017\u001a\u0004\b8\u0010\u0019R\u0017\u00109\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b9\u0010\u0017\u001a\u0004\b:\u0010\u0019R2\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b;\u0010\u000b\u001a\u0004\b<\u0010\r\"\u0004\b=\u0010\u000fR2\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010\u000b\u001a\u0004\b?\u0010\r\"\u0004\b@\u0010\u000f\u00a8\u0006D"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen2/TyphlosionHisuianModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "sleep", "getSleep", "setSleep", "spoopy_flame", "getSpoopy_flame", "spoopy_orb1", "getSpoopy_orb1", "spoopy_orb2", "getSpoopy_orb2", "spoopy_orb3", "getSpoopy_orb3", "standing", "getStanding", "setStanding", "walking", "getWalking", "setWalking", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class TyphlosionHisuianModel
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
    public Pose<PokemonEntity, ModelFrame> battleidle;
    @NotNull
    private final ModelPart spoopy_flame;
    @NotNull
    private final ModelPart spoopy_orb1;
    @NotNull
    private final ModelPart spoopy_orb2;
    @NotNull
    private final ModelPart spoopy_orb3;
    @NotNull
    private final CryProvider cryAnimation;

    public TyphlosionHisuianModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "typhlosion_hisui");
        this.head = this.getPart("head");
        this.portraitScale = 1.4f;
        this.portraitTranslation = new Vec3(-0.45, 2.37, 0.0);
        this.profileScale = 0.5f;
        this.profileTranslation = new Vec3(0.0, 1.0, 0.0);
        this.spoopy_flame = this.getPart("fire_main");
        this.spoopy_orb1 = this.getPart("fire_orb_right1");
        this.spoopy_orb2 = this.getPart("fire_orb_middle1");
        this.spoopy_orb3 = this.getPart("fire_orb_left1");
        this.cryAnimation = (arg_0, arg_1) -> TyphlosionHisuianModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public final ModelPart getSpoopy_flame() {
        return this.spoopy_flame;
    }

    @NotNull
    public final ModelPart getSpoopy_orb1() {
        return this.spoopy_orb1;
    }

    @NotNull
    public final ModelPart getSpoopy_orb2() {
        return this.spoopy_orb2;
    }

    @NotNull
    public final ModelPart getSpoopy_orb3() {
        return this.spoopy_orb3;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ TyphlosionHisuianModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "typhlosion_hisuian", "blink", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object[] objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(false)};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "sleep", null, 4, null)};
        objectArray = objectArray3;
        this.setSleep(PoseableEntityModel.registerPose$default(this, object, null, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray2, null, 158, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STATIONARY_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.UI_POSES");
        object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(false)};
        objectArray = objectArray3;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, Float.valueOf(0.0f), null, null, 895, null), PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.1.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, Float.valueOf(0.0f), null, null, 895, null), PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "ground_walk", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalking(PoseableEntityModel.registerPose$default(poseableEntityModel, "walking", (Set)object, null, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 52, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.spoopy_flame).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.spoopy_orb1).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.spoopy_orb2).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.spoopy_orb3).withVisibility(true)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, Float.valueOf(0.0f), null, null, 895, null), PoseableEntityModel.bedrock$default(this, "typhlosion_hisuian", "battle_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel2, "battle_idle", (Set)object, (Function1)registerPoses.2.INSTANCE, 10, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 48, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(TyphlosionHisuianModel this$0, PokemonEntity entity2, PoseableEntityState poseableEntityState) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)poseableEntityState, (String)"<anonymous parameter 1>");
        return entity2.isBattling() ? (StatefulAnimation)PoseableEntityModel.bedrockStateful$default(this$0, "typhlosion_hisuian", "battle_cry", null, 4, null) : (StatefulAnimation)PoseableEntityModel.bedrockStateful$default(this$0, "typhlosion_hisuian", "cry", null, 4, null);
    }
}

