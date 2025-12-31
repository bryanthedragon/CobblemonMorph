/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.SummaryUIPacket
import net.minecraft.client.Minecraft
public final class SummaryUIPacketHandler: ClientNetworkPacketHandler<SummaryUIPacket> {
    override fun handle(packet: SummaryUIPacket, Minecraft client) {
        try {
            Summary.open(
                party = packet.pokemon,
                editable = packet.editable
            )
        } catch (Exception e) {
            Cobblemon.LOGGER.debug("Failed to open the summary from the SummaryUI packet handler", e)
        }
    }
}