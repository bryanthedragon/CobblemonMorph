/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

class BerryRegistrySyncPacket(berries: Collection<Berry>) : DataRegistrySyncPacket<Berry, BerryRegistrySyncPacket>(berries) {
    companion object {
        val ID = cobblemonResource("berry_sync")
        fun decode(buffer: RegistryFriendlyByteBuf) = BerryRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }

    override val id = ID
    override fun encodeEntry(buffer: RegistryFriendlyByteBuf, entry: Berry) {
        entry.encode(buffer)
    }

    override fun decodeEntry(buffer: RegistryFriendlyByteBuf): Berry? = Berry.decode(buffer)

    override fun synchronizeDecoded(entries: Collection<Berry>) {
        Berries.reload(entries.associateBy { it.identifier })
    }
}