/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PartyOverlayDataControl
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.ExpGainedDataPacket
import net.minecraft.client.Minecraft
final class ExpGainedDataPacketHandler: ClientNetworkPacketHandler<ExpGainedDataPacket> {
    override fun handle(packet: ExpGainedDataPacket, client: Minecraft) {
        PartyOverlayDataControl.pokemonGainedExp(
            packet.pokemonUUID,
            packet.oldLevel,
            packet.expGained,
            packet.countOfMovesLearned
        )
    }
}