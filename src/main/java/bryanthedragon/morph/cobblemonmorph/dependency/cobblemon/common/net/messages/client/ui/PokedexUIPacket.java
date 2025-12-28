/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.gui.PokedexUIPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.pokedex.PokedexType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.core.BlockPos
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Tells the client to open the Pokédex interface.
 *
 * Handled by [PokedexUIPacketHandler].
 */
class PokedexUIPacket(val type: PokedexType, val initSpecies: ResourceLocation? = null, val blockPos: BlockPos? = null): NetworkPacket<PokedexUIPacket> {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeEnumConstant(type)
        buffer.writeNullable(initSpecies) { pb, value -> pb.writeIdentifier(value) }
        buffer.writeNullable(blockPos) { pb, value -> pb.writeBlockPos(value) }
    }

    companion object {
        val ID = cobblemonResource("pokedex_ui")

        fun decode(buffer: RegistryFriendlyByteBuf) = PokedexUIPacket(buffer.readEnumConstant(PokedexType::class.java), buffer.readNullable { it.readIdentifier() }, buffer.readNullable { it.readBlockPos() })
    }
}