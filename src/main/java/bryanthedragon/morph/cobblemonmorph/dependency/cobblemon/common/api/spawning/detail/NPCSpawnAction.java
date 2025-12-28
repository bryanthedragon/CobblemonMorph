/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt

/**
 * A [SpawnAction] for creating [NPCEntity]s.
 *
 * @author Hiroku
 * @since October 8th, 2023
 */
class NPCSpawnAction(spawnablePosition: SpawnablePosition, bucket: SpawnBucket, override val detail: NPCSpawnDetail) : SingleEntitySpawnAction<NPCEntity>(spawnablePosition, bucket, detail) {
    override fun createEntity(): NPCEntity {
        val npc = NPCEntity(spawnablePosition.world)
        npc.npc = detail.npcClass
        npc.appliedAspects.addAll(detail.aspects)
        val minLevel = spawnablePosition.runtime.resolveInt(detail.minLevel).coerceIn(1, Cobblemon.config.maxPokemonLevel)
        val maxLevel = spawnablePosition.runtime.resolveInt(detail.maxLevel).coerceIn(1, Cobblemon.config.maxPokemonLevel)
        val seedLevel = (minLevel..maxLevel).random()
        npc.initialize(seedLevel)
        return npc
    }
}