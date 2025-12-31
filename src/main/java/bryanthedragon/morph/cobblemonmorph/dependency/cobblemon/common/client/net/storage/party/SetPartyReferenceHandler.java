/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.party.SetPartyReferencePacket
import net.minecraft.client.Minecraft
public final class SetPartyReferenceHandler : ClientNetworkPacketHandler<SetPartyReferencePacket> {
    override fun handle(packet: SetPartyReferencePacket, Minecraft client) {
        CobblemonClient.storage.setPartyStore(packet.storeID)
    }
}