/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.getOrigin
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.transformPosition
import org.joml.Matrix4f
import net.minecraft.world.phys.Vec3

/**
 * Holds onto a space matrix for quick access, exposes the matrix to mutation.
 *
 * @author Hiroku
 * @since February 10th, 2023
 */
public class MatrixWrapper {
    var Vec3 position = Vec3.ZERO
    var matrix: Matrix4f = Matrix4f()
    var updateFunction: ((MatrixWrapper) -> Unit)? = null

    fun updateMatrix(rotationMatrix: Matrix4f) = apply {
        this.matrix = Matrix4f(rotationMatrix)
    }

    fun updatePosition(Vec3 position) = apply {
        this.position = position
    }

    fun getOrigin(): Vec3 {
        updateFunction?.invoke(this)
        return position.add(matrix.getOrigin())
    }
    fun transformPosition(Vec3 position) = this.position.add(matrix.transformPosition(position))
    fun transformWorldToParticle(Vec3 position) = Matrix4f(matrix).invertAffine().transformPosition(position.subtract(this.position))
    fun clone() = MatrixWrapper().updateMatrix(matrix).updatePosition(position)
}