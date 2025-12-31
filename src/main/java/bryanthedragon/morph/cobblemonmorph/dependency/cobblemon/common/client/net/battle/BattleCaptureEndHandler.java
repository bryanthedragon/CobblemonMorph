/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.MoveTileOffscreenAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleCaptureEndPacket
import net.minecraft.client.Minecraft
public final class BattleCaptureEndHandler : ClientNetworkPacketHandler<BattleCaptureEndPacket> {
    override fun handle(packet: BattleCaptureEndPacket, Minecraft client) {
        val battle = CobblemonClient.battle ?: return
        val overlay = CobblemonClient.battleOverlay
        val (_, activeBattlePokemon) = battle.getPokemonFromPNX(packet.targetPNX)
        if (packet.succeeded) {
            activeBattlePokemon.animations.add(MoveTileOffscreenAnimation().also { overlay.after(seconds = it.duration) { activeBattlePokemon.ballCapturing = null } })
        }
        activeBattlePokemon.ballCapturing = null
    }
}