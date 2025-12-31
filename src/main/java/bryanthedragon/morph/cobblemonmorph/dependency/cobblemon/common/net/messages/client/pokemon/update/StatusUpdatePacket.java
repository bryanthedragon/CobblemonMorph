/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import io.netty.buffer.ByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf

public class StatusUpdatePacket(pokemon: () -> Pokemon?, value: PersistentStatus?): SingleUpdatePacket<PersistentStatus?, StatusUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(this.value) { pb, value -> pb.writeIdentifier(value.name) }
    }

    override fun set(Pokemon pokemon, value: PersistentStatus?) {
        if (value == null) {
            pokemon.status = null
            return
        }
        pokemon.applyStatus(value)
    }

    final class Companion {
        val ID = cobblemonResource("status_update")
        fun decode(RegistryFriendlyByteBuf buffer): StatusUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val identifier = buffer.readNullable(ByteBuf::readIdentifier) ?: return StatusUpdatePacket(pokemon, null)
            val status = Statuses.getStatus(identifier) as? PersistentStatus
            return StatusUpdatePacket(pokemon, status)
        }
    }
}