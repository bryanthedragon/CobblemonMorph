/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b'\u0010(R#\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR#\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0006\u001a\u0004\b\u000b\u0010\bR\u001a\u0010\r\u001a\u00020\f8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\f8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b%\u0010\u000e\u001a\u0004\b&\u0010\u0010\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "", "", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/biome/Biome;", "biomes", "Ljava/util/List;", "getBiomes", "()Ljava/util/List;", "Lnet/minecraft/world/level/block/Block;", "blocks", "getBlocks", "", "canSleep", "Z", "getCanSleep", "()Z", "Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "depth", "Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "getDepth", "()Lcom/cobblemon/mod/common/api/ai/SleepDepth;", "Lkotlin/ranges/IntRange;", "light", "Lkotlin/ranges/IntRange;", "getLight", "()Lkotlin/ranges/IntRange;", "", "sleepChance", "F", "getSleepChance", "()F", "Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "times", "Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "getTimes", "()Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "willSleepOnBed", "getWillSleepOnBed", "<init>", "()V", "common"})
public final class RestBehaviour {
    private final boolean canSleep;
    @NotNull
    private final TimeRange times;
    private final float sleepChance;
    @NotNull
    private final List<RegistryLikeCondition<Block>> blocks;
    @NotNull
    private final List<RegistryLikeCondition<Biome>> biomes;
    @NotNull
    private final IntRange light;
    @NotNull
    private final SleepDepth depth;
    private final boolean willSleepOnBed;

    public RestBehaviour() {
        TimeRange timeRange = TimeRange.Companion.getTimeRanges().get("night");
        Intrinsics.checkNotNull((Object)timeRange);
        this.times = timeRange;
        this.sleepChance = 0.0016666667f;
        this.blocks = new ArrayList();
        this.biomes = new ArrayList();
        this.light = new IntRange(0, 15);
        this.depth = SleepDepth.Companion.getNormal();
    }

    public final boolean getCanSleep() {
        return this.canSleep;
    }

    @NotNull
    public final TimeRange getTimes() {
        return this.times;
    }

    public final float getSleepChance() {
        return this.sleepChance;
    }

    @NotNull
    public final List<RegistryLikeCondition<Block>> getBlocks() {
        return this.blocks;
    }

    @NotNull
    public final List<RegistryLikeCondition<Biome>> getBiomes() {
        return this.biomes;
    }

    @NotNull
    public final IntRange getLight() {
        return this.light;
    }

    @NotNull
    public final SleepDepth getDepth() {
        return this.depth;
    }

    public final boolean getWillSleepOnBed() {
        return this.willSleepOnBed;
    }
}

