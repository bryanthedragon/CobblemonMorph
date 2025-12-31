/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle

import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.struct.VariableStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleOptions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.ParticleType
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.phys.Vec3

public class SnowstormParticleType : ParticleType<SnowstormParticleOptions>(true) {
    final class Companion {
        val CODEC: MapCodec<SnowstormParticleOptions> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                BedrockParticleOptions.CODEC.fieldOf("effect").forGetter { it.effect }
            ).apply(instance, ::SnowstormParticleOptions)
        }

        val encoder = { effect: SnowstormParticleOptions, RegistryFriendlyByteBuf buf ->
            effect.effect.writeToBuffer(buf)
        }

        val decoder = { RegistryFriendlyByteBuf buf ->
            SnowstormParticleOptions(BedrockParticleOptions().also { it.readFromBuffer(buf) })
        }

        val PACKET_CODEC = StreamCodec.ofMember(encoder, decoder)

    }

    class Factory(val spriteProvider: SpriteSet) : ParticleProvider<SnowstormParticleOptions> {
        override fun createParticle(
            parameters: SnowstormParticleOptions,
            world: ClientLevel,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double
        ): Particle {
            //Particles with local position move with their emitter
            val isLocal = parameters.effect.space.localPosition
            val storm = ParticleStorm.contextStorm!!

            //Particles may need to have variables that are specific to the instance of the particle
            //For instance, distance variables are different per particle, it can't be tied to the emitter
            //since the emitter can move separately from the particle
            //We keep everything else from the storm. Just separate var structs
            val runtime = MoLangRuntime()
            runtime.environment.query = storm.runtime.environment.query
            val mapClone = storm.runtime.environment.variable.map.entries.associateBy({it.key}, {it.value})
            val varStruct = VariableStruct(mapClone)
            storm.lockParticleVars(varStruct)
            runtime.environment.variable = varStruct
            runtime.environment.context = storm.runtime.environment.context
            runtime.environment.temp = storm.runtime.environment.temp


            return SnowstormParticle(
                storm,
                world,
                x, y, z,
                Vec3(velocityX, velocityY, velocityZ),
                runtime,
                invisible = false,
                matrixWrapper = if (isLocal) storm.emitterSpaceMatrix else storm.emitterSpaceMatrix.clone()
            )
        }
    }

    override fun codec() = CODEC

    override fun streamCodec(): StreamCodec<in RegistryFriendlyByteBuf, SnowstormParticleOptions> {
        TODO("Not yet implemented")
    }
}