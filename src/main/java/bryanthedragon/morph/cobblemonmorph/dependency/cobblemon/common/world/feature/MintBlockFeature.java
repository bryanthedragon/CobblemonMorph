/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.levelgen.feature.Feature
 *  net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
 *  net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0006\u001a\u00020\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J%\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/world/feature/MintBlockFeature;", "Lnet/minecraft/world/level/levelgen/feature/Feature;", "Lnet/minecraft/world/level/levelgen/feature/configurations/BlockStateConfiguration;", "Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;", "context", "", "generate", "(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", "Lnet/minecraft/world/level/WorldGenLevel;", "world", "Lnet/minecraft/core/BlockPos;", "origin", "", "getValidPositions", "(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/core/BlockPos;)Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nMintBlockFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MintBlockFeature.kt\ncom/cobblemon/mod/common/world/feature/MintBlockFeature\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1855#2,2:65\n*S KotlinDebug\n*F\n+ 1 MintBlockFeature.kt\ncom/cobblemon/mod/common/world/feature/MintBlockFeature\n*L\n39#1:65,2\n*E\n"})
public final class MintBlockFeature
extends Feature<BlockStateConfiguration> {
    public MintBlockFeature() {
        super(BlockStateConfiguration.f_67546_);
    }

    public boolean m_142674_(@NotNull FeaturePlaceContext<BlockStateConfiguration> context) {
        Intrinsics.checkNotNullParameter(context, (String)"context");
        WorldGenLevel world = context.m_159774_();
        BlockPos blockPos2 = context.m_159777_();
        BlockState blockState = ((BlockStateConfiguration)context.m_159778_()).f_67547_;
        BlockPos floor = blockPos2.m_7495_();
        if (!world.m_8055_(floor).m_204336_(BlockTags.f_144274_)) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"blockPos");
        List<BlockPos> validPlacements = this.getValidPositions(world, blockPos2);
        if (validPlacements.isEmpty()) {
            return false;
        }
        int minAge = 5;
        int maxAge = 7;
        world.m_7731_(blockPos2, (BlockState)blockState.m_61124_((Property)MintBlock.Companion.getAGE(), (Comparable)Integer.valueOf(context.m_225041_().m_216332_(minAge, maxAge))), 2);
        Iterable $this$forEach$iv = CollectionsKt.take((Iterable)CollectionsKt.shuffled((Iterable)validPlacements), (int)2);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BlockPos position = (BlockPos)element$iv;
            boolean bl = false;
            world.m_7731_(position, (BlockState)blockState.m_61124_((Property)MintBlock.Companion.getAGE(), (Comparable)Integer.valueOf(context.m_225041_().m_216332_(minAge, maxAge))), 2);
        }
        return true;
    }

    private final List<BlockPos> getValidPositions(WorldGenLevel world, BlockPos origin) {
        List validPositions = new ArrayList();
        for (int x = -1; x < 2; ++x) {
            for (int y = -1; y < 2; ++y) {
                for (int z = -1; z < 2; ++z) {
                    if (x == 0 && z == 0) continue;
                    BlockPos offsetPos = origin.m_7918_(x, y, z);
                    BlockState floorBlockState = world.m_8055_(offsetPos.m_7495_());
                    if (!world.m_46859_(offsetPos) || !floorBlockState.m_204336_(BlockTags.f_144274_)) continue;
                    Intrinsics.checkNotNullExpressionValue((Object)offsetPos, (String)"offsetPos");
                    validPositions.add(offsetPos);
                }
            }
        }
        return validPositions;
    }
}

