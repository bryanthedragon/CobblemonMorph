/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.wallpaper.ChangePCBoxWallpaperPacket
import net.minecraft.client.Minecraft
public final class ChangePCBoxWallpaperHandler : ClientNetworkPacketHandler<ChangePCBoxWallpaperPacket> {
    override fun handle(packet: ChangePCBoxWallpaperPacket, Minecraft client) {
        CobblemonClient.storage.changeBoxWallpaper(packet.storeID, packet.boxNumber, packet.wallpaper)
    }
}