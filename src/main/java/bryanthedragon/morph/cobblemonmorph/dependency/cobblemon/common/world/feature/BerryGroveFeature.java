/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Vec3i
 *  net.minecraft.data.worldgen.placement.PlacementUtils
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.valueproviders.ClampedNormalInt
 *  net.minecraft.util.valueproviders.IntProvider
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.GrassBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
 *  net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
 *  net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
 *  net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
 *  net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider
 *  net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
 *  net.minecraft.world.level.levelgen.placement.BlockPredicateFilter
 *  net.minecraft.world.level.levelgen.placement.PlacedFeature
 *  net.minecraft.world.level.levelgen.placement.PlacementModifier
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.BerryHelper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.BerryGroveFeature;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/world/feature/BerryGroveFeature;", "Lnet/minecraft/world/level/levelgen/feature/Feature;", "Lnet/minecraft/world/level/levelgen/feature/configurations/NoneFeatureConfiguration;", "Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;", "context", "", "generate", "(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBerryGroveFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryGroveFeature.kt\ncom/cobblemon/mod/common/world/feature/BerryGroveFeature\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,105:1\n1#2:106\n*E\n"})
public final class BerryGroveFeature
extends Feature<NoneFeatureConfiguration> {
    public BerryGroveFeature() {
        super(NoneFeatureConfiguration.f_67815_);
    }

    /*
     * WARNING - void declaration
     */
    public boolean m_142674_(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int numTreesToGen;
        int n;
        boolean isGenerating;
        Intrinsics.checkNotNullParameter(context, (String)"context");
        WorldGenLevel worldGenLevel = context.m_159774_();
        Intrinsics.checkNotNull((Object)worldGenLevel);
        WorldGenLevel worldGenLevel2 = worldGenLevel;
        RandomSource randomSource = context.m_225041_();
        Intrinsics.checkNotNull((Object)randomSource);
        RandomSource random = randomSource;
        BlockPos blockPos2 = context.m_159777_();
        Intrinsics.checkNotNull((Object)blockPos2);
        BlockPos origin = blockPos2;
        boolean bl = isGenerating = !Intrinsics.areEqual((Object)worldGenLevel2.m_46865_(origin).m_6415_(), (Object)ChunkStatus.f_62326_);
        if (!isGenerating) {
            return false;
        }
        Holder biome2 = worldGenLevel2.m_204166_(origin);
        Intrinsics.checkNotNullExpressionValue((Object)biome2, (String)"biome");
        List<BerryBlock> validTrees = BerryHelper.INSTANCE.getBerriesForBiome((Holder<Biome>)biome2);
        if (validTrees.isEmpty()) {
            return false;
        }
        Object t = CollectionUtilsKt.weightedSelection((Iterable)validTrees, generate.pickedTree.1.INSTANCE);
        Intrinsics.checkNotNull(t);
        BerryBlock pickedTree2 = (BerryBlock)t;
        Berry berry = pickedTree2.berry();
        Intrinsics.checkNotNull((Object)berry);
        Berry berry2 = berry;
        Object object = pickedTree2.berry();
        if (object != null && (object = ((Berry)object).getSpawnConditions()) != null) {
            Iterable iterable = (Iterable)object;
            int n2 = 0;
            for (Object t2 : iterable) {
                void cond;
                BerrySpawnCondition berrySpawnCondition = (BerrySpawnCondition)t2;
                int n3 = n2;
                boolean bl2 = false;
                Integer n4 = cond.getGroveSize(random);
                int it = ((Number)n4).intValue();
                boolean bl3 = false;
                Integer n5 = cond.canSpawn(berry2, (Holder<Biome>)biome2) ? n4 : null;
                int n6 = n5 != null ? n5 : 0;
                n2 = n3 + n6;
            }
            n = n2;
        } else {
            n = 0;
        }
        int numTreesLeftToGen = numTreesToGen = n;
        SimpleStateProvider defTreeState = BlockStateProvider.m_191384_((BlockState)((BlockState)pickedTree2.m_49966_().m_61124_((Property)BerryBlock.Companion.getWAS_GENERATED(), (Comparable)Boolean.valueOf(true))));
        RandomizedIntStateProvider randomTreeStateProvider = new RandomizedIntStateProvider((BlockStateProvider)defTreeState, BerryBlock.Companion.getAGE(), (IntProvider)ClampedNormalInt.m_185879_((float)4.0f, (float)1.0f, (int)3, (int)5));
        PlacementModifier[] placementModifierArray = new PlacementModifier[]{BlockPredicateFilter.m_191576_((BlockPredicate)BlockPredicate.m_204677_(CobblemonBlockTags.BERRY_REPLACEABLE)), BlockPredicateFilter.m_191576_((BlockPredicate)BlockPredicate.m_224768_((Vec3i)new Vec3i(0, -1, 0), CobblemonBlockTags.BERRY_WILD_SOIL))};
        PlacedFeature blockPlaceFeature = (PlacedFeature)PlacementUtils.m_206502_((Feature)Feature.f_65741_, (FeatureConfiguration)((FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)randomTreeStateProvider)), (PlacementModifier[])placementModifierArray).m_203334_();
        BlockPos[] blockPosArray = new BlockPos[]{origin.m_122012_(), origin.m_122012_().m_122029_(), origin.m_122029_(), origin.m_122019_().m_122029_(), origin.m_122019_(), origin.m_122019_().m_122024_(), origin.m_122024_(), origin.m_122012_().m_122024_(), origin.m_7494_().m_122012_(), origin.m_7494_().m_122012_().m_122029_(), origin.m_7494_().m_122029_(), origin.m_7494_().m_122019_().m_122029_(), origin.m_7494_().m_122019_(), origin.m_7494_().m_122019_().m_122024_(), origin.m_7494_().m_122024_(), origin.m_7494_().m_122012_().m_122024_(), origin.m_7495_().m_122012_(), origin.m_7495_().m_122012_().m_122029_(), origin.m_7495_().m_122029_(), origin.m_7495_().m_122019_().m_122029_(), origin.m_7495_().m_122019_(), origin.m_7495_().m_122019_().m_122024_(), origin.m_7495_().m_122024_(), origin.m_7495_().m_122012_().m_122024_()};
        List possiblePositions = CollectionsKt.shuffled((Iterable)CollectionsKt.listOf((Object[])blockPosArray));
        for (BlockPos dir : possiblePositions) {
            if (numTreesLeftToGen <= 0) continue;
            PlacedFeature placedFeature = blockPlaceFeature;
            boolean bl4 = placedFeature != null ? placedFeature.m_226377_(worldGenLevel2, context.m_159775_(), random, dir) : false;
            if (!bl4) continue;
            worldGenLevel2.m_6289_(dir, worldGenLevel2.m_8055_(dir).m_60734_());
            --numTreesLeftToGen;
            BlockState below = worldGenLevel2.m_8055_(dir.m_7495_());
            if (below.m_60713_(Blocks.f_50440_)) {
                Comparable comparable = below.m_61143_((Property)GrassBlock.f_56637_);
                Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"below.get(GrassBlock.SNOWY)");
                if (((Boolean)comparable).booleanValue()) {
                    worldGenLevel2.m_7731_(dir.m_7495_(), (BlockState)below.m_61124_((Property)GrassBlock.f_56637_, (Comparable)Boolean.valueOf(false)), 2);
                }
            }
            worldGenLevel2.m_7731_(dir.m_7494_(), Blocks.f_50016_.m_49966_(), 2);
        }
        return numTreesToGen != numTreesLeftToGen;
    }
}

