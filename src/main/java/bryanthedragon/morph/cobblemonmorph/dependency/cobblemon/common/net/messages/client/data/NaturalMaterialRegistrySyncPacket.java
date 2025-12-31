/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterial
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class NaturalMaterialRegistrySyncPacket(naturalMaterials: List<NaturalMaterial>) : DataRegistrySyncPacket<NaturalMaterial, NaturalMaterialRegistrySyncPacket>(naturalMaterials) {
    final class Companion {
        val ID = cobblemonResource("natural_materials")
        fun decode(RegistryFriendlyByteBuf buffer) = NaturalMaterialRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }


    override val id = ID
    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: NaturalMaterial) {
        buffer.writeNullable(entry.item) {pb, type -> pb.writeIdentifier(entry.item!!)}
        buffer.writeNullable(entry.tag) { pb, type -> pb.writeString(NaturalMaterials.gson.toJson("#" + entry.tag?.tag?.location.toString()) ) }
        buffer.writeNullable(entry.returnItem) { pb, type -> pb.writeIdentifier(entry.returnItem!!) }
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): NaturalMaterial {
        return NaturalMaterial (
                content = 0, // Server handles incrementing of the fossil machine
                item = buffer.readNullable { pb -> pb.readIdentifier() },
                tag = buffer.readNullable { pb -> NaturalMaterials.gson.fromJson(buffer.readString(), ItemTagCondition.class) },
                returnItem = buffer.readNullable { pb -> pb.readIdentifier() }
        )
    }

    override fun synchronizeDecoded(entries: Collection<NaturalMaterial>) {
        NaturalMaterials.reload(mapOf(cobblemonResource("natural_materials") to entries.toList())  )
    }
}