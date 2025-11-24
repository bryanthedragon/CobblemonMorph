/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.BiWingedFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003B\u0088\u0001\b\u0016\u0012\u0006\u00103\u001a\u000202\u0012\u0016\u00104\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0 j\u0002`!\u0012U\u0010.\u001aQ\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\r\u0012\u0006\u0012\u0004\u0018\u00010\n0+\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b5\u00106B\u009c\u0001\u0012\u0006\u00103\u001a\u00020\u0004\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0019\u0012\b\u0010\u001e\u001a\u0004\u0018\u00010\u0019\u0012\u0016\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0 j\u0002`!\u0012W\b\u0002\u0010.\u001aQ\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\r\u0012\u0006\u0012\u0004\u0018\u00010\n0+\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b5\u00107Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0019\u0010\u001a\u001a\u0004\u0018\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0019\u0010\u001e\u001a\u0004\u0018\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001b\u001a\u0004\b\u001f\u0010\u001dR'\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0 j\u0002`!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R \u0010'\u001a\b\u0012\u0004\u0012\u00020\u00040&8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*Rf\u0010.\u001aQ\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(\r\u0012\u0006\u0012\u0004\u0018\u00010\n0+8\u0006\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\u00a8\u00068"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WingFlapIdleAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "", "axis", "I", "getAxis", "()I", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "leftWing", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getLeftWing", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "rightWing", "getRightWing", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "rotation", "Lkotlin/jvm/functions/Function1;", "getRotation", "()Lkotlin/jvm/functions/Function1;", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "timeVariable", "Lkotlin/jvm/functions/Function3;", "getTimeVariable", "()Lkotlin/jvm/functions/Function3;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;", "frame", "flapFunction", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;I)V", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;I)V", "common"})
public final class WingFlapIdleAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @Nullable
    private final Bone leftWing;
    @Nullable
    private final Bone rightWing;
    @NotNull
    private final Function1<Float, Float> rotation;
    @NotNull
    private final Function3<PoseableEntityState<T>, Float, Float, Float> timeVariable;
    private final int axis;
    @NotNull
    private final Class<ModelFrame> targetFrame;

    public WingFlapIdleAnimation(@NotNull ModelFrame frame, @Nullable Bone leftWing, @Nullable Bone rightWing, @NotNull Function1<? super Float, Float> rotation, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        Intrinsics.checkNotNullParameter(rotation, (String)"rotation");
        Intrinsics.checkNotNullParameter(timeVariable, (String)"timeVariable");
        super(frame);
        this.leftWing = leftWing;
        this.rightWing = rightWing;
        this.rotation = rotation;
        this.timeVariable = timeVariable;
        this.axis = axis;
        this.targetFrame = ModelFrame.class;
    }

    public /* synthetic */ WingFlapIdleAnimation(ModelFrame modelFrame, Bone bone, Bone bone2, Function1 function1, Function3 function3, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 0x10) != 0) {
            function3 = 1.INSTANCE;
        }
        this(modelFrame, bone, bone2, (Function1<? super Float, Float>)function1, function3, n);
    }

    @Nullable
    public final Bone getLeftWing() {
        return this.leftWing;
    }

    @Nullable
    public final Bone getRightWing() {
        return this.rightWing;
    }

    @NotNull
    public final Function1<Float, Float> getRotation() {
        return this.rotation;
    }

    @NotNull
    public final Function3<PoseableEntityState<T>, Float, Float, Float> getTimeVariable() {
        return this.timeVariable;
    }

    public final int getAxis() {
        return this.axis;
    }

    public WingFlapIdleAnimation(@NotNull BiWingedFrame frame, @NotNull Function1<? super Float, Float> flapFunction, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        Intrinsics.checkNotNullParameter(flapFunction, (String)"flapFunction");
        Intrinsics.checkNotNullParameter(timeVariable, (String)"timeVariable");
        this(frame, (Bone)frame.getLeftWing(), (Bone)frame.getRightWing(), flapFunction, timeVariable, axis);
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        block1: {
            Intrinsics.checkNotNullParameter(model, (String)"model");
            Float f = (Float)this.timeVariable.invoke(state, (Object)Float.valueOf(limbSwing), (Object)Float.valueOf(ageInTicks));
            float time = f != null ? f.floatValue() : 0.0f;
            float angle = ((Number)this.rotation.invoke((Object)Float.valueOf(time))).floatValue();
            Bone bone = this.leftWing;
            if (bone != null) {
                ModelPartExtensionsKt.addRotation(bone, this.axis, angle);
            }
            Bone bone2 = this.rightWing;
            if (bone2 == null) break block1;
            ModelPartExtensionsKt.addRotation(bone2, this.axis, -angle);
        }
    }
}

