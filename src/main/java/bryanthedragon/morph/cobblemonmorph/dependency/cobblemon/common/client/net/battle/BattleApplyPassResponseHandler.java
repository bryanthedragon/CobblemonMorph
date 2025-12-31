/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForcePassActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleApplyPassResponsePacket
import net.minecraft.client.Minecraft
public final class BattleApplyPassResponseHandler : ClientNetworkPacketHandler<BattleApplyPassResponsePacket> {
    override fun handle(packet: BattleApplyPassResponsePacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        val req = battle.pendingActionRequests.firstOrNull { it.response == null } ?: return
        val res = ForcePassActionResponse()
        val gui = Minecraft.getInstance().screen
        if (gui is BattleGUI) {
            gui.selectAction(req, res)
        } else {
            req.response = res
            battle.checkForFinishedChoosing()
        }
    }
}