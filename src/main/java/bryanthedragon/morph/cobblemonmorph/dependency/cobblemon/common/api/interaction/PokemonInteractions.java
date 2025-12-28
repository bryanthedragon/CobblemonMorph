/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.OwnerQueryRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonRequirementAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.LegacyItemConditionWrapperAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements.PokemonPropertiesRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory
final class PokemonInteractions : JsonDataRegistry<PokemonInteractionSet> {
    override val id = cobblemonResource("pokemon_interactions")
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<PokemonInteractions>()
    override val typeToken: TypeToken<PokemonInteractionSet> = TypeToken.get(PokemonInteractionSet::class.java)
    override val resourcePath = "pokemon_interactions"
    override val gson: Gson = GsonBuilder()
        .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter)
        .registerTypeAdapter(PokemonProperties::class.java, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(ItemPredicate::class.java, LegacyItemConditionWrapperAdapter)
        .registerTypeAdapter(Requirement::class.java, CobblemonRequirementAdapter)
        .registerTypeAdapter(InteractionEffect::class.java, InteractionEffectAdapter)
        .registerTypeAdapter(ExpressionLike::class.java, ExpressionLikeAdapter)
        .registerTypeAdapter(IntRange::class.java, IntRangeAdapter)
        .registerTypeAdapterFactory(LowerCaseEnumTypeAdapterFactory())
        .setPrettyPrinting()
        .create()

    val speciesInteractions = mutableListOf<PokemonInteractionSet>()
    val generalInteractions = mutableListOf<PokemonInteractionSet>()

    override fun sync(player: ServerPlayer) {}

    override fun reload(data: Map<ResourceLocation, PokemonInteractionSet>) {
        speciesInteractions.clear()
        generalInteractions.clear()
        val split = data.entries.partition { it.value.requirements.any { requirement -> requirement is PokemonPropertiesRequirement && requirement.target.species != null } }
        speciesInteractions.addAll(split.first.map {it.value})
        generalInteractions.addAll(split.second.map {it.value})
        Cobblemon.LOGGER.info("Loaded {} Pokémon interaction sets", data.size)
    }

    fun findInteraction(pokemon: PokemonEntity): PokemonInteraction? {
        val setCheck: (PokemonInteractionSet) -> Boolean = { it.requirements.all { req -> req.check(pokemon.pokemon) }}
        val interactionCheck: (PokemonInteraction) -> Boolean = { it.requirements.all { req -> req.check(pokemon.pokemon) } && !pokemon.pokemon.isOnInteractionCooldown(it.grouping)  }
        // species-specific interactions take priority
        val validInteractions = speciesInteractions
            .filter(setCheck)
            .flatMap { it.interactions }
            .filter(interactionCheck)
            .toMutableList()
        if (validInteractions.isEmpty()) { // if all species-specific interactions are absent/on cooldown, fallback to more generic ones
            validInteractions.addAll(generalInteractions.filter(setCheck).flatMap { it.interactions }
                .filter(interactionCheck)
            )
        }

        return validInteractions.randomOrNull()
    }
}