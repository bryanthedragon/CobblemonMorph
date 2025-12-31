/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import net.minecraft.commands.CommandSourceStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

/**
 * A source of experience gain for a Pokémon. This could be a battle, a command, something like a level-up item, etc.
 *
 * @author Hiroku
 * @since August 5th, 2022
 */
public interface ExperienceSource {
    fun isBattle() = this is BattleExperienceSource
    fun isInteraction() = this is CandyExperienceSource
    fun isCommand() = this is CommandExperienceSource
    fun isSidemod() = this is SidemodExperienceSource
}

open class SidemodExperienceSource(
    val sideString modId
) : ExperienceSource

open class CandyExperienceSource(
    val ServerPlayer player,
    val ItemStack stack
) : ExperienceSource

open class CommandExperienceSource(
    val source: CommandSourceStack
) : ExperienceSource

open class BattleExperienceSource(
    val battle: PokemonBattle,
    val facedPokemon: List<BattlePokemon>
) : ExperienceSource