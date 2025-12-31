/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.tasks

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getBlockPositions
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
import net.minecraft.world.entity.ai.behavior.declarative.Trigger
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.memory.WalkTarget
import net.minecraft.world.phys.AABB
public final class FindRestingPlaceTask {
    fun create(horizontalSearchDistance: Int, verticalSearchDistance: Int): OneShot<PokemonEntity> {
        return BehaviorBuilder.create {
            it.group(
                it.absent(MemoryModuleType.WALK_TARGET),
                it.present(CobblemonMemories.POKEMON_DROWSY),
                it.absent(CobblemonMemories.PATH_COOLDOWN)
            ).apply(it) { walkTarget, pokemonDrowsy, restPathCooldown ->
                Trigger { world, entity, _ ->
                    return@Trigger if (it.get(pokemonDrowsy) && entity.pokemon.status?.status != Statuses.SLEEP) {
                        entity.brain.setMemoryWithExpiry(CobblemonMemories.PATH_COOLDOWN, true, 80)
                        val position = entity.level()
                            .getBlockPositions(AABB.ofSize(entity.position(), horizontalSearchDistance.toDouble(), verticalSearchDistance.toDouble(), horizontalSearchDistance.toDouble()))
                            .filter(entity::canSleepAt)
//                            .randomOrNull()
                            .minByOrNull { it.distToCenterSqr(entity.position()) }
                        if (position != null) {
                            walkTarget.set(WalkTarget(position.below(), 0.3F, 1))
                        }
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }
}