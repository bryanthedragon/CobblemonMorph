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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen4.HippowdonModel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b/\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010S\u001a\u00020\u001b\u00a2\u0006\u0004\bT\u0010UJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R2\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR2\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR2\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000fR\u001a\u0010\u0017\u001a\u00020\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\"\u0010!\u001a\u00020 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\"\u0010(\u001a\u00020'8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\"\u0010.\u001a\u00020 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010\"\u001a\u0004\b/\u0010$\"\u0004\b0\u0010&R\"\u00101\u001a\u00020'8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b1\u0010)\u001a\u0004\b2\u0010+\"\u0004\b3\u0010-R\u0014\u00104\u001a\u00020\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b4\u0010\u001dR\u001a\u00105\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b5\u0010\u001d\u001a\u0004\b6\u0010\u001fR\u0014\u00107\u001a\u00020\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010\u001dR2\u00108\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u0010\u000b\u001a\u0004\b9\u0010\r\"\u0004\b:\u0010\u000fR2\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b;\u0010\u000b\u001a\u0004\b<\u0010\r\"\u0004\b=\u0010\u000fR2\u0010>\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010\u000b\u001a\u0004\b?\u0010\r\"\u0004\b@\u0010\u000fR2\u0010A\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u0010\u000b\u001a\u0004\bB\u0010\r\"\u0004\bC\u0010\u000fR2\u0010D\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bD\u0010\u000b\u001a\u0004\bE\u0010\r\"\u0004\bF\u0010\u000fR2\u0010G\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bG\u0010\u000b\u001a\u0004\bH\u0010\r\"\u0004\bI\u0010\u000fR2\u0010J\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bJ\u0010\u000b\u001a\u0004\bK\u0010\r\"\u0004\bL\u0010\u000fR2\u0010M\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bM\u0010\u000b\u001a\u0004\bN\u0010\r\"\u0004\bO\u0010\u000fR2\u0010P\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0002`\t8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bP\u0010\u000b\u001a\u0004\bQ\u0010\r\"\u0004\bR\u0010\u000f\u00a8\u0006V"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen4/HippowdonModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPose;", "battleidle", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "getBattleidle", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;", "setBattleidle", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Pose;)V", "battleidleredsand", "getBattleidleredsand", "setBattleidleredsand", "battleidlesand", "getBattleidlesand", "setBattleidlesand", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "getCryAnimation", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/CryProvider;", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "redsand", "rootPart", "getRootPart", "sand", "sleep", "getSleep", "setSleep", "sleepredsand", "getSleepredsand", "setSleepredsand", "sleepsand", "getSleepsand", "setSleepsand", "standing", "getStanding", "setStanding", "standingredsand", "getStandingredsand", "setStandingredsand", "standingsand", "getStandingsand", "setStandingsand", "walk", "getWalk", "setWalk", "walkredsand", "getWalkredsand", "setWalkredsand", "walksand", "getWalksand", "setWalksand", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class HippowdonModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final ModelPart sand;
    @NotNull
    private final ModelPart redsand;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;
    public Pose<PokemonEntity, ModelFrame> standing;
    public Pose<PokemonEntity, ModelFrame> walk;
    public Pose<PokemonEntity, ModelFrame> battleidle;
    public Pose<PokemonEntity, ModelFrame> standingsand;
    public Pose<PokemonEntity, ModelFrame> walksand;
    public Pose<PokemonEntity, ModelFrame> battleidlesand;
    public Pose<PokemonEntity, ModelFrame> battleidleredsand;
    public Pose<PokemonEntity, ModelFrame> sleep;
    public Pose<PokemonEntity, ModelFrame> sleepsand;
    public Pose<PokemonEntity, ModelFrame> sleepredsand;
    public Pose<PokemonEntity, ModelFrame> standingredsand;
    public Pose<PokemonEntity, ModelFrame> walkredsand;
    @NotNull
    private final CryProvider cryAnimation;

    public HippowdonModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "hippowdon");
        this.head = this.getPart("head");
        this.sand = this.getPart("sand");
        this.redsand = this.getPart("redsand");
        this.portraitScale = 0.6f;
        this.portraitTranslation = new Vec3(-0.63, 0.73, 0.0);
        this.profileScale = 0.4f;
        this.profileTranslation = new Vec3(-0.1, 1.0, 0.0);
        this.cryAnimation = (arg_0, arg_1) -> HippowdonModel.cryAnimation$lambda$0(this, arg_0, arg_1);
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
    public final Pose<PokemonEntity, ModelFrame> getStandingsand() {
        Pose<PokemonEntity, ModelFrame> pose = this.standingsand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"standingsand");
        return null;
    }

    public final void setStandingsand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.standingsand = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWalksand() {
        Pose<PokemonEntity, ModelFrame> pose = this.walksand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"walksand");
        return null;
    }

    public final void setWalksand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.walksand = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getBattleidlesand() {
        Pose<PokemonEntity, ModelFrame> pose = this.battleidlesand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battleidlesand");
        return null;
    }

    public final void setBattleidlesand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.battleidlesand = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getBattleidleredsand() {
        Pose<PokemonEntity, ModelFrame> pose = this.battleidleredsand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battleidleredsand");
        return null;
    }

    public final void setBattleidleredsand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.battleidleredsand = pose;
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
    public final Pose<PokemonEntity, ModelFrame> getSleepsand() {
        Pose<PokemonEntity, ModelFrame> pose = this.sleepsand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"sleepsand");
        return null;
    }

    public final void setSleepsand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.sleepsand = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getSleepredsand() {
        Pose<PokemonEntity, ModelFrame> pose = this.sleepredsand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"sleepredsand");
        return null;
    }

    public final void setSleepredsand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.sleepredsand = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getStandingredsand() {
        Pose<PokemonEntity, ModelFrame> pose = this.standingredsand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"standingredsand");
        return null;
    }

    public final void setStandingredsand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.standingredsand = pose;
    }

    @NotNull
    public final Pose<PokemonEntity, ModelFrame> getWalkredsand() {
        Pose<PokemonEntity, ModelFrame> pose = this.walkredsand;
        if (pose != null) {
            return pose;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"walkredsand");
        return null;
    }

    public final void setWalkredsand(@NotNull Pose<PokemonEntity, ModelFrame> pose) {
        Intrinsics.checkNotNullParameter(pose, (String)"<set-?>");
        this.walkredsand = pose;
    }

    @Override
    @NotNull
    public CryProvider getCryAnimation() {
        return this.cryAnimation;
    }

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ HippowdonModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "hippowdon", "blink", null, 4, null);
            }
        }, 7, null);
        SimpleQuirk idlequirk2 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ HippowdonModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "hippowdon", "quirk_idle", null, 4, null);
            }
        }, 7, null);
        Object object = PoseType.SLEEP;
        Object[] objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "sleep", null, 4, null)};
        objectArray = objectArray3;
        this.setSleep(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleep", object, (Function1)registerPoses.1.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray2, null, 312, null));
        object = PoseType.SLEEP;
        objectArray = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_sleep", null, 4, null)};
        objectArray = objectArray3;
        this.setSleepredsand(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "sleepsand", object, (Function1)registerPoses.2.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray2, null, 312, null));
        EnumSet<PoseType> enumSet = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"PoseType.UI_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getSTATIONARY_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"PoseType.STATIONARY_POSES");
        object = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        objectArray = new ModelQuirk[]{blink7, idlequirk2};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        StatelessAnimation[] statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "ground_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        this.setStanding(PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "standing", (Set)object, (Function1)registerPoses.3.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "ground_walk", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalk(PoseableEntityModel.registerPose$default(poseableEntityModel, "walk", (Set)object, (Function1)registerPoses.4.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, idlequirk2};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel2 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setStandingsand(PoseableEntityModel.registerPose$default(poseableEntityModel2, "standingsand", (Set)object, (Function1)registerPoses.5.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7, idlequirk2};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel3 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setStandingredsand(PoseableEntityModel.registerPose$default(poseableEntityModel3, "standingredsand", (Set)object, (Function1)registerPoses.6.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_swim", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel4 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalksand(PoseableEntityModel.registerPose$default(poseableEntityModel4, "walksand", (Set)object, (Function1)registerPoses.7.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getMOVING_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_swim", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel5 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"MOVING_POSES");
        this.setWalkredsand(PoseableEntityModel.registerPose$default(poseableEntityModel5, "walkredsand", (Set)object, (Function1)registerPoses.8.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "battle_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel6 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidle(PoseableEntityModel.registerPose$default(poseableEntityModel6, "battleidle", (Set)object, (Function1)registerPoses.9.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(true), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(false)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_battle_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel7 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidlesand(PoseableEntityModel.registerPose$default(poseableEntityModel7, "battleidlesand", (Set)object, (Function1)registerPoses.10.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
        object = PoseType.Companion.getSTATIONARY_POSES();
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new ModelPartTransformation[]{ModelPartExtensionsKt.createTransformation(this.sand).withVisibility(false), ModelPartExtensionsKt.createTransformation(this.redsand).withVisibility(true)};
        objectArray = objectArray3;
        statelessAnimationArray = new StatelessAnimation[]{HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null), PoseableEntityModel.bedrock$default(this, "hippowdon", "sand_battle_idle", null, 4, null)};
        objectArray3 = statelessAnimationArray;
        PoseableEntityModel poseableEntityModel8 = this;
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"STATIONARY_POSES");
        this.setBattleidleredsand(PoseableEntityModel.registerPose$default(poseableEntityModel8, "battleidleredsand", (Set)object, (Function1)registerPoses.11.INSTANCE, 0, null, null, (StatelessAnimation[])objectArray3, (ModelPartTransformation[])objectArray, (ModelQuirk[])objectArray2, 56, null));
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }

    private static final StatefulAnimation cryAnimation$lambda$0(HippowdonModel this$0, PokemonEntity pokemonEntity, PoseableEntityState pose) {
        StatefulAnimation statefulAnimation;
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pose, (String)"pose");
        Pose[] poseArray = new Pose[]{this$0.getStandingsand(), this$0.getWalksand()};
        if (pose.isPosedIn(poseArray)) {
            statefulAnimation = PoseableEntityModel.bedrockStateful$default(this$0, "hippowdon", "sand_cry", null, 4, null);
        } else {
            poseArray = new Pose[]{this$0.getBattleidle()};
            if (pose.isPosedIn(poseArray)) {
                statefulAnimation = PoseableEntityModel.bedrockStateful$default(this$0, "hippowdon", "battle_cry", null, 4, null);
            } else {
                poseArray = new Pose[]{this$0.getBattleidlesand(), this$0.getBattleidleredsand()};
                statefulAnimation = pose.isPosedIn(poseArray) ? (StatefulAnimation)PoseableEntityModel.bedrockStateful$default(this$0, "hippowdon", "sand_battle_cry", null, 4, null) : (StatefulAnimation)PoseableEntityModel.bedrockStateful$default(this$0, "hippowdon", "cry", null, 4, null);
            }
        }
        return statefulAnimation;
    }
}

