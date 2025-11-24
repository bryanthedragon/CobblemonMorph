/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SeafloorSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.FlooredSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R&\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\u00020\u000e8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R&\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0014\u0010\r\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/calculators/SeafloorSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/FlooredSpawningContextCalculator;", "Lcom/cobblemon/mod/common/api/spawning/context/SeafloorSpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;", "input", "calculate", "(Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningInput;)Lcom/cobblemon/mod/common/api/spawning/context/SeafloorSpawningContext;", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "", "baseCondition", "Lkotlin/jvm/functions/Function1;", "getBaseCondition", "()Lkotlin/jvm/functions/Function1;", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "surroundingCondition", "getSurroundingCondition", "<init>", "()V", "common"})
public final class SeafloorSpawningContextCalculator
implements FlooredSpawningContextCalculator<SeafloorSpawningContext> {
    @NotNull
    public static final SeafloorSpawningContextCalculator INSTANCE = new SeafloorSpawningContextCalculator();
    @NotNull
    private static final String name = "seafloor";
    @NotNull
    private static final Function1<BlockState, Boolean> baseCondition = SpawningContextCalculator.Companion.isSolidCondition();
    @NotNull
    private static final Function1<BlockState, Boolean> surroundingCondition = SpawningContextCalculator.Companion.isWaterCondition();

    private SeafloorSpawningContextCalculator() {
    }

    @Override
    @NotNull
    public String getName() {
        return name;
    }

    @Override
    @NotNull
    public Function1<BlockState, Boolean> getBaseCondition() {
        return baseCondition;
    }

    @Override
    @NotNull
    public Function1<BlockState, Boolean> getSurroundingCondition() {
        return surroundingCondition;
    }

    @Override
    @NotNull
    public SeafloorSpawningContext calculate(@NotNull AreaSpawningInput input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        SpawnCause spawnCause = input.getCause();
        ServerLevel serverLevel = input.getWorld();
        BlockPos blockPos2 = input.getPosition().m_7949_();
        int n = AreaSpawningContextCalculator.DefaultImpls.getLight$default(this, input, 0, 2, null);
        int n2 = AreaSpawningContextCalculator.DefaultImpls.getSkyLight$default(this, input, 0, 2, null);
        boolean bl = this.getCanSeeSky(input);
        List<SpawningInfluence> list = input.getSpawner().copyInfluences();
        int n3 = AreaSpawningContextCalculator.DefaultImpls.getHeight$default(this, input, this.getSurroundingCondition(), Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace(), 0, 1, 0, 40, null);
        WorldSlice worldSlice = input.getSlice();
        List list2 = AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks$default(this, input, 0, 0, 6, null);
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"toImmutable()");
        return new SeafloorSpawningContext(spawnCause, serverLevel, blockPos2, n, n2, bl, list, n3, list2, worldSlice);
    }

    @Override
    public boolean fits(@NotNull AreaSpawningInput input) {
        return FlooredSpawningContextCalculator.DefaultImpls.fits(this, input);
    }

    @Override
    public int getDepth(@NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
        return FlooredSpawningContextCalculator.DefaultImpls.getDepth(this, input, condition2, maximum);
    }

    @Override
    public int getHeight(@NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
        return FlooredSpawningContextCalculator.DefaultImpls.getHeight(this, input, condition2, maximum, offsetX, offsetY, offsetZ);
    }

    @Override
    public int getHorizontalSpace(@NotNull AreaSpawningInput input, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum, int offsetX, int offsetY, int offsetZ) {
        return FlooredSpawningContextCalculator.DefaultImpls.getHorizontalSpace(this, input, condition2, maximum, offsetX, offsetY, offsetZ);
    }

    @Override
    public int getLight(@NotNull AreaSpawningInput input, int elseLight) {
        return FlooredSpawningContextCalculator.DefaultImpls.getLight(this, input, elseLight);
    }

    @Override
    public int getSkyLight(@NotNull AreaSpawningInput input, int elseLight) {
        return FlooredSpawningContextCalculator.DefaultImpls.getSkyLight(this, input, elseLight);
    }

    @Override
    public boolean getCanSeeSky(@NotNull AreaSpawningInput input) {
        return FlooredSpawningContextCalculator.DefaultImpls.getCanSeeSky(this, input);
    }

    @Override
    public int getSkySpaceAbove(@NotNull AreaSpawningInput input) {
        return FlooredSpawningContextCalculator.DefaultImpls.getSkySpaceAbove(this, input);
    }

    @Override
    @NotNull
    public List<BlockState> getNearbyBlocks(@NotNull AreaSpawningInput input, int horizontalRadius, int verticalRadius) {
        return FlooredSpawningContextCalculator.DefaultImpls.getNearbyBlocks(this, input, horizontalRadius, verticalRadius);
    }
}

