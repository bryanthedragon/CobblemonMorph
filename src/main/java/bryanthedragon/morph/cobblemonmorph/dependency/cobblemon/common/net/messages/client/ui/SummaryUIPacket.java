/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.UnsplittablePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class SummaryUIPacket internal constructor(val pokemon: List<Pokemon>, val editable: Boolean): NetworkPacket<SummaryUIPacket>, UnsplittablePacket {

    override val id = ID

    override fun encode(buffer: RegistryFriendlyByteBuf) {
        buffer.writeBoolean(editable)
        buffer.writeCollection(this.pokemon) { _, pokemon -> Pokemon.S2C_CODEC.encode(buffer, pokemon) }
    }

    companion object {
        val ID = cobblemonResource("summary_ui")
        fun decode(buffer: RegistryFriendlyByteBuf) = SummaryUIPacket(buffer.readList { _ -> Pokemon.S2C_CODEC.decode(buffer) }, buffer.readBoolean())
    }
}