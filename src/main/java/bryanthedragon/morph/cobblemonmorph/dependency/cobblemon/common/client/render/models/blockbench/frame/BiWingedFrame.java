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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.WingFlapIdleAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0096\u0001\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u0002`\u00062U\u0010\u000f\u001aQ\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00028\u0000\u0018\u00010\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00050\b2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0018\u001a\u00020\u00158&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u00158&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/BiWingedFrame;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "Lnet/minecraft/world/entity/Entity;", "T", "Lkotlin/Function1;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "flapFunction", "Lkotlin/Function3;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lkotlin/ParameterName;", "name", "state", "limbSwing", "ageInTicks", "timeVariable", "", "axis", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WingFlapIdleAnimation;", "wingFlap", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;I)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/WingFlapIdleAnimation;", "Lnet/minecraft/client/model/geom/ModelPart;", "getLeftWing", "()Lnet/minecraft/client/model/geom/ModelPart;", "leftWing", "getRightWing", "rightWing", "common"})
public interface BiWingedFrame
extends ModelFrame {
    @NotNull
    public ModelPart getLeftWing();

    @NotNull
    public ModelPart getRightWing();

    @NotNull
    public <T extends Entity> WingFlapIdleAnimation<T> wingFlap(@NotNull Function1<? super Float, Float> var1, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> var2, int var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static <T extends Entity> WingFlapIdleAnimation<T> wingFlap(@NotNull BiWingedFrame $this, @NotNull Function1<? super Float, Float> flapFunction, @NotNull Function3<? super PoseableEntityState<T>, ? super Float, ? super Float, Float> timeVariable, int axis) {
            Intrinsics.checkNotNullParameter(flapFunction, (String)"flapFunction");
            Intrinsics.checkNotNullParameter(timeVariable, (String)"timeVariable");
            return new WingFlapIdleAnimation($this, flapFunction, timeVariable, axis);
        }
    }
}

