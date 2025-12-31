/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import com.google.gson.JsonObject
import java.util.UUID
import net.minecraft.core.RegistryAccess
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer

public class BottomlessPosition(val currentInt index) : StorePosition

/**
 * A [PokemonStore] that has no maximum capacity. It's used internally as an overflow store.
 *
 * @author Hiroku
 * @since May 2nd, 2022
 */
open class BottomlessStore(override val UUID uuid) : PokemonStore<BottomlessPosition>() {
    val pokemon = mutableListOf<Pokemon>()
    val storeChangeObservable = SimpleObservable<Unit>()

    override fun iterator() = pokemon.iterator()

    override fun get(position: BottomlessPosition) = position.currentIndex
        .takeIf { it in pokemon.indices }
        ?.let { pokemon[it] }

    override fun getFirstAvailablePosition() = BottomlessPosition(pokemon.size)
    override fun isValidPosition(position: BottomlessPosition) = position.currentIndex >= 0
    operator fun get(Int index) = index.takeIf { it in pokemon.indices }?.let { pokemon[it] }
    override fun getObservingPlayers() = emptySet<ServerPlayer>()
    override fun sendTo(ServerPlayer player) {}

    override fun initialize() {
        pokemon.forEachIndexed { index, pokemon ->
            pokemon.storeCoordinates.set(StoreCoordinates(this, BottomlessPosition(index)))
        }
    }

    override fun saveToNBT(CompoundTag nbt, RegistryAccess registryAccess): CompoundTag {
        pokemon.forEachIndexed { index, pokemon -> nbt.put(DataKeys.STORE_SLOT + index, pokemon.saveToNBT(registryAccess)) }
        return nbt
    }

    override fun loadFromNBT(CompoundTag nbt, RegistryAccess registryAccess): BottomlessStore {
        var i = -1
        while (nbt.contains(DataKeys.STORE_SLOT + ++i)) {
            val pokemonNBT = nbt.getCompound(DataKeys.STORE_SLOT + i)
            try {
                pokemon.add(Pokemon.loadFromNBT(registryAccess, pokemonNBT))
            } catch(_: InvalidSpeciesException) {
                handleInvalidSpeciesNBT(pokemonNBT)
            } catch (Exception e) {
                LOGGER.error("Failed to read a pokémon: $pokemonNBT", e)
            }
        }
        return this
    }

    override fun saveToJSON(JsonObject json, RegistryAccess registryAccess): JsonObject {
        pokemon.forEachIndexed { index, pokemon -> json.add(DataKeys.STORE_SLOT + index, pokemon.saveToJSON(registryAccess)) }
        return json
    }

    override fun loadFromJSON(JsonObject json, RegistryAccess registryAccess): BottomlessStore {
        var i = -1
        while (json.has(DataKeys.STORE_SLOT + ++i)) {
            val pokemonJSON = json.getAsJsonObject(DataKeys.STORE_SLOT + i)
            try {
                pokemon.add(Pokemon.loadFromJSON(registryAccess, pokemonJSON))
            } catch (_: InvalidSpeciesException) {
                handleInvalidSpeciesJSON(pokemonJSON)
            } catch (Exception e) {
                LOGGER.error("Failed to read a pokémon: $pokemonJSON", e)
            }
        }
        return this
    }

    override fun loadPositionFromNBT(CompoundTag nbt): StoreCoordinates<BottomlessPosition> {
        val slot = nbt.getByte(DataKeys.STORE_SLOT).toInt()
        return StoreCoordinates(this, BottomlessPosition(slot))
    }

    override fun savePositionToNBT(position: BottomlessPosition, CompoundTag nbt) {
        nbt.putByte(DataKeys.STORE_SLOT, position.currentIndex.toByte())
    }

    override fun getAnyChangeObservable() = storeChangeObservable

    override fun setAtPosition(position: BottomlessPosition, Pokemon pokemon?) {
        if (position.currentIndex == this.pokemon.size && pokemon != null) {
            this.pokemon.add(pokemon)
            storeChangeObservable.emit(Unit)
        } else if (position.currentIndex in 0 until this.pokemon.size) {
            var startIndex = position.currentIndex
            if (pokemon != null) {
                this.pokemon.add(position.currentIndex, pokemon)
                startIndex += 1
            } else {
                this.pokemon.removeAt(position.currentIndex)
            }
            for (i in startIndex until this.pokemon.size) {
                this.pokemon[i].storeCoordinates.set(StoreCoordinates(this, BottomlessPosition(i)))
            }
            storeChangeObservable.emit(Unit)
        }
    }

    override fun onPokemonChanged(Pokemon pokemon) {
        this.storeChangeObservable.emit(Unit)
    }
}