/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.entry.DexEntries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.entry.PokedexEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class DexEntrySyncPacket(dexEntries: Collection<PokedexEntry>) :
    DataRegistrySyncPacket<PokedexEntry, DexEntrySyncPacket>(dexEntries) {
    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: PokedexEntry) {
        entry.encode(buffer)
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): PokedexEntry? {
        return PokedexEntry.decode(buffer)
    }

    override fun synchronizeDecoded(entries: Collection<PokedexEntry>) {
        DexEntries.reload(entries.associateBy { it.id })
    }

    override val id = ID

    final class Companion {
        val ID = cobblemonResource("dex_entry_sync")
        fun decode(RegistryFriendlyByteBuf buffer) = DexEntrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }
}