/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.font
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets.BattleMessagePane
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket
import net.minecraft.client.Minecraft
import net.minecraft.locale.Language
public final class BattleMessageHandler : ClientNetworkPacketHandler<BattleMessagePacket> {
    override fun handle(packet: BattleMessagePacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        val textRenderer = Minecraft.getInstance().font
        for (message in packet.messages) {
            val line = message.copy().bold().font(CobblemonResources.DEFAULT_LARGE)
            val lines = Language.getInstance().getVisualOrder(textRenderer.splitter.splitLines(line, BattleMessagePane.LINE_WIDTH, line.style))
            battle.messages.add(lines)
        }
    }
}