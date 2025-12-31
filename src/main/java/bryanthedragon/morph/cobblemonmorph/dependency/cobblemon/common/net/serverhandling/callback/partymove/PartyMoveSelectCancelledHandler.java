/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.callback.partymove

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartyMoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyMoveSelectCancelledPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
public final class PartyMoveSelectCancelledHandler : ServerNetworkPacketHandler<PartyMoveSelectCancelledPacket> {
    override fun handle(packet: PartyMoveSelectCancelledPacket, server: MinecraftServer, ServerPlayer player) {
        PartyMoveSelectCallbacks.handleCancelled(player, packet.uuid)
    }
}