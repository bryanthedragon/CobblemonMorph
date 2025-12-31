/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PosableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPosableAnimationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolve
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity

public interface PosableEntity {
    fun getCurrentPoseType(): PoseType
    val delegate: EntitySideDelegate<*>
    val struct: QueryStruct

    fun addPosableFunctions(struct: QueryStruct) {
        struct.addFunction("pose_type") { StringValue(getCurrentPoseType().name) }
        delegate.addToStruct(struct)
    }

    fun playAnimation(animation: String, expressions: List<String> = emptyList()) {
        val animations = setOf(animation)
        this as Entity

        val delegate = delegate
        if (delegate is PosableState) {
            delegate.runtime.resolve(expressions.asExpressionLike())
            delegate.addFirstAnimation(animations)
        } else {
            val level = level() as? ServerLevel ?: return
            val entityId = id
            val packet = PlayPosableAnimationPacket(entityId, animations, emptyList())
            level.players().filter { it.distanceTo(this) < 128.0 }.forEach { packet.sendToPlayer(it) }
        }
    }
}