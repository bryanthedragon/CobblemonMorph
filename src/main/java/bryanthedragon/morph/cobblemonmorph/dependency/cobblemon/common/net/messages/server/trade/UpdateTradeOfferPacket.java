/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readPartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readUUID
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeNullable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writePartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeUUID
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

public class UpdateTradeOfferPacket(val newOffer: Pair<UUID, PartyPosition>?): NetworkPacket<UpdateTradeOfferPacket> {
    final class Companion {
        val ID = cobblemonResource("update_trade_offer")
        fun decode(RegistryFriendlyByteBuf buffer) = UpdateTradeOfferPacket(buffer.readNullable { buffer.readUUID() to buffer.readPartyPosition() })
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(newOffer) { buffer, (pokemonId, partyPosition) ->
            buffer.writeUUID(pokemonId)
            buffer.writePartyPosition(partyPosition)
        }
    }
}