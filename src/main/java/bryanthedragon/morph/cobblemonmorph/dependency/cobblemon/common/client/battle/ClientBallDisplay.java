/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPosableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall

/**
 * Handles the state for a capture PokéBall in a battle on the client side.
 *
 * @author Hiroku
 * @since July 2nd, 2022
 */
public class ClientBallDisplay(val pokeBall: PokeBall, val aspects: Set<String>) : PokeBallPosableState() {
    override val stateEmitter = SettableObservable(EmptyPokeBallEntity.CaptureState.FALL)
    override val shakeEmitter = SimpleObservable<Unit>()
    override val schedulingTracker = ClientTaskTracker

    override fun getEntity() = null
    override fun updatePartialTicks(partialTicks: Float) {
        this.currentPartialTicks += partialTicks
    }

    var scale = 1F

    fun start() {
        initSubscriptions()

        after(seconds = 1F) {
            lerp(seconds = 0.3F) { scale = 1 - it }
            after(seconds = 0.3F) {
                stateEmitter.set(EmptyPokeBallEntity.CaptureState.SHAKE)
                lerp(seconds = 0.3F) { scale = it }
            }
        }
    }
}