/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent to close the trade GUI as the trade was cancelled (programmatically, by log-off, or by player action).
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeCancelledHandler]
 *
 * @author Hiroku
 * @since March 5th, 2023
 */
public class TradeCancelledPacket : NetworkPacket<TradeCancelledPacket> {
    final class Companion {
        val ID = cobblemonResource("trade_cancelled")
        fun decode(RegistryFriendlyByteBuf buffer) = TradeCancelledPacket()
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {}
}