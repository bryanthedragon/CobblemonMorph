/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to the client to close the currently open NPC editor, if one is open.
 *
 * @author Hiroku
 * @since February 4th, 2024
 */
public class CloseNPCEditorPacket : NetworkPacket<CloseNPCEditorPacket> {
    final class Companion {
        val ID = cobblemonResource("close_npc_editor")
        fun decode(RegistryFriendlyByteBuf buffer) = CloseNPCEditorPacket()
    }
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
}