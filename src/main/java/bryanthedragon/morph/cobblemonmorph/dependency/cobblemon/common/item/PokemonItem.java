/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.components.PokemonItemComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import org.joml.Vector4f

public class PokemonItem : CobblemonItem(Properties().stacksTo(1).component(CobblemonItemComponents.POKEMON_ITEM, null)) {

    override fun getName(ItemStack stack): Component = this.species(stack)?.translatedName ?: super.getName(stack)

    fun asPokemon(ItemStack stack): Pokemon? {
        val species = this.species(stack) ?: return null
        val aspects = this.aspects(stack) ?: setOf()
        return Pokemon().apply {
            this.species = species
            this.forcedAspects = aspects
        }
    }

    fun getSpeciesAndAspects(ItemStack stack): Pair<Species, Set<String>>? {
        return (species(stack) ?: return null) to (aspects(stack) ?: setOf())
    }

    fun asRenderablePokemon(ItemStack stack): RenderablePokemon? = this.asPokemon(stack)?.asRenderablePokemon()

    private fun species(ItemStack stack): Species? {
        return stack.get(CobblemonItemComponents.POKEMON_ITEM)?.species?.let(PokemonSpecies::getByIdentifier)
    }

    private fun aspects(ItemStack stack): Set<String>? {
        return stack.get(CobblemonItemComponents.POKEMON_ITEM)?.aspects
    }

    fun tint(ItemStack stack): Vector4f {
        return stack.get(CobblemonItemComponents.POKEMON_ITEM)?.tint ?: Vector4f(1f, 1f, 1f, 1f)
    }

    final class Companion {
        @JvmOverloads
        @JvmStatic
        fun from(Pokemon pokemon, count: Int = 1, tint: Vector4f? = null): ItemStack = from(pokemon.species, pokemon.aspects, count, tint)

        @JvmOverloads
        @JvmStatic
        fun from(properties: PokemonProperties, count: Int = 1, tint: Vector4f? = null): ItemStack = from(properties.create(), count, tint)

        @JvmOverloads
        @JvmStatic
        fun from(species: Species, vararg aspects: String, count: Int = 1, tint: Vector4f? = null): ItemStack = from(species, aspects.toSet(), count, tint)

        @JvmStatic
        fun from(species: Species, aspects: Set<String>, count: Int = 1, tint: Vector4f? = null): ItemStack {
            val stack = ItemStack(CobblemonItems.POKEMON_MODEL, count)
            stack.set(CobblemonItemComponents.POKEMON_ITEM, PokemonItemComponent(species.resourceIdentifier, aspects, tint))
            return stack
        }
    }
}