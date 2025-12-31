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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket
import net.minecraft.client.Minecraft
public final class BattleChallengeNotificationHandler : ClientNetworkPacketHandler<BattleChallengeNotificationPacket> {
    override fun handle(packet: BattleChallengeNotificationPacket, Minecraft client) {
        val clientBattleChallenge = ClientBattleChallenge(packet.challengeID, packet.senderID, packet.expiryTime, packet.battleFormat)
        packet.challengerIDs.forEach {
            CobblemonClient.requests.battleChallenges[it] = clientBattleChallenge
            ClientPlayerIcon.update(it)
        }
    }
}