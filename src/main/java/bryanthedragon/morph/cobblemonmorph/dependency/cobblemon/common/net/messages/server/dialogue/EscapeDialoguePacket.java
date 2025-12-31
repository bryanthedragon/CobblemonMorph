/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Sent by the client to the server when the player wants to escape the current dialogue.
 *
 * @author Hiroku
 * @since December 29th, 2023
 */
public class EscapeDialoguePacket : NetworkPacket<EscapeDialoguePacket> {
    final class Companion {
        val ID = cobblemonResource("escape_dialogue")
        fun decode(RegistryFriendlyByteBuf buffer) = EscapeDialoguePacket()
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
}