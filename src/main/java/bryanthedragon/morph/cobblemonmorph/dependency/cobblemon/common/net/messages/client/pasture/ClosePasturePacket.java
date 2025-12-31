/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to close the pasture GUI if it's open. Used when the block is broken.
 *
 * @author Hiroku
 * @since April 17th, 2023
 */
public class ClosePasturePacket : NetworkPacket<ClosePasturePacket> {
    final class Companion {
        val ID = cobblemonResource("close_pasture")
        fun decode(RegistryFriendlyByteBuf buffer) = ClosePasturePacket()
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
}