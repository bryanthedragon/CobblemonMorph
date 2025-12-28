/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Mark
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Marks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import io.netty.buffer.ByteBuf
import net.minecraft.network.RegistryFriendlyByteBuf

class MarksUpdatePacket(pokemon: () -> Pokemon?, value: MutableSet<Mark>): SingleUpdatePacket<MutableSet<Mark>, MarksUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(buffer: RegistryFriendlyByteBuf) {
        buffer.writeCollection(this.value) { pb, value -> pb.writeIdentifier(value.identifier) }
    }

    override fun set(pokemon: Pokemon, value: MutableSet<Mark>) {
        pokemon.marks = value
    }

    companion object {
        val ID = cobblemonResource("marks_update")
        fun decode(buffer: RegistryFriendlyByteBuf): MarksUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val identifiers = buffer.readList(ByteBuf::readIdentifier).toList()
            val marks = identifiers.map { Marks.getByIdentifier(it) }.filterNotNull().toMutableSet()
            return MarksUpdatePacket(pokemon, marks)
        }
    }
}
