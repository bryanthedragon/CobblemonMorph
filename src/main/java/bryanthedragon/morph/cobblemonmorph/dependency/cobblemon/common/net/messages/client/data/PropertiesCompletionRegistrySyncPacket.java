/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

internal class PropertiesCompletionRegistrySyncPacket(suggestions: Collection<PropertiesCompletionProvider.SuggestionHolder>) : DataRegistrySyncPacket<PropertiesCompletionProvider.SuggestionHolder, PropertiesCompletionRegistrySyncPacket>(suggestions) {

    override val id = ID

    override fun encodeEntry(
        RegistryFriendlyByteBuf buffer,
        entry: PropertiesCompletionProvider.SuggestionHolder
    ) {
        buffer.writeCollection(entry.keys) { pb, value -> pb.writeString(value) }
        buffer.writeCollection(entry.suggestions) { pb, value -> pb.writeString(value) }
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): PropertiesCompletionProvider.SuggestionHolder? {
        val keys = buffer.readList { pb -> pb.readString() }
        val suggestions = buffer.readList { pb -> pb.readString() }
        return PropertiesCompletionProvider.SuggestionHolder(keys, suggestions)
    }

    override fun synchronizeDecoded(entries: Collection<PropertiesCompletionProvider.SuggestionHolder>) {
        entries.forEach { suggestionHolder ->
            PropertiesCompletionProvider.inject(suggestionHolder.keys, suggestionHolder.suggestions)
        }
    }

    final class Companion {
        val ID = cobblemonResource("properties_completion_sync")
        fun decode(RegistryFriendlyByteBuf buffer): PropertiesCompletionRegistrySyncPacket = PropertiesCompletionRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }

}