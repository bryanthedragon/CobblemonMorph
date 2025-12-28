/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.PollinateFlowerTaskConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SaccharineLogSlatheredInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokeSnackBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate
import net.minecraft.core.particles.BlockParticleOption
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.resources.ResourceLocation

/**
 * Used by [PokemonClientDelegate.spawnAspectParticle] to spawn associated particles for aspects
 */
val aspectParticleMap: Map<String, ParticleData> = mapOf(
    SaccharineLogSlatheredInfluence.HONEY_DRENCHED_ASPECT to ParticleData.MinecraftParticle(ParticleTypes.FALLING_HONEY, 0.075, 1),
    PokeSnackBlockEntity.POKE_SNACK_CRUMBED_ASPECT to ParticleData.MinecraftParticle(
        BlockParticleOption(
            ParticleTypes.BLOCK,
            CobblemonBlocks.POKE_SNACK.defaultBlockState()
        ), 0.05, 3),
    PollinateFlowerTaskConfig.HAS_NECTAR_ASPECT to ParticleData.MinecraftParticle(ParticleTypes.FALLING_NECTAR, 0.075, 1)
)

sealed class ParticleData {
    record SnowstormParticle(val particle: ResourceLocation, val chance: Double, val amount: Int, val locators: List<String>): ParticleData()
    record MinecraftParticle(val particle: ParticleOptions, val chance: Double, val amount: Int): ParticleData()
}
