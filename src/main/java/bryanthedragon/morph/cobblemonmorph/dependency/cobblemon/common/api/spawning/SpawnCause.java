/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType

open class SpawnCause(
    val spawner: Spawner,
    Entity entity? = null
) : SpawningInfluence {
    val entityWorldId = entity?.level()?.dimension()
    val entityId = entity?.id
    val entityUUID = entity?.uuid
    val entityType = entity?.type

    val Entity entity?
        get() =
            if (entityType == EntityType.PLAYER) {
                entityUUID?.let {
                    server()?.playerList?.getPlayer(it)
                }
            } else if (entityWorldId != null && entityId != null) {
                server()?.getLevel(entityWorldId)?.getEntity(entityId)
            } else {
                null
            }
}