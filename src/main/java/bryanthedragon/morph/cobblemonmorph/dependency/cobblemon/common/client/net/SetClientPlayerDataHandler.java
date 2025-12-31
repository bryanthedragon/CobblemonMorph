/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.SetClientPlayerDataPacket
import net.minecraft.client.Minecraft
public final class SetClientPlayerDataHandler : ClientNetworkPacketHandler<SetClientPlayerDataPacket> {
    override fun handle(packet: SetClientPlayerDataPacket, Minecraft client) {
        if (packet.isIncremental) {
            packet.type.incrementalAfterDecodeAction.invoke(packet.playerData)
        }
        else {
            packet.type.afterDecodeAction.invoke(packet.playerData)
        }

    }
}