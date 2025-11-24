/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a7\u0010\u0007\u001a\u0004\u0018\u00010\u0002*\u00020\u00002\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\u0007\u0010\b\u001a\u0019\u0010\u000b\u001a\u00020\n*\u00020\u00002\u0006\u0010\t\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000b\u0010\f\u001a\u0011\u0010\r\u001a\u00020\u0005*\u00020\u0000\u00a2\u0006\u0004\b\r\u0010\u000e\u001a\u0011\u0010\u000f\u001a\u00020\u0005*\u00020\u0000\u00a2\u0006\u0004\b\u000f\u0010\u000e\u001a\u0011\u0010\u0010\u001a\u00020\u0005*\u00020\u0000\u00a2\u0006\u0004\b\u0010\u0010\u000e\u001a\u0011\u0010\u0011\u001a\u00020\u0005*\u00020\u0000\u00a2\u0006\u0004\b\u0011\u0010\u000e\u001a\u0019\u0010\u0013\u001a\u00020\u0005*\u00020\u00002\u0006\u0010\t\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a9\u0010\u001b\u001a\u00020\u001a\"\u0004\b\u0000\u0010\u0015*\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00172\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001d"}, d2={"Lnet/minecraft/world/entity/Entity;", "", "Lnet/minecraft/core/BlockPos;", "positions", "Lkotlin/Function1;", "", "filter", "closestPosition", "(Lnet/minecraft/world/entity/Entity;Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Lnet/minecraft/core/BlockPos;", "pos", "", "distanceTo", "(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;)D", "isDusk", "(Lnet/minecraft/world/entity/Entity;)Z", "isStandingOnRedSand", "isStandingOnSand", "isStandingOnSandOrRedSand", "Lnet/minecraft/world/phys/Vec3;", "setPositionSafely", "(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)Z", "T", "Lnet/minecraft/network/syncher/SynchedEntityData;", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "mutator", "", "update", "(Lnet/minecraft/network/syncher/SynchedEntityData;Lnet/minecraft/network/syncher/EntityDataAccessor;Lkotlin/jvm/functions/Function1;)V", "common"})
public final class EntityExtensionsKt {
    public static final boolean setPositionSafely(@NotNull Entity $this$setPositionSafely, @NotNull Vec3 pos) {
        int roundedY;
        Object[] target;
        Intrinsics.checkNotNullParameter((Object)$this$setPositionSafely, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Vec3 result = pos;
        Vec3 eyes = pos.m_193103_(Direction.Axis.Y, pos.f_82480_ + (double)$this$setPositionSafely.m_20192_());
        AABB box = $this$setPositionSafely.m_20191_().m_82383_(pos);
        Set conflicts = new LinkedHashSet();
        if (!$this$setPositionSafely.m_9236_().m_186434_($this$setPositionSafely, box).iterator().hasNext()) {
            $this$setPositionSafely.m_146884_(pos);
            return true;
        }
        Iterator iterator = BlockPos.m_121921_((AABB)box).iterator();
        while (iterator.hasNext()) {
            target = (Object[])iterator.next();
            BlockState blockState = $this$setPositionSafely.m_9236_().m_8055_((BlockPos)target);
            boolean collides = !blockState.m_60795_() && blockState.m_60828_((BlockGetter)$this$setPositionSafely.m_9236_(), (BlockPos)target) && Shapes.m_83157_((VoxelShape)blockState.m_60812_((BlockGetter)$this$setPositionSafely.m_9236_(), (BlockPos)target).m_83216_((double)target.m_123341_(), (double)target.m_123342_(), (double)target.m_123343_()), (VoxelShape)Shapes.m_83064_((AABB)box), (BooleanOp)BooleanOp.f_82689_);
            if (!collides) continue;
            Intrinsics.checkNotNullExpressionValue((Object)eyes, (String)"eyes");
            BlockPos x = Vec3ExtensionsKt.toBlockPos(eyes);
            block8: for (Direction direction : Direction.values()) {
                if (conflicts.contains(direction)) continue;
                Intrinsics.checkNotNullExpressionValue((Object)target, (String)"target");
                Vec3 conflict = BlockPosExtensionsKt.toVec3d((BlockPos)target);
                if (!Intrinsics.areEqual((Object)x.m_121955_(direction.m_122436_()), (Object)target)) continue;
                conflicts.add(direction);
                switch (WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
                    case 1: {
                        return false;
                    }
                    case 2: {
                        Intrinsics.checkNotNullExpressionValue((Object)result.m_82549_(new Vec3(0.0, 0.0, 1.0 + (conflict.f_82481_ - box.f_82290_ + 0.125))), (String)"result.add(Vec3d(0.0, 0.\u2026box.minZ + (1.0 / 8.0))))");
                        continue block8;
                    }
                    case 3: {
                        Intrinsics.checkNotNullExpressionValue((Object)result.m_82549_(new Vec3(0.0, 0.0, conflict.f_82481_ - box.f_82293_ - 0.125)), (String)"result.add(Vec3d(0.0, 0.\u2026box.maxZ) - (1.0 / 8.0)))");
                        continue block8;
                    }
                    case 4: {
                        Intrinsics.checkNotNullExpressionValue((Object)result.m_82549_(new Vec3(1.0 + (conflict.f_82479_ - box.f_82288_ + 0.125), 0.0, 0.0)), (String)"result.add(Vec3d(1 + (co\u2026 (1.0 / 8.0)), 0.0, 0.0))");
                        continue block8;
                    }
                    case 5: {
                        Intrinsics.checkNotNullExpressionValue((Object)result.m_82549_(new Vec3(conflict.f_82479_ - box.f_82291_ - 0.125, 0.0, 0.0)), (String)"result.add(Vec3d((confli\u2026- (1.0 / 8.0), 0.0, 0.0))");
                    }
                }
            }
        }
        box = $this$setPositionSafely.m_20191_().m_82383_(result);
        if (!$this$setPositionSafely.m_9236_().m_186434_($this$setPositionSafely, box).iterator().hasNext()) {
            $this$setPositionSafely.m_146884_(result);
            return true;
        }
        target = new Double[]{1.0, -1.0, 2.0, -2.0};
        List yChanges = CollectionsKt.listOf((Object[])target);
        double previousChange = 0.0;
        Iterator<Object> iterator2 = yChanges.iterator();
        while (iterator2.hasNext()) {
            double yChange = ((Number)iterator2.next()).doubleValue();
            box = box.m_82386_(0.0, yChange - previousChange, 0.0);
            previousChange = yChange;
            if ($this$setPositionSafely.m_9236_().m_186434_($this$setPositionSafely, box).iterator().hasNext()) continue;
            roundedY = (int)(result.f_82480_ + yChange);
            box = box.m_82386_(0.0, (double)roundedY - result.f_82480_, 0.0);
            if ($this$setPositionSafely.m_9236_().m_186434_($this$setPositionSafely, box).iterator().hasNext()) {
                Vec3 vec3 = result.m_82520_(0.0, yChange, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"result.add(0.0, yChange, 0.0)");
                result = vec3;
                $this$setPositionSafely.m_146884_(result);
                return true;
            }
            result = new Vec3(result.f_82479_, (double)roundedY, result.f_82481_);
            $this$setPositionSafely.m_146884_(result);
            return true;
        }
        previousChange = 0.0;
        box = $this$setPositionSafely.m_20191_().m_82383_(pos);
        iterator2 = yChanges.iterator();
        while (iterator2.hasNext()) {
            double yChange = ((Number)iterator2.next()).doubleValue();
            box = box.m_82386_(0.0, yChange - previousChange, 0.0);
            previousChange = yChange;
            if ($this$setPositionSafely.m_9236_().m_186434_($this$setPositionSafely, box).iterator().hasNext()) continue;
            roundedY = (int)(result.f_82480_ + yChange);
            box = box.m_82386_(0.0, (double)roundedY - result.f_82480_, 0.0);
            if ($this$setPositionSafely.m_9236_().m_186434_($this$setPositionSafely, box).iterator().hasNext()) {
                Vec3 vec3 = result.m_82520_(0.0, yChange, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"result.add(0.0, yChange, 0.0)");
                result = vec3;
                $this$setPositionSafely.m_146884_(result);
                return true;
            }
            result = new Vec3(result.f_82479_, (double)roundedY, result.f_82481_);
            $this$setPositionSafely.m_146884_(result);
            return true;
        }
        if (conflicts.size() >= 3) {
            $this$setPositionSafely.m_146884_(pos);
        }
        Vec3 resultEyes = result.m_193103_(Direction.Axis.Y, result.f_82480_ + (double)$this$setPositionSafely.m_20192_());
        AABB resultEyeBox = AABB.m_165882_((Vec3)resultEyes, (double)$this$setPositionSafely.m_20205_(), (double)1.0E-6, (double)$this$setPositionSafely.m_20205_());
        boolean collides = false;
        iterator2 = BlockPos.m_121921_((AABB)resultEyeBox).iterator();
        while (iterator2.hasNext()) {
            BlockPos target2 = (BlockPos)iterator2.next();
            BlockState blockState = $this$setPositionSafely.m_9236_().m_8055_(target2);
            collides = !blockState.m_60795_() && blockState.m_60828_((BlockGetter)$this$setPositionSafely.m_9236_(), target2) && Shapes.m_83157_((VoxelShape)blockState.m_60812_((BlockGetter)$this$setPositionSafely.m_9236_(), target2).m_83216_((double)target2.m_123341_(), (double)target2.m_123342_(), (double)target2.m_123343_()), (VoxelShape)Shapes.m_83064_((AABB)box), (BooleanOp)BooleanOp.f_82689_);
            if (!collides) continue;
        }
        if (collides) {
            $this$setPositionSafely.m_146884_(pos);
            return true;
        }
        $this$setPositionSafely.m_146884_(result);
        return true;
    }

    public static final boolean isStandingOnSandOrRedSand(@NotNull Entity $this$isStandingOnSandOrRedSand) {
        Intrinsics.checkNotNullParameter((Object)$this$isStandingOnSandOrRedSand, (String)"<this>");
        int sandDepth = 2;
        int a = 1;
        while (true) {
            BlockState sandBlockState;
            Block sandBlock;
            if (Intrinsics.areEqual((Object)(sandBlock = (sandBlockState = $this$isStandingOnSandOrRedSand.m_9236_().m_8055_($this$isStandingOnSandOrRedSand.m_20183_().m_6625_(a))).m_60734_()), (Object)Blocks.f_49992_) && !sandBlockState.m_60795_() && sandBlockState.m_60838_((BlockGetter)$this$isStandingOnSandOrRedSand.m_9236_(), $this$isStandingOnSandOrRedSand.m_20183_().m_6625_(a))) {
                return true;
            }
            if (Intrinsics.areEqual((Object)sandBlock, (Object)Blocks.f_49993_) && !sandBlockState.m_60795_() && sandBlockState.m_60838_((BlockGetter)$this$isStandingOnSandOrRedSand.m_9236_(), $this$isStandingOnSandOrRedSand.m_20183_().m_6625_(a))) {
                return true;
            }
            if (a == sandDepth) break;
            ++a;
        }
        return false;
    }

    public static final boolean isDusk(@NotNull Entity $this$isDusk) {
        Intrinsics.checkNotNullParameter((Object)$this$isDusk, (String)"<this>");
        long time = $this$isDusk.m_9236_().m_46468_() % (long)24000;
        return 12000L <= time ? time < 13001L : false;
    }

    public static final boolean isStandingOnSand(@NotNull Entity $this$isStandingOnSand) {
        Intrinsics.checkNotNullParameter((Object)$this$isStandingOnSand, (String)"<this>");
        int sandDepth = 2;
        int a = 1;
        while (true) {
            BlockState sandBlockState;
            Block sandBlock;
            if (Intrinsics.areEqual((Object)(sandBlock = (sandBlockState = $this$isStandingOnSand.m_9236_().m_8055_($this$isStandingOnSand.m_20183_().m_6625_(a))).m_60734_()), (Object)Blocks.f_49992_) && !sandBlockState.m_60795_() && sandBlockState.m_60838_((BlockGetter)$this$isStandingOnSand.m_9236_(), $this$isStandingOnSand.m_20183_().m_6625_(a))) {
                return true;
            }
            if (a == sandDepth) break;
            ++a;
        }
        return false;
    }

    public static final boolean isStandingOnRedSand(@NotNull Entity $this$isStandingOnRedSand) {
        Intrinsics.checkNotNullParameter((Object)$this$isStandingOnRedSand, (String)"<this>");
        int redSandDepth = 2;
        int i = 1;
        while (true) {
            BlockState redSandBlockState;
            Block redSandBlock;
            if (Intrinsics.areEqual((Object)(redSandBlock = (redSandBlockState = $this$isStandingOnRedSand.m_9236_().m_8055_($this$isStandingOnRedSand.m_20183_().m_6625_(i))).m_60734_()), (Object)Blocks.f_49993_) && !redSandBlockState.m_60795_() && redSandBlockState.m_60838_((BlockGetter)$this$isStandingOnRedSand.m_9236_(), $this$isStandingOnRedSand.m_20183_().m_6625_(i))) {
                return true;
            }
            if (i == redSandDepth) break;
            ++i;
        }
        return false;
    }

    public static final double distanceTo(@NotNull Entity $this$distanceTo, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)$this$distanceTo, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Vec3 difference = BlockPosExtensionsKt.toVec3d(pos).m_82546_($this$distanceTo.m_20182_());
        return difference.m_82553_();
    }

    @Nullable
    public static final BlockPos closestPosition(@NotNull Entity $this$closestPosition, @NotNull Iterable<? extends BlockPos> positions, @NotNull Function1<? super BlockPos, Boolean> filter) {
        Intrinsics.checkNotNullParameter((Object)$this$closestPosition, (String)"<this>");
        Intrinsics.checkNotNullParameter(positions, (String)"positions");
        Intrinsics.checkNotNullParameter(filter, (String)"filter");
        BlockPos closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (BlockPos blockPos2 : positions) {
            double distance;
            if (!((Boolean)filter.invoke((Object)blockPos2)).booleanValue() || !((distance = EntityExtensionsKt.distanceTo($this$closestPosition, blockPos2)) < closestDistance)) continue;
            closest = new BlockPos((Vec3i)blockPos2);
            closestDistance = distance;
        }
        return closest;
    }

    public static /* synthetic */ BlockPos closestPosition$default(Entity entity2, Iterable iterable, Function1 function1, int n, Object object) {
        if ((n & 2) != 0) {
            function1 = closestPosition.1.INSTANCE;
        }
        return EntityExtensionsKt.closestPosition(entity2, iterable, (Function1<? super BlockPos, Boolean>)function1);
    }

    public static final <T> void update(@NotNull SynchedEntityData $this$update, @NotNull EntityDataAccessor<T> data, @NotNull Function1<? super T, ? extends T> mutator) {
        Intrinsics.checkNotNullParameter((Object)$this$update, (String)"<this>");
        Intrinsics.checkNotNullParameter(data, (String)"data");
        Intrinsics.checkNotNullParameter(mutator, (String)"mutator");
        Object value2 = $this$update.m_135370_(data);
        Object newValue = mutator.invoke(value2);
        if (!Intrinsics.areEqual((Object)value2, (Object)newValue)) {
            $this$update.m_135381_(data, newValue);
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.EAST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

