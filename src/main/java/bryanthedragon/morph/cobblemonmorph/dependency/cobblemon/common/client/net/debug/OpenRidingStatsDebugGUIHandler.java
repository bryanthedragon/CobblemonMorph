/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.debug

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.debug.riding.RidingStatsDebugGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.debug.OpenRidingStatsDebugGUIPacket
import net.minecraft.client.Minecraft
final class OpenRidingStatsDebugGUIHandler : ClientNetworkPacketHandler<OpenRidingStatsDebugGUIPacket> {
    override fun handle(
        packet: OpenRidingStatsDebugGUIPacket,
        client: Minecraft
    ) {
        val vehicle = Minecraft.getInstance().player?.vehicle as? PokemonEntity ?: return
        client.setScreen(RidingStatsDebugGUI(vehicle))
    }
}