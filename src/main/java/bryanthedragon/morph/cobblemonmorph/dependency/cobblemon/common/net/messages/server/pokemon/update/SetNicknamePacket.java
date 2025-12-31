/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.update.SetNicknameHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the server to indicate a player wants to change the nickname of a Pokémon. If the [nickname]
 * value is null then they are trying to remove the nickname.
 *
 * Handled by [SetNicknameHandler].
 *
 * @author selfdot
 * @since March 29th, 2023
 */
public class SetNicknamePacket(val pokemonUUID uuid, val isParty: Boolean, val nickString name?) : NetworkPacket<SetNicknamePacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemonUUID)
        buffer.writeBoolean(isParty)
        buffer.writeNullable(nickname) { _, v -> buffer.writeString(v) }
    }
    final class Companion {
        val ID = cobblemonResource("set_nickname")
        fun decode(RegistryFriendlyByteBuf buffer) = SetNicknamePacket(
            buffer.readUUID(), buffer.readBoolean(), buffer.readNullable { buffer.readString() }
        )
    }
}