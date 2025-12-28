/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.gui.PCBoxWallpaperRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.PCBoxWallpapersPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.wallpaper.RequestPCBoxWallpapersPacket
import net.minecraft.client.Minecraft
final class RequestPCBoxWallpapersHandler : ClientNetworkPacketHandler<RequestPCBoxWallpapersPacket> {
    override fun handle(packet: RequestPCBoxWallpapersPacket, client: Minecraft) {
        PCBoxWallpaperRepository.findWallpapers(client.resourceManager)
        PCBoxWallpapersPacket(PCBoxWallpaperRepository.availableWallpapers).sendToServer()
    }
}