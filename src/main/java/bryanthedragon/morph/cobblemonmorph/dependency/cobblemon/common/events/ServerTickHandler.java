/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
final class ServerTickHandler {
    private var secondsTick = 0

    fun onTick(server: MinecraftServer) {
        BattleRegistry.tick()

        secondsTick++

        if (secondsTick == 20) {
            secondsTick = 0

            // Party tick
            for (player in server.playerList.players) {
                player.party().onSecondPassed(player)
            }
        }
    }
}