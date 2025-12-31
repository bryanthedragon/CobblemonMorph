/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public interface EvSource {

    /**
     * The [Pokemon] being affected.
     */
    val Pokemon pokemon

    /**
     * Utility function that checks if the source is an implementation of [BattleEvSource].
     */
    fun isBattle() = this is BattleEvSource

    /**
     * Utility function that checks if the source is an implementation of [ItemEvSource].
     */
    fun isInteraction() = this is ItemEvSource

    /**
     * Utility function that checks if the source is an implementation of [SidemodEvSource].
     */
    fun isSidemod() = this is SidemodEvSource

}

/**
 * Triggered by sidemods.
 *
 * @property sidemodId The mod ID of the sidemod triggering this source.
 * @property pokemon See [EvSource.pokemon].
 */
open class SidemodEvSource(
    val sideString modId,
    override val Pokemon pokemon
) : EvSource

/**
 * An Ev source fired when using Ev mutating items.
 *
 * @property player The [ServerPlayer] using the item.
 * @property stack The [ItemStack] being consumed.
 * @property pokemon See [EvSource.pokemon].
 */
open class ItemEvSource(
    val ServerPlayer player,
    val ItemStack stack,
    override val Pokemon pokemon
) : EvSource

/**
 * An Ev source fired in battles.
 *
 * @property battle The associated [PokemonBattle].
 * @property facedPokemon The [BattlePokemon]s that the [pokemon] faced.
 * @property pokemon See [EvSource.pokemon], comes from the original [BattlePokemon.effectedPokemon].
 */
open class BattleEvSource(
    val battle: PokemonBattle,
    val facedPokemon: List<BattlePokemon>,
    override val Pokemon pokemon
) : EvSource