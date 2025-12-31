/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc.UnlinkPlayerFromPCHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to remove the PC link for the player.
 *
 * Handled by [UnlinkPlayerFromPCHandler].
 *
 * @author Village
 * @since January 18th, 2023
 */
public class UnlinkPlayerFromPCPacket : NetworkPacket<UnlinkPlayerFromPCPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
    final class Companion {
        val ID = cobblemonResource("unlink_player_from_pc")
        fun decode(RegistryFriendlyByteBuf buffer) = UnlinkPlayerFromPCPacket()
    }
}