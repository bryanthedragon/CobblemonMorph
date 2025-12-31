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
 * Packet sent to the client when the trade has started.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeProcessStartedHandler]
 */
public class TradeProcessStartedPacket(val isTradeInitiator: Boolean) : NetworkPacket<TradeProcessStartedPacket> {
    final class Companion {
        val ID = cobblemonResource("trade_process_started")
        fun decode(RegistryFriendlyByteBuf buffer) = TradeProcessStartedPacket(buffer.readBoolean())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(isTradeInitiator)
    }
}