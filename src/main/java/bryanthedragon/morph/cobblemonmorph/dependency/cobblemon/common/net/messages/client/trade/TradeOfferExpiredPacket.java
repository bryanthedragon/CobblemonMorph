/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Packet fired to tell the client that a trade offer expired.
 *
 * Handled by [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.trade.TradeOfferExpiredHandler].
 *
 * @param senderID The unique identifier of the party that sent the request.
 * @param expired Whether this cancellation is due to expiration.
 *
 * @author Hiroku
 * @since March 11th, 2023
 */
class TradeOfferExpiredPacket(val senderID: UUID) : NetworkPacket<TradeOfferExpiredPacket> {
    companion object {
        val ID = cobblemonResource("trade_offer_canceled")
        fun decode(buffer: RegistryFriendlyByteBuf) = TradeOfferExpiredPacket(buffer.readUUID())
    }

    override val id = ID
    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeUUID(senderID)
    }

    constructor(request: TradeManager.TradeRequest) : this(request.senderID)
}