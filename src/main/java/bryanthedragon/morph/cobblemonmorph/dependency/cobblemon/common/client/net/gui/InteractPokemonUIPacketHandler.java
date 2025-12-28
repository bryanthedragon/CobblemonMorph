/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.createPokemonInteractGui
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.InteractPokemonUIPacket
import net.minecraft.client.Minecraft
final class InteractPokemonUIPacketHandler: ClientNetworkPacketHandler<InteractPokemonUIPacket> {
    override fun handle(packet: InteractPokemonUIPacket, client: Minecraft) {
        client.setScreen(createPokemonInteractGui(
            packet.pokemonID,
            packet.canMountShoulder,
            packet.canGiveHeld,
            packet.canGiveCosmetic,
            packet.canRide
        ))
    }
}