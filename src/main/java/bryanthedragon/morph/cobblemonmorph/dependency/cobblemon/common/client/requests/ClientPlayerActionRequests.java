/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.requests

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientTeamRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeRequest
import java.util.*

class ClientPlayerActionRequests {
    val battleChallenges = mutableMapOf<UUID, ClientBattleChallenge>()      // Player to challenge
    val multiBattleTeamRequests = mutableMapOf<UUID, ClientTeamRequest>()   // Player to team up invite
    val tradeOffers = mutableMapOf<UUID, ClientTradeRequest>()              // Player to trade offer

    fun getRequestsFrom(player: UUID): List<ClientPlayerActionRequest> = buildList {
        battleChallenges[player]?.let { add(it) }
        multiBattleTeamRequests[player]?.let { add(it) }
        tradeOffers[player]?.let { add(it) }
    }
}