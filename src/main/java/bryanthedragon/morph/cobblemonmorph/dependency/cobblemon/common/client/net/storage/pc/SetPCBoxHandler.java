/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientBox
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.SetPCBoxPacket
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
final class SetPCBoxHandler : ClientNetworkPacketHandler<SetPCBoxPacket> {
    override fun handle(packet: SetPCBoxPacket, client: Minecraft) {
        CobblemonClient.storage.pcStores[packet.storeID]?.let { pc ->
            val boxNumber = packet.boxNumber
            while (pc.boxes.size <= boxNumber) { pc.boxes.add(ClientBox()) }
            pc.boxes[boxNumber] = ClientBox(if (packet.name.isBlank()) null else Component.literal(packet.name), packet.wallpaper)
            packet.pokemon.forEach { (slot, pokemon) -> pc.boxes[packet.boxNumber].slots[slot] = pokemon(client.level!!.registryAccess()) }
        }
    }
}