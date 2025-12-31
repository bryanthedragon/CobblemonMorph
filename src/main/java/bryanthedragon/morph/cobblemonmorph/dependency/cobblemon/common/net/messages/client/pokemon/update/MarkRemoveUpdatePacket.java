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
import net.minecraft.network.RegistryFriendlyByteBuf

public class MarkRemoveUpdatePacket(pokemon: () -> Pokemon?, value: Mark?): SingleUpdatePacket<Mark?, MarkRemoveUpdatePacket>(pokemon, value) {
    override val id = ID
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeNullable(this.value) { _, v -> buffer.writeIdentifier(v.identifier) }
    }

    override fun set(Pokemon pokemon, value: Mark?) {
        if (value != null) pokemon.exchangeMark(value, false)
    }

    final class Companion {
        val ID = cobblemonResource("mark_remove_update")
        fun decode(RegistryFriendlyByteBuf buffer): MarkRemoveUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val markIdentifier = buffer.readNullable { buffer.readIdentifier() }
            val mark = if (markIdentifier == null) null else Marks.getByIdentifier(markIdentifier)
            return MarkRemoveUpdatePacket(pokemon, mark)
        }
    }
}
