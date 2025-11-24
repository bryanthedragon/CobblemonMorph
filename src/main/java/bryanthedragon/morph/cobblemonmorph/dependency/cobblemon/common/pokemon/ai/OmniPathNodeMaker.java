/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Plane
 *  net.minecraft.core.Vec3i
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.block.BaseRailBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.level.pathfinder.Target
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\bQ\u0010\u001eJ%\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ7\u0010\u0011\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0006H\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012J\r\u0010\u0013\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0013\u0010\u0014J\r\u0010\u0015\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0015\u0010\u0014J\u0015\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001a\u001a\u00020\u000b\u00a2\u0006\u0004\b\u001a\u0010\u0014J\r\u0010\u001b\u001a\u00020\u000b\u00a2\u0006\u0004\b\u001b\u0010\u0014J\u000f\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJs\u0010$\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00022\u0006\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\"2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b$\u0010%J/\u0010&\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b&\u0010'J)\u0010*\u001a\u0004\u0018\u00010)2\u0006\u0010\u0003\u001a\u00020(2\u0006\u0010\u0004\u001a\u00020(2\u0006\u0010\u0005\u001a\u00020(H\u0016\u00a2\u0006\u0004\b*\u0010+J)\u0010*\u001a\u0004\u0018\u00010,2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b*\u0010-J/\u00100\u001a\u0004\u0018\u00010\u00062\u0006\u0010/\u001a\u00020.2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b0\u00101J\u001f\u00100\u001a\u0004\u0018\u00010\u00062\u0006\u0010/\u001a\u00020.2\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b0\u00102J9\u00100\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u00103\u001a\u00020.H\u0016\u00a2\u0006\u0004\b0\u00104J\u0011\u00105\u001a\u0004\u0018\u00010,H\u0016\u00a2\u0006\u0004\b5\u00106J'\u0010:\u001a\u00020\u00022\u000e\u00108\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010,072\u0006\u00109\u001a\u00020,H\u0016\u00a2\u0006\u0004\b:\u0010;J\u0017\u0010=\u001a\u00020\u000b2\b\u0010<\u001a\u0004\u0018\u00010,\u00a2\u0006\u0004\b=\u0010>J\u001f\u0010A\u001a\u00020\u001c2\u0006\u0010@\u001a\u00020?2\u0006\u0010/\u001a\u00020.H\u0016\u00a2\u0006\u0004\bA\u0010BJ/\u0010D\u001a\u00020\u000b2\b\u0010<\u001a\u0004\u0018\u00010,2\u0016\u0010C\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010,07\"\u0004\u0018\u00010,\u00a2\u0006\u0004\bD\u0010EJ\u0015\u0010G\u001a\u00020\u000b2\u0006\u0010F\u001a\u00020\u0006\u00a2\u0006\u0004\bG\u0010HR\"\u0010I\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bI\u0010J\u001a\u0004\bK\u0010\u0014\"\u0004\bL\u0010MR\u001a\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00060N8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bO\u0010P\u00a8\u0006R"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/OmniPathNodeMaker;", "Lnet/minecraft/world/level/pathfinder/NodeEvaluator;", "", "x", "y", "z", "Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "addPathNodePos", "(III)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "Lnet/minecraft/world/level/BlockGetter;", "world", "", "canOpenDoors", "canEnterOpenDoors", "Lnet/minecraft/core/BlockPos;", "pos", "type", "adjustNodeType", "(Lnet/minecraft/world/level/BlockGetter;ZZLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/BlockPathTypes;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "canFly", "()Z", "canSwimInWater", "Lnet/minecraft/world/level/material/FluidState;", "fluidState", "canSwimUnderFluid", "(Lnet/minecraft/world/level/material/FluidState;)Z", "canSwimUnderlava", "canWalk", "", "clear", "()V", "sizeX", "sizeY", "sizeZ", "Ljava/util/EnumSet;", "nearbyTypes", "findNearbyNodeTypes", "(Lnet/minecraft/world/level/BlockGetter;IIIIIIZZLjava/util/EnumSet;Lnet/minecraft/world/level/pathfinder/BlockPathTypes;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "getDefaultNodeType", "(Lnet/minecraft/world/level/BlockGetter;III)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "", "Lnet/minecraft/world/level/pathfinder/Target;", "getNode", "(DDD)Lnet/minecraft/world/level/pathfinder/Target;", "Lnet/minecraft/world/level/pathfinder/Node;", "(III)Lnet/minecraft/world/level/pathfinder/Node;", "Lnet/minecraft/world/entity/Mob;", "entity", "getNodeType", "(Lnet/minecraft/world/entity/Mob;III)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "mob", "(Lnet/minecraft/world/level/BlockGetter;IIILnet/minecraft/world/entity/Mob;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", "getStart", "()Lnet/minecraft/world/level/pathfinder/Node;", "", "successors", "node", "getSuccessors", "([Lnet/minecraft/world/level/pathfinder/Node;Lnet/minecraft/world/level/pathfinder/Node;)I", "pathNode", "hasNotVisited", "(Lnet/minecraft/world/level/pathfinder/Node;)Z", "Lnet/minecraft/world/level/PathNavigationRegion;", "cachedWorld", "init", "(Lnet/minecraft/world/level/PathNavigationRegion;Lnet/minecraft/world/entity/Mob;)V", "borderNodes", "isAccessibleDiagonal", "(Lnet/minecraft/world/level/pathfinder/Node;[Lnet/minecraft/world/level/pathfinder/Node;)Z", "pathNodeType", "isValidPathNodeType", "(Lnet/minecraft/world/level/pathfinder/BlockPathTypes;)Z", "canPathThroughFire", "Z", "getCanPathThroughFire", "setCanPathThroughFire", "(Z)V", "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;", "nodePosToType", "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nOmniPathNodeMaker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 OmniPathNodeMaker.kt\ncom/cobblemon/mod/common/pokemon/ai/OmniPathNodeMaker\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,428:1\n12541#2,2:429\n1#3:431\n*S KotlinDebug\n*F\n+ 1 OmniPathNodeMaker.kt\ncom/cobblemon/mod/common/pokemon/ai/OmniPathNodeMaker\n*L\n195#1:429,2\n*E\n"})
public final class OmniPathNodeMaker
extends NodeEvaluator {
    @NotNull
    private final Long2ObjectMap<BlockPathTypes> nodePosToType = (Long2ObjectMap)new Long2ObjectOpenHashMap();
    private boolean canPathThroughFire;

    public final boolean getCanPathThroughFire() {
        return this.canPathThroughFire;
    }

    public final void setCanPathThroughFire(boolean bl) {
        this.canPathThroughFire = bl;
    }

    public void m_6028_(@NotNull PathNavigationRegion cachedWorld, @NotNull Mob entity2) {
        Intrinsics.checkNotNullParameter((Object)cachedWorld, (String)"cachedWorld");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        super.m_6028_(cachedWorld, entity2);
        this.nodePosToType.clear();
    }

    public void m_6802_() {
        super.m_6802_();
        this.nodePosToType.clear();
    }

    @Nullable
    public Target m_7568_(double x, double y, double z) {
        return this.m_230615_(super.m_5676_(Mth.m_14107_((double)x), Mth.m_14107_((double)(y + 0.5)), Mth.m_14107_((double)z)));
    }

    @Nullable
    public Node m_7171_() {
        int x = Mth.m_14107_((double)this.f_77313_.m_20191_().f_82288_);
        int y = Mth.m_14107_((double)(this.f_77313_.m_20191_().f_82289_ + 0.5));
        int z = Mth.m_14107_((double)this.f_77313_.m_20191_().f_82290_);
        Node node = super.m_5676_(x, y, z);
        Mob mob = this.f_77313_;
        Intrinsics.checkNotNullExpressionValue((Object)mob, (String)"entity");
        BlockPos blockPos2 = node.m_77288_();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"node.blockPos");
        node.f_77282_ = this.getNodeType(mob, blockPos2);
        node.f_77281_ = this.f_77313_.m_21439_(node.f_77282_);
        return node;
    }

    @Nullable
    public final BlockPathTypes getNodeType(@NotNull Mob entity2, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return this.getNodeType(entity2, pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
    }

    @Nullable
    public final BlockPathTypes getNodeType(@NotNull Mob entity2, int x, int y, int z) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        return (BlockPathTypes)this.nodePosToType.computeIfAbsent(BlockPos.m_121882_((int)x, (int)y, (int)z), arg_0 -> OmniPathNodeMaker.getNodeType$lambda$0(this, x, y, z, entity2, arg_0));
    }

    public int m_6065_(@NotNull Node[] successors, @NotNull Node node) {
        Node pathNode2;
        Intrinsics.checkNotNullParameter((Object)successors, (String)"successors");
        Intrinsics.checkNotNullParameter((Object)node, (String)"node");
        int i = 0;
        EnumMap map = Maps.newEnumMap(Direction.class);
        EnumMap upperMap = Maps.newEnumMap(Direction.class);
        EnumMap lowerMap = Maps.newEnumMap(Direction.class);
        Mob mob = this.f_77313_;
        Intrinsics.checkNotNullExpressionValue((Object)mob, (String)"entity");
        Entity entity2 = (Entity)mob;
        BlockPos blockPos2 = node.m_77288_().m_7494_();
        Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"node.blockPos.up()");
        boolean upIsOpen = WorldExtensionsKt.canFit(entity2, blockPos2);
        for (Direction direction : Direction.values()) {
            Node pathNode;
            if (this.m_5676_(node.f_77271_ + direction.m_122429_(), node.f_77272_ + direction.m_122430_(), node.f_77273_ + direction.m_122431_()) == null) continue;
            Intrinsics.checkNotNullExpressionValue((Object)map, (String)"map");
            ((Map)map).put(direction, pathNode);
            if (!this.hasNotVisited(pathNode)) continue;
            successors[i++] = pathNode;
        }
        Iterator iterator = Direction.Plane.HORIZONTAL.iterator();
        Intrinsics.checkNotNullExpressionValue((Object)iterator, (String)"HORIZONTAL.iterator()");
        Object object = iterator;
        while (object.hasNext()) {
            int z;
            Direction direction = (Direction)object.next();
            Direction direction2 = direction.m_122427_();
            int x = node.f_77271_ + direction.m_122429_() + direction2.m_122429_();
            if (this.m_5676_(x, node.f_77272_, z = node.f_77273_ + direction.m_122431_() + direction2.m_122431_()) == null) continue;
            Node[] nodeArray = new Node[]{map.get(direction), map.get(direction2)};
            if (!this.isAccessibleDiagonal(pathNode2, nodeArray) && (node.f_77282_ != BlockPathTypes.BLOCKED || pathNode2.f_77279_)) continue;
            successors[i++] = pathNode2;
        }
        Iterator iterator2 = Direction.Plane.HORIZONTAL.iterator();
        Intrinsics.checkNotNullExpressionValue((Object)iterator2, (String)"HORIZONTAL.iterator()");
        object = iterator2;
        while (object.hasNext()) {
            Node pathNode22;
            Direction direction = (Direction)object.next();
            if (this.m_5676_(node.f_77271_ + direction.m_122429_(), node.f_77272_ + 1, node.f_77273_ + direction.m_122431_()) == null || !upIsOpen || !this.hasNotVisited(pathNode22)) continue;
            successors[i++] = pathNode22;
            Intrinsics.checkNotNullExpressionValue((Object)upperMap, (String)"upperMap");
            ((Map)upperMap).put(direction, pathNode22);
        }
        Iterator iterator3 = Direction.Plane.HORIZONTAL.iterator();
        Intrinsics.checkNotNullExpressionValue((Object)iterator3, (String)"HORIZONTAL.iterator()");
        object = iterator3;
        while (object.hasNext()) {
            Node pathNode23;
            Direction direction = (Direction)object.next();
            Direction direction2 = direction.m_122427_();
            if (this.m_5676_(node.f_77271_ + direction.m_122429_() + direction2.m_122429_(), node.f_77272_ + 1, node.f_77273_ + direction.m_122431_() + direction2.m_122431_()) == null) continue;
            Node[] z = new Node[]{upperMap.get(direction), upperMap.get(direction2)};
            if (!this.isAccessibleDiagonal(pathNode23, z)) continue;
            successors[i++] = pathNode23;
        }
        BlockPos.MutableBlockPos connectingBlockPos = new BlockPos.MutableBlockPos();
        Iterator iterator4 = Direction.Plane.HORIZONTAL.iterator();
        Intrinsics.checkNotNullExpressionValue((Object)iterator4, (String)"HORIZONTAL.iterator()");
        Iterator direction = iterator4;
        while (direction.hasNext()) {
            Direction direction2 = (Direction)direction.next();
            connectingBlockPos.m_122190_((Vec3i)node.m_77288_().m_121955_(direction2.m_122436_()));
            BlockState blockState = this.f_77312_.m_8055_((BlockPos)connectingBlockPos);
            boolean traversableByTangent = blockState.m_60647_((BlockGetter)this.f_77312_, (BlockPos)connectingBlockPos, PathComputationType.AIR);
            if (this.m_5676_(node.f_77271_ + direction2.m_122429_(), node.f_77272_ - 1, node.f_77273_ + direction2.m_122431_()) == null || !this.hasNotVisited(pathNode2) || !traversableByTangent) continue;
            successors[i++] = pathNode2;
            Intrinsics.checkNotNullExpressionValue((Object)lowerMap, (String)"lowerMap");
            ((Map)lowerMap).put(direction2, pathNode2);
        }
        Iterator iterator5 = Direction.Plane.HORIZONTAL.iterator();
        Intrinsics.checkNotNullExpressionValue((Object)iterator5, (String)"HORIZONTAL.iterator()");
        direction = iterator5;
        while (direction.hasNext()) {
            Node pathNode24;
            Direction direction3 = (Direction)direction.next();
            Direction direction2 = direction3.m_122427_();
            if (this.m_5676_(node.f_77271_ + direction3.m_122429_() + direction2.m_122429_(), node.f_77272_ - 1, node.f_77273_ + direction3.m_122431_() + direction2.m_122431_()) == null) continue;
            pathNode2 = new Node[]{lowerMap.get(direction3), lowerMap.get(direction2)};
            if (!this.isAccessibleDiagonal(pathNode24, (Node[])pathNode2)) continue;
            successors[i++] = pathNode24;
        }
        if (this.f_77313_.m_21439_(node.f_77282_) < 0.0f && i > 1) {
            double x = this.f_77313_.m_20191_().f_82288_;
            double y = this.f_77313_.m_20191_().f_82289_ + 0.5;
            double z = this.f_77313_.m_20191_().f_82290_;
            Vec3 pos = new Vec3(x, y, z);
            Node node2 = successors[0];
            Intrinsics.checkNotNull((Object)node2);
            Node closestSuccessor = node2;
            double closestDistance = closestSuccessor.m_164701_().m_82520_(0.5, 0.0, 0.5).m_82554_(pos);
            for (int n = 1; n < i; ++n) {
                Node next;
                Intrinsics.checkNotNull((Object)successors[n]);
                double nextDist = next.m_164701_().m_82520_(0.5, 0.0, 0.5).m_82554_(pos);
                if (!(nextDist < closestDistance)) continue;
                closestSuccessor = next;
                closestDistance = nextDist;
            }
            successors[0] = closestSuccessor;
            i = 1;
        }
        return i;
    }

    public final boolean hasNotVisited(@Nullable Node pathNode) {
        return pathNode != null && !pathNode.f_77279_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isAccessibleDiagonal(@Nullable Node pathNode, Node ... borderNodes) {
        Intrinsics.checkNotNullParameter((Object)borderNodes, (String)"borderNodes");
        if (!this.hasNotVisited(pathNode)) return false;
        Node[] $this$all$iv = borderNodes;
        boolean $i$f$all = false;
        int n = 0;
        int n2 = $this$all$iv.length;
        while (n < n2) {
            Node element$iv;
            Node it = element$iv = $this$all$iv[n];
            boolean bl = false;
            if (it == null) return false;
            if (!(it.f_77281_ >= 0.0f)) return false;
            boolean bl2 = true;
            if (!bl2) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public final boolean isValidPathNodeType(@NotNull BlockPathTypes pathNodeType) {
        Intrinsics.checkNotNullParameter((Object)pathNodeType, (String)"pathNodeType");
        return (pathNodeType == BlockPathTypes.BREACH || pathNodeType == BlockPathTypes.WATER || pathNodeType == BlockPathTypes.WATER_BORDER) && this.canSwimInWater() ? true : (pathNodeType == BlockPathTypes.OPEN && this.canFly() ? true : pathNodeType == BlockPathTypes.WALKABLE && (this.canWalk() || this.canFly()));
    }

    @Nullable
    protected Node m_5676_(int x, int y, int z) {
        float nodePenalty = 0.0f;
        Node pathNode = null;
        BlockPathTypes pathNodeType = this.addPathNodePos(x, y, z);
        if (this.isValidPathNodeType(pathNodeType)) {
            float f;
            float it = f = this.f_77313_.m_21439_(pathNodeType);
            boolean bl = false;
            nodePenalty = it;
            if (f >= 0.0f) {
                Node node;
                Node it2 = node = super.m_5676_(x, y, z);
                boolean bl2 = false;
                pathNode = it2;
                if (node != null) {
                    Intrinsics.checkNotNull((Object)pathNode);
                    pathNode.f_77282_ = pathNodeType;
                    pathNode.f_77281_ = RangesKt.coerceAtLeast((float)pathNode.f_77281_, (float)nodePenalty);
                }
            }
        }
        return pathNode;
    }

    @NotNull
    public final BlockPathTypes addPathNodePos(int x, int y, int z) {
        Object object = this.nodePosToType.computeIfAbsent(BlockPos.m_121882_((int)x, (int)y, (int)z), arg_0 -> OmniPathNodeMaker.addPathNodePos$lambda$4(this, x, y, z, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"nodePosToType.computeIfA\u2026orld, x, y, z, entity) })");
        return (BlockPathTypes)object;
    }

    @NotNull
    public BlockPathTypes m_8086_(@NotNull BlockGetter world, int x, int y, int z) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        BlockPos pos = new BlockPos(x, y, z);
        BlockPos below = new BlockPos(x, y - 1, z);
        BlockState blockState = world.m_8055_(pos);
        BlockState blockStateBelow = world.m_8055_(below);
        boolean belowSolid = blockStateBelow.m_280296_();
        boolean isWater = blockState.m_60819_().m_205070_(FluidTags.f_13131_);
        boolean isLava = blockState.m_60819_().m_205070_(FluidTags.f_13132_);
        FluidState fluidState = blockState.m_60819_();
        Intrinsics.checkNotNullExpressionValue((Object)fluidState, (String)"blockState.fluidState");
        boolean canBreatheUnderFluid = this.canSwimUnderFluid(fluidState);
        boolean solid = blockState.m_280296_();
        BlockPathTypes figuredNode = blockStateBelow.m_204336_(BlockTags.f_13039_) || blockStateBelow.m_204336_(BlockTags.f_13032_) || blockStateBelow.m_60734_() instanceof FenceGateBlock && (Boolean)blockStateBelow.m_61143_((Property)FenceGateBlock.f_53341_) == false ? BlockPathTypes.FENCE : (isWater && belowSolid && !this.canSwimInWater() && canBreatheUnderFluid ? BlockPathTypes.WALKABLE : (isWater || isLava && this.canSwimUnderlava() ? BlockPathTypes.WATER : (!solid && belowSolid ? BlockPathTypes.WALKABLE : (!solid && !belowSolid ? BlockPathTypes.OPEN : BlockPathTypes.BLOCKED))));
        return this.adjustNodeType(world, this.f_77319_, this.f_77318_, below, figuredNode);
    }

    @Nullable
    public BlockPathTypes m_7209_(@NotNull BlockGetter world, int x, int y, int z, @NotNull Mob mob) {
        BlockPathTypes blockPathTypes;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)mob, (String)"mob");
        EnumSet<BlockPathTypes> set2 = EnumSet.noneOf(BlockPathTypes.class);
        int sizeX = (int)(mob.m_20191_().f_82291_ - mob.m_20191_().f_82288_) + 1;
        int sizeY = (int)(mob.m_20191_().f_82292_ - mob.m_20191_().f_82289_) + 1;
        int sizeZ = (int)(mob.m_20191_().f_82293_ - mob.m_20191_().f_82290_) + 1;
        Intrinsics.checkNotNullExpressionValue(set2, (String)"set");
        BlockPathTypes type = this.findNearbyNodeTypes(world, x, y, z, sizeX, sizeY, sizeZ, this.f_77319_, this.f_77318_, set2, BlockPathTypes.BLOCKED, new BlockPos(x, y, z));
        if (set2.contains(BlockPathTypes.DAMAGE_CAUTIOUS)) {
            return BlockPathTypes.DAMAGE_CAUTIOUS;
        }
        if (set2.contains(BlockPathTypes.DANGER_OTHER)) {
            return BlockPathTypes.DANGER_OTHER;
        }
        if (set2.contains(BlockPathTypes.FENCE)) {
            blockPathTypes = BlockPathTypes.FENCE;
        } else if (set2.contains(BlockPathTypes.UNPASSABLE_RAIL)) {
            blockPathTypes = BlockPathTypes.UNPASSABLE_RAIL;
        } else if (set2.contains(BlockPathTypes.DAMAGE_OTHER)) {
            blockPathTypes = BlockPathTypes.DAMAGE_OTHER;
        } else {
            BlockPathTypes pathNodeType2 = BlockPathTypes.BLOCKED;
            Iterator iterator = set2.iterator();
            Intrinsics.checkNotNullExpressionValue(iterator, (String)"set.iterator()");
            Iterator nearbyTypeIterator = iterator;
            while (nearbyTypeIterator.hasNext()) {
                BlockPathTypes nearbyType = (BlockPathTypes)nearbyTypeIterator.next();
                if (mob.m_21439_(nearbyType) < 0.0f) {
                    return nearbyType;
                }
                if (mob.m_21439_(nearbyType) > mob.m_21439_(pathNodeType2) || nearbyType == BlockPathTypes.WALKABLE) {
                    pathNodeType2 = nearbyType;
                    continue;
                }
                if (type != BlockPathTypes.WATER || nearbyType != BlockPathTypes.WATER) continue;
                pathNodeType2 = BlockPathTypes.WATER;
            }
            if (type == BlockPathTypes.OPEN && mob.m_21439_(pathNodeType2) == 0.0f && sizeX <= 1) {
                blockPathTypes = BlockPathTypes.OPEN;
            } else {
                BlockPathTypes blockPathTypes2 = pathNodeType2;
                blockPathTypes = blockPathTypes2;
                Intrinsics.checkNotNull((Object)blockPathTypes2);
            }
        }
        return blockPathTypes;
    }

    @NotNull
    public final BlockPathTypes findNearbyNodeTypes(@NotNull BlockGetter world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors, @NotNull EnumSet<BlockPathTypes> nearbyTypes, @NotNull BlockPathTypes type, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter(nearbyTypes, (String)"nearbyTypes");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockPathTypes type2 = type;
        for (int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                for (int k = 0; k < sizeZ; ++k) {
                    int l = i + x;
                    int m = j + y;
                    int n = k + z;
                    BlockPathTypes pathNodeType = this.m_8086_(world, l, m, n);
                    if (i == 0 && j == 0 && k == 0) {
                        type2 = pathNodeType;
                    }
                    nearbyTypes.add(pathNodeType);
                }
            }
        }
        return type2;
    }

    @NotNull
    protected final BlockPathTypes adjustNodeType(@NotNull BlockGetter world, boolean canOpenDoors, boolean canEnterOpenDoors, @NotNull BlockPos pos, @NotNull BlockPathTypes type) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        BlockState blockState = world.m_8055_(pos);
        Block block = blockState.m_60734_();
        if (blockState.m_60713_(Blocks.f_50128_) || blockState.m_60713_(Blocks.f_50685_)) {
            return BlockPathTypes.DANGER_OTHER;
        }
        if (WalkNodeEvaluator.m_77622_((BlockState)blockState) && !this.canPathThroughFire) {
            return BlockPathTypes.DANGER_FIRE;
        }
        if (world.m_6425_(pos).m_205070_(FluidTags.f_13131_)) {
            return BlockPathTypes.WATER_BORDER;
        }
        if (blockState.m_60713_(Blocks.f_50070_) || blockState.m_60713_(Blocks.f_152588_)) {
            return BlockPathTypes.DAMAGE_CAUTIOUS;
        }
        return type == BlockPathTypes.DOOR_WOOD_CLOSED && canOpenDoors && canEnterOpenDoors ? BlockPathTypes.WALKABLE_DOOR : (type == BlockPathTypes.DOOR_OPEN && !canEnterOpenDoors ? BlockPathTypes.BLOCKED : (type == BlockPathTypes.RAIL && !(block instanceof BaseRailBlock) && !(world.m_8055_(pos.m_7495_()).m_60734_() instanceof BaseRailBlock) ? BlockPathTypes.UNPASSABLE_RAIL : (type == BlockPathTypes.LEAVES ? BlockPathTypes.BLOCKED : type)));
    }

    public final boolean canWalk() {
        boolean bl;
        if (this.f_77313_ instanceof PokemonEntity) {
            Mob mob = this.f_77313_;
            Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
            bl = ((PokemonEntity)mob).getBehaviour().getMoving().getWalk().getCanWalk();
        } else {
            bl = true;
        }
        return bl;
    }

    public final boolean canSwimInWater() {
        boolean bl;
        if (this.f_77313_ instanceof PokemonEntity) {
            Mob mob = this.f_77313_;
            Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
            bl = ((PokemonEntity)mob).getBehaviour().getMoving().getSwim().getCanSwimInWater();
        } else {
            bl = false;
        }
        return bl;
    }

    public final boolean canSwimUnderlava() {
        boolean bl;
        if (this.f_77313_ instanceof PokemonEntity) {
            Mob mob = this.f_77313_;
            Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
            bl = ((PokemonEntity)mob).getBehaviour().getMoving().getSwim().getCanBreatheUnderlava();
        } else {
            bl = false;
        }
        return bl;
    }

    public final boolean canSwimUnderFluid(@NotNull FluidState fluidState) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)fluidState, (String)"fluidState");
        if (this.f_77313_ instanceof PokemonEntity) {
            if (fluidState.m_205070_(FluidTags.f_13132_)) {
                Mob mob = this.f_77313_;
                Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
                bl = ((PokemonEntity)mob).getBehaviour().getMoving().getSwim().getCanBreatheUnderlava();
            } else if (fluidState.m_205070_(FluidTags.f_13131_)) {
                Mob mob = this.f_77313_;
                Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
                bl = ((PokemonEntity)mob).getBehaviour().getMoving().getSwim().getCanBreatheUnderwater();
            } else {
                bl = false;
            }
        } else {
            bl = false;
        }
        return bl;
    }

    public final boolean canFly() {
        boolean bl;
        if (this.f_77313_ instanceof PokemonEntity) {
            Mob mob = this.f_77313_;
            Intrinsics.checkNotNull((Object)mob, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
            bl = ((PokemonEntity)mob).getBehaviour().getMoving().getFly().getCanFly();
        } else {
            bl = false;
        }
        return bl;
    }

    private static final BlockPathTypes getNodeType$lambda$0(OmniPathNodeMaker this$0, int $x, int $y, int $z, Mob $entity, long it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$entity, (String)"$entity");
        PathNavigationRegion pathNavigationRegion = this$0.f_77312_;
        Intrinsics.checkNotNullExpressionValue((Object)pathNavigationRegion, (String)"cachedWorld");
        return this$0.m_7209_((BlockGetter)pathNavigationRegion, $x, $y, $z, $entity);
    }

    private static final BlockPathTypes addPathNodePos$lambda$4(OmniPathNodeMaker this$0, int $x, int $y, int $z, long it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        PathNavigationRegion pathNavigationRegion = this$0.f_77312_;
        Intrinsics.checkNotNullExpressionValue((Object)pathNavigationRegion, (String)"cachedWorld");
        BlockGetter blockGetter = (BlockGetter)pathNavigationRegion;
        Mob mob = this$0.f_77313_;
        Intrinsics.checkNotNullExpressionValue((Object)mob, (String)"entity");
        return this$0.m_7209_(blockGetter, $x, $y, $z, mob);
    }
}

