/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokedex

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.PokedexLearnedInformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.pokedex.ServerConfirmedRegisterHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Send confirmation to client after new Pokédex registration
 *
 * Handled by [ServerConfirmedRegisterHandler]
 */
class ServerConfirmedRegisterPacket(
    val species: ResourceLocation,
    val newInformation: PokedexLearnedInformation
): NetworkPacket<ServerConfirmedRegisterPacket> {
    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeIdentifier(species)
        buffer.writeEnumConstant(newInformation)
    }

    companion object {
        fun decode(buffer: RegistryFriendlyByteBuf) = ServerConfirmedRegisterPacket(buffer.readIdentifier(), buffer.readEnumConstant(PokedexLearnedInformation::class.java))
        val ID = cobblemonResource("server_confirmed_scan")
    }
}