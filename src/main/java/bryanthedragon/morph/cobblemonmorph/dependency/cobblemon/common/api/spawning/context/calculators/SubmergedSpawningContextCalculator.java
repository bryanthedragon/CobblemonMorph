/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ#\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0007\u0018\u00010\n2\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\f\u0010\rR)\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00070\n0\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\u00020\u00138\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/SubmergedSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/SubmergedSpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;", "input", "calculate", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)Lcom/cobblemon/mod/common/api/spawning/context/SubmergedSpawningContext;", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)Z", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "getFluidCondition", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)Lkotlin/jvm/functions/Function1;", "", "fluidConditions", "Ljava/util/List;", "getFluidConditions", "()Ljava/util/List;", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSubmergedContextCalculators.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SubmergedContextCalculators.kt\ncom/cobblemon/mod/common/api/spawning/context/calculators/SubmergedSpawningContextCalculator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,60:1\n288#2,2:61\n*S KotlinDebug\n*F\n+ 1 SubmergedContextCalculators.kt\ncom/cobblemon/mod/common/api/spawning/context/calculators/SubmergedSpawningContextCalculator\n*L\n39#1:61,2\n*E\n"})
public final class SubmergedSpawningContextCalculator
implements AreaSpawningContextCalculator<SubmergedSpawningContext> {
    @NotNull
    public static final SubmergedSpawningContextCalculator INSTANCE = new SubmergedSpawningContextCalculator();
    @NotNull
    private static final String name = "submerged";
    @NotNull
    private static final List<Function1<BlockState, Boolean>> fluidConditions;

    private SubmergedSpawningContextCalculator() {
    }

    @Override
    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public final List<Function1<BlockState, Boolean>> getFluidConditions() {
        return fluidConditions;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean fits(@NotNull AreaSpawningInput input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Function1<BlockState, Boolean> condition2 = this.getFluidCondition(input);
        if (condition2 == null) return false;
        WorldSlice worldSlice = input.getSlice();
        BlockPos blockPos2 = input.getPosition().m_7495_();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"input.position.down()");
        if ((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(worldSlice, blockPos2, null, 2, null)) == false) return false;
        WorldSlice worldSlice2 = input.getSlice();
        BlockPos blockPos3 = input.getPosition().m_7494_();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"input.position.up()");
        if ((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(worldSlice2, blockPos3, null, 2, null)) == false) return false;
        return true;
    }

    @Nullable
    public final Function1<BlockState, Boolean> getFluidCondition(@NotNull AreaSpawningInput input) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Iterable $this$firstOrNull$iv = fluidConditions;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Function1 it = (Function1)element$iv;
                boolean bl = false;
                if (!((Boolean)it.invoke((Object)WorldSlice.getBlockState$default(input.getSlice(), input.getPosition(), null, 2, null))).booleanValue()) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Override
    @NotNull
    public SubmergedSpawningContext calculate(@NotNull AreaSpawningInput input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Function1<BlockState, Boolean> function1 = this.getFluidCondition(input);
        Intrinsics.checkNotNull(function1);
        Function1<BlockState, Boolean> fluidCondition = function1;
        SpawnCause spawnCause = input.getCause();
        ServerLevel serverLevel = input.getWorld();
        BlockPos blockPos2 = input.getPosition().m_7949_();
        int n = AreaSpawningContextCalculator.DefaultImpls.getLight$default(this, input, 0, 2, null);
        int n2 = AreaSpawningContextCalculator.DefaultImpls.getSkyLight$default(this, input, 0, 2, null);
        boolean bl = this.getCanSeeSky(input);
        List<SpawningInfluence> list = input.getSpawner().copyInfluences();
        int n3 = AreaSpawningContextCalculator.DefaultImpls.getHeight$default(this, input, fluidCondition, Mth.m_14167_((float)((float)Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace() / 2.0f)), 0, 0, 0, 56, null);
        int n4 = this.getDepth(input, fluidCondition, Mth.m_14167_((float)((float)Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace() / 2.0f)));
        WorldSlice worldSlice = input.getSlice();
        List list2 = AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks$default(this, input, 0, 0, 6, null);
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"toImmutable()");
        return new SubmergedSpawningContext(spawnCause, serverLevel, blockPos2, n, n2, bl, list, n3, n4, list2, worldSlice);
    }

    @Override
    public int getDepth(@NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
        return AreaSpawningContextCalculator.DefaultImpls.getDepth(this, input, condition2, maximum);
    }

    @Override
    public int getHeight(@NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
        return AreaSpawningContextCalculator.DefaultImpls.getHeight(this, input, condition2, maximum, offsetX, offsetY, offsetZ);
    }

    @Override
    public int getHorizontalSpace(@NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
        return AreaSpawningContextCalculator.DefaultImpls.getHorizontalSpace(this, input, condition2, maximum, offsetX, offsetY, offsetZ);
    }

    @Override
    public int getLight(@NotNull AreaSpawningInput input, int elseLight) {
        return AreaSpawningContextCalculator.DefaultImpls.getLight(this, input, elseLight);
    }

    @Override
    public int getSkyLight(@NotNull AreaSpawningInput input, int elseLight) {
        return AreaSpawningContextCalculator.DefaultImpls.getSkyLight(this, input, elseLight);
    }

    @Override
    public boolean getCanSeeSky(@NotNull AreaSpawningInput input) {
        return AreaSpawningContextCalculator.DefaultImpls.getCanSeeSky(this, input);
    }

    @Override
    public int getSkySpaceAbove(@NotNull AreaSpawningInput input) {
        return AreaSpawningContextCalculator.DefaultImpls.getSkySpaceAbove(this, input);
    }

    @Override
    @NotNull
    public List<BlockState> getNearbyBlocks(@NotNull AreaSpawningInput input, int horizontalRadius, int verticalRadius) {
        return AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks(this, input, horizontalRadius, verticalRadius);
    }

    static {
        Object[] objectArray = new Function1[]{SpawningContextCalculator.Companion.isWaterCondition(), SpawningContextCalculator.Companion.isLavaCondition()};
        fluidConditions = CollectionsKt.mutableListOf((Object[])objectArray);
    }
}

