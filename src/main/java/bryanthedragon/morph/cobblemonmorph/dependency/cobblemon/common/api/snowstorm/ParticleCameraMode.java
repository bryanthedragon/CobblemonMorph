/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.hamiltonProduct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.set
import com.mojang.math.Axis
import com.mojang.serialization.Codec
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.util.Mth
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt
import net.minecraft.world.phys.Vec3
import org.joml.AxisAngle4d
import org.joml.Quaternionf
import org.joml.Vector3f

public interface ParticleCameraMode : CodecMapped {
    final class Companion : ArbitrarilyMappedSerializableCompanion<ParticleCameraMode, ParticleCameraModeType>(
        keyFromString = ParticleCameraModeType::valueOf,
        stringFromKey = { it.name },
        keyFromValue = { it.type }
    ) {
        init {
            registerSubtype(ParticleCameraModeType.ROTATE_XYZ, RotateXYZCameraMode.class, RotateXYZCameraMode.CODEC)
            registerSubtype(ParticleCameraModeType.ROTATE_Y, RotateYCameraMode.class, RotateYCameraMode.CODEC)
            registerSubtype(ParticleCameraModeType.LOOK_AT_XYZ, LookAtXYZ.class, LookAtXYZ.CODEC)
            registerSubtype(ParticleCameraModeType.LOOK_AT_Y, LookAtY.class, LookAtY.CODEC)
            registerSubtype(ParticleCameraModeType.DIRECTION_X, DirectionX.class, DirectionX.CODEC)
            registerSubtype(ParticleCameraModeType.DIRECTION_Y, DirectionY.class, DirectionY.CODEC)
            registerSubtype(ParticleCameraModeType.DIRECTION_Z, DirectionZ.class, DirectionZ.CODEC)
            registerSubtype(ParticleCameraModeType.LOOK_AT_DIRECTION, LookAtDirection.class, LookAtDirection.CODEC)
            registerSubtype(ParticleCameraModeType.EMITTER_XZ_PLANE, EmitterXZPlane.class, EmitterXZPlane.CODEC)
            registerSubtype(ParticleCameraModeType.EMITTER_XY_PLANE, EmitterXYPlane.class, EmitterXYPlane.CODEC)
            registerSubtype(ParticleCameraModeType.EMITTER_YZ_PLANE, EmitterYZPlane.class, EmitterYZPlane.CODEC)
        }
    }

    val type: ParticleCameraModeType
    fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf
}

public class RotateXYZCameraMode : ParticleCameraMode {
    final class Companion {
        val CODEC: Codec<RotateXYZCameraMode> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { RotateXYZCameraMode() }
        }
    }

    override val type = ParticleCameraModeType.ROTATE_XYZ

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val i = if (angle == 0.0f) 0F else Mth.lerp(deltaTicks, prevAngle, angle)
        val q = Quaternionf(cameraAngle)
        q.hamiltonProduct(Axis.ZP.rotationDegrees(i))
        return q
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

public class RotateYCameraMode : ParticleCameraMode {
    final class Companion {
        val CODEC: Codec<RotateYCameraMode> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { RotateYCameraMode() }
        }
    }

    override val type = ParticleCameraModeType.ROTATE_Y

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val i = if (angle == 0F) 0F else Mth.lerp(deltaTicks, prevAngle, angle)
        val q2 = Axis.YP.rotationDegrees(-cameraYaw)
        q2.hamiltonProduct(Axis.ZP.rotationDegrees(i))
        q2.hamiltonProduct(Axis.YP.rotationDegrees(180F))
        return q2
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

public class LookAtXYZ : ParticleCameraMode {
    final class Companion {
        val CODEC: Codec<LookAtXYZ> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { LookAtXYZ() }
        }
    }

    override val type = ParticleCameraModeType.LOOK_AT_XYZ
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val i = if (angle == 0F) 0F else Mth.lerp(deltaTicks, prevAngle, angle)
        val rotation = Quaternionf()
        rotation.hamiltonProduct(Axis.YP.rotationDegrees(-cameraYaw - 180))
        rotation.hamiltonProduct(Axis.XP.rotationDegrees(cameraPitch))
        rotation.hamiltonProduct(Axis.ZP.rotationDegrees(i))
        return rotation
    }
}

public class LookAtY : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.LOOK_AT_Y
    final class Companion {
        val CODEC: Codec<LookAtY> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { LookAtY() }
        }
    }
    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val i = if (angle == 0F) 0F else Mth.lerp(deltaTicks, prevAngle, angle)
        val q2 = Axis.YP.rotationDegrees(-cameraYaw + 180)
        q2.hamiltonProduct(Axis.ZP.rotationDegrees(i))
        return q2
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}
public class DirectionZ : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.DIRECTION_Z
    final class Companion {
        val CODEC: Codec<DirectionZ> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { DirectionZ() }
        }
    }

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val rotation = Quaternionf(0F, 0F, 0F, 1F)
        val y = atan2(viewDirection.x, viewDirection.z)
        val x = atan2(viewDirection.y, sqrt(viewDirection.x.pow(2.0) + viewDirection.z.pow(2.0)))
        rotation.hamiltonProduct(Axis.XP.rotationDegrees(-x.toFloat()))
        rotation.hamiltonProduct(Axis.YP.rotationDegrees(y.toFloat()))
        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

public class EmitterYZPlane : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.EMITTER_YZ_PLANE
    final class Companion {
        val CODEC: Codec<EmitterYZPlane> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { EmitterYZPlane() }
        }
    }

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val rotation = Quaternionf(0F, 0F, 0F, 1F)

        val quat = AxisAngle4d(rotation)
        matrixWrapper.matrix.getRotation(quat)
        rotation.set(quat)

        rotation.hamiltonProduct(Axis.YP.rotationDegrees(180F)) // Don't worry about it.
        rotation.hamiltonProduct(Axis.YP.rotationDegrees(90F))
        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}

}

public class EmitterXZPlane : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.EMITTER_XZ_PLANE
    final class Companion {
        val CODEC: Codec<EmitterXZPlane> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { EmitterXZPlane() }
        }
    }

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val rotation = Quaternionf(0F, 0F, 0F, 1F)

        val quat = AxisAngle4d(rotation)
        matrixWrapper.matrix.getRotation(quat)
        rotation.set(quat)
        rotation.hamiltonProduct(Axis.YP.rotationDegrees(180F)) // Don't worry about it.
        rotation.hamiltonProduct(Axis.XP.rotationDegrees(-90F))
        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}

}

public class EmitterXYPlane : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.EMITTER_XY_PLANE
    final class Companion {
        val CODEC: Codec<EmitterXYPlane> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { EmitterXYPlane() }
        }
    }

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val rotation = Quaternionf(0F, 0F, 0F, 1F)

        val quat = AxisAngle4d(rotation)
        matrixWrapper.matrix.getRotation(quat)
        rotation.set(quat)

        rotation.hamiltonProduct(Axis.YP.rotationDegrees(180F)) // Don't worry about it.

        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}

}
public class DirectionY : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.DIRECTION_Y
    final class Companion {
        val CODEC: Codec<DirectionY> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { DirectionY() }
        }
    }

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val rotation = Quaternionf(0F, 0F, 0F, 1F)
        val y = atan2(viewDirection.x, viewDirection.z)
        val x = atan2(viewDirection.y, sqrt(viewDirection.x.pow(2.0) + viewDirection.z.pow(2.0)))
        rotation.hamiltonProduct(Axis.XP.rotationDegrees(x.toFloat() - PI.toFloat()/2f))
        rotation.hamiltonProduct(Axis.YP.rotationDegrees(y.toFloat() - PI.toFloat()))
        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

public class DirectionX : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.DIRECTION_X
    final class Companion {
        val CODEC: Codec<DirectionX> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { DirectionX() }
        }
    }

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        val rotation = Quaternionf(0F, 0F, 0F, 1F)
        val y = atan2(viewDirection.x, viewDirection.z)
        val z = atan2(viewDirection.y, sqrt(viewDirection.x.pow(2.0) + viewDirection.z.pow(2.0)))
        rotation.hamiltonProduct(Axis.YP.rotationDegrees(y.toFloat() - PI.toFloat()/2f))
        rotation.hamiltonProduct(Axis.ZP.rotationDegrees(z.toFloat()))
        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

/**
 * You can think of this camera mode as the one that does the following:
 *
 * - Uses the direction of motion as the particle X axis
 * - Uses a vector parallel to the camera plane and perpendicular to the direction of motion as the particle Y axis.
 *
 * The second requirement is difficult to accomplish. The way it was eventually solved is by
 * thinking of the plane generated by two specific vectors. One is the camera to the particle,
 * the other is the camera to where the particle will be after it continues moving on its
 * trajectory for some amount of time.
 *
 * That plane slices right through the camera, and so the normal vector of that plane is parallel
 * lies on the camera plane (hard to explain that one). Since the plane includes the direction vector,
 * the normal vector of that plane is perpendicular to the direction vector. Since the direction
 * vector is being used for the X axis, and the normal vector is perpendicular, we can use that as
 * the Y axis safely.
 *
 * The chicanery of this look direction is all simply to calculate the correct Y axis, use it,
 * and then use the direction vector for the X axis.
 *
 * @author Hiroku
 * @since June 29th, 2023
 */
public class LookAtDirection : ParticleCameraMode {
    override val type: ParticleCameraModeType = ParticleCameraModeType.LOOK_AT_DIRECTION
    final class Companion {
        val CODEC: Codec<LookAtDirection> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { LookAtDirection() }
        }
    }

    // Reusable shit so fewer instantiations during rendering.
    val viewDirectionF = Vector3f(0F, 0F, 0F)
    val particlePositionF = Vector3f(0F, 0F, 0F)
    val cameraPositionF = Vector3f(0F, 0F, 0F)
    val axisAngle = AxisAngle4d()

    override fun getRotation(
        matrixWrapper: MatrixWrapper,
        prevAngle: Float,
        angle: Float,
        deltaTicks: Float,
        particlePosition: Vec3,
        cameraPosition: Vec3,
        cameraAngle: Quaternionf,
        cameraYaw: Float,
        cameraPitch: Float,
        viewDirection: Vec3
    ): Quaternionf {
        viewDirectionF.set(viewDirection)
        particlePositionF.set(particlePosition)
        cameraPositionF.set(cameraPosition)

        // Perform a rotation around the axis that should later become the particle Y axis
        Quaternionf().rotateTo(
            // Camera -> Particle
            particlePositionF.sub(cameraPositionF, Vector3f()),
            // Camera -> (Particle + Direction)
            particlePositionF.add(viewDirectionF, Vector3f()).sub(cameraPositionF)
        ).get(axisAngle) // Extract the rotation into axis + angle, so we can pluck the axis out.

        // Extract the axis of rotation, it is what we want the particle's local Y axis to be.
        val correctY = Vector3f(axisAngle.x.toFloat(), axisAngle.y.toFloat(), axisAngle.z.toFloat())
        // First make X look along the direction vector
        val rotation = Quaternionf().rotateTo(Vector3f(1F, 0F, 0F), viewDirectionF)
        // Now figure out what the local Y axis is
        val currentY = Vector3f(0F, 1F, 0F).rotate(rotation)
        // Move that local Y to the correct local Y
        // Pre-multiply is because the previous transform won't affect the correct Y, but vice versa absolutely would; we don't want that.
        rotation.premul(Quaternionf().rotateTo(currentY, correctY))

        // Do the regular rotation around Z to spin the particle, same as all other modes.
        val particleAngle = if (angle == 0.0f) 0F else Mth.lerp(deltaTicks, prevAngle, angle)
        rotation.hamiltonProduct(Axis.ZP.rotationDegrees(particleAngle))

        // Minecraft in 1.21 or 1.20.4 or something fucked with vertex draw order, and it flipped everything.
        rotation.hamiltonProduct(Axis.XP.rotationDegrees(180F))

        return rotation
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}
public enum ParticleCameraModeType {
    ROTATE_XYZ,
    ROTATE_Y,
    LOOK_AT_XYZ,
    LOOK_AT_Y,
    LOOK_AT_DIRECTION,
    DIRECTION_X,
    DIRECTION_Y,
    DIRECTION_Z,
    EMITTER_XY_PLANE,
    EMITTER_XZ_PLANE,
    EMITTER_YZ_PLANE
}