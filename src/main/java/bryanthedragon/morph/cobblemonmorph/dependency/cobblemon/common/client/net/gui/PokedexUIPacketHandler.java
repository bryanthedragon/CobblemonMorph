/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokedex.PokedexGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui.PokedexUIPacket
import net.minecraft.client.Minecraft
public final class PokedexUIPacketHandler: ClientNetworkPacketHandler<PokedexUIPacket> {
    override fun handle(packet: PokedexUIPacket, Minecraft client) {
        try {
            PokedexGUI.open(CobblemonClient.clientPokedexData, packet.type, packet.initSpecies, packet.blockPos)
        } catch (Exception e) {
            Cobblemon.LOGGER.debug("Failed to open the Pokedex from the Pokedex UI packet", e)
        }
    }
}