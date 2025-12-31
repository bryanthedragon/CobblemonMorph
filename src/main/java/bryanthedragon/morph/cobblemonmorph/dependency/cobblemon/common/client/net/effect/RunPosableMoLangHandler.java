/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PosableState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PosableEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolve
import net.minecraft.client.Minecraft
public final class RunPosableMoLangHandler : ClientNetworkPacketHandler<RunPosableMoLangPacket> {
    override fun handle(packet: RunPosableMoLangPacket, Minecraft client) {
        val world = client.level ?: return
        val entity = world.getEntity(packet.entityId) ?: return
        if (entity is PosableEntity) {
            val state = entity.delegate as? PosableState ?: return
            for (expression in packet.expressions.map { it.asExpressionLike() }) {
                state.runtime.resolve(expression)
            }
        }
    }
}