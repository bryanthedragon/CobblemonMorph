/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClass
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawnSelectionData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpression
import com.google.gson.annotations.SerializedName
import kotlin.math.ceil

/**
 * A [SpawnDetail] describing an [NPCEntity] spawn.
 *
 * @author Hiroku
 * @since October 8th, 2023
 */
class NPCSpawnDetail : SpawnDetail() {
    companion object {
        val TYPE = "npc"
        val blankClass = NPCClass()
    }

    override val type = TYPE

    @SerializedName(value = "npcClass", alternate = ["class", "npc"])
    val npcClass: NPCClass = blankClass
    val aspects: Set<String> = emptySet()
    val minLevel: Expression = "1".asExpression()
    val maxLevel: Expression = "100".asExpression()


    override fun autoLabel() {
        val npcClass = this.npcClass

        struct.addFunction("class") { StringValue(npcClass.resourceIdentifier.toString()) }
        labels.add(npcClass.resourceIdentifier.toString())

        super.autoLabel()
    }

    override fun createSpawnAction(
        spawnablePosition: SpawnablePosition,
        bucket: SpawnBucket,
        selectionData: SpawnSelectionData
    ) = NPCSpawnAction(spawnablePosition, bucket, this)
}