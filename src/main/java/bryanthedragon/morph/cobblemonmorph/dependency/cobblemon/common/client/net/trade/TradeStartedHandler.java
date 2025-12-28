/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.TradeGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket.TradeablePokemon
import net.minecraft.client.Minecraft
final class TradeStartedHandler : ClientNetworkPacketHandler<TradeStartedPacket> {
    override fun handle(packet: TradeStartedPacket, client: Minecraft) {
        Cobblemon.LOGGER.error("handle trade start from: " + packet.traderId)
        val trade = ClientTrade()
        CobblemonClient.trade = trade
        Minecraft.getInstance().setScreen(
            TradeGUI(
                trade,
                packet.traderId,
                packet.traderName,
                packet.traderParty.toMutableList(),
                CobblemonClient.storage.party.map { it?.let(::TradeablePokemon) }.toMutableList()
            )
        )
        CobblemonClient.requests.tradeOffers.remove(packet.traderId)
        ClientPlayerIcon.update(packet.traderId)
    }
}