/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.ai

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getNearbyBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.mainThreadRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveDouble
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveFloat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.BlockPosTracker
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
import net.minecraft.world.entity.ai.behavior.declarative.Trigger
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.memory.WalkTarget
import net.minecraft.world.phys.AABB
public final class GoToHealingMachineTask {
    fun create(
        horizontalSearchRange: Expression,
        verticalSearchRange: Expression,
        speedMultiplier: Expression,
        completionRange: Expression
    ): OneShot<in LivingEntity> {
        return BehaviorBuilder.create {
            it.group(
                it.absent(MemoryModuleType.WALK_TARGET),
                it.registered(MemoryModuleType.LOOK_TARGET),
                it.absent(CobblemonMemories.NPC_BATTLING)
            ).apply(it) { walkTarget, lookTarget, _ ->
                Trigger { world, entity, time ->
                    if (entity !is NPCEntity) {
                        return@Trigger false
                    }

                    val horizontalSearchRange = mainThreadRuntime.resolveDouble(horizontalSearchRange)
                    val verticalSearchRange = mainThreadRuntime.resolveDouble(verticalSearchRange)
                    val speedMultiplier = mainThreadRuntime.resolveFloat(speedMultiplier)
                    val completionRange = mainThreadRuntime.resolveInt(completionRange)

                    if ((entity.party?.getHealingRemainderPercent() ?: 0F) > 0F) {
                        val npcPos = entity.blockPosition()
                        val nearestFreeHealer = world
                            .getNearbyBlockEntities(
                                box = AABB.ofSize(
                                    entity.position(),
                                    horizontalSearchRange,
                                    verticalSearchRange,
                                    horizontalSearchRange
                                ),
                                blockEntityType = CobblemonBlockEntities.HEALING_MACHINE
                            )
                            .filterNot { it.second.isInUse }
                            .minByOrNull { it.first.distSqr(npcPos) }
                            ?.first
                        if (nearestFreeHealer != null) {
                            walkTarget.set(WalkTarget(nearestFreeHealer, speedMultiplier, completionRange))
                            lookTarget.set(BlockPosTracker(nearestFreeHealer))
                            return@Trigger true
                        }
                    }
                    return@Trigger false
                }
            }
        }
    }
}