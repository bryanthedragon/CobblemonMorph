/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats.RidingStat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeEnumConstant
import net.minecraft.network.RegistryFriendlyByteBuf

public class RideBoostsUpdatePacket(pokemon: () -> Pokemon?, rideBoosts: Map<RidingStat, Float>) : SingleUpdatePacket<Map<RidingStat, Float>, RideBoostsUpdatePacket>(pokemon, rideBoosts) {
    final class Companion {
        val ID = cobblemonResource("ride_boosts_update")
        fun decode(RegistryFriendlyByteBuf buffer): RideBoostsUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val rideBoosts = buffer.readMap({ buffer.readEnumConstant(RidingStat.class) }, { buffer.readFloat() })
            return RideBoostsUpdatePacket(pokemon, rideBoosts)
        }
    }

    override val id = ID
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeMap(
            value,
            { _, it -> buffer.writeEnumConstant(it) },
            { _, it -> buffer.writeFloat(it) }
        )
    }

    override fun set(Pokemon pokemon, value: Map<RidingStat, Float>) {
        pokemon.setRideBoosts(value)
    }
}
