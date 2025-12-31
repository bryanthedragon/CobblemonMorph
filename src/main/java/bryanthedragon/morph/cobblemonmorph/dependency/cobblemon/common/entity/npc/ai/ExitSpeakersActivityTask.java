/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMemories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import net.minecraft.world.entity.ai.behavior.OneShot
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder
import net.minecraft.world.entity.ai.behavior.declarative.Trigger
import net.minecraft.world.entity.schedule.Activity

@Deprecated("Deprecated in favour of the JSON system")public final class ExitSpeakersActivityTask {
    fun create(): OneShot<NPCEntity> {
        return BehaviorBuilder.create {
            it.group(
                it.absent(CobblemonMemories.DIALOGUES)
            ).apply(it) { _ ->
                Trigger { _, entity, _ ->
                    entity.brain.setActiveActivityIfPossible(Activity.IDLE)
                    return@Trigger true
                }
            }
        }
    }
}