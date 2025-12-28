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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.CancelTradePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.UpdateTradeOfferPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class UpdateTradeOfferHandler : ServerNetworkPacketHandler<UpdateTradeOfferPacket> {
    override fun handle(packet: UpdateTradeOfferPacket, server: MinecraftServer, player: ServerPlayer) {
        val trade = TradeManager.getActiveTrade(player.uuid) ?: return player.sendPacket(CancelTradePacket())
        val tradeParticipant = trade.getTradeParticipant(player.uuid)
        val newOffer = packet.newOffer
        if (newOffer == null) {
            trade.updateOffer(tradeParticipant, null)
        } else {
            val (pokemonId, partyPosition) = newOffer
            val party = player.party()
            val pokemon = party[partyPosition]
            if (pokemon == null || pokemon.uuid != pokemonId) {
                return
            } else if (!pokemon.tradeable) {
                return
            } else {
                trade.updateOffer(tradeParticipant, pokemon)
            }
        }
    }
}