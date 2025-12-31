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
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the server to pasture a Pokémon.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pasture.PasturePokemonHandler]
 *
 * @author Hiroku
 * @since April 9th, 2023
 */
public class PasturePokemonPacket(val UUID pokemonId, val pastureId: UUID) : NetworkPacket<PasturePokemonPacket> {
    final class Companion {
        val ID = cobblemonResource("pasture_pokemon")

        fun decode(RegistryFriendlyByteBuf buffer) = PasturePokemonPacket(buffer.readUUID(), buffer.readUUID())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemonId)
        buffer.writeUUID(pastureId)
    }
}