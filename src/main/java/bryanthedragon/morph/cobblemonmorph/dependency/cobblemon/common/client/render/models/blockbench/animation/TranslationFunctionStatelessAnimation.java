/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00040\u0003B\u008e\u0001\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0019j\u0002`\u001a\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012U\u0010,\u001aQ\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(\r\u0012\u0006\u0012\u0004\u0018\u00010\n0)\u0012\u0006\u00100\u001a\u00020\u0004\u00a2\u0006\u0004\b1\u00102Jg\u0010\u0012\u001a\u00020\u00112\b\u0010\u0005\u001a\u0004\u0018\u00018\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R'\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0019j\u0002`\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040$8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(Rf\u0010,\u001aQ\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\b\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b*\u0012\b\b+\u0012\u0004\b\b(\r\u0012\u0006\u0012\u0004\u0018\u00010\n0)8\u0006\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/TranslationFunctionStatelessAnimation;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "entity", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "", "limbSwing", "limbSwingAmount", "ageInTicks", "headYaw", "headPitch", "intensity", "", "setAngles", "(Lnet/minecraft/world/entity/Entity;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;FFFFFF)V", "", "axis", "I", "getAxis", "()I", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "function", "Lkotlin/jvm/functions/Function1;", "getFunction", "()Lkotlin/jvm/functions/Function1;", "Lnet/minecraft/client/model/geom/ModelPart;", "part", "Lnet/minecraft/client/model/geom/ModelPart;", "getPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "Ljava/lang/Class;", "targetFrame", "Ljava/lang/Class;", "getTargetFrame", "()Ljava/lang/Class;", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "timeVariable", "Lkotlin/jvm/functions/Function3;", "getTimeVariable", "()Lkotlin/jvm/functions/Function3;", "frame", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;Lkotlin/jvm/functions/Function1;ILkotlin/jvm/functions/Function3;Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;)V", "common"})
public final class TranslationFunctionStatelessAnimation<T extends Entity>
extends StatelessAnimation<T, ModelFrame> {
    @NotNull
    private final ModelPart part;
    @NotNull
    private final Function1<Float, Float> function;
    private final int axis;
    @NotNull
    private final Function3<PoseableEntityState<T>, Float, Float, Float> timeVariable;
    @NotNull
    private final Class<ModelFrame> targetFrame;

    public TranslationFunctionStatelessAnimation(@NotNull ModelPart part, @NotNull Function1<? super Float, Float> function, int axis, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, @NotNull ModelFrame frame) {
        Intrinsics.checkNotNullParameter((Object)part, (String)"part");
        Intrinsics.checkNotNullParameter(function, (String)"function");
        Intrinsics.checkNotNullParameter(timeVariable, (String)"timeVariable");
        Intrinsics.checkNotNullParameter((Object)frame, (String)"frame");
        super(frame);
        this.part = part;
        this.function = function;
        this.axis = axis;
        this.timeVariable = timeVariable;
        this.targetFrame = ModelFrame.class;
    }

    @NotNull
    public final ModelPart getPart() {
        return this.part;
    }

    @NotNull
    public final Function1<Float, Float> getFunction() {
        return this.function;
    }

    public final int getAxis() {
        return this.axis;
    }

    @NotNull
    public final Function3<PoseableEntityState<T>, Float, Float, Float> getTimeVariable() {
        return this.timeVariable;
    }

    @Override
    @NotNull
    public Class<ModelFrame> getTargetFrame() {
        return this.targetFrame;
    }

    @Override
    protected void setAngles(@Nullable T entity2, @NotNull PoseableEntityModel<T> model, @Nullable PoseableEntityState<T> state, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float intensity) {
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Float f = (Float)this.timeVariable.invoke(state, (Object)Float.valueOf(limbSwing), (Object)Float.valueOf(ageInTicks));
        ModelPartExtensionsKt.addPosition(this.part, this.axis, ((Number)this.function.invoke((Object)Float.valueOf(f != null ? f.floatValue() : 0.0f))).floatValue() * intensity);
    }
}

