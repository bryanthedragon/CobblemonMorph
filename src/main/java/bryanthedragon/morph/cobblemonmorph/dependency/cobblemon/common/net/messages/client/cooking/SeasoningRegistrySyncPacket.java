/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.cooking

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Seasoning
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Seasonings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import net.minecraft.network.RegistryFriendlyByteBuf

public class SeasoningRegistrySyncPacket(seasonings: List<Seasoning>) :
    DataRegistrySyncPacket<Seasoning, SeasoningRegistrySyncPacket>(seasonings) {

    final class Companion {
        val ID = cobblemonResource("seasonings")
        fun decode(RegistryFriendlyByteBuf buffer) =
            SeasoningRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: Seasoning) {
        Seasoning.STREAM_CODEC.encode(buffer, entry)
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): Seasoning {
        return Seasoning.STREAM_CODEC.decode(buffer)
    }

    override fun synchronizeDecoded(entries: Collection<Seasoning>) {
        Seasonings.reloadEntries(entries)
    }
}
