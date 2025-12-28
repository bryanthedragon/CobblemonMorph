/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket
import net.minecraft.client.Minecraft
final class TradeOfferNotificationHandler : ClientNetworkPacketHandler<TradeOfferNotificationPacket> {
    override fun handle(packet: TradeOfferNotificationPacket, client: Minecraft) {
        CobblemonClient.requests.tradeOffers[packet.senderID] = ClientTradeRequest(packet.requestID, packet.senderID, packet.expiryTime)
        ClientPlayerIcon.update(packet.senderID)
    }
}