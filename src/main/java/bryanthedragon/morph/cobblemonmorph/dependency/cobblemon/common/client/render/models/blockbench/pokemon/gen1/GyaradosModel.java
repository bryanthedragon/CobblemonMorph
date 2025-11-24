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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.gen1.GyaradosModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0018\u0002\n\u0002\b\u001e\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010T\u001a\u00020\u0006\u00a2\u0006\u0004\bU\u0010VJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\"\u0010\f\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\"\u0010\u0013\u001a\u00020\u00128\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u00020\u000b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\r\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\"\u0010\u001c\u001a\u00020\u00128\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u0014\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u0018R\u001a\u0010\u001f\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001f\u0010\b\u001a\u0004\b \u0010\nR\u0017\u0010!\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b\"\u0010\nR\u0017\u0010#\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b#\u0010\b\u001a\u0004\b$\u0010\nR\u0017\u0010%\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b%\u0010\b\u001a\u0004\b&\u0010\nR\u0017\u0010'\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b'\u0010\b\u001a\u0004\b(\u0010\nR\u0017\u0010)\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b)\u0010\b\u001a\u0004\b*\u0010\nR\u0017\u0010+\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b+\u0010\b\u001a\u0004\b,\u0010\nR\u0017\u0010-\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b-\u0010\b\u001a\u0004\b.\u0010\nR\u0017\u0010/\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b/\u0010\b\u001a\u0004\b0\u0010\nR\u0017\u00101\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b1\u0010\b\u001a\u0004\b2\u0010\nR\u0017\u00103\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b3\u0010\b\u001a\u0004\b4\u0010\nR\u0017\u00105\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b5\u0010\b\u001a\u0004\b6\u0010\nR\u0017\u00107\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b7\u0010\b\u001a\u0004\b8\u0010\nR\u0017\u0010:\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=R\u0017\u0010>\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\b>\u0010;\u001a\u0004\b?\u0010=R\u0017\u0010@\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\b@\u0010;\u001a\u0004\bA\u0010=R\u0017\u0010B\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bB\u0010;\u001a\u0004\bC\u0010=R\u0017\u0010D\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bD\u0010;\u001a\u0004\bE\u0010=R\u0017\u0010F\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bF\u0010;\u001a\u0004\bG\u0010=R\u0017\u0010H\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bH\u0010;\u001a\u0004\bI\u0010=R\u0017\u0010J\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bJ\u0010;\u001a\u0004\bK\u0010=R\u0017\u0010L\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bL\u0010;\u001a\u0004\bM\u0010=R\u0017\u0010N\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bN\u0010;\u001a\u0004\bO\u0010=R\u0017\u0010P\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bP\u0010;\u001a\u0004\bQ\u0010=R\u0017\u0010R\u001a\u0002098\u0006\u00a2\u0006\f\n\u0004\bR\u0010;\u001a\u0004\bS\u0010=\u00a8\u0006W"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/gen1/GyaradosModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonPoseableModel;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/HeadedFrame;", "", "registerPoses", "()V", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "", "portraitScale", "F", "getPortraitScale", "()F", "setPortraitScale", "(F)V", "Lnet/minecraft/world/phys/Vec3;", "portraitTranslation", "Lnet/minecraft/world/phys/Vec3;", "getPortraitTranslation", "()Lnet/minecraft/world/phys/Vec3;", "setPortraitTranslation", "(Lnet/minecraft/world/phys/Vec3;)V", "profileScale", "getProfileScale", "setProfileScale", "profileTranslation", "getProfileTranslation", "setProfileTranslation", "rootPart", "getRootPart", "seg1", "getSeg1", "seg10", "getSeg10", "seg11", "getSeg11", "seg12", "getSeg12", "seg2", "getSeg2", "seg3", "getSeg3", "seg4", "getSeg4", "seg5", "getSeg5", "seg6", "getSeg6", "seg7", "getSeg7", "seg8", "getSeg8", "seg9", "getSeg9", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "wseg1", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "getWseg1", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "wseg10", "getWseg10", "wseg11", "getWseg11", "wseg12", "getWseg12", "wseg2", "getWseg2", "wseg3", "getWseg3", "wseg4", "getWseg4", "wseg5", "getWseg5", "wseg6", "getWseg6", "wseg7", "getWseg7", "wseg8", "getWseg8", "wseg9", "getWseg9", "root", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
public final class GyaradosModel
extends PokemonPoseableModel
implements HeadedFrame {
    @NotNull
    private final ModelPart rootPart;
    @NotNull
    private final ModelPart seg1;
    @NotNull
    private final ModelPart seg2;
    @NotNull
    private final ModelPart seg3;
    @NotNull
    private final ModelPart seg4;
    @NotNull
    private final ModelPart seg5;
    @NotNull
    private final ModelPart seg6;
    @NotNull
    private final ModelPart seg7;
    @NotNull
    private final ModelPart seg8;
    @NotNull
    private final ModelPart seg9;
    @NotNull
    private final ModelPart seg10;
    @NotNull
    private final ModelPart seg11;
    @NotNull
    private final ModelPart seg12;
    @NotNull
    private final ModelPart head;
    @NotNull
    private final WaveSegment wseg1;
    @NotNull
    private final WaveSegment wseg2;
    @NotNull
    private final WaveSegment wseg3;
    @NotNull
    private final WaveSegment wseg4;
    @NotNull
    private final WaveSegment wseg5;
    @NotNull
    private final WaveSegment wseg6;
    @NotNull
    private final WaveSegment wseg7;
    @NotNull
    private final WaveSegment wseg8;
    @NotNull
    private final WaveSegment wseg9;
    @NotNull
    private final WaveSegment wseg10;
    @NotNull
    private final WaveSegment wseg11;
    @NotNull
    private final WaveSegment wseg12;
    private float portraitScale;
    @NotNull
    private Vec3 portraitTranslation;
    private float profileScale;
    @NotNull
    private Vec3 profileTranslation;

    public GyaradosModel(@NotNull ModelPart root) {
        Intrinsics.checkNotNullParameter((Object)root, (String)"root");
        this.rootPart = this.registerChildWithAllChildren(root, "gyarados");
        this.seg1 = this.getPart("segment1");
        this.seg2 = this.getPart("segment2");
        this.seg3 = this.getPart("segment3");
        this.seg4 = this.getPart("segment4");
        this.seg5 = this.getPart("segment5");
        this.seg6 = this.getPart("segment6");
        this.seg7 = this.getPart("segment7");
        this.seg8 = this.getPart("segment8");
        this.seg9 = this.getPart("segment9");
        this.seg10 = this.getPart("segment10");
        this.seg11 = this.getPart("segment11");
        this.seg12 = this.getPart("segment12");
        this.head = this.getPart("head");
        this.wseg1 = new WaveSegment(this.seg1, 7.0f);
        this.wseg2 = new WaveSegment(this.seg2, 5.0f);
        this.wseg3 = new WaveSegment(this.seg3, 6.0f);
        this.wseg4 = new WaveSegment(this.seg4, 6.0f);
        this.wseg5 = new WaveSegment(this.seg5, 6.0f);
        this.wseg6 = new WaveSegment(this.seg6, 6.0f);
        this.wseg7 = new WaveSegment(this.seg7, 6.0f);
        this.wseg8 = new WaveSegment(this.seg8, 6.0f);
        this.wseg9 = new WaveSegment(this.seg9, 6.0f);
        this.wseg10 = new WaveSegment(this.seg10, 5.0f);
        this.wseg11 = new WaveSegment(this.seg11, 5.0f);
        this.wseg12 = new WaveSegment(this.seg12, 4.0f);
        this.portraitScale = 1.8f;
        this.portraitTranslation = new Vec3(-1.55, 0.35, 0.0);
        this.profileScale = 0.7f;
        this.profileTranslation = new Vec3(-0.1, 0.65, 0.0);
    }

    @NotNull
    public ModelPart getRootPart() {
        return this.rootPart;
    }

    @NotNull
    public final ModelPart getSeg1() {
        return this.seg1;
    }

    @NotNull
    public final ModelPart getSeg2() {
        return this.seg2;
    }

    @NotNull
    public final ModelPart getSeg3() {
        return this.seg3;
    }

    @NotNull
    public final ModelPart getSeg4() {
        return this.seg4;
    }

    @NotNull
    public final ModelPart getSeg5() {
        return this.seg5;
    }

    @NotNull
    public final ModelPart getSeg6() {
        return this.seg6;
    }

    @NotNull
    public final ModelPart getSeg7() {
        return this.seg7;
    }

    @NotNull
    public final ModelPart getSeg8() {
        return this.seg8;
    }

    @NotNull
    public final ModelPart getSeg9() {
        return this.seg9;
    }

    @NotNull
    public final ModelPart getSeg10() {
        return this.seg10;
    }

    @NotNull
    public final ModelPart getSeg11() {
        return this.seg11;
    }

    @NotNull
    public final ModelPart getSeg12() {
        return this.seg12;
    }

    @NotNull
    public ModelPart getHead() {
        return this.head;
    }

    @NotNull
    public final WaveSegment getWseg1() {
        return this.wseg1;
    }

    @NotNull
    public final WaveSegment getWseg2() {
        return this.wseg2;
    }

    @NotNull
    public final WaveSegment getWseg3() {
        return this.wseg3;
    }

    @NotNull
    public final WaveSegment getWseg4() {
        return this.wseg4;
    }

    @NotNull
    public final WaveSegment getWseg5() {
        return this.wseg5;
    }

    @NotNull
    public final WaveSegment getWseg6() {
        return this.wseg6;
    }

    @NotNull
    public final WaveSegment getWseg7() {
        return this.wseg7;
    }

    @NotNull
    public final WaveSegment getWseg8() {
        return this.wseg8;
    }

    @NotNull
    public final WaveSegment getWseg9() {
        return this.wseg9;
    }

    @NotNull
    public final WaveSegment getWseg10() {
        return this.wseg10;
    }

    @NotNull
    public final WaveSegment getWseg11() {
        return this.wseg11;
    }

    @NotNull
    public final WaveSegment getWseg12() {
        return this.wseg12;
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

    @Override
    public void registerPoses() {
        SimpleQuirk blink7 = PoseableEntityModel.quirk$default(this, null, null, null, new Function1<PoseableEntityState<PokemonEntity>, StatefulAnimation<PokemonEntity, ?>>(this){
            final /* synthetic */ GyaradosModel this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final StatefulAnimation<PokemonEntity, ?> invoke(@NotNull PoseableEntityState<PokemonEntity> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return PoseableEntityModel.bedrockStateful$default(this.this$0, "gyarados", "blink", null, 4, null);
            }
        }, 7, null);
        EnumSet<PoseType> enumSet = PoseType.Companion.getSTANDING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet, (String)"STANDING_POSES");
        Set set2 = enumSet;
        EnumSet<PoseType> enumSet2 = PoseType.Companion.getUI_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet2, (String)"UI_POSES");
        Set set3 = SetsKt.plus((Set)set2, (Iterable)enumSet2);
        Object[] objectArray = new ModelQuirk[]{blink7};
        Object[] objectArray2 = objectArray;
        Object[] objectArray3 = new StatelessAnimation[3];
        objectArray3[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        objectArray3[1] = PoseableEntityModel.bedrock$default(this, "gyarados", "ground_idle", null, 4, null);
        Function1 function1 = WaveFunctionKt.sineFunction$default(0.4f, 8.0f, 0.0f, 0.0f, 12, null);
        ModelPart modelPart = this.seg5;
        WaveSegment[] waveSegmentArray = new WaveSegment[]{this.wseg6, this.wseg7, this.wseg8, this.wseg9, this.wseg10, this.wseg11, this.wseg12};
        WaveSegment[] waveSegmentArray2 = waveSegmentArray;
        objectArray3[2] = new WaveAnimation(this, function1, 8.0f, modelPart, 0.1f, false, 1, 0, true, waveSegmentArray2, 32, null);
        objectArray = objectArray3;
        PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "land", set3, (Function1)registerPoses.1.INSTANCE, 20, null, null, (StatelessAnimation[])objectArray, null, objectArray2, 176, null);
        objectArray2 = new PoseType[]{PoseType.STAND, PoseType.WALK};
        set3 = SetsKt.setOf((Object[])objectArray2);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[3];
        objectArray3[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        objectArray3[1] = PoseableEntityModel.bedrock$default(this, "gyarados", "surface_idle", null, 4, null);
        function1 = WaveFunctionKt.sineFunction$default(0.2f, 3.0f, 0.0f, 0.0f, 12, null);
        modelPart = this.seg6;
        waveSegmentArray = new WaveSegment[]{this.wseg7, this.wseg8, this.wseg9, this.wseg10, this.wseg11, this.wseg12};
        waveSegmentArray2 = waveSegmentArray;
        objectArray3[2] = new WaveAnimation(this, (Function1<? super Float, Float>)function1, 24.0f, modelPart, 0.0f, false, 0, 1, false, waveSegmentArray2);
        objectArray = objectArray3;
        function1 = new Function1[]{ModelPartExtensionsKt.createTransformation(this.getRootPart()).addPosition(1, Float.valueOf(-6.0f))};
        objectArray3 = function1;
        PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "surface", set3, (Function1)registerPoses.2.INSTANCE, 20, null, null, (StatelessAnimation[])objectArray, (ModelPartTransformation[])objectArray3, objectArray2, 48, null);
        EnumSet<PoseType> enumSet3 = PoseType.Companion.getSWIMMING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet3, (String)"SWIMMING_POSES");
        Set set4 = enumSet3;
        EnumSet<PoseType> enumSet4 = PoseType.Companion.getFLYING_POSES();
        Intrinsics.checkNotNullExpressionValue(enumSet4, (String)"FLYING_POSES");
        set3 = SetsKt.plus((Set)set4, (Iterable)enumSet4);
        objectArray = new ModelQuirk[]{blink7};
        objectArray2 = objectArray;
        objectArray3 = new StatelessAnimation[2];
        objectArray3[0] = HeadedFrame.DefaultImpls.singleBoneLook$default(this, false, false, false, false, null, null, null, null, null, null, 1023, null);
        function1 = WaveFunctionKt.sineFunction$default(0.4f, 3.0f, 0.0f, 0.0f, 12, null);
        modelPart = this.getRootPart();
        waveSegmentArray = new WaveSegment[]{this.wseg1, this.wseg2, this.wseg3, this.wseg4, this.wseg5, this.wseg6, this.wseg7, this.wseg8, this.wseg9, this.wseg10, this.wseg11, this.wseg12};
        waveSegmentArray2 = waveSegmentArray;
        objectArray3[1] = new WaveAnimation(this, function1, 24.0f, modelPart, 4.0f, true, 0, 1, false, waveSegmentArray2, 256, null);
        objectArray = objectArray3;
        PoseableEntityModel.registerPose$default((PoseableEntityModel)this, "swim", set3, null, 20, null, null, (StatelessAnimation[])objectArray, null, objectArray2, 180, null);
    }

    @Override
    @NotNull
    public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(boolean invertX, boolean invertY, boolean disableX, boolean disableY, @Nullable Float pitchMultiplier, @Nullable Float yawMultiplier, @Nullable Float maxPitch, @Nullable Float minPitch, @Nullable Float maxYaw, @Nullable Float minYaw) {
        return HeadedFrame.DefaultImpls.singleBoneLook(this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw);
    }
}

