/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.FlooredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningInput;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\t8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\t8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/FlooredSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/FlooredSpawningContext;", "T", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;", "input", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)Z", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "getBaseCondition", "()Lkotlin/jvm/functions/Function1;", "baseCondition", "getSurroundingCondition", "surroundingCondition", "common"})
public interface FlooredSpawningContextCalculator<T extends FlooredSpawningContext>
extends AreaSpawningContextCalculator<T> {
    @NotNull
    public Function1<BlockState, Boolean> getBaseCondition();

    @NotNull
    public Function1<BlockState, Boolean> getSurroundingCondition();

    @Override
    public boolean fits(@NotNull AreaSpawningInput var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T extends FlooredSpawningContext> boolean fits(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            BlockState floorState = WorldSlice.getBlockState$default(input.getSlice(), input.getPosition(), null, 2, null);
            WorldSlice worldSlice = input.getSlice();
            BlockPos blockPos2 = input.getPosition().m_7494_();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"input.position.up()");
            BlockState aboveState = WorldSlice.getBlockState$default(worldSlice, blockPos2, null, 2, null);
            return (Boolean)$this.getBaseCondition().invoke((Object)floorState) != false && (Boolean)$this.getSurroundingCondition().invoke((Object)aboveState) != false;
        }

        public static <T extends FlooredSpawningContext> int getDepth(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(condition2, (String)"condition");
            return AreaSpawningContextCalculator.DefaultImpls.getDepth((AreaSpawningContextCalculator)$this, input, condition2, maximum);
        }

        public static <T extends FlooredSpawningContext> int getHeight(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(condition2, (String)"condition");
            return AreaSpawningContextCalculator.DefaultImpls.getHeight((AreaSpawningContextCalculator)$this, input, condition2, maximum, offsetX, offsetY, offsetZ);
        }

        public static <T extends FlooredSpawningContext> int getHorizontalSpace(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(condition2, (String)"condition");
            return AreaSpawningContextCalculator.DefaultImpls.getHorizontalSpace((AreaSpawningContextCalculator)$this, input, condition2, maximum, offsetX, offsetY, offsetZ);
        }

        public static <T extends FlooredSpawningContext> int getLight(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input, int elseLight) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return AreaSpawningContextCalculator.DefaultImpls.getLight((AreaSpawningContextCalculator)$this, input, elseLight);
        }

        public static <T extends FlooredSpawningContext> int getSkyLight(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input, int elseLight) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return AreaSpawningContextCalculator.DefaultImpls.getSkyLight((AreaSpawningContextCalculator)$this, input, elseLight);
        }

        public static <T extends FlooredSpawningContext> boolean getCanSeeSky(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return AreaSpawningContextCalculator.DefaultImpls.getCanSeeSky((AreaSpawningContextCalculator)$this, input);
        }

        public static <T extends FlooredSpawningContext> int getSkySpaceAbove(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return AreaSpawningContextCalculator.DefaultImpls.getSkySpaceAbove((AreaSpawningContextCalculator)$this, input);
        }

        @NotNull
        public static <T extends FlooredSpawningContext> List<BlockState> getNearbyBlocks(@NotNull FlooredSpawningContextCalculator<T> $this, @NotNull AreaSpawningInput input, int horizontalRadius, int verticalRadius) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks((AreaSpawningContextCalculator)$this, input, horizontalRadius, verticalRadius);
        }
    }
}

