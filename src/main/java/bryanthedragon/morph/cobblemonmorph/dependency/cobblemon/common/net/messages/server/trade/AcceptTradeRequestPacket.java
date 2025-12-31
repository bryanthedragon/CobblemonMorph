/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager.TradeRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade.AcceptTradeRequestHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Packet sent when a player accepts a [TradeRequest] after receiving the respective [TradeOfferNotificationPacket].
 *
 * Handled by [AcceptTradeRequestHandler].
 *
 * @param requestID The unique identifier of the request that the player is responding to.
 * @param accept Whether the player accepted the team request.
 *
 * @author Hiroku
 * @since March 12th, 2023
 */
public class AcceptTradeRequestPacket(val tradeOfferId: UUID) : NetworkPacket<AcceptTradeRequestPacket> {
    final class Companion {
        val ID = cobblemonResource("accept_trade_request")
        fun decode(RegistryFriendlyByteBuf buffer) = AcceptTradeRequestPacket(buffer.readUUID())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(tradeOfferId)
    }
}