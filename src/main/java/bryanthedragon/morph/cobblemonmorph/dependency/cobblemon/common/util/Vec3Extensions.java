/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import org.joml.Vector3f;

public class Vec3Extensions {
    /**
     * For conversion from Vec3dto BlockPos, loses accuracy
     */
    BlockPos toBlockPos(Vec3 vec3) {
        return BlockPos.containing(this.x, this.y, this.z);
    }

    Vector3f toVec3f(Vec3 vec3) { 
        Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
    }
    Vec3 toVec3d(Vector3f vector3f) { 
        Vec3(x.toDouble(), y.toDouble(), z.toDouble())
    }
    Vector3f set(Vector3f vector3f, Vec3 vec3d) {
        x = vec3d.x.toFloat();
        y = vec3d.y.toFloat();
        z = vec3d.z.toFloat();
        return this;
    }
}