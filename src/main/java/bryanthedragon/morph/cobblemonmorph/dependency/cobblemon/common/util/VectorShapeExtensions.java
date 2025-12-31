/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.EAST;
import net.minecraft.core.Direction.NORTH;
import net.minecraft.core.Direction.SOUTH;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VectorShapeExtensions {
    VoxelShape voxelShape(Double minX, Double minY, Double minZ, Double maxX, Double maxY, Double maxZ, Direction direction) {
        public static final fMinX = when (direction) {
            NORTH -> minX
            SOUTH -> 1 - maxX
            EAST -> minZ
            else -> 1 - maxZ
        }

        public static final fMaxX = when (direction) {
            NORTH -> maxX
            SOUTH -> 1 - minX
            EAST -> maxZ
            else -> 1 - minZ
        }

        public static final fMinZ = when (direction) {
            NORTH -> minZ
            SOUTH -> 1 - maxZ
            EAST -> minX
            else -> 1 - maxX
        }

        public static final fMaxZ = when (direction) {
            NORTH -> maxZ
            SOUTH -> 1 - minZ
            EAST -> maxX
            else -> 1 - minX
        }
        return Shapes.box(fMinX, minY, fMinZ, fMaxX, maxY, fMaxZ);
    }

    fun rotateShape(from: Direction, to: Direction, shape: VoxelShape): VoxelShape {
        var shape = shape
        public static final times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4
        for (i in 0 until times) {
            var newShape = Shapes.empty();
            shape.forAllBoxes { minX, minY, minZ, maxX, maxY, maxZ -> newShape = Shapes.or(newShape, Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)) }
            shape = newShape;
        }
        return shape;
    }
}