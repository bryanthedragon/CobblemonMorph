/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.afterOnServer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.aqua
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.GO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |turn|NUMBER
 *
 * It is now turn NUMBER.
 * @author Deltric
 * @since January 22nd, 2022
 */
public class TurnInstruction(val message: BattleMessage) : InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        // TODO maybe tell the client that the turn number has changed
        val turnNumber = message.argumentAt(0)?.toInt() ?: return

        battle.dispatch {
            battle.sendToActors(BattleMakeChoicePacket())
            battle.broadcastChatMessage(battleLang("turn", turnNumber).aqua())
            battle.turn(turnNumber)
            GO
        }
    }
}