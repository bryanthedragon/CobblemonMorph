/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMadeInvalidChoicePacket
import net.minecraft.client.Minecraft
final class BattleMadeInvalidChoiceHandler : ClientNetworkPacketHandler<BattleMadeInvalidChoicePacket> {
    override fun handle(packet: BattleMadeInvalidChoicePacket, client: Minecraft) {
        //Remove previous selected action, so user can select a new action
        val battle = CobblemonClient.battle ?: return
        battle.mustChoose = true
        val gui = Minecraft.getInstance().screen
        if (gui is BattleGUI) {
            gui.removeInvalidBattleActionSelection()
        }
    }
}