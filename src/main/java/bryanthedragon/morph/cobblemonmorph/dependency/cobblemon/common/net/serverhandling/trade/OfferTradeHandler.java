/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager.TradeRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.OfferTradePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

/**
 * Processes a player's interaction request to trade with another player. If valid, creates a respective [TradeRequest]
 * and sends a [TradeOfferNotificationPacket] to the player to decide upon.
 *
 * @author Hiroku
 * @since March 12th, 2023
 */
public final class OfferTradeHandler : ServerNetworkPacketHandler<OfferTradePacket> {
    override fun handle(packet: OfferTradePacket, server: MinecraftServer, ServerPlayer player) {
        val targetPlayerEntity = packet.offeredPlayerId.getPlayer() ?: return
        TradeManager.sendRequest(TradeRequest(player, targetPlayerEntity))
    }
}