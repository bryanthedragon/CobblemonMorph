/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\b\u0006\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\t\u0010\bJ3\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010JQ\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015JQ\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0015J!\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0017\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J1\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001c2\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u001a\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ!\u0010\u001f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0017\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u001f\u0010\u0019J\u0017\u0010 \u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b \u0010!\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext;", "O", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;", "input", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)Z", "getCanSeeSky", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "condition", "", "maximum", "getDepth", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;Lkotlin/jvm/functions/Function1;I)I", "offsetX", "offsetY", "offsetZ", "getHeight", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;Lkotlin/jvm/functions/Function1;IIII)I", "getHorizontalSpace", "elseLight", "getLight", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;I)I", "horizontalRadius", "verticalRadius", "", "getNearbyBlocks", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;II)Ljava/util/List;", "getSkyLight", "getSkySpaceAbove", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)I", "common"})
public interface AreaSpawningContextCalculator<O extends AreaSpawningContext>
extends SpawningContextCalculator<AreaSpawningInput, O> {
    public boolean fits(@NotNull AreaSpawningInput var1);

    public int getDepth(@NotNull AreaSpawningInput var1, @NotNull Function1<? super BlockState, Boolean> var2, int var3);

    public int getHeight(@NotNull AreaSpawningInput var1, @NotNull Function1<? super BlockState, Boolean> var2, int var3, int var4, int var5, int var6);

    public int getHorizontalSpace(@NotNull AreaSpawningInput var1, @NotNull Function1<? super BlockState, Boolean> var2, int var3, int var4, int var5, int var6);

    public int getLight(@NotNull AreaSpawningInput var1, int var2);

    public int getSkyLight(@NotNull AreaSpawningInput var1, int var2);

    public boolean getCanSeeSky(@NotNull AreaSpawningInput var1);

    public int getSkySpaceAbove(@NotNull AreaSpawningInput var1);

    @NotNull
    public List<BlockState> getNearbyBlocks(@NotNull AreaSpawningInput var1, int var2, int var3);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <O extends AreaSpawningContext> int getDepth(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(condition2, (String)"condition");
            return input.getSlice().depthSpace(input.getPosition().m_123341_(), input.getPosition().m_123342_(), input.getPosition().m_123343_(), condition2, maximum);
        }

        public static <O extends AreaSpawningContext> int getHeight(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(condition2, (String)"condition");
            return input.getSlice().heightSpace(input.getPosition().m_123341_() + offsetX, input.getPosition().m_123342_() + offsetY, input.getPosition().m_123343_() + offsetZ, condition2, maximum);
        }

        public static /* synthetic */ int getHeight$default(AreaSpawningContextCalculator areaSpawningContextCalculator, AreaSpawningInput areaSpawningInput, Function1 function1, int n, int n2, int n3, int n4, int n5, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getHeight");
            }
            if ((n5 & 8) != 0) {
                n2 = 0;
            }
            if ((n5 & 0x10) != 0) {
                n3 = 0;
            }
            if ((n5 & 0x20) != 0) {
                n4 = 0;
            }
            return areaSpawningContextCalculator.getHeight(areaSpawningInput, (Function1<BlockState, Boolean>)function1, n, n2, n3, n4);
        }

        public static <O extends AreaSpawningContext> int getHorizontalSpace(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(condition2, (String)"condition");
            return input.getSlice().horizontalSpace(input.getPosition().m_123341_() + offsetX, input.getPosition().m_123342_() + offsetY, input.getPosition().m_123343_() + offsetZ, condition2, maximum);
        }

        public static /* synthetic */ int getHorizontalSpace$default(AreaSpawningContextCalculator areaSpawningContextCalculator, AreaSpawningInput areaSpawningInput, Function1 function1, int n, int n2, int n3, int n4, int n5, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getHorizontalSpace");
            }
            if ((n5 & 8) != 0) {
                n2 = 0;
            }
            if ((n5 & 0x10) != 0) {
                n3 = 0;
            }
            if ((n5 & 0x20) != 0) {
                n4 = 0;
            }
            return areaSpawningContextCalculator.getHorizontalSpace(areaSpawningInput, (Function1<BlockState, Boolean>)function1, n, n2, n3, n4);
        }

        public static <O extends AreaSpawningContext> int getLight(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input, int elseLight) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return input.getSlice().getLight(input.getPosition().m_123341_(), input.getPosition().m_123342_() + 1, input.getPosition().m_123343_(), elseLight);
        }

        public static /* synthetic */ int getLight$default(AreaSpawningContextCalculator areaSpawningContextCalculator, AreaSpawningInput areaSpawningInput, int n, int n2, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getLight");
            }
            if ((n2 & 2) != 0) {
                n = 0;
            }
            return areaSpawningContextCalculator.getLight(areaSpawningInput, n);
        }

        public static <O extends AreaSpawningContext> int getSkyLight(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input, int elseLight) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return input.getSlice().getSkyLight(input.getPosition().m_123341_(), input.getPosition().m_123342_() + 1, input.getPosition().m_123343_(), elseLight);
        }

        public static /* synthetic */ int getSkyLight$default(AreaSpawningContextCalculator areaSpawningContextCalculator, AreaSpawningInput areaSpawningInput, int n, int n2, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getSkyLight");
            }
            if ((n2 & 2) != 0) {
                n = 0;
            }
            return areaSpawningContextCalculator.getSkyLight(areaSpawningInput, n);
        }

        public static <O extends AreaSpawningContext> boolean getCanSeeSky(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return WorldSlice.canSeeSky$default(input.getSlice(), input.getPosition().m_123341_(), input.getPosition().m_123342_() + 1, input.getPosition().m_123343_(), false, 8, null);
        }

        public static <O extends AreaSpawningContext> int getSkySpaceAbove(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return input.getSlice().skySpaceAbove(input.getPosition().m_123341_(), input.getPosition().m_123342_(), input.getPosition().m_123343_());
        }

        @NotNull
        public static <O extends AreaSpawningContext> List<BlockState> getNearbyBlocks(@NotNull AreaSpawningContextCalculator<O> $this, @NotNull AreaSpawningInput input, int horizontalRadius, int verticalRadius) {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return input.getSlice().nearbyBlocks(input.getPosition(), horizontalRadius, verticalRadius);
        }

        public static /* synthetic */ List getNearbyBlocks$default(AreaSpawningContextCalculator areaSpawningContextCalculator, AreaSpawningInput areaSpawningInput, int n, int n2, int n3, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getNearbyBlocks");
            }
            if ((n3 & 2) != 0) {
                n = Cobblemon.INSTANCE.getConfig().getMaxNearbyBlocksHorizontalRange();
            }
            if ((n3 & 4) != 0) {
                n2 = Cobblemon.INSTANCE.getConfig().getMaxNearbyBlocksVerticalRange();
            }
            return areaSpawningContextCalculator.getNearbyBlocks(areaSpawningInput, n, n2);
        }
    }
}

