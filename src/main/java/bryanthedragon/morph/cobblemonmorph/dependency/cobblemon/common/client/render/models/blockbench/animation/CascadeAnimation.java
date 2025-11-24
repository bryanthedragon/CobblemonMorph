/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003BM\u0012\u0006\u0010)\u001a\u00020\u0004\u0012\u0016\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0014j\u0002`\u001b\u0012\u0016\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\n0\u0014j\u0002`\u0016\u0012\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e\u00a2\u0006\u0004\b*\u0010+Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R'\u0010\u0017\u001a\u0012\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\n0\u0014j\u0002`\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR'\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0014j\u0002`\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0018\u001a\u0004\b\u001d\u0010\u001aR\u001d\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040$8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/CascadeAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "Lkotlin/Function1;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/AmplitudeFunction;", "amplitudeFunction", "Lkotlin/jvm/functions/Function1;", "getAmplitudeFunction", "()Lkotlin/jvm/functions/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/RootFunction;", "rootFunction", "getRootFunction", "", "Lnet/minecraft/client/model/geom/ModelPart;", "segments", "[Lnet/minecraft/client/model/geom/ModelPart;", "getSegments", "()[Lnet/minecraft/client/model/geom/ModelPart;", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "frame", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;[Lnet/minecraft/client/model/geom/ModelPart;)V", "common"})
@SourceDebugExtension(value={"SMAP\nCascadeAnimation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CascadeAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/CascadeAnimation\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,55:1\n13644#2,3:56\n*S KotlinDebug\n*F\n+ 1 CascadeAnimation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/animation/CascadeAnimation\n*L\n35#1:56,3\n*E\n"})
public final class CascadeAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @NotNull
    private final Function1<Float, Float> rootFunction;
    @NotNull
    private final Function1<Integer, Float> amplitudeFunction;
    @NotNull
    private final ModelPart[] segments;
    @NotNull
    private final Class<ModelFrame> targetFrame;

    public CascadeAnimation(@NotNull ModelFrame frame, @NotNull Function1<? super Float, Float> rootFunction, @NotNull Function1<? super Integer, Float> amplitudeFunction, @NotNull ModelPart[] segments) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        Intrinsics.checkNotNullParameter(rootFunction, (String)"rootFunction");
        Intrinsics.checkNotNullParameter(amplitudeFunction, (String)"amplitudeFunction");
        Intrinsics.checkNotNullParameter((Object)segments, (String)"segments");
        super(frame);
        this.rootFunction = rootFunction;
        this.amplitudeFunction = amplitudeFunction;
        this.segments = segments;
        this.targetFrame = ModelFrame.class;
    }

    @NotNull
    public final Function1<Float, Float> getRootFunction() {
        return this.rootFunction;
    }

    @NotNull
    public final Function1<Integer, Float> getAmplitudeFunction() {
        return this.amplitudeFunction;
    }

    @NotNull
    public final ModelPart[] getSegments() {
        return this.segments;
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        ModelPart[] $this$forEachIndexed$iv = this.segments;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (ModelPart item$iv : $this$forEachIndexed$iv) {
            int n = index$iv++;
            ModelPart modelPart = item$iv;
            int index = n;
            boolean bl = false;
            modelPart.f_104204_ += ((Number)this.rootFunction.invoke((Object)Float.valueOf(ageInTicks))).floatValue() * ((Number)this.amplitudeFunction.invoke((Object)(index + 1))).floatValue() * intensity;
        }
    }
}

