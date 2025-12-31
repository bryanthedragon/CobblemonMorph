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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.EXPRESSION_CODEC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveDouble
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import com.mojang.serialization.Codec
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import kotlin.math.abs

public interface ParticleRotation : CodecMapped {
    final class Companion : ArbitrarilyMappedSerializableCompanion<ParticleRotation, ParticleRotationType>(
        keyFromValue = { it.type },
        keyFromString = ParticleRotationType::valueOf,
        stringFromKey = { it.name }
    ) {
        init {
            registerSubtype(ParticleRotationType.DYNAMIC, DynamicParticleRotation.class, DynamicParticleRotation.CODEC)
            registerSubtype(ParticleRotationType.PARAMETRIC, ParametricParticleRotation.class, ParametricParticleRotation.CODEC)
        }
    }

    val type: ParticleRotationType

    fun getInitialRotation(MoLangRuntime runtime): Double
    fun getInitialAngularVelocity(MoLangRuntime runtime): Double
    fun getAngularVelocity(MoLangRuntime runtime, angle: Double, angularVelocity: Double): Double
}

public class ParametricParticleRotation(var expression: Expression = NumberExpression(0.0)): ParticleRotation {
    final class Companion {
        val CODEC: Codec<ParametricParticleRotation> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                EXPRESSION_CODEC.fieldOf("expression").forGetter { it.expression }
            ).apply(instance) { _, expression ->
                ParametricParticleRotation(expression)
            }
        }
    }

    override val type = ParticleRotationType.PARAMETRIC
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun getInitialRotation(MoLangRuntime runtime) = runtime.resolveDouble(expression)
    override fun getInitialAngularVelocity(MoLangRuntime runtime) = 0.0
    override fun getAngularVelocity(MoLangRuntime runtime, angle: Double, angularVelocity: Double) = runtime.resolveDouble(expression) - angle

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        expression = MoLang.createParser(buffer.readString()).parseExpression()
    }

    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(expression.getString())
    }
}

public class DynamicParticleRotation(
    var startRotation: Expression = NumberExpression(0.0),
    var speed: Expression = NumberExpression(0.0),
    var acceleration: Expression = NumberExpression(0.0),
    var drag: Expression = NumberExpression(0.0)
): ParticleRotation {
    final class Companion {
        val CODEC: Codec<DynamicParticleRotation> = RecordCodecBuilder.create { instance ->
            instance.group(
                PrimitiveCodec.STRING.fieldOf("type").forGetter { it.type.name },
                EXPRESSION_CODEC.fieldOf("startRotation").forGetter { it.startRotation },
                EXPRESSION_CODEC.fieldOf("speed").forGetter { it.speed },
                EXPRESSION_CODEC.fieldOf("acceleration").forGetter { it.acceleration },
                EXPRESSION_CODEC.fieldOf("drag").forGetter { it.drag }
            ).apply(instance) { _, startRotation, speed, acceleration, drag ->
                DynamicParticleRotation(startRotation, speed, acceleration, drag)
            }
        }
    }

    override val type = ParticleRotationType.DYNAMIC
    override fun <T> encode(DynamicOps<T> ops) = CODEC.encodeStart(ops, this)
    override fun getInitialRotation(MoLangRuntime runtime) = runtime.resolveDouble(startRotation)
    override fun getInitialAngularVelocity(MoLangRuntime runtime) = runtime.resolveDouble(speed) / 20
    override fun getAngularVelocity(MoLangRuntime runtime, angle: Double, angularVelocity: Double): Double {
        val acceleration = runtime.resolveDouble(acceleration)
        val nextVelocity = angularVelocity * 20 + acceleration
        val drag = nextVelocity * runtime.resolveDouble(drag)
        return angularVelocity + (if (abs(drag) > abs(nextVelocity)) {
            0.0
        } else {
            nextVelocity - drag - angularVelocity * 20
        })
    }

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer) {
        startRotation = MoLang.createParser(buffer.readString()).parseExpression()
        speed = MoLang.createParser(buffer.readString()).parseExpression()
        acceleration = MoLang.createParser(buffer.readString()).parseExpression()
        drag = MoLang.createParser(buffer.readString()).parseExpression()
    }

    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(startRotation.getString())
        buffer.writeString(speed.getString())
        buffer.writeString(acceleration.getString())
        buffer.writeString(drag.getString())
    }
}

public enum ParticleRotationType {
    DYNAMIC,
    PARAMETRIC
}