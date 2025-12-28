/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork.sendPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.ChangeTradeAcceptancePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class ChangeTradeAcceptanceHandler : ServerNetworkPacketHandler<ChangeTradeAcceptancePacket> {
    override fun handle(packet: ChangeTradeAcceptancePacket, server: MinecraftServer, player: ServerPlayer) {
        val trade = TradeManager.getActiveTrade(player.uuid) ?: return player.sendPacket(TradeCancelledPacket())
        val tradeParticipant = trade.getTradeParticipant(player.uuid)
        if (trade.getOpposingOffer(tradeParticipant).pokemon?.uuid == packet.pokemonOfferId) {
            trade.updateAcceptance(tradeParticipant, packet.newAcceptance)
        }
    }
}