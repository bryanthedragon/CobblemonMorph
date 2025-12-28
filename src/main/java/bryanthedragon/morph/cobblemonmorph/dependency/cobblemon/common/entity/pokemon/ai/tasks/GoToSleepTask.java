/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.ai.tasks

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonActivities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
import net.minecraft.world.entity.ai.behavior.declarative.Trigger
import net.minecraft.world.entity.ai.memory.MemoryModuleType
final class GoToSleepTask {
    fun create(
        onlyFromStatus: Boolean
    ): OneShot<PokemonEntity> {
        return BehaviorBuilder.create {
            it.group(
                it.absent(MemoryModuleType.WALK_TARGET),
                it.registered(CobblemonMemories.POKEMON_DROWSY)
            ).apply(it) { _, pokemonDrowsy ->
                Trigger { _, entity, _ ->
                    val hasSleepStatus = entity.pokemon.status?.status === Statuses.SLEEP
                    if (entity.behaviour.resting.canSleep && ((it.tryGet(pokemonDrowsy).orElse(false) && entity.canSleepAt(entity.blockPosition().below())) || hasSleepStatus)) {
                        if (!hasSleepStatus) {
                            if (onlyFromStatus) {
                                return@Trigger false
                            }
                            entity.pokemon.status = PersistentStatusContainer(Statuses.SLEEP)
                        }
                        entity.brain.eraseMemory(MemoryModuleType.WALK_TARGET)
                        entity.navigation.stop()
                        entity.brain.setActiveActivityToFirstValid(listOf(CobblemonActivities.POKEMON_SLEEPING_ACTIVITY))
                        entity.brain.setMemory(CobblemonMemories.POKEMON_SLEEPING, true)
                        return@Trigger true
                    } else {
                        return@Trigger false
                    }
                }
            }
        }
    }
}