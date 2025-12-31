/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.ai

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.mainThreadRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveFloat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
import net.minecraft.world.entity.ai.behavior.declarative.Trigger
import net.minecraft.world.entity.ai.memory.MemoryModuleType
public final class MeleeAttackTask {
    fun create(range: Expression, cooldownTicks: Expression): OneShot<LivingEntity> = BehaviorBuilder.create {
        it.group(
            it.present(MemoryModuleType.ATTACK_TARGET),
            it.absent(MemoryModuleType.ATTACK_COOLING_DOWN)
        ).apply(it) { attackTarget, cooldown ->
            Trigger { world, entity, _ ->
                mainThreadRuntime.withQueryValue("entity", entity.asMostSpecificMoLangValue())
                val range = mainThreadRuntime.resolveFloat(range)
                val cooldownTicks = mainThreadRuntime.resolveInt(cooldownTicks)

                val attackTarget = it.get(attackTarget)
                if (entity.boundingBox.inflate(range.toDouble(), 0.5, range.toDouble()).intersects(attackTarget.boundingBox)) {
                    entity.doHurtTarget(attackTarget)
                    cooldown.setWithExpiry(true, cooldownTicks.toLong())
                    true
                } else {
                    false
                }
            }
        }
    }
}