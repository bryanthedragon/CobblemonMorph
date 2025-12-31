/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnExtraDataEntityPacket
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.Entity

public class SpawnExtraDataEntityHandler<T : SpawnExtraDataEntityPacket<T, E>, E : Entity> : ClientNetworkPacketHandler<T> {
    override fun handle(packet: T, Minecraft client) {
        packet.spawnAndApply(client)
    }

}