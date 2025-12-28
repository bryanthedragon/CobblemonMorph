/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyCallbackPacket
import net.minecraft.client.Minecraft
final class OpenPartyCallbackHandler : ClientNetworkPacketHandler<OpenPartyCallbackPacket> {
    override fun handle(packet: OpenPartyCallbackPacket, client: Minecraft) {
        client.setScreen(
            PartySelectGUI(
                title = packet.title,
                pokemon = packet.pokemon,
//                usePortraits = packet.usePortraits,
//                animate = packet.animate,
                uuid = packet.uuid
            )
        )
    }
}