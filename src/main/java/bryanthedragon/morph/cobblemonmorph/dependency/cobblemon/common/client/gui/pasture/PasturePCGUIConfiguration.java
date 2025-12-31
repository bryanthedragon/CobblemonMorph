/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket
import java.util.*

public class PasturePCGUIConfiguration(
    val pastureId: UUID,
    val limit: Int,
    val pasturedPokemon: SettableObservable<List<OpenPasturePacket.PasturePokemonDataDTO>>,
    var permissions: PasturePermissions
) : PCGUIConfiguration(
    exitFunction = { it.closeNormally(unlink = true) },
    showParty = false,
    selectOverride = { pcGui, position, pokemon ->
        if (pokemon != null && !pokemon.isFainted() && pokemon.tetheringId == null && permissions.canPasture) {
            CobblemonNetwork.sendToServer(PasturePokemonPacket(pokemonId = pokemon.uuid, pastureId = pastureId))
            pcGui.playSound(CobblemonSounds.PC_CLICK)
        }
    },
    canSelect = { pokemon -> !pokemon.isFainted() && pokemon.tetheringId == null }
)