/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.critereon.BlockPredicate
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition.MultiblockCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u000f\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/multiblock/condition/BlockRelativeCondition;", "Lcom/cobblemon/mod/common/api/multiblock/condition/MultiblockCondition;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "box", "", "test", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/shapes/VoxelShape;)Z", "", "Lnet/minecraft/core/Direction;", "directionsToCheck", "[Lnet/minecraft/core/Direction;", "getDirectionsToCheck", "()[Lnet/minecraft/core/Direction;", "Lnet/minecraft/advancements/critereon/BlockPredicate;", "relToBlock", "Lnet/minecraft/advancements/critereon/BlockPredicate;", "getRelToBlock", "()Lnet/minecraft/advancements/critereon/BlockPredicate;", "targetBlock", "getTargetBlock", "<init>", "(Lnet/minecraft/advancements/critereon/BlockPredicate;Lnet/minecraft/advancements/critereon/BlockPredicate;[Lnet/minecraft/core/Direction;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBlockRelativeCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockRelativeCondition.kt\ncom/cobblemon/mod/common/api/multiblock/condition/BlockRelativeCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,47:1\n766#2:48\n857#2,2:49\n1855#2:51\n1856#2:54\n13579#3,2:52\n*S KotlinDebug\n*F\n+ 1 BlockRelativeCondition.kt\ncom/cobblemon/mod/common/api/multiblock/condition/BlockRelativeCondition\n*L\n35#1:48\n35#1:49,2\n36#1:51\n36#1:54\n37#1:52,2\n*E\n"})
public final class BlockRelativeCondition
implements MultiblockCondition {
    @NotNull
    private final BlockPredicate relToBlock;
    @NotNull
    private final BlockPredicate targetBlock;
    @NotNull
    private final Direction[] directionsToCheck;

    public BlockRelativeCondition(@NotNull BlockPredicate relToBlock, @NotNull BlockPredicate targetBlock, @NotNull Direction[] directionsToCheck) {
        Intrinsics.checkNotNullParameter((Object)relToBlock, (String)"relToBlock");
        Intrinsics.checkNotNullParameter((Object)targetBlock, (String)"targetBlock");
        Intrinsics.checkNotNullParameter((Object)directionsToCheck, (String)"directionsToCheck");
        this.relToBlock = relToBlock;
        this.targetBlock = targetBlock;
        this.directionsToCheck = directionsToCheck;
    }

    public /* synthetic */ BlockRelativeCondition(BlockPredicate blockPredicate, BlockPredicate blockPredicate2, Direction[] directionArray, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            directionArray = Direction.values();
        }
        this(blockPredicate, blockPredicate2, directionArray);
    }

    @NotNull
    public final BlockPredicate getRelToBlock() {
        return this.relToBlock;
    }

    @NotNull
    public final BlockPredicate getTargetBlock() {
        return this.targetBlock;
    }

    @NotNull
    public final Direction[] getDirectionsToCheck() {
        return this.directionsToCheck;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean test(@NotNull ServerLevel world, @NotNull VoxelShape box) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        Iterable $this$filter$iv = MiscUtilsKt.blockPositionsAsList(box);
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            BlockPos it = (BlockPos)element$iv$iv;
            boolean bl = false;
            if (!this.relToBlock.m_17914_(world, it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List relToBlockBlockPositions = (List)destination$iv$iv;
        Iterable $this$forEach$iv = relToBlockBlockPositions;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BlockPos pos = (BlockPos)element$iv;
            boolean bl = false;
            Direction[] $this$forEach$iv2 = this.directionsToCheck;
            boolean $i$f$forEach2 = false;
            int n = $this$forEach$iv2.length;
            for (int i = 0; i < n; ++i) {
                Direction element$iv2;
                Direction it = element$iv2 = $this$forEach$iv2[i];
                boolean bl2 = false;
                if (!this.targetBlock.m_17914_(world, pos.m_121945_(it))) continue;
                return true;
            }
        }
        return false;
    }
}

