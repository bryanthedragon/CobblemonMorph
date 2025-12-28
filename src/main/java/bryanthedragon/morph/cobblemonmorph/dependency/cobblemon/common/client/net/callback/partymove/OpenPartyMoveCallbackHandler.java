/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.callback.partymove

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback.OpenPartyMoveCallbackPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyMoveSelectCancelledPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.partymove.PartyPokemonMoveSelectedPacket
import net.minecraft.client.Minecraft
final class OpenPartyMoveCallbackHandler : ClientNetworkPacketHandler<OpenPartyMoveCallbackPacket> {
    override fun handle(packet: OpenPartyMoveCallbackPacket, client: Minecraft) {
        val pokemonToMoves = packet.pokemonList.toMap()
        val cancel: (Any) -> Unit = {
            CobblemonNetwork.sendToServer(PartyMoveSelectCancelledPacket(uuid = packet.uuid))
            if (it is MoveSelectGUI) {
                it.closeProperly()
            } else if (it is PartySelectGUI) {
                it.closeProperly()
            }
        }


        lateinit var partySelectConfiguration: PartySelectConfiguration

        fun makeMoveSelectConfiguration(pokemonSelectDTO: PartySelectPokemonDTO): MoveSelectConfiguration {
            return MoveSelectConfiguration(
                title = "".text(),
                moves = pokemonToMoves[pokemonSelectDTO]!!,
                onCancel = cancel,
                onBack = { Minecraft.getInstance().setScreen(PartySelectGUI(partySelectConfiguration)) },
                onSelect = { gui, moveSelectDTO ->
                    val pokemonIndex = packet.pokemonList.indexOfFirst { it.first == pokemonSelectDTO }
                    val moveIndex = pokemonToMoves[pokemonSelectDTO]!!.indexOf(moveSelectDTO)
                    CobblemonNetwork.sendToServer(PartyPokemonMoveSelectedPacket(packet.uuid, pokemonIndex, moveIndex))
                    gui.closeProperly()
                }
            )
        }

        partySelectConfiguration = PartySelectConfiguration(
            title = packet.partyTitle,
            pokemon = pokemonToMoves.keys.toList(),
            onCancel = cancel,
            onBack = cancel,
            onSelect = { _, it -> Minecraft.getInstance().setScreen(MoveSelectGUI(makeMoveSelectConfiguration(it))) }
        )

        Minecraft.getInstance().setScreen(PartySelectGUI(partySelectConfiguration))
    }
}