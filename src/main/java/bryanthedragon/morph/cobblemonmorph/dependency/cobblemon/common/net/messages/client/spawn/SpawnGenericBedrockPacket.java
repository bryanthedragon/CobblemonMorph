/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

/**
 * Spawn packet for [GenericBedrockEntity]. Wraps around vanilla spawn packet behaviour.
 *
 * @author Hiroku
 * @since May 22nd, 2023
 */
public class SpawnGenericBedrockPacket(
    val category: ResourceLocation,
    val aspects: Set<String>,
    val poseType: PoseType,
    val scale: Float,
    val width: Float,
    val height: Float,
    val startAge: Int,
    vanillaSpawnPacket: ClientboundAddEntityPacket
) : SpawnExtraDataEntityPacket<SpawnGenericBedrockPacket, GenericBedrockEntity>(vanillaSpawnPacket) {
    override val ResourceLocation id = ID

    override fun applyData(entity: GenericBedrockEntity, level: ClientLevel) {
        entity.category = this.category
        entity.aspects = this.aspects
        entity.entityData.set(GenericBedrockEntity.POSE_TYPE, this.poseType)
        entity.scale = this.scale
        entity.colliderWidth = this.width
        entity.colliderHeight = this.height
        entity.delegate.initialize(entity)
        entity.tickCount = startAge
        (entity.delegate as GenericBedrockClientDelegate).updateAge(startAge)
    }

    override fun checkType(Entity entity): Boolean = entity is GenericBedrockEntity

    override fun encodeEntityData(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(this.category)
        buffer.writeSizedInt(size = IntSize.U_BYTE, poseType.ordinal)
        buffer.writeFloat(scale)
        buffer.writeFloat(width)
        buffer.writeFloat(height)
        buffer.writeInt(startAge)
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect) }
    }

    final class Companion {
        val ID = cobblemonResource("spawn_generic_bedrock_entity")
        fun decode(RegistryFriendlyByteBuf buffer): SpawnGenericBedrockPacket {
            val category = buffer.readIdentifier()
            val poseType = PoseType.entries[buffer.readSizedInt(IntSize.U_BYTE)]
            val scale = buffer.readFloat()
            val width = buffer.readFloat()
            val height = buffer.readFloat()
            val startAge = buffer.readInt()
            val aspects = buffer.readList { it.readString() }.toSet()
            val vanillaPacket = decodeVanillaPacket(buffer)
            return SpawnGenericBedrockPacket(category, aspects, poseType, scale, width, height, startAge, vanillaPacket)
        }
    }
}
