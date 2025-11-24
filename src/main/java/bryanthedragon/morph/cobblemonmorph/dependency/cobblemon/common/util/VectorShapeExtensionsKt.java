/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Direction
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0016\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aE\u0010\n\u001a\u00020\t2\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2={"", "minX", "minY", "minZ", "maxX", "maxY", "maxZ", "Lnet/minecraft/core/Direction;", "direction", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "voxelShape", "(DDDDDDLnet/minecraft/core/Direction;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "common"})
public final class VectorShapeExtensionsKt {
    @NotNull
    public static final VoxelShape voxelShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, @NotNull Direction direction) {
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        double fMinX = switch (WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1 -> minX;
            case 2 -> 1.0 - maxX;
            case 3 -> minZ;
            default -> 1.0 - maxZ;
        };
        double fMaxX = switch (WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1 -> maxX;
            case 2 -> 1.0 - minX;
            case 3 -> maxZ;
            default -> 1.0 - minZ;
        };
        double fMinZ = switch (WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1 -> minZ;
            case 2 -> 1.0 - maxZ;
            case 3 -> minX;
            default -> 1.0 - maxX;
        };
        double fMaxZ = switch (WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1 -> maxZ;
            case 2 -> 1.0 - minZ;
            case 3 -> maxX;
            default -> 1.0 - minX;
        };
        VoxelShape voxelShape = Shapes.m_83048_((double)fMinX, (double)minY, (double)fMinZ, (double)fMaxX, (double)maxY, (double)fMaxZ);
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"cuboid(fMinX, minY, fMinZ, fMaxX, maxY, fMaxZ)");
        return voxelShape;
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

