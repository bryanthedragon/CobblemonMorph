/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.ai

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.mainThreadRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveFloat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
import net.minecraft.world.entity.ai.behavior.declarative.Trigger
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.memory.WalkTarget
public final class MoveToAttackTargetTask {
    fun create(
        speedMultiplier: Expression = "0.5".asExpression(),
        closeEnoughDistance: Expression = "0".asExpression()
    ): OneShot<LivingEntity> = BehaviorBuilder.create {
        it.group(
            it.present(MemoryModuleType.ATTACK_TARGET),
            it.registered(MemoryModuleType.WALK_TARGET)
        ).apply(it) { attackTarget, walkTarget ->
            Trigger { _, entity, _ ->
                mainThreadRuntime.withQueryValue("entity", entity.asMostSpecificMoLangValue())
                val speedMultiplier = mainThreadRuntime.resolveFloat(speedMultiplier)
                val closeEnoughDistance = mainThreadRuntime.resolveInt(closeEnoughDistance)

                val attackTarget = it.get(attackTarget)
                val position = attackTarget.position()
                val walkTarget = it.tryGet(walkTarget).orElse(null)
                if (walkTarget == null || walkTarget.target.currentPosition().distanceToSqr(position) > closeEnoughDistance) {
                    entity.brain.setMemory(MemoryModuleType.WALK_TARGET, WalkTarget(attackTarget, speedMultiplier, closeEnoughDistance))
                    true
                } else {
                    false
                }
            }
        }
    }
}
