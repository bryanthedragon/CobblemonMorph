/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import com.bedrockk.molang.ast.NumberExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.EXPRESSION_CODEC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.transformDirection
import com.mojang.serialization.Codec
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.phys.Vec3

public interface ParticleMotion : CodecMapped {
    final class Companion : ArbitrarilyMappedSerializableCompanion<ParticleMotion, ParticleMotionType>(
        keyFromValue = { it.type },
        keyFromString = ParticleMotionType::valueOf,
        stringFromKey = { it.name }
    ) {
        init {
            registerSubtype(ParticleMotionType.DYNAMIC, DynamicParticleMotion.class, DynamicParticleMotion.CODEC)
            registerSubtype(ParticleMotionType.STATIC, StaticParticleMotion.class, StaticParticleMotion.CODEC)
            registerSubtype(ParticleMotionType.PARAMETRIC, ParametricParticleMotion.class, ParametricParticleMotion.CODEC)
        }
    }

    val type: ParticleMotionType
    fun getInitialVelocity(MoLangRuntime runtime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3): Vec3
    fun getVelocity(MoLangRuntime runtime, particle: SnowstormParticle, velocity: Vec3): Vec3
    fun getParticleDirection(MoLangRuntime runtime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float): Vec3
}

public enum ParticleMotionType {
    DYNAMIC,
    PARAMETRIC,
    STATIC
}

public class ParametricParticleMotion(
    var offset: Triple<Expression, Expression, Expression> = Triple(
        NumberExpression(0.0),
        NumberExpression(0.0),
        NumberExpression(0.0)
    ),
    var direction: Triple<Expression, Expression, Expression> = Triple(
        NumberExpression(0.0),
        NumberExpression(0.0),
        NumberExpression(0.0)
    )
) : ParticleMotion {
    final class Companion {
        val CODEC: Codec<ParametricParticleMotion> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                EXPRESSION_CODEC.listOf().fieldOf("offset").forGetter { listOf(it.offset.first, it.offset.second, it.offset.third) },
                EXPRESSION_CODEC.listOf().fieldOf("direction").forGetter { listOf(it.direction.first, it.direction.second, it.direction.third) }
            ).apply(instance) { _, offset, direction -> ParametricParticleMotion(Triple(offset[0], offset[1], offset[2]), Triple(direction[0], direction[1], direction[2])) }
        }
    }

    override val type = ParticleMotionType.PARAMETRIC

    override fun getInitialVelocity(MoLangRuntime runtime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3) = Vec3.ZERO
    override fun getVelocity(MoLangRuntime runtime, particle: SnowstormParticle, velocity: Vec3): Vec3 {
        val particlePositionWS = Vec3(
            particle.getX(),
            particle.getY(),
            particle.getZ()
        )
        val matrix = particle.matrixWrapper

        val targetPosPS = runtime.resolveVec3d(offset)
        val targetPosWS = matrix.transformPosition(targetPosPS)

        return targetPosWS.subtract(particlePositionWS)
    }

    override fun getParticleDirection(MoLangRuntime runtime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float) = runtime.resolveVec3d(direction).normalize()
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        offset = Triple(
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression()
        )
        direction = Triple(
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression()
        )
    }
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(offset.first.getString())
        buffer.writeString(offset.second.getString())
        buffer.writeString(offset.third.getString())
        buffer.writeString(direction.first.getString())
        buffer.writeString(direction.second.getString())
        buffer.writeString(direction.third.getString())
    }
}

public class DynamicParticleMotion(
    var direction: ParticleMotionDirection = InwardsMotionDirection(),
    var speed: Expression = NumberExpression(0.0),
    var acceleration: Triple<Expression, Expression, Expression> = Triple(
        NumberExpression(0.0),
        NumberExpression(0.0),
        NumberExpression(0.0)
    ),
    var drag: Expression = NumberExpression(0.0)
) : ParticleMotion {
    override val type = ParticleMotionType.DYNAMIC

    final class Companion {
        val CODEC: Codec<DynamicParticleMotion> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                ParticleMotionDirection.codec.fieldOf("direction").forGetter { it.direction },
                EXPRESSION_CODEC.fieldOf("speed").forGetter { it.speed },
                EXPRESSION_CODEC.fieldOf("accelX").forGetter { it.acceleration.first },
                EXPRESSION_CODEC.fieldOf("accelY").forGetter { it.acceleration.second },
                EXPRESSION_CODEC.fieldOf("accelZ").forGetter { it.acceleration.third },
                EXPRESSION_CODEC.fieldOf("drag").forGetter { it.drag }
            ).apply(instance) { _, direction, speed, accelX, accelY, accelZ, drag -> DynamicParticleMotion(direction, speed, Triple(accelX, accelY, accelZ), drag) }
        }
    }

    override fun getInitialVelocity(MoLangRuntime runtime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3): Vec3 {
        return direction.getDirectionVector(runtime, storm, emitterPos, particlePos).normalize().scale(runtime.resolveDouble(speed))
    }

    override fun getVelocity(MoLangRuntime runtime, particle: SnowstormParticle, velocity: Vec3): Vec3 {
        val acceleration = Vec3(
            runtime.resolveDouble(acceleration.first),
            runtime.resolveDouble(acceleration.second),
            runtime.resolveDouble(acceleration.third)
        )
            .subtract(velocity.scale(20 * runtime.resolveDouble(drag)))
            .scale(1 / 20.0).scale(1 / 20.0) // blocks per second per second -> blocks per tick per tick

        return Vec3(
            velocity.x + acceleration.x,
            velocity.y + acceleration.y,
            velocity.z + acceleration.z
        )
    }

    override fun getParticleDirection(MoLangRuntime runtime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float) = velocity.normalize()
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        direction = ParticleMotionDirection.readFromBuffer(buffer)
        speed = MoLang.createParser(buffer.readString()).parseExpression()
        acceleration = Triple(
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression()
        )
        drag = MoLang.createParser(buffer.readString()).parseExpression()
    }

    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        ParticleMotionDirection.writeToBuffer(buffer, direction)
        buffer.writeString(speed.getString())
        buffer.writeString(acceleration.first.getString())
        buffer.writeString(acceleration.second.getString())
        buffer.writeString(acceleration.third.getString())
        buffer.writeString(drag.getString())
    }
}

public interface ParticleMotionDirection : CodecMapped {
    final class Companion : ArbitrarilyMappedSerializableCompanion<ParticleMotionDirection, ParticleMotionDirectionType>(
        keyFromString = ParticleMotionDirectionType::valueOf,
        stringFromKey = { it.name },
        keyFromValue = { it.type }
    ) {
        init {
            // class map adapter
            registerSubtype(ParticleMotionDirectionType.INWARDS, InwardsMotionDirection.class, InwardsMotionDirection.CODEC)
            registerSubtype(ParticleMotionDirectionType.OUTWARDS, OutwardsMotionDirection.class, OutwardsMotionDirection.CODEC)
            registerSubtype(ParticleMotionDirectionType.CUSTOM, CustomMotionDirection.class, CustomMotionDirection.CODEC)
        }
    }
    val type: ParticleMotionDirectionType
    fun getDirectionVector(MoLangRuntime runtime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3
}

public class InwardsMotionDirection : ParticleMotionDirection {
    final class Companion {
        val CODEC: Codec<InwardsMotionDirection> = RecordCodecBuilder.create { instance ->
            instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name })
                .apply(instance) { InwardsMotionDirection() }
        }
    }

    override val type = ParticleMotionDirectionType.INWARDS
    override fun getDirectionVector(MoLangRuntime runtime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3 {
        return if (particlePos == emitterPos) {
            Vec3(
                storm.world.random.nextDouble() - 0.5,
                storm.world.random.nextDouble() - 0.5,
                storm.world.random.nextDouble() - 0.5
            )
        } else {
            emitterPos.subtract(particlePos)
        }.normalize()
    }
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

public class OutwardsMotionDirection : ParticleMotionDirection {
    final class Companion {
        val CODEC: Codec<OutwardsMotionDirection> = RecordCodecBuilder.create { instance ->
            instance.group(PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name })
                .apply(instance) { OutwardsMotionDirection() }
        }
    }

    override val type = ParticleMotionDirectionType.OUTWARDS
    override fun getDirectionVector(MoLangRuntime runtime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3 {
        return if (particlePos == emitterPos) {
            Vec3(
                storm.world.random.nextDouble() - 0.5,
                storm.world.random.nextDouble() - 0.5,
                storm.world.random.nextDouble() - 0.5
            )
        } else {
            particlePos.subtract(emitterPos)
        }.normalize()
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}

public class CustomMotionDirection(
    var direction: Triple<Expression, Expression, Expression> = Triple(
        NumberExpression(0.0),
        NumberExpression(0.0),
        NumberExpression(0.0)
    )
) : ParticleMotionDirection {
    final class Companion {
        val CODEC: Codec<CustomMotionDirection> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                EXPRESSION_CODEC.fieldOf("directionX").forGetter { it.direction.first },
                EXPRESSION_CODEC.fieldOf("directionY").forGetter { it.direction.second },
                EXPRESSION_CODEC.fieldOf("directionZ").forGetter { it.direction.third }
            ).apply(instance) { _, dirX, dirY, dirZ -> CustomMotionDirection(Triple(dirX, dirY, dirZ)) }
        }
    }

    override val type = ParticleMotionDirectionType.CUSTOM

    override fun getDirectionVector(MoLangRuntime runtime, storm: ParticleStorm, emitterPos: Vec3, particlePos: Vec3): Vec3 {
        val v = Vec3(
            runtime.resolveDouble(direction.first),
            runtime.resolveDouble(direction.second),
            runtime.resolveDouble(direction.third)
        )
        return storm.emitterSpaceMatrix.matrix.transformDirection(v)
    }

    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        direction = Triple(
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression(),
            MoLang.createParser(buffer.readString()).parseExpression()
        )
    }

    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(direction.first.getString())
        buffer.writeString(direction.second.getString())
        buffer.writeString(direction.third.getString())
    }
}

public enum ParticleMotionDirectionType {
    CUSTOM,
    INWARDS,
    OUTWARDS
}

public class StaticParticleMotion : ParticleMotion {
    final class Companion {
        val CODEC: Codec<StaticParticleMotion> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name }
            ).apply(instance) { StaticParticleMotion() }
        }
    }

    @Transient
    override val type = ParticleMotionType.STATIC

    override fun getInitialVelocity(MoLangRuntime runtime, storm: ParticleStorm, particlePos: Vec3, emitterPos: Vec3) = Vec3.ZERO
    override fun getVelocity(MoLangRuntime runtime, particle: SnowstormParticle, velocity: Vec3) = velocity
    override fun getParticleDirection(MoLangRuntime runtime, storm: ParticleStorm, velocity: Vec3, minSpeed: Float) = velocity.normalize()
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {}
    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {}
}