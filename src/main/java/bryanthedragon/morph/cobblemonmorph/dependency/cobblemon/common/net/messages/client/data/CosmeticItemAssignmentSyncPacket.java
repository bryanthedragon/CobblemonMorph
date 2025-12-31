/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCosmeticItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.cosmetic.CosmeticItemAssignment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.RegistryFriendlyByteBuf

public class CosmeticItemAssignmentSyncPacket(
    assignments: Collection<CosmeticItemAssignment>
) : DataRegistrySyncPacket<CosmeticItemAssignment, CosmeticItemAssignmentSyncPacket>(assignments) {

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: CosmeticItemAssignment) {
        CosmeticItemAssignment.PACKET_CODEC.encode(buffer, entry)
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): CosmeticItemAssignment? {
        return CosmeticItemAssignment.PACKET_CODEC.decode(buffer)
    }

    override fun synchronizeDecoded(entries: Collection<CosmeticItemAssignment>) {
        CobblemonCosmeticItems.reload(entries.associateBy { it.id })
    }

    final class Companion {
        val ID = cobblemonResource("cosmetic_item_assignment_sync")
        fun decode(RegistryFriendlyByteBuf buffer) = CosmeticItemAssignmentSyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }
}