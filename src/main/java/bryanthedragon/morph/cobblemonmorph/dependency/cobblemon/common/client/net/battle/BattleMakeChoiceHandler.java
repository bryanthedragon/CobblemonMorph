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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import net.minecraft.client.Minecraft
public final class BattleMakeChoiceHandler : ClientNetworkPacketHandler<BattleMakeChoicePacket> {
    override fun handle(packet: BattleMakeChoicePacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        CobblemonClient.battleOverlay.passedSeconds = 0F
        battle.mustChoose = true
    }
}