/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable.Companion.emitWhile
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable.Companion.filter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureEndPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureShakePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureStartPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang

/**
 * Wrapper object for an attempt at capturing a wild Pokémon during a battle.
 *
 * @author Hiroku
 * @since July 2nd, 2022
 */
class BattleCaptureAction(
    val battle: PokemonBattle,
    val targetPokemon: ActiveBattlePokemon,
    val pokeBallEntity: EmptyPokeBallEntity
) {
    val pokemonName = targetPokemon.battlePokemon?.getName() ?: "error".red()
    fun attach() {
        battle.sendUpdate(BattleCaptureStartPacket(pokeBallEntity.pokeBall.name, pokeBallEntity.aspects, targetPokemon.getPNX()))

        pokeBallEntity.dataTrackerEmitter
            .pipe(
                filter { it == EmptyPokeBallEntity.SHAKE },
                emitWhile { pokeBallEntity.isAlive && this in battle.captureActions }
            )
            .subscribe { battle.sendUpdate(BattleCaptureShakePacket(targetPokemon.getPNX(), pokeBallEntity.entityData.get(EmptyPokeBallEntity.SHAKE))) }

        pokeBallEntity.captureFuture.thenAccept { successful ->
            if (successful) {
                targetPokemon.battlePokemon?.gone = true
                battle.dispatchWaiting(2F) { battle.broadcastChatMessage(lang("capture.succeeded", pokemonName).green()) }
                battle.writeShowdownAction(">capture ${targetPokemon.getPNX()}")
            } else {
                battle.dispatchWaiting(2F) { battle.broadcastChatMessage(lang("capture.broke_free", pokemonName).red()) }
            }
            battle.sendUpdate(BattleCaptureEndPacket(targetPokemon.getPNX(), successful))
            battle.finishCaptureAction(this)
        }
    }
}
