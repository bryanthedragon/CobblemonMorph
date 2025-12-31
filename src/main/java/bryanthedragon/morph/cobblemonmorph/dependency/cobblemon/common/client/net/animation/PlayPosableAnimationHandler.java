/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.animation

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PosableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PosableEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPosableAnimationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolve
import net.minecraft.client.Minecraft
public final class PlayPosableAnimationHandler : ClientNetworkPacketHandler<PlayPosableAnimationPacket> {
    override fun handle(packet: PlayPosableAnimationPacket, Minecraft client) {
        val world = client.level ?: return
        val entity = world.getEntity(packet.entityId) ?: return
        if (entity is PosableEntity) {
            val delegate = entity.delegate
            if (delegate is PosableState) {
                for (expr in packet.expressions) {
                    delegate.runtime.resolve(expr.asExpressionLike())
                }
                delegate.addFirstAnimation(packet.animation)
            }
        }
    }
}