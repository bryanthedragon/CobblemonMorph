/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WaveSegment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003Bq\u0012\u0006\u0010>\u001a\u00020\u0004\u0012\u0016\u0010:\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n08j\u0002`9\u0012\u0006\u0010)\u001a\u00020\n\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\u001e\u001a\u00020\n\u0012\b\b\u0002\u0010'\u001a\u00020\u0014\u0012\u0006\u0010+\u001a\u00020\"\u0012\u0006\u0010#\u001a\u00020\"\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u0012\f\u0010/\u001a\b\u0012\u0004\u0012\u00020.0-\u00a2\u0006\u0004\b?\u0010@Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u001a\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u0017\u0010#\u001a\u00020\"8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u0017\u0010'\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b'\u0010\u0016\u001a\u0004\b(\u0010\u0018R\u0017\u0010)\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b)\u0010\u001f\u001a\u0004\b*\u0010!R\u0017\u0010+\u001a\u00020\"8\u0006\u00a2\u0006\f\n\u0004\b+\u0010$\u001a\u0004\b,\u0010&R\u001d\u0010/\u001a\b\u0012\u0004\u0012\u00020.0-8\u0006\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R \u00104\u001a\b\u0012\u0004\u0012\u00020\u0004038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107R'\u0010:\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n08j\u0002`98\u0006\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\u00a8\u0006A"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "", "basedOnLimbSwing", "Z", "getBasedOnLimbSwing", "()Z", "Lnet/minecraft/client/model/geom/ModelPart;", "head", "Lnet/minecraft/client/model/geom/ModelPart;", "getHead", "()Lnet/minecraft/client/model/geom/ModelPart;", "headLength", "F", "getHeadLength", "()F", "", "motionAxis", "I", "getMotionAxis", "()I", "moveHead", "getMoveHead", "oscillationsScalar", "getOscillationsScalar", "rotationAxis", "getRotationAxis", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "segments", "[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "getSegments", "()[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "waveFunction", "Lkotlin/jvm/functions/Function1;", "getWaveFunction", "()Lkotlin/jvm/functions/Function1;", "frame", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;Lkotlin/jvm/functions/Function1;FLnet/minecraft/client/model/geom/ModelPart;FZIIZ[Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveSegment;)V", "common"})
@SourceDebugExtension(value={"SMAP\nWaveAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WaveAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveAnimation\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,103:1\n1#2:104\n11335#3:105\n11670#3,3:106\n*S KotlinDebug\n*F\n+ 1 WaveAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/WaveAnimation\n*L\n64#1:105\n64#1:106,3\n*E\n"})
public final class WaveAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @NotNull
    private final Function1<Float, Float> waveFunction;
    private final float oscillationsScalar;
    @NotNull
    private final ModelPart head;
    private final float headLength;
    private final boolean moveHead;
    private final int rotationAxis;
    private final int motionAxis;
    private final boolean basedOnLimbSwing;
    @NotNull
    private final WaveSegment[] segments;
    @NotNull
    private final Class<ModelFrame> targetFrame;

    public WaveAnimation(@NotNull ModelFrame frame, @NotNull Function1<? super Float, Float> waveFunction, float oscillationsScalar, @NotNull ModelPart head5, float headLength, boolean moveHead, int rotationAxis, int motionAxis, boolean basedOnLimbSwing, @NotNull WaveSegment[] segments) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        Intrinsics.checkNotNullParameter(waveFunction, (String)"waveFunction");
        Intrinsics.checkNotNullParameter((Object)head5, (String)"head");
        Intrinsics.checkNotNullParameter((Object)segments, (String)"segments");
        super(frame);
        this.waveFunction = waveFunction;
        this.oscillationsScalar = oscillationsScalar;
        this.head = head5;
        this.headLength = headLength;
        this.moveHead = moveHead;
        this.rotationAxis = rotationAxis;
        this.motionAxis = motionAxis;
        this.basedOnLimbSwing = basedOnLimbSwing;
        this.segments = segments;
        this.targetFrame = ModelFrame.class;
    }

    public /* synthetic */ WaveAnimation(ModelFrame modelFrame, Function1 function1, float f, ModelPart modelPart, float f2, boolean bl, int n, int n2, boolean bl2, WaveSegment[] waveSegmentArray, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 0x20) != 0) {
            bl = false;
        }
        if ((n3 & 0x100) != 0) {
            bl2 = false;
        }
        this(modelFrame, (Function1<? super Float, Float>)function1, f, modelPart, f2, bl, n, n2, bl2, waveSegmentArray);
    }

    @NotNull
    public final Function1<Float, Float> getWaveFunction() {
        return this.waveFunction;
    }

    public final float getOscillationsScalar() {
        return this.oscillationsScalar;
    }

    @NotNull
    public final ModelPart getHead() {
        return this.head;
    }

    public final float getHeadLength() {
        return this.headLength;
    }

    public final boolean getMoveHead() {
        return this.moveHead;
    }

    public final int getRotationAxis() {
        return this.rotationAxis;
    }

    public final int getMotionAxis() {
        return this.motionAxis;
    }

    public final boolean getBasedOnLimbSwing() {
        return this.basedOnLimbSwing;
    }

    @NotNull
    public final WaveSegment[] getSegments() {
        return this.segments;
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Collection<Float> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        float f;
        Intrinsics.checkNotNullParameter(model, (String)"model");
        if (this.basedOnLimbSwing) {
            f = limbSwing;
        } else {
            T t = entity2;
            if (t != null) {
                T it = t;
                boolean bl = false;
                f = model.getState(it).getAnimationSeconds();
            } else {
                f = 0.0f;
            }
        }
        float t = f;
        WaveSegment[] waveSegmentArray = this.segments;
        float f2 = this.headLength;
        boolean $i$f$map = false;
        void bl = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        int n = ((void)$this$mapTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var20_29 = item$iv$iv = $this$mapTo$iv$iv[i];
            collection = destination$iv$iv;
            boolean bl2 = false;
            collection.add(Float.valueOf(it.getLength()));
        }
        collection = (List)destination$iv$iv;
        float totalTimeDisplacement = (f2 + CollectionsKt.sumOfFloat((Iterable)collection)) / this.oscillationsScalar;
        if (this.moveHead) {
            float headDisplacement = ((Number)this.waveFunction.invoke((Object)Float.valueOf(t + totalTimeDisplacement - this.headLength / this.oscillationsScalar))).floatValue() * (float)16;
            ModelPartExtensionsKt.addPosition(this.head, this.motionAxis, -headDisplacement * intensity);
        }
        totalTimeDisplacement -= this.headLength / this.oscillationsScalar;
        float previousSegmentLength = this.headLength;
        float previousTheta = 0.0f;
        for (WaveSegment segment : this.segments) {
            float t2 = totalTimeDisplacement + previousSegmentLength / (float)2 / this.oscillationsScalar;
            float t1 = totalTimeDisplacement - segment.getLength() / (float)2 / this.oscillationsScalar;
            float yAfter = ((Number)this.waveFunction.invoke((Object)Float.valueOf(t + t1))).floatValue();
            float yBefore = ((Number)this.waveFunction.invoke((Object)Float.valueOf(t + t2))).floatValue();
            float ratio = (yAfter - yBefore) / (t2 - t1);
            float theta = (float)Math.atan(ratio);
            ModelPartExtensionsKt.addRotation((Bone)segment.getModelPart(), this.rotationAxis, (theta - previousTheta) * intensity);
            previousTheta = theta;
            previousSegmentLength = segment.getLength();
            totalTimeDisplacement -= segment.getLength() / this.oscillationsScalar;
            if (!(totalTimeDisplacement < 0.0f)) continue;
            totalTimeDisplacement = 0.0f;
        }
    }
}

