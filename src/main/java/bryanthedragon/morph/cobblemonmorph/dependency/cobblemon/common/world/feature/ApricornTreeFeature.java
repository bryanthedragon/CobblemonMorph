/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelSimulatedReader
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
 *  net.minecraft.world.level.levelgen.feature.TreeFeature
 *  net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ListExtensionsKt;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J+\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\f0\f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ+\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u000f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J!\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0019H\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/world/feature/ApricornTreeFeature;", "Lnet/minecraft/world/level/levelgen/feature/Feature;", "Lnet/minecraft/world/level/levelgen/feature/configurations/BlockStateConfiguration;", "Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;", "context", "", "generate", "(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", "Lnet/minecraft/core/BlockPos;", "origin", "Lnet/minecraft/util/RandomSource;", "random", "", "getLayerFourVariation", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)Ljava/util/List;", "Lkotlin/Pair;", "getLayerOneVariation", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)Lkotlin/Pair;", "Lnet/minecraft/world/level/LevelSimulatedReader;", "testableWorld", "blockPos", "isAir", "(Lnet/minecraft/world/level/LevelSimulatedReader;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/level/WorldGenLevel;", "worldGenLevel", "Lnet/minecraft/world/level/block/state/BlockState;", "blockState", "", "setBlockIfClear", "(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nApricornTreeFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ApricornTreeFeature.kt\ncom/cobblemon/mod/common/world/feature/ApricornTreeFeature\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,238:1\n766#2:239\n857#2,2:240\n1549#2:242\n1620#2,3:243\n1855#2,2:246\n*S KotlinDebug\n*F\n+ 1 ApricornTreeFeature.kt\ncom/cobblemon/mod/common/world/feature/ApricornTreeFeature\n*L\n159#1:239\n159#1:240,2\n161#1:242\n161#1:243,3\n162#1:246,2\n*E\n"})
public final class ApricornTreeFeature
extends Feature<BlockStateConfiguration> {
    public ApricornTreeFeature() {
        super(BlockStateConfiguration.f_67546_);
    }

    /*
     * WARNING - void declaration
     */
    public boolean m_142674_(@NotNull FeaturePlaceContext<BlockStateConfiguration> context) {
        Object leafPos;
        Direction direction32;
        Object[] leafPos2;
        boolean isGenerating;
        Intrinsics.checkNotNullParameter(context, (String)"context");
        WorldGenLevel worldGenLevel = context.m_159774_();
        Intrinsics.checkNotNullExpressionValue((Object)worldGenLevel, (String)"context.world");
        WorldGenLevel worldGenLevel2 = worldGenLevel;
        RandomSource random = context.m_225041_();
        BlockPos origin = context.m_159777_();
        boolean bl = isGenerating = !Intrinsics.areEqual((Object)worldGenLevel2.m_46865_(origin).m_6415_(), (Object)ChunkStatus.f_62326_);
        if (isGenerating) {
            float f;
            Holder biome2 = worldGenLevel2.m_204166_(origin);
            if (biome2.m_203656_(CobblemonBiomeTags.HAS_APRICORNS_SPARSE)) {
                f = 0.1f;
            } else if (biome2.m_203656_(CobblemonBiomeTags.HAS_APRICORNS_DENSE)) {
                f = 10.0f;
            } else if (biome2.m_203656_(CobblemonBiomeTags.HAS_APRICORNS_NORMAL)) {
                f = 1.0f;
            } else {
                return false;
            }
            float multiplier = f;
            if (random.m_188501_() > multiplier * Cobblemon.INSTANCE.getConfig().getBaseApricornTreeGenerationChance()) {
                return false;
            }
        }
        if (!worldGenLevel2.m_8055_(origin.m_7495_()).m_204336_(BlockTags.f_144274_)) {
            return false;
        }
        BlockState logState = CobblemonBlocks.APRICORN_LOG.m_49966_();
        for (int y = 0; y < 5; ++y) {
            try {
                BlockPos logPos = origin.m_5484_(Direction.UP, y);
                worldGenLevel2.m_7731_(logPos, logState, 2);
                continue;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        List allApricornSpots = new ArrayList();
        BlockState leafBlock = CobblemonBlocks.APRICORN_LEAVES.m_49966_();
        BlockPos layerOnePos = origin.m_121945_(Direction.UP);
        Object[] objectArray = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        for (Object direction2 : CollectionsKt.listOf((Object[])objectArray)) {
            leafPos2 = layerOnePos.m_121945_((Direction)direction2);
            Intrinsics.checkNotNullExpressionValue((Object)leafPos2, (String)"leafPos");
            BlockState blockState = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos2);
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, (BlockPos)leafPos2, blockState);
            for (int offset = 1; offset < 4; ++offset) {
                leafPos2 = leafPos2.m_7494_();
                Intrinsics.checkNotNullExpressionValue((Object)leafPos2, (String)"leafPos");
                BlockState blockState2 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos2);
                Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
                this.setBlockIfClear(worldGenLevel2, (BlockPos)leafPos2, blockState2);
            }
        }
        Intrinsics.checkNotNullExpressionValue((Object)layerOnePos, (String)"layerOnePos");
        Intrinsics.checkNotNullExpressionValue((Object)random, (String)"random");
        Pair<BlockPos, BlockPos> layerOneExtenders = this.getLayerOneVariation(layerOnePos, random);
        BlockPos blockPos2 = (BlockPos)layerOneExtenders.getFirst();
        BlockState blockState = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)((BlockPos)layerOneExtenders.getFirst()));
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"updateDistanceFromLogs(l\u2026 layerOneExtenders.first)");
        this.setBlockIfClear(worldGenLevel2, blockPos2, blockState);
        BlockPos blockPos3 = (BlockPos)layerOneExtenders.getSecond();
        BlockState blockState3 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)((BlockPos)layerOneExtenders.getSecond()));
        Intrinsics.checkNotNullExpressionValue((Object)blockState3, (String)"updateDistanceFromLogs(l\u2026layerOneExtenders.second)");
        this.setBlockIfClear(worldGenLevel2, blockPos3, blockState3);
        leafPos2 = new Pair[]{new Pair((Object)1, (Object)1), new Pair((Object)-1, (Object)-1), new Pair((Object)1, (Object)-1), new Pair((Object)-1, (Object)1)};
        for (Object coords : CollectionsKt.listOf((Object[])leafPos2)) {
            BlockPos leafPos3;
            BlockPos blockPos4 = leafPos3 = layerOnePos.m_7918_(((Number)coords.getFirst()).intValue(), 0, ((Number)coords.getSecond()).intValue());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"leafPos");
            BlockState blockState4 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos3);
            Intrinsics.checkNotNullExpressionValue((Object)blockState4, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, blockPos4, blockState4);
            for (int i = 1; i < 4; ++i) {
                BlockPos blockPos5 = leafPos3 = leafPos3.m_7494_();
                Intrinsics.checkNotNullExpressionValue((Object)blockPos5, (String)"leafPos");
                BlockState blockState5 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos3);
                Intrinsics.checkNotNullExpressionValue((Object)blockState5, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
                this.setBlockIfClear(worldGenLevel2, blockPos5, blockState5);
            }
        }
        BlockPos layerTwoPos = origin.m_7918_(0, 2, 0);
        Object[] leafPos3 = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        for (Direction direction32 : Lists.newArrayList((Object[])leafPos3)) {
            List list = new ArrayList();
            leafPos = layerTwoPos.m_7918_(direction32.m_122429_() * 2, direction32.m_122430_() * 2, direction32.m_122431_() * 2);
            BlockPos blockPos6 = leafPos;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos6, (String)"leafPos");
            BlockState blockState6 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos);
            Intrinsics.checkNotNullExpressionValue((Object)blockState6, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, blockPos6, blockState6);
            list.add(TuplesKt.to((Object)direction32.m_122424_(), (Object)leafPos.m_121945_(direction32)));
            leafPos = leafPos.m_7494_();
            BlockPos blockPos7 = leafPos;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos7, (String)"leafPos");
            BlockState blockState7 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos);
            Intrinsics.checkNotNullExpressionValue((Object)blockState7, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, blockPos7, blockState7);
            list.add(TuplesKt.to((Object)direction32.m_122424_(), (Object)leafPos.m_121945_(direction32)));
            allApricornSpots.add(list);
        }
        direction32 = new Direction[]{new Pair((Object)1, (Object)2), new Pair((Object)-1, (Object)2), new Pair((Object)1, (Object)-2), new Pair((Object)-2, (Object)1), new Pair((Object)2, (Object)1), new Pair((Object)-2, (Object)-1), new Pair((Object)-1, (Object)-2), new Pair((Object)2, (Object)-1)};
        for (Object coords : Lists.newArrayList((Object[])direction32)) {
            Object apricornPos;
            Object[] direction422;
            List list = new ArrayList();
            leafPos = layerTwoPos.m_7918_(((Number)coords.getFirst()).intValue(), 0, ((Number)coords.getSecond()).intValue());
            BlockPos blockPos8 = leafPos;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos8, (String)"leafPos");
            BlockState blockState8 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos);
            Intrinsics.checkNotNullExpressionValue((Object)blockState8, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, blockPos8, blockState8);
            Object[] objectArray2 = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            for (Object[] direction422 : CollectionsKt.listOf((Object[])objectArray2)) {
                apricornPos = leafPos.m_121945_((Direction)direction422);
                if (!this.isAir((LevelSimulatedReader)worldGenLevel2, (BlockPos)apricornPos)) continue;
                list.add(TuplesKt.to((Object)direction422.m_122424_(), (Object)apricornPos));
            }
            leafPos = leafPos.m_7494_();
            BlockPos blockPos9 = leafPos;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos9, (String)"leafPos");
            BlockState blockState9 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos);
            Intrinsics.checkNotNullExpressionValue((Object)blockState9, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, blockPos9, blockState9);
            direction422 = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            for (Direction direction : CollectionsKt.listOf((Object[])direction422)) {
                apricornPos = leafPos.m_121945_(direction);
                if (!this.isAir((LevelSimulatedReader)worldGenLevel2, (BlockPos)apricornPos)) continue;
                list.add(TuplesKt.to((Object)direction.m_122424_(), (Object)apricornPos));
            }
            allApricornSpots.add(list);
        }
        BlockPos topperPos = origin.m_7918_(0, 5, 0);
        Intrinsics.checkNotNullExpressionValue((Object)topperPos, (String)"topperPos");
        BlockState blockState10 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)topperPos);
        Intrinsics.checkNotNullExpressionValue((Object)blockState10, (String)"updateDistanceFromLogs(l\u2026worldGenLevel, topperPos)");
        this.setBlockIfClear(worldGenLevel2, topperPos, blockState10);
        Object[] objectArray3 = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        for (Direction direction : Lists.newArrayList((Object[])objectArray3)) {
            leafPos = topperPos.m_121945_(direction);
            Intrinsics.checkNotNullExpressionValue((Object)leafPos, (String)"leafPos");
            BlockState blockState11 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)leafPos);
            Intrinsics.checkNotNullExpressionValue((Object)blockState11, (String)"updateDistanceFromLogs(l\u2026, worldGenLevel, leafPos)");
            this.setBlockIfClear(worldGenLevel2, (BlockPos)leafPos, blockState11);
        }
        BlockPos blockPos10 = origin.m_5484_(Direction.UP, 4);
        Intrinsics.checkNotNullExpressionValue((Object)blockPos10, (String)"origin.offset(UP, 4)");
        for (List list : this.getLayerFourVariation(blockPos10, random)) {
            for (BlockPos block : list) {
                BlockState blockState12 = LeavesBlock.m_54435_((BlockState)leafBlock, (LevelAccessor)((LevelAccessor)worldGenLevel2), (BlockPos)block);
                Intrinsics.checkNotNullExpressionValue((Object)blockState12, (String)"updateDistanceFromLogs(l\u2026ck, worldGenLevel, block)");
                this.setBlockIfClear(worldGenLevel2, block, blockState12);
            }
        }
        if (!((Collection)allApricornSpots).isEmpty()) {
            void $this$forEach$iv;
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv;
            List p0;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = allApricornSpots;
            boolean bl2 = false;
            leafPos = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                p0 = (List)element$iv$iv;
                boolean bl22 = false;
                if (!(!((Collection)p0).isEmpty())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = ListExtensionsKt.randomNoCopy((List)destination$iv$iv, RangesKt.coerceAtMost((int)allApricornSpots.size(), (int)8));
            boolean bl3 = false;
            $this$filterTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                p0 = (List)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl32 = false;
                collection.add((Pair)CollectionsKt.random((Collection)((Collection)it), (Random)((Random)Random.Default)));
            }
            $this$map$iv = (List)destination$iv$iv;
            boolean bl4 = false;
            for (Object element$iv : $this$forEach$iv) {
                Pair it = (Pair)element$iv;
                boolean bl42 = false;
                if (!worldGenLevel2.m_8055_(((BlockPos)it.getSecond()).m_121945_((Direction)it.getFirst())).m_60734_().equals((Object)leafBlock.m_60734_())) continue;
                BlockPos blockPos11 = (BlockPos)it.getSecond();
                Object object = ((BlockState)((BlockStateConfiguration)context.m_159778_()).f_67547_.m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)it.getFirst())).m_61124_((Property)ApricornBlock.Companion.getAGE(), (Comparable)Integer.valueOf(isGenerating ? random.m_188503_(4) : 0));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.config.state\n   \u2026                        )");
                this.setBlockIfClear(worldGenLevel2, blockPos11, (BlockState)object);
            }
        }
        return true;
    }

    private final void setBlockIfClear(WorldGenLevel worldGenLevel, BlockPos blockPos2, BlockState blockState) {
        if (!TreeFeature.m_67267_((LevelSimulatedReader)((LevelSimulatedReader)worldGenLevel), (BlockPos)blockPos2)) {
            return;
        }
        worldGenLevel.m_7731_(blockPos2, blockState, 3);
    }

    private final Pair<BlockPos, BlockPos> getLayerOneVariation(BlockPos origin, RandomSource random) {
        Direction direction = Direction.NORTH;
        switch (random.m_188503_(4)) {
            case 1: {
                direction = Direction.EAST;
                break;
            }
            case 2: {
                direction = Direction.SOUTH;
                break;
            }
            case 3: {
                direction = Direction.WEST;
            }
        }
        BlockPos posOne = origin.m_7918_(direction.m_122429_() * 2, direction.m_122430_() * 2, direction.m_122431_() * 2);
        int offset = random.m_188499_() ? -1 : 1;
        BlockPos posTwo = direction.m_122429_() == 0 ? posOne.m_7918_(offset, 0, 0) : posOne.m_7918_(0, 0, offset);
        return TuplesKt.to((Object)posOne, (Object)posTwo);
    }

    private final List<List<BlockPos>> getLayerFourVariation(BlockPos origin, RandomSource random) {
        List variationList = new ArrayList();
        List usedDirections = new ArrayList();
        int i = 1;
        int n = Random.Default.nextInt(2, 4);
        if (i <= n) {
            while (true) {
                BlockPos posTwo;
                Direction direction = null;
                while (direction == null || usedDirections.contains(direction)) {
                    switch (random.m_188503_(4)) {
                        case 0: {
                            direction = Direction.NORTH;
                            break;
                        }
                        case 1: {
                            direction = Direction.EAST;
                            break;
                        }
                        case 2: {
                            direction = Direction.SOUTH;
                            break;
                        }
                        case 3: {
                            direction = Direction.WEST;
                        }
                    }
                }
                BlockPos posOne = origin.m_7918_(direction.m_122429_() * 2, direction.m_122430_() * 2, direction.m_122431_() * 2);
                int offset = random.m_188499_() ? -1 : 1;
                BlockPos blockPos2 = posTwo = direction.m_122429_() == 0 ? posOne.m_7918_(offset, 0, 0) : posOne.m_7918_(0, 0, offset);
                if (random.m_188503_(3) == 0) {
                    Object[] objectArray = new BlockPos[]{posOne, posTwo};
                    variationList.add(CollectionsKt.listOf((Object[])objectArray));
                } else {
                    variationList.add(CollectionsKt.listOf((Object)(random.m_188499_() ? posOne : posTwo)));
                }
                if (i == n) break;
                ++i;
            }
        }
        return variationList;
    }

    private final boolean isAir(LevelSimulatedReader testableWorld, BlockPos blockPos2) {
        return testableWorld.m_7433_(blockPos2, ApricornTreeFeature::isAir$lambda$2);
    }

    private static final boolean isAir$lambda$2(BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return blockState.m_60713_(Blocks.f_50016_);
    }
}

