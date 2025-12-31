/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.def.PokedexDef
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.Dexes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class PokedexDexSyncPacket(
    dexes: Collection<PokedexDef>
) : DataRegistrySyncPacket<PokedexDef, PokedexDexSyncPacket>(dexes) {

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: PokedexDef) {
        PokedexDef.PACKET_CODEC.encode(buffer, entry)
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): PokedexDef? {
        return PokedexDef.PACKET_CODEC.decode(buffer)
    }

    override fun synchronizeDecoded(entries: Collection<PokedexDef>) {
        Dexes.reload(entries.associateBy { it.id })
    }

    final class Companion {
        val ID = cobblemonResource("pokedex_sync")
        fun decode(RegistryFriendlyByteBuf buffer) = PokedexDexSyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }
}