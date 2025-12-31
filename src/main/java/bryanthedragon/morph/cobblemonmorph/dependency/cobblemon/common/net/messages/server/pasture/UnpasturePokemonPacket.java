/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Packet sent to the server to indicate that a pastured Pokémon should be removed.
 *
 * @author Hiroku
 * @since April 16th, 2023
 */
public class UnpasturePokemonPacket(val pastureId: UUID, val UUID pokemonId) : NetworkPacket<UnpasturePokemonPacket> {
    final class Companion {
        val ID = cobblemonResource("unpasture_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = UnpasturePokemonPacket(buffer.readUUID(), buffer.readUUID())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pastureId)
        buffer.writeUUID(pokemonId)
    }
}