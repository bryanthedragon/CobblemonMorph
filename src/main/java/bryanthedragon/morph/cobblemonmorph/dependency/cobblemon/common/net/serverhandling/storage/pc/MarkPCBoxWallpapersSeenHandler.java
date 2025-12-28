/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.MarkPCBoxWallpapersSeenPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class MarkPCBoxWallpapersSeenHandler : ServerNetworkPacketHandler<MarkPCBoxWallpapersSeenPacket> {
    override fun handle(packet: MarkPCBoxWallpapersSeenPacket, server: MinecraftServer, player: ServerPlayer) {
        val pc = PCLinkManager.getPC(player) ?: return
        pc.markWallpapersSeen(packet.seenTextures)
    }
}