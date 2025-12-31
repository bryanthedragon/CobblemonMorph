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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeUpdatedPacket
import net.minecraft.client.Minecraft
public final class TradeUpdatedHandler : ClientNetworkPacketHandler<TradeUpdatedPacket> {
    override fun handle(packet: TradeUpdatedPacket, Minecraft client) {
        val trade = CobblemonClient.trade ?: return

        if (packet.playerId == Minecraft.getInstance().player?.uuid) {
            trade.myOffer.set(packet.pokemon)
        } else {
            trade.oppositeOffer.set(packet.pokemon)
        }
        trade.oppositeAcceptedMyOffer.set(false)
        trade.acceptedOppositeOffer = false
    }
}