/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.addFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallPosableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity.CaptureState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity.CaptureState.NOT
import net.minecraft.network.syncher.EntityDataAccessor

public class EmptyPokeBallClientDelegate : PokeBallPosableState(), EntitySideDelegate<EmptyPokeBallEntity> {
    override val stateEmitter: SettableObservable<CaptureState> = SettableObservable(NOT)
    override val shakeEmitter = SimpleObservable<Unit>()

    lateinit var currentEntity: EmptyPokeBallEntity
    override val schedulingTracker
        get() = getEntity().schedulingTracker

    override fun getEntity() = currentEntity

    override fun updatePartialTicks(partialTicks: Float) {
        this.currentPartialTicks = partialTicks
    }

    override fun initialize(entity: EmptyPokeBallEntity) {
        this.currentEntity = entity
        age = entity.tickCount
        initSubscriptions()
        this.runtime.environment.query.addFunctions(getEntity().struct.functions)
    }

    override fun tick(entity: EmptyPokeBallEntity) {
        super.tick(entity)
        incrementAge(entity)
    }

    override fun onSyncedDataUpdated(data: EntityDataAccessor<*>) {
        super.onSyncedDataUpdated(data)
        when (data) {
            EmptyPokeBallEntity.CAPTURE_STATE -> stateEmitter.set(currentEntity.captureState)
            EmptyPokeBallEntity.SHAKE -> shakeEmitter.emit(Unit)
            EmptyPokeBallEntity.ASPECTS -> currentAspects = currentEntity.aspects
        }
    }
}