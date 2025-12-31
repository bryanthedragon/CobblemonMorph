/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.resources.ResourceLocation
import java.util.UUID

/**
 * Manages all known Pokémon stores on the client. This holds onto the player's party and PC permanently, but can also
 * hold other stores arbitrarily.
 *
 * @author Hiroku
 * @since November 28th, 2021
 */
public class ClientStorageManager {
    var party = ClientParty(UUID.randomUUID(), 1)
    val partyStores = mutableMapOf<UUID, ClientParty>()
    val pcStores = mutableMapOf<UUID, ClientPC>()

    var selectedSlot = -1
    private var selectedPokemon: UUID? = null

    fun shiftSelected(forward: Boolean) {
        val partyHasSome = party.slots.any { it != null }
        if (!partyHasSome) {
            selectedSlot = 0
            selectedPokemon = null
            return
        }

        selectedSlot += if (forward) 1 else -1
        if (selectedSlot >= party.slots.size) {
            selectedSlot = -1
            shiftSelected(forward)
        } else if (selectedSlot < 0) {
            selectedSlot = party.slots.size
            shiftSelected(forward)
        } else if (party.get(selectedSlot) == null) {
            shiftSelected(forward)
        } else {
            selectedPokemon = party.get(selectedSlot)?.uuid
        }
    }

    fun switchToPokemon(pokemon: UUID) {
        selectedPokemon = pokemon
        selectedSlot = party.indexOf(party.findByUUID(pokemon))
        // Check selected Pokémon in-case it's been set to -1 (Pokémon was not in the party for some reason)
        checkSelectedPokemon()
    }

    fun checkSelectedPokemon() {
        if (selectedSlot == -1) {
            val pokemon = party.firstOrNull { it != null } ?: return
            selectedSlot = party.slots.indexOf(pokemon)
            selectedPokemon = pokemon.uuid
        } else if (selectedPokemon == null) {
            selectedPokemon = party.get(PartyPosition(selectedSlot))?.uuid ?: run {
                selectedSlot = -1
                checkSelectedPokemon()
                null
            }
        } else if (party.getPosition(selectedPokemon!!) != selectedSlot) {
            val foundSlot = party.getPosition(selectedPokemon!!)
            if (foundSlot != -1) {
                selectedSlot = foundSlot
            } else {
                selectedPokemon = null
                checkSelectedPokemon()
            }
        } else if (selectedSlot >= party.slots.size) {
            selectedSlot = -1
            checkSelectedPokemon()
        }
    }

    fun locatePokemon(UUID storeID, UUID pokemonId): Pokemon? {
        return partyStores[storeID]?.findByUUID(pokemonID) ?: pcStores[storeID]?.findByUUID(pokemonID)
    }

    fun createParty(mine: Boolean, UUID uuid, slots: Int) {
        val party = ClientParty(uuid, slots)
        partyStores[uuid] = party
        if (mine) {
            this@ClientStorageManager.party = party
            checkSelectedPokemon()
        }
    }

    fun setPartyPokemon(UUID storeID, position: PartyPosition, Pokemon pokemon) {
        val party = partyStores[storeID]
            ?: return LOGGER.error("Tried setting a Pokémon in position $position for party store $storeID but no such store found.")
        party.set(position, pokemon)
        checkSelectedPokemon()
    }

    fun setPCPokemon(UUID storeID, position: PCPosition, Pokemon pokemon) {
        val pc = pcStores[storeID]
            ?: return LOGGER.error("Tried setting a Pokémon in position $position for PC store $storeID but no such store found.")
        pc.set(position, pokemon)
    }

    fun setPartyStore(UUID storeID) {
        party = partyStores[storeID] ?: throw IllegalArgumentException("Was told to set party store to $storeID but no such store is known!")
        checkSelectedPokemon()
    }

    fun removeFromParty(UUID storeID, UUID pokemonId) {
        partyStores[storeID]?.remove(pokemonID)
        checkSelectedPokemon()
    }

    fun moveInParty(UUID storeID, UUID pokemonId, newPosition: PartyPosition) {
        partyStores[storeID]?.move(pokemonID, newPosition)
        checkSelectedPokemon()
    }

    fun swapInParty(UUID storeID, pokemonID1: UUID, pokemonID2: UUID) {
        partyStores[storeID]?.swap(pokemonID1, pokemonID2)
        checkSelectedPokemon()
    }

    fun swapInPC(UUID storeID, pokemonID1: UUID, pokemonID2: UUID) {
        pcStores[storeID]?.swap(pokemonID1, pokemonID2)
    }

    fun moveInPC(UUID storeID, UUID pokemonId, newPosition: PCPosition) {
        pcStores[storeID]?.move(pokemonID, newPosition)
        checkSelectedPokemon()
    }

    fun removeFromPC(UUID storeID, UUID pokemonId) {
        pcStores[storeID]?.remove(pokemonID)
    }

    fun renameBox(UUID storeID, Int boxNumber, String name?) {
        pcStores[storeID]?.renameBox(boxNumber, name)
    }

    fun changeBoxWallpaper(UUID storeID, Int boxNumber, wallpaper: ResourceLocation) {
        pcStores[storeID]?.changeBoxWallpaper(boxNumber, wallpaper)
    }

    fun onLogin() {
        party = ClientParty(UUID.randomUUID(), 1)
        checkSelectedPokemon()
    }

    fun onLogout() {
        partyStores.clear()
        pcStores.clear()
    }
}
