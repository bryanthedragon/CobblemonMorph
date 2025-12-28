/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket
import net.minecraft.client.Minecraft
final class OpenPastureHandler : ClientNetworkPacketHandler<OpenPasturePacket> {
    override fun handle(packet: OpenPasturePacket, client: Minecraft) {

        val pcConfiguration = PasturePCGUIConfiguration(
            pastureId = packet.pastureId,
            limit = packet.limit,
            permissions = packet.permissions,
            pasturedPokemon = SettableObservable(packet.tetheredPokemon)
        )

        client.setScreen(PCGUI(pc = CobblemonClient.storage.pcStores[packet.pcId]!!, party = CobblemonClient.storage.party, configuration = pcConfiguration))
    }
}