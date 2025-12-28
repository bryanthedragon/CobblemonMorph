/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBehaviours
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.BehaviourConfigurationContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.AddVariablesConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.ApplyBehaviours
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.BehaviourConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClass
import com.mojang.serialization.Dynamic
final class NPCBrain {
    fun configure(npcEntity: NPCEntity, npcClass: NPCClass, dynamic: Dynamic<*>) {
        var behaviourConfigurations: List<BehaviourConfig> = CobblemonBehaviours.autoNPCBehaviours.flatMap { it.configurations } + npcClass.behaviours
        if (npcEntity.behavioursAreCustom) {
            behaviourConfigurations = listOf(ApplyBehaviours().apply { behaviours.addAll(npcEntity.behaviours) })
        }
        behaviourConfigurations = behaviourConfigurations + AddVariablesConfig(npcClass.config)

        val ctx = BehaviourConfigurationContext()
        ctx.addMemories(CobblemonMemories.DIALOGUES)
        ctx.addMemories(CobblemonMemories.NPC_BATTLING)
        ctx.apply(npcEntity, behaviourConfigurations, dynamic)
        npcEntity.behaviours.clear()
        npcEntity.behaviours.addAll(ctx.appliedBehaviours)
    }
}