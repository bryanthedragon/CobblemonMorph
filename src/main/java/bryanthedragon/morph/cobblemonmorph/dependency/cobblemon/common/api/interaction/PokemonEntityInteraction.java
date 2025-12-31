/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack

/**
 * An [EntityInteraction] targeting [PokemonEntity]s.
 * These need to be manually triggered if not implemented by an item.
 */
public interface PokemonEntityInteraction : EntityInteraction<PokemonEntity> {

    /**
     * The accepted [Ownership] for the Pokémon entity in order for the interaction to fire.
     */
    val accepted: Set<Ownership>
    val SoundEvent sound?
        get() = CobblemonSounds.ITEM_USE

    override fun onInteraction(ServerPlayer player, entity: PokemonEntity, ItemStack stack): Boolean {
        val pokemon = entity.pokemon
        val storeCoordinates = pokemon.storeCoordinates.get()
        val ownership = when {
            storeCoordinates == null -> Ownership.WILD
            storeCoordinates.store.uuid == player.uuid -> Ownership.OWNER
            else -> Ownership.OWNED_ANOTHER
        }
        return if (ownership in accepted) {
            this.processInteraction(player, entity, stack)
        } else {
            false
        }
    }

    /**
     * Fired after [EntityInteraction.onInteraction] the [Ownership] is checked if contained in [accepted].
     *
     * @param player The [ServerPlayer] interacting with the [entity].
     * @param entity The [PokemonEntity] being interacted with.
     * @param stack The [ItemStack] used in this interaction.
     * @return true if the interaction was successful and no further interactions should be processed.
     */
    fun processInteraction(ServerPlayer player, entity: PokemonEntity, ItemStack stack): Boolean

    /**
     * Represents the ownership status of a Pokemon relative to a Player.
     *
     * @author Licious
     * @since March 24th, 2022
     */
    enum class Ownership {

        /**
         * When the player owns the Pokemon.
         */
        OWNER,

        /**
         * When the Pokemon is owned by another entity.
         */
        OWNED_ANOTHER,

        /**
         * When the Pokemon has no owner.
         */
        WILD

    }

}