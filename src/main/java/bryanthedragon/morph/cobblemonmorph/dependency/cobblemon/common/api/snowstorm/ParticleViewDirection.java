/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.EXPRESSION_CODEC
import com.mojang.serialization.Codec
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.phys.Vec3

/**
 * A type of view direction for rotating a particle's rendering.
 *
 * @author Hiroku
 * @since May 14th, 2023
 */
public interface ParticleViewDirection : CodecMapped {
    final class Companion : ArbitrarilyMappedSerializableCompanion<ParticleViewDirection, ParticleViewDirectionType>(
        keyFromValue = { it.type },
        keyFromString = ParticleViewDirectionType::valueOf,
        stringFromKey = { it.name }
    ) {
        init {
            registerSubtype(ParticleViewDirectionType.CUSTOM, CustomViewDirection.class, CustomViewDirection.CODEC)
            registerSubtype(ParticleViewDirectionType.FROM_MOTION, FromMotionViewDirection.class, FromMotionViewDirection.CODEC)
        }
    }

    val type: ParticleViewDirectionType
    fun getDirection(MoLangRuntime runtime, lastDirection: Vec3, currentVelocity: Vec3): Vec3
}

public class FromMotionViewDirection(var minDouble speed = 0.01) : ParticleViewDirection {
    final class Companion {
        val CODEC: Codec<FromMotionViewDirection> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                PrimitiveCodec.DOUBLE.fieldOf("minSpeed").forGetter { it.minSpeed }
            ).apply(instance) { _, minSpeed -> FromMotionViewDirection(minSpeed) }
        }
    }

    override val type: ParticleViewDirectionType = ParticleViewDirectionType.FROM_MOTION
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeDouble(minSpeed)
    }

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        minSpeed = buffer.readDouble()
    }

    override fun getDirection(MoLangRuntime runtime, lastDirection: Vec3, currentVelocity: Vec3): Vec3 {
        return if (currentVelocity.length() * 20 >= minSpeed) {
            currentVelocity.normalize()
        } else {
            lastDirection
        }
    }
}

public class CustomViewDirection(var direction: Triple<Expression, Expression, Expression>) : ParticleViewDirection {
    final class Companion {
        val CODEC: Codec<CustomViewDirection> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                EXPRESSION_CODEC.fieldOf("directionX").forGetter { it.direction.first },
                EXPRESSION_CODEC.fieldOf("directionY").forGetter { it.direction.second },
                EXPRESSION_CODEC.fieldOf("directionZ").forGetter { it.direction.third },
            ).apply(instance) { _, directionX, directionY, directionZ -> CustomViewDirection(Triple(directionX, directionY, directionZ)) }
        }
    }

    override val type = ParticleViewDirectionType.CUSTOM

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(direction.first.getString())
        buffer.writeString(direction.second.getString())
        buffer.writeString(direction.third.getString())
    }

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        direction = Triple(
            buffer.readString().asExpression(),
            buffer.readString().asExpression(),
            buffer.readString().asExpression()
        )
    }

    override fun getDirection(MoLangRuntime runtime, lastDirection: Vec3, currentVelocity: Vec3) = runtime.resolveVec3d(direction)
}

public enum ParticleViewDirectionType {
    CUSTOM,
    FROM_MOTION
}
