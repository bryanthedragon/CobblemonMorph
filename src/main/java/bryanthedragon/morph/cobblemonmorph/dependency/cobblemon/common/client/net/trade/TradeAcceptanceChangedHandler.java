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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeAcceptanceChangedPacket
import net.minecraft.client.Minecraft
public final class TradeAcceptanceChangedHandler : ClientNetworkPacketHandler<TradeAcceptanceChangedPacket> {
    override fun handle(packet: TradeAcceptanceChangedPacket, Minecraft client) {
        if (CobblemonClient.trade?.myOffer?.get()?.uuid == packet.pokemonId) {
            CobblemonClient.trade!!.oppositeAcceptedMyOffer.set(packet.accepted)
        } else if (CobblemonClient.trade?.oppositeOffer?.get()?.uuid == packet.pokemonId) {
            CobblemonClient.trade!!.acceptedOppositeOffer = packet.accepted
        }
    }
}