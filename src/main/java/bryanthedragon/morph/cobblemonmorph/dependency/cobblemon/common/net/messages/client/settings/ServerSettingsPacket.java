/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * A packet that will sync simple config settings to the client that shouldn't require to be data pack powered.
 *
 * @author Licious
 * @since September 25th, 2022
 */
public class ServerSettingsPacket internal constructor(
    val preventCompletePartyDeposit: Boolean,
    val displayEntityLevelLabel: Boolean,
    val displayEntityNameLabel: Boolean,
    val maxPokemonLevel: Int,
    val maxPokemonFriendship: Int,
    val maxDynamaxLevel: Int
) : NetworkPacket<ServerSettingsPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(Cobblemon.config.preventCompletePartyDeposit)
        buffer.writeBoolean(Cobblemon.config.displayEntityLevelLabel)
        buffer.writeBoolean(Cobblemon.config.displayEntityNameLabel)
        buffer.writeInt(Cobblemon.config.maxPokemonLevel)
        buffer.writeInt(Cobblemon.config.maxPokemonFriendship)
        buffer.writeInt(Cobblemon.config.maxDynamaxLevel)
    }
    final class Companion {
        val ID = cobblemonResource("server_settings")
        fun decode(RegistryFriendlyByteBuf buffer) = ServerSettingsPacket(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readInt(), buffer.readInt(), buffer.readInt())
    }
}