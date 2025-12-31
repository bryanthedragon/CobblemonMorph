/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCPlayerModelType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.npc.NPCPlayerTexture
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeEnumConstant
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.syncher.EntityDataSerializer
public final class NPCPlayerTextureSerializer : EntityDataSerializer<NPCPlayerTexture> {
    val ID = cobblemonResource("npcplayertexture")

    fun write(RegistryFriendlyByteBuf buffer, texture: NPCPlayerTexture) {
        buffer.writeEnumConstant(texture.model)
        if (texture.model == NPCPlayerModelType.NONE) {
            return
        }
        buffer.writeByteArray(texture.texture)
    }

    fun read(RegistryFriendlyByteBuf buffer): NPCPlayerTexture {
        val model = buffer.readEnumConstant(NPCPlayerModelType.class)
        val texture = if (model == NPCPlayerModelType.NONE) ByteArray(1) else buffer.readByteArray()
        return NPCPlayerTexture(texture, model)
    }

    override fun codec() = StreamCodec.of(::write, ::read)
    override fun copy(texture: NPCPlayerTexture) = NPCPlayerTexture(texture.texture, texture.model)
}