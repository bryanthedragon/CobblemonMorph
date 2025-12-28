/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.BestSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import net.minecraft.world.entity.Entity

/**
 * Event fired when an entity is spawned using the [BestSpawner]. The entity that was spawned
 * and the [SpawnablePosition] of the spawn are provided. Cancelling this event will prevent the
 * entity from spawning.
 *
 * @author Hiroku
 * @since April 22nd, 2023
 */
class SpawnEvent<T : Entity>(val entity: T, val spawnablePosition: SpawnablePosition) : Cancelable()