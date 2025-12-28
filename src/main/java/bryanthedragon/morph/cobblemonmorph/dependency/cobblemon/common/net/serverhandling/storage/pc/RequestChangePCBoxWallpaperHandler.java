/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonUnlockableWallpapers
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage.ChangePCBoxWallpaperEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.ClosePCPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.wallpaper.ChangePCBoxWallpaperPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.RequestChangePCBoxWallpaperPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
final class RequestChangePCBoxWallpaperHandler : ServerNetworkPacketHandler<RequestChangePCBoxWallpaperPacket> {
    override fun handle(packet: RequestChangePCBoxWallpaperPacket, server: MinecraftServer, player: ServerPlayer) {
        val pc = PCLinkManager.getPC(player) ?: return run { ClosePCPacket(null).sendToPlayer(player) }
        if (pc.boxes.size <= packet.boxNumber || Cobblemon.wallpapers[player.uuid]?.contains(packet.wallpaper) == false) {
            return
        }

        CobblemonUnlockableWallpapers.unlockableWallpapers.values.find { it.texture == packet.wallpaper }?.let { unlockable ->
            if (!unlockable.enabled || unlockable.id !in pc.unlockedWallpapers) {
                // Bro did you just try to hack on a wallpaper? How embarrassing.
                return
            }
        }

        val box = pc.boxes[packet.boxNumber]
        val event = ChangePCBoxWallpaperEvent.Pre(player, box, packet.wallpaper, packet.altWallpaper)
        CobblemonEvents.CHANGE_PC_BOX_WALLPAPER_EVENT_PRE.postThenFinally(
            event = event,
            ifSucceeded = { preEvent ->
                box.wallpaper = preEvent.altWallpaper?.let { preEvent.altWallpaper } ?: preEvent.wallpaper
                CobblemonEvents.CHANGE_PC_BOX_WALLPAPER_EVENT_POST.post(ChangePCBoxWallpaperEvent.Post(player, box, preEvent.wallpaper, preEvent.altWallpaper))
            },
            finally = {
                ChangePCBoxWallpaperPacket(pc.uuid, packet.boxNumber, box.wallpaper).sendToPlayer(player)
            }
        )
    }
}
