/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.FloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import java.util.UUID
import net.minecraft.network.chat.MutableComponent

/**
 * The client side representation of a Pokémon in battle.
 *
 * @property uuid
 * @property displayName
 * @property properties
 * @property aspects
 * @property hpValue The current value of the HP.
 * @property maxHp The maximum value of HP.
 * @property isHpFlat If this is a flat value, this will be true if the client is the player controlling the Pokémon or is an ally of the controller.
 * @property status
 * @property statChanges
 */
public class ClientBattlePokemon(
    val UUID uuid,
    var MutableComponent displayName,
    var properties: PokemonProperties,
    private Set<String> aspects,
    var hpValue: Float,
    var maxHp: Float,
    var isHpFlat: Boolean,
    var PersistentStatus status?,
    var statChanges: MutableMap<Stat, Int>
) {
    lateinit var actor: ClientBattleActor
    val species: Species
        get() = PokemonSpecies.getByIdentifier(properties.species!!.asIdentifierDefaultingNamespace())!!
    val level: Int
        get() = properties.level ?: 0

    val gender: Gender
        get() = properties.gender ?: Gender.GENDERLESS

    var state = FloatingState().also {
        it.currentAspects = aspects
    }

    fun updateAspects(aspects: Set<String>) {
        this.aspects = aspects
        state.currentAspects = aspects
    }
}