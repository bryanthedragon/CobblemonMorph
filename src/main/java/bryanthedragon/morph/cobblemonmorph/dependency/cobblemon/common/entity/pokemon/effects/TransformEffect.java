/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonPropertyExtractor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.afterOnServer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects.SHINY_ASPECT
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import java.util.concurrent.CompletableFuture

/**
 * A [BattleEffect] that alters a [PokemonEntity] to look like a target [Pokemon].
 *
 * @param mimic The [Pokemon] to copy.
 * @author Segfault Guy
 * @since March 5th, 2024
 */
public class TransformEffect(
    // This really should include aspects separately
    override var mock: PokemonProperties = PokemonProperties(),
    override var scale: Float = 1.0F,
    val Boolean doCry = true
) : BattleEffect(), MocKEffect {

    constructor(mimic: Pokemon, Boolean doCry = true) : this(
        mock = mimic.createPokemonProperties(PokemonPropertyExtractor.TRANSFORM),
        scale = mimic.form.baseScale * mimic.scaleModifier,
        doCry = doCry
    )

    override fun apply(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
        mock.aspects += SHINY_ASPECT.provide(entity.pokemon)    // apply shiny property to new appearance
        entity.effects.mockEffect = this
        afterOnServer(seconds = 1.0F) {
            if (doCry) entity.cry()
            future.complete(entity)
        }
    }

    override fun revert(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
        entity.effects.mockEffect = null
        afterOnServer(seconds = 1.0F) {
            entity.cry()
            future.complete(entity)
        }
    }

    override fun saveToNbt(registryLookup: HolderLookup.Provider): CompoundTag {
        val nbt = CompoundTag()
        nbt.putString(DataKeys.ENTITY_EFFECT_MOCK, ID)
        nbt.put(DataKeys.POKEMON_ENTITY_MOCK, mock.saveToNBT(registryLookup))
        nbt.putFloat(DataKeys.POKEMON_ENTITY_SCALE, scale)
        return nbt
    }

    override fun loadFromNBT(CompoundTag nbt, registryLookup: HolderLookup.Provider) {
        if (nbt.contains(DataKeys.POKEMON_ENTITY_MOCK)) this.mock = PokemonProperties().loadFromNBT(nbt.getCompound(DataKeys.POKEMON_ENTITY_MOCK), registryLookup)
        if (nbt.contains(DataKeys.POKEMON_ENTITY_SCALE)) this.scale = nbt.getFloat(DataKeys.POKEMON_ENTITY_SCALE)
    }

    final class Companion {
        val ID = "TRANSFORM"
    }
}