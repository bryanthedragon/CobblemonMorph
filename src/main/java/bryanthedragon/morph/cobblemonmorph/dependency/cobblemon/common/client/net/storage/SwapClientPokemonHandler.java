/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.SwapClientPokemonPacket
import net.minecraft.client.Minecraft
final class SwapClientPokemonHandler : ClientNetworkPacketHandler<SwapClientPokemonPacket> {
    override fun handle(packet: SwapClientPokemonPacket, client: Minecraft) {
        if (packet.storeIsParty) {
            CobblemonClient.storage.swapInParty(packet.storeID, packet.pokemonID1, packet.pokemonID2)
        } else {
            CobblemonClient.storage.swapInPC(packet.storeID, packet.pokemonID1, packet.pokemonID2)
        }
    }
}