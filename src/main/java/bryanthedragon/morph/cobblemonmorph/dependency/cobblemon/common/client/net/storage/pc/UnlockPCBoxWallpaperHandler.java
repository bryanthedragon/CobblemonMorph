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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.wallpaper.UnlockPCBoxWallpaperPacket
import net.minecraft.client.Minecraft
public final class UnlockPCBoxWallpaperHandler : ClientNetworkPacketHandler<UnlockPCBoxWallpaperPacket> {
    override fun handle(packet: UnlockPCBoxWallpaperPacket, Minecraft client) {
        PCBoxWallpaperRepository.availableWallpapers.add(packet.texture)
    }
}