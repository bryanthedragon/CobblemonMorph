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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket
import net.minecraft.client.Minecraft
public final class TradeCancelledHandler : ClientNetworkPacketHandler<TradeCancelledPacket> {
    override fun handle(packet: TradeCancelledPacket, Minecraft client) {
        val trade = CobblemonClient.trade ?: return
        trade.cancelEmitter.emit(Unit)
        CobblemonClient.trade = null
    }
}