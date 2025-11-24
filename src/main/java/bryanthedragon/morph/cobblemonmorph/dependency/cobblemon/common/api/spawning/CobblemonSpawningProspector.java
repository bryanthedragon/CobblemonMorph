/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntProgression
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.SectionPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.lighting.LevelLightEngine
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/spawning/CobblemonSpawningProspector;", "Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "area", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "prospect", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;)Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobbledSpawningProspector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledSpawningProspector.kt\ncom/cobblemon/mod/common/api/spawning/CobblemonSpawningProspector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,118:1\n800#2,11:119\n1549#2:130\n1620#2,3:131\n*S KotlinDebug\n*F\n+ 1 CobbledSpawningProspector.kt\ncom/cobblemon/mod/common/api/spawning/CobblemonSpawningProspector\n*L\n69#1:119,11\n70#1:130\n70#1:131,3\n*E\n"})
public final class CobblemonSpawningProspector
implements SpawningProspector {
    @NotNull
    public static final CobblemonSpawningProspector INSTANCE = new CobblemonSpawningProspector();

    private CobblemonSpawningProspector() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public WorldSlice prospect(@NotNull Spawner spawner, @NotNull SpawningArea area) {
        int n;
        int n2;
        int n3;
        WorldSlice.BlockData[][][] blockDataArray;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$filterIsInstance$iv;
        int difference;
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter((Object)area, (String)"area");
        ServerLevel world = area.getWorld();
        int baseY = area.getBaseY();
        int height = area.getHeight();
        if (baseY < world.m_141937_()) {
            difference = world.m_141937_() - baseY;
            baseY += difference;
            if ((height -= difference) < 1) {
                throw new IllegalStateException("World slice was attempted with totally awful base and dimensions");
            }
        }
        if (baseY + height >= world.m_151558_() && (height -= (difference = baseY + height - 1 - world.m_151558_())) < 1) {
            throw new IllegalStateException("World slice was attempted with totally awful base and dimensions");
        }
        double minimumDistanceBetweenEntities = Cobblemon.INSTANCE.getConfig().getMinimumDistanceBetweenEntities();
        List list = area.getWorld().m_45933_(area.getCause().getEntity(), AABB.m_165882_((Vec3)new Vec3((double)area.getBaseX() + (double)area.getLength() / 2.0, (double)baseY + (double)height / 2.0, (double)area.getBaseZ() + (double)area.getWidth() / 2.0), (double)((double)area.getLength() + minimumDistanceBetweenEntities), (double)((double)height + minimumDistanceBetweenEntities), (double)((double)area.getWidth() + minimumDistanceBetweenEntities)));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"area.world.getOtherEntit\u2026s\n            )\n        )");
        Iterable iterable = list;
        boolean $i$f$filterIsInstance = false;
        void var11_11 = $this$filterIsInstance$iv;
        WorldSlice.BlockData[][][] destination$iv$iv = (WorldSlice.BlockData[][][])new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof LivingEntity)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterIsInstanceTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        int $i$f$mapTo = 0;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            LivingEntity livingEntity = (LivingEntity)item$iv$iv;
            blockDataArray = destination$iv$iv;
            n3 = 0;
            blockDataArray.add(it.m_20182_());
        }
        List nearbyEntityPositions = (List)destination$iv$iv;
        BlockState defaultState = Blocks.f_50069_.m_49966_();
        Intrinsics.checkNotNullExpressionValue((Object)defaultState, (String)"defaultState");
        WorldSlice.BlockData defaultBlockData = new WorldSlice.BlockData(defaultState, 0, 0);
        $i$f$mapTo = area.getLength();
        WorldSlice.BlockData[][][] blockDataArray2 = new WorldSlice.BlockData[$i$f$mapTo][][];
        for (int destination$iv$iv2 = 0; destination$iv$iv2 < $i$f$mapTo; ++destination$iv$iv2) {
            int item$iv$iv = destination$iv$iv2;
            n3 = height;
            WorldSlice.BlockData[][] blockDataArrayArray = new WorldSlice.BlockData[n3][];
            n2 = item$iv$iv;
            blockDataArray = blockDataArray2;
            for (int it = 0; it < n3; ++it) {
                int n4 = it;
                n = 0;
                int n5 = area.getWidth();
                WorldSlice.BlockData[] blockDataArray3 = new WorldSlice.BlockData[n5];
                int n6 = n4;
                WorldSlice.BlockData[][] blockDataArrayArray2 = blockDataArrayArray;
                while (n < n5) {
                    int n7 = n++;
                    blockDataArray3[n7] = defaultBlockData;
                }
                blockDataArrayArray2[n6] = blockDataArray3;
            }
            blockDataArray[n2] = blockDataArrayArray;
        }
        WorldSlice.BlockData[][][] blocks = blockDataArray2;
        int n8 = area.getLength();
        Integer[][] item$iv$iv = new Integer[n8][];
        for ($i$f$mapTo = 0; $i$f$mapTo < n8; ++$i$f$mapTo) {
            int it = $i$f$mapTo;
            n3 = 0;
            int n9 = area.getWidth();
            Integer[] integerArray = new Integer[n9];
            n2 = it;
            blockDataArray = item$iv$iv;
            while (n3 < n9) {
                n = n3++;
                integerArray[n] = world.m_151558_();
            }
            blockDataArray[n2] = integerArray;
        }
        Integer[][] skyLevel = item$iv$iv;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        Map chunks = new LinkedHashMap();
        IntProgression yRange = RangesKt.reversed((IntProgression)((IntProgression)RangesKt.until((int)baseY, (int)(baseY + height))));
        LevelLightEngine lightingProvider = world.m_5518_();
        int n10 = area.getBaseX() + area.getLength();
        for (int x = area.getBaseX(); x < n10; ++x) {
            n = area.getBaseZ() + area.getWidth();
            block8: for (int z = area.getBaseZ(); z < n; ++z) {
                if (chunks.computeIfAbsent(new Pair((Object)SectionPos.m_123171_((int)x), (Object)SectionPos.m_123171_((int)z)), arg_0 -> CobblemonSpawningProspector.prospect$lambda$1((Function1)new Function1<Pair<? extends Integer, ? extends Integer>, ChunkAccess>(world){
                    final /* synthetic */ ServerLevel $world;
                    {
                        this.$world = $world;
                        super(1);
                    }

                    @Nullable
                    public final ChunkAccess invoke(@NotNull Pair<Integer, Integer> it) {
                        Intrinsics.checkNotNullParameter(it, (String)"it");
                        return this.$world.m_6522_(((Number)it.getFirst()).intValue(), ((Number)it.getSecond()).intValue(), ChunkStatus.f_62326_, false);
                    }
                }, arg_0)) == null) continue;
                boolean canSeeSky = world.m_46861_((BlockPos)pos.m_122178_(x, yRange.getFirst(), z));
                int y = yRange.getFirst();
                int n11 = yRange.getLast();
                int n12 = yRange.getStep();
                if ((n12 <= 0 || y > n11) && (n12 >= 0 || n11 > y)) continue;
                while (true) {
                    ChunkAccess query2;
                    int skyLight = lightingProvider.m_75814_(LightLayer.SKY).m_7768_((BlockPos)pos.m_122178_(x, y, z));
                    BlockState state = query2.m_8055_((BlockPos)pos.m_122178_(x, y, z));
                    WorldSlice.BlockData[] blockDataArray4 = blocks[x - area.getBaseX()][y - baseY];
                    int n13 = z - area.getBaseZ();
                    Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
                    blockDataArray4[n13] = new WorldSlice.BlockData(state, world.m_46803_((BlockPos)pos), skyLight);
                    if (canSeeSky) {
                        skyLevel[x - area.getBaseX()][z - area.getBaseZ()] = y;
                    }
                    if (state.m_60819_().m_76178_() && !state.m_204336_(CobblemonBlockTags.SEES_SKY)) {
                        canSeeSky = false;
                    }
                    if (y == n11) continue block8;
                    y += n12;
                }
            }
        }
        return new WorldSlice(area.getCause(), world, area.getBaseX(), baseY, area.getBaseZ(), blocks, skyLevel, nearbyEntityPositions);
    }

    private static final ChunkAccess prospect$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (ChunkAccess)$tmp0.invoke(p0);
    }
}

