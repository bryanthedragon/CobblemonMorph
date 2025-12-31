/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonBehaviourFlag
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.UpdatePastureConflictFlagPacket
import net.minecraft.client.Minecraft
public final class UpdatePastureConflictFlagHandler : ClientNetworkPacketHandler<UpdatePastureConflictFlagPacket> {
    override fun handle(packet: UpdatePastureConflictFlagPacket, Minecraft client) {
        val screen = client.screen as? PCGUI ?: return
        val pastureWidget = screen.storage.pastureWidget ?: return
        for (slot in pastureWidget.pastureScrollList.children()) {
            if (slot.pokemon.pokemonId == packet.pokemonId) {
                // Update behavior flags
                slot.pokemon.behaviourFlags = if (packet.enabled) {
                    slot.pokemon.behaviourFlags + PokemonBehaviourFlag.PASTURE_CONFLICT
                } else {
                    slot.pokemon.behaviourFlags - PokemonBehaviourFlag.PASTURE_CONFLICT
                }

                slot.conflictButton.setEnabled(packet.enabled)
                break
            }
        }
    }
}