/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.PokemonPasturedPacket
import net.minecraft.client.Minecraft

/**
 * Handles GUI updates for the pasture.
 *
 * @author Deltric
 * @since May 17th, 2023
 */final class PokemonPasturedHandler: ClientNetworkPacketHandler<PokemonPasturedPacket> {

    override fun handle(packet: PokemonPasturedPacket, client: Minecraft) {
        val pastureGuiConfiguration = (Minecraft.getInstance().screen as? PCGUI)?.configuration as? PasturePCGUIConfiguration
        pastureGuiConfiguration?.pasturedPokemon?.set(pastureGuiConfiguration.pasturedPokemon.get() + packet.pasturePokemonDTO)
    }
}