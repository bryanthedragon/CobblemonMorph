/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.TransformEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

/**
 * Represents a temporary alteration to how a [PokemonEntity] is rendered and behaves. May include a temporary visual effect
 * when applied.
 *
 * @author Segfault Guy
 * @since March 5th, 2024
 */
public interface EntityEffect {

    /**
     * Starts this effect for the provided [PokemonEntity].
     *
     * @return A [CompletableFuture] that completes after the effect has been applied. Or null if the effect failed to start.
     */
    fun start(entity: PokemonEntity): CompletableFuture<PokemonEntity>?

    /**
     * Ends this effect for the provided [PokemonEntity].
     *
     * @return A [CompletableFuture] that completes after the effect has been reverted. Or null if the effect failed to end.
     */
    fun end(entity: PokemonEntity): CompletableFuture<PokemonEntity>?

    /** Saves this effect to NBT. */
    fun saveToNbt(registryLookup: HolderLookup.Provider): CompoundTag

    /** Loads this effect from NBT. */
    fun loadFromNBT(CompoundTag nbt, registryLookup: HolderLookup.Provider)

    final class Companion {

        private val effects = mutableMapOf<String, KClass<out EntityEffect>>()
        private val defaults = mutableMapOf<String, () -> EntityEffect>()

        init {
            register(IllusionEffect.ID, IllusionEffect::class, ::IllusionEffect)
            register(TransformEffect.ID, TransformEffect::class, ::TransformEffect)
        }

        fun <T : EntityEffect> register(id: String, type: KClass<T>, default: () -> T) {
            effects[id] = type
            defaults[id] = default
        }

        fun createDefault(id: String): EntityEffect? = defaults[id]?.invoke()

        fun loadFromNbt(CompoundTag nbt, registryLookup: HolderLookup.Provider): EntityEffect? {
            if (nbt.contains(DataKeys.ENTITY_EFFECT_ID)) {
                val id = nbt.getString(DataKeys.ENTITY_EFFECT_ID)
                return createDefault(id)?.also { it.loadFromNBT(nbt, registryLookup) }
            }
            return null
        }
    }
}


/** An [EntityEffect] that modifies the dimensions of a [PokemonEntity]. */
public interface PhysicalEffect : EntityEffect {
    val scale: Float
}

/** An [EntityEffect] that alters the physical appearance of a [PokemonEntity] to match a [mock]. */
public interface MocKEffect : PhysicalEffect {
    val mock: PokemonProperties

    val exposedSpecies: Species?
        get() = this.mock.species?.let { PokemonSpecies.getByIdentifier(it.asIdentifierDefaultingNamespace()) }

    val exposedForm: FormData?
        get() = this.mock.form?.let {
            formID -> this.exposedSpecies?.forms?.firstOrNull { it.formOnlyShowdownId().equals(formID, true) } }
                ?: this.exposedSpecies?.standardForm

    val exposedBall: PokeBall?
        get() = this.mock.pokeball?.let { PokeBalls.getPokeBall(it.asIdentifierDefaultingNamespace()) }
}
