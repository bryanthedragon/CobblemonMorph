/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1.WartortleModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0006\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010P\u001a\u00020\u0013\u00a2\u0006\u0004\bQ\u0010RJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R2\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR2\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u001a\u0010\u0014\u001a\u00020\u00138\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\"\u0010\u0019\u001a\u00020\u00188\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\"\u0010 \u001a\u00020\u001f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\"\u0010&\u001a\u00020\u00188\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010\u001a\u001a\u0004\b'\u0010\u001c\"\u0004\b(\u0010\u001eR\"\u0010)\u001a\u00020\u001f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010!\u001a\u0004\b*\u0010#\"\u0004\b+\u0010%R\u001a\u0010,\u001a\u00020\u00138\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010\u0015\u001a\u0004\b-\u0010\u0017R2\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b.\u0010\u000b\u001a\u0004\b/\u0010\r\"\u0004\b0\u0010\u000fR\u001a\u00102\u001a\u0002018\u0006X\u0086D\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R2\u00106\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b6\u0010\u000b\u001a\u0004\b7\u0010\r\"\u0004\b8\u0010\u000fR2\u00109\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b9\u0010\u000b\u001a\u0004\b:\u0010\r\"\u0004\b;\u0010\u000fR2\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b<\u0010\u000b\u001a\u0004\b=\u0010\r\"\u0004\b>\u0010\u000fR2\u0010?\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b?\u0010\u000b\u001a\u0004\b@\u0010\r\"\u0004\bA\u0010\u000fR2\u0010B\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bB\u0010\u000b\u001a\u0004\bC\u0010\r\"\u0004\bD\u0010\u000fR2\u0010E\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bE\u0010\u000b\u001a\u0004\bF\u0010\r\"\u0004\bG\u0010\u000fR2\u0010H\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010\u000b\u001a\u0004\bI\u0010\r\"\u0004\bJ\u0010\u000fR\u001a\u0010L\u001a\u00020K8\u0006X\u0086D\u00a2\u0006\f\n\u0004\bL\u0010M\u001a\u0004\bN\u0010O\u00a8\u0006S"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/WartortleModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "floating", "getFloating", "setFloating", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "shoulderLeft", "getShoulderLeft", "setShoulderLeft", "", "shoulderOffset", "D", "getShoulderOffset", "()D", "shoulderRight", "getShoulderRight", "setShoulderRight", "sleep", "getSleep", "setSleep", "standing", "getStanding", "setStanding", "swimming", "getSwimming", "setSwimming", "walk", "getWalk", "setWalk", "water_surface_idle", "getWater_surface_idle", "setWater_surface_idle", "water_surface_swim", "getWater_surface_swim", "setWater_surface_swim", "", "wateroffset", "I", "getWateroffset", "()I", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class WartortleModel
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
    public Pose<PokemonEntity, ModelFrame> battleidle;
    public Pose<PokemonEntity, ModelFrame> shoulderLeft;
    public Pose<PokemonEntity, ModelFrame> shoulderRight;
    public Pose<PokemonEntity, ModelFrame> floating;
    public Pose<PokemonEntity, ModelFrame> swimming;
    public Pose<PokemonEntity, ModelFrame> water_surface_idle;
    public Pose<PokemonEntity, ModelFrame> water_surface_swim;
    private final double shoulderOffset;
    private final int wateroffset;

    public WartortleModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "wartortle");
        this.head = this.getPart("head_ai");
        this.portraitScale = 1.57f;
        this.portraitTranslation = new Vec3(-0.05, 0.54, 0.0);
        this.profileScale = 0.69f;
        this.profileTranslation = new Vec3(-0.04, 0.69, 0.0);
        this.shoulderOffset = 5.5;
        this.wateroffset = -10;
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

    public final double getShoulderOffset() {
        return this.shoulderOffset;
    }

    public final int getWateroffset() {
        return this.wateroffset;
    }

    @Override
    public void registerPoses() {
        this.getAnimations().put("physical", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'physical', 'look', q.curve('symmetrical_wide'))"));
        this.getAnimations().put("special", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'special', 'look', q.curve('symmetrical_wide'))"));
        this.getAnimations().put("status", MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'status', q.curve('symmetrical_wide'))"));
        this.getAnimations().put("recoil", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('wartortle', 'recoil')"));
        this.getAnimations().put("cry", MoLangExtensionsKt.asExpressionLike("q.bedrock_stateful('wartortle', 'cry')"));
        ExpressionLike faint = MoLangExtensionsKt.asExpressionLike("q.bedrock_primary('wartortle', 'faint', q.curve('one'))");
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ WartortleModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "wartortle", "blink", null, 4, null);
            }
        }, 7, null);
        SimpleQuirk quirkidle2 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ WartortleModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "wartortle", "quirk_idle", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object object2 = new ModelQuirk[]{blink7};
        ModelQuirk[] modelQuirkArray = object2;
        Object[] objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        Object[] objectArray2 = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "wartortle", "sleep", null, 4, null)};
        objectArray = objectArray2;
        this.setSleep(PoseableEntityModel.registerPose$default(this, object, null, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 86, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.STATIONARY_POSES");
        Set set2 = SetsKt.minus((Set)enumSet, (Object)((Object)PoseType.HOVER));
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        object2 = new ModelQuirk[]{blink7, quirkidle2};
        modelQuirkArray = object2;
        objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "ground_idle", null, 4, null)};
        objectArray = objectArray2;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.1.INSTANCE, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 168, null));
        object = SetsKt.setOf((Object)((Object)PoseType.STAND));
        object2 = new ModelQuirk[]{blink7, quirkidle2};
        modelQuirkArray = object2;
        objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "battle_idle", null, 4, null)};
        objectArray = objectArray2;
        this.setBattleidle(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "battle_standing", (Set)object, (Function1)registerPoses.2.INSTANCE, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 168, null));
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getMOVING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"PoseType.MOVING_POSES");
        object = SetsKt.minus((Set)enumSet3, (Object)((Object)PoseType.FLY));
        object2 = new ModelQuirk[]{blink7};
        modelQuirkArray = object2;
        objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "ground_walk", null, 4, null)};
        objectArray = objectArray2;
        this.setWalk(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "walk", (Set)object, (Function1)registerPoses.3.INSTANCE, 0, (Map)object2, null, (StatelessAnimation[])objectArray, null, modelQuirkArray, 168, null));
        object = PoseType.FLOAT;
        object2 = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        modelQuirkArray = MapsKt.mutableMapOf((Pair[])object2);
        objectArray = new ModelQuirk[]{blink7};
        object2 = objectArray;
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "water_idle", null, 4, null)};
        objectArray = objectArray2;
        this.setFloating(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "floating", object, (Function1)registerPoses.4.INSTANCE, 10, (Map)modelQuirkArray, null, (StatelessAnimation[])objectArray, null, object2, 160, null));
        modelQuirkArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object = MapsKt.mutableMapOf((Pair[])modelQuirkArray);
        modelQuirkArray = PoseType.SWIM;
        objectArray = new ModelQuirk[]{blink7};
        object2 = objectArray;
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "water_swim", null, 4, null)};
        objectArray = objectArray2;
        this.setSwimming(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "swimming", (PoseType)modelQuirkArray, (Function1)registerPoses.5.INSTANCE, 10, (Map)object, null, (StatelessAnimation[])objectArray, null, object2, 160, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        object2 = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        modelQuirkArray = MapsKt.mutableMapOf((Pair[])object2);
        objectArray = new ModelQuirk[]{blink7};
        object2 = objectArray;
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "surfacewater_idle", null, 4, null)};
        objectArray = objectArray2;
        ModelPartTransformation[] modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray2 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setWater_surface_idle(PoseableEntityModel.registerPose$default(poseableEntityModel, "surface_idle", (Set)object, (Function1)registerPoses.6.INSTANCE, 0, (Map)modelQuirkArray, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray2, object2, 40, null));
        object = PoseType.Companion.getMOVING_POSES();
        object2 = new ModelQuirk[]{blink7};
        modelQuirkArray = object2;
        objectArray = new Pair[]{TuplesKt.to((Object)"faint", (Object)faint)};
        object2 = MapsKt.mutableMapOf((Pair[])objectArray);
        objectArray2 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "wartortle", "surfacewater_swim", null, 4, null)};
        objectArray = objectArray2;
        modelPartTransformationArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, this.wateroffset)};
        objectArray2 = modelPartTransformationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWater_surface_swim(PoseableEntityModel.registerPose$default(poseableEntityModel2, "surface_swim", (Set)object, (Function1)registerPoses.7.INSTANCE, 0, (Map)object2, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray2, modelQuirkArray, 40, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

