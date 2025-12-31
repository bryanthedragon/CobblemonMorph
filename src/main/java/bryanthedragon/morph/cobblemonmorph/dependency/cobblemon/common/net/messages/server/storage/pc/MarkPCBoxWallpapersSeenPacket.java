/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Packet sent to the server to indicate that some set of wallpapers have now been seen and don't need to be presented
 * as new the next time the PC is opened. The packet is sent once the wallpapers button is clicked.
 *
 * @author Hiroku
 * @since February 10th, 2025
 */
public class MarkPCBoxWallpapersSeenPacket(val seenTextures: Set<ResourceLocation>) : NetworkPacket<MarkPCBoxWallpapersSeenPacket> {
    final class Companion {
        val ID = cobblemonResource("mark_pc_box_wallpapers_seen")
        fun decode(RegistryFriendlyByteBuf buffer) = MarkPCBoxWallpapersSeenPacket(buffer.readList { ResourceLocation.parse(it.readString()) }.toSet())
    }

    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeCollection(seenTextures.map { it.toString() }) { _, it -> buffer.writeString(it) }
    }
}