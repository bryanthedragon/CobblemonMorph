/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.ViewerCountBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.block.AdjustBlockEntityViewerCountPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class AdjustBlockEntityViewerCountHandler : ServerNetworkPacketHandler<AdjustBlockEntityViewerCountPacket> {
    override fun handle(packet: AdjustBlockEntityViewerCountPacket, server: MinecraftServer, ServerPlayer player) {
        val blockEntity = player.level().getBlockEntity(packet.blockPos)
        if (blockEntity != null && blockEntity is ViewerCountBlockEntity) {
            if (packet.increment) blockEntity.incrementViewerCount()
            else blockEntity.decrementViewerCount()
        }
    }
}