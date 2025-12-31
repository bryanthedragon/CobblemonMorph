/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class FossilRegistrySyncPacket(fossils: List<Fossil>) : DataRegistrySyncPacket<Fossil, FossilRegistrySyncPacket>(fossils) {
    final class Companion {
        val ID = cobblemonResource("fossils")
        fun decode(RegistryFriendlyByteBuf buffer) = FossilRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }


    override val id = ID
    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: Fossil) {
        buffer.writeIdentifier(entry.identifier)
        buffer.writeString(Fossils.gson.toJson(entry.result, PokemonProperties.class))
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): Fossil {
        return Fossil (
                identifier = buffer.readIdentifier(),
                result = Fossils.gson.fromJson(buffer.readString(), PokemonProperties.class),
                fossils = emptyList()
        )
    }

    override fun synchronizeDecoded(entries: Collection<Fossil>) {
        Fossils.reload(entries.associateBy { it.identifier })
    }
}