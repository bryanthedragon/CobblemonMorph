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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.AcceptTradeRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

/**
 * Processes a player's acceptance of a [TradeRequest].
 *
 * @author Hiroku
 * @since March 12th, 2023
 */
public final class AcceptTradeRequestHandler : ServerNetworkPacketHandler<AcceptTradeRequestPacket> {
    override fun handle(packet: AcceptTradeRequestPacket, server: MinecraftServer, ServerPlayer player) {
        TradeManager.acceptRequest(player, packet.tradeOfferId)
    }
}