/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet used for when EVs have changed.
 *
 * @author Hiroku
 * @since November 23rd, 2022
 */
class EVsUpdatePacket(pokemon: () -> Pokemon?, eVs: EVs) : SingleUpdatePacket<EVs, EVsUpdatePacket>(pokemon, eVs) {
    override val id = ID
    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        EVs.STREAM_CODEC.encode(buffer, this.value)
    }
    override fun set(pokemon: Pokemon, value: EVs) {
        value.forEach { (stat, value) ->
            pokemon.evs[stat] = value
        }
    }
    companion object {
        val ID = cobblemonResource("ev_update")
        fun decode(buffer: RegistryFriendlyByteBuf) = EVsUpdatePacket(decodePokemon(buffer), EVs.STREAM_CODEC.decode(buffer))
    }
}

/**
 * Packet used for when IVs have changed.
 *
 * @author Hiroku
 * @since November 23rd, 2022
 */
class IVsUpdatePacket(pokemon: () -> Pokemon?, iVs: IVs) : SingleUpdatePacket<IVs, IVsUpdatePacket>(pokemon, iVs) {
    override val id = ID
    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        IVs.STREAM_CODEC.encode(buffer, this.value)
    }
    override fun set(pokemon: Pokemon, value: IVs) {
        value.forEach { (stat, value) ->
            pokemon.ivs[stat] = value
        }
        pokemon.ivs.hyperTrainedIVs.clear()
        value.hyperTrainedIVs.forEach { (stat, value) ->
            pokemon.ivs.setHyperTrainedIV(stat, value)
        }
    }
    companion object {
        val ID = cobblemonResource("iv_update")
        fun decode(buffer: RegistryFriendlyByteBuf) = IVsUpdatePacket(decodePokemon(buffer), IVs.STREAM_CODEC.decode(buffer))
    }
}