/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic

import com.bedrockk.molang.runtime.struct.QueryStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.IdentifierDataSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.PoseTypeDataSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.StringSetDataSerializer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PosableEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn.SpawnGenericBedrockPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerEntity
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Pose
import net.minecraft.world.level.Level

class GenericBedrockEntity(world: Level) : Entity(CobblemonEntities.GENERIC_BEDROCK_ENTITY, world), PosableEntity, Schedulable {
    companion object {
        val CATEGORY = SynchedEntityData.defineId(GenericBedrockEntity::class.java, IdentifierDataSerializer)
        val ASPECTS = SynchedEntityData.defineId(GenericBedrockEntity::class.java, StringSetDataSerializer)
        val POSE_TYPE = SynchedEntityData.defineId(GenericBedrockEntity::class.java, PoseTypeDataSerializer)
        val SCALE = SynchedEntityData.defineId(GenericBedrockEntity::class.java, EntityDataSerializers.FLOAT)
    }

    var savesToWorld = false
    override val schedulingTracker = SchedulingTracker()
    override val delegate = if (world.isClientSide) {
        // Don't import because scanning for imports is a CI job we'll do later to detect errant access to client from server
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate()
    } else {
        GenericBedrockServerDelegate()
    }

    override val struct: QueryStruct = QueryStruct(hashMapOf())

    var category: ResourceLocation
        get() = this.entityData.get(CATEGORY)
        set(value) {
            this.entityData.set(CATEGORY, value)
        }

    var aspects: Set<String>
        get() = this.entityData.get(ASPECTS)
        set(value) {
            this.entityData.set(ASPECTS, value)
        }

    var scale: Float
        get() = this.entityData.get(SCALE)
        set(value) {
            this.entityData.set(SCALE, value)
        }

    var colliderWidth = 1F
        set(value) {
            super.getBbWidth()
            field = value
            refreshDimensions()
        }

    var colliderHeight = 1F
        set(value) {
            field = value
            refreshDimensions()
        }

    var syncAge = false

    init {
        addPosableFunctions(struct)
    }

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        builder.define(CATEGORY, cobblemonResource("generic"))
        builder.define(ASPECTS, emptySet())
        builder.define(SCALE, 1F)
        builder.define(POSE_TYPE, PoseType.NONE)
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        this.category = ResourceLocation.parse(nbt.getString(DataKeys.GENERIC_BEDROCK_CATEGORY))
        this.aspects = nbt.getList(DataKeys.GENERIC_BEDROCK_ASPECTS, Tag.TAG_STRING.toInt()).map { it.asString }.toSet()
        this.entityData.set(POSE_TYPE, PoseType.values()[nbt.getByte(DataKeys.GENERIC_BEDROCK_POSE_TYPE).toInt()])
        this.scale = nbt.getFloat(DataKeys.GENERIC_BEDROCK_SCALE)
        this.colliderWidth = nbt.getFloat(DataKeys.GENERIC_BEDROCK_COLLIDER_WIDTH)
        this.colliderHeight = nbt.getFloat(DataKeys.GENERIC_BEDROCK_COLLIDER_HEIGHT)
        this.syncAge = nbt.getBoolean(DataKeys.GENERIC_BEDROCK_SYNC_AGE)
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        nbt.putString(DataKeys.GENERIC_BEDROCK_CATEGORY, category.toString())
        nbt.put(DataKeys.GENERIC_BEDROCK_ASPECTS, ListTag().also { it.addAll(aspects.map(StringTag::valueOf)) })
        nbt.putByte(DataKeys.GENERIC_BEDROCK_POSE_TYPE, getCurrentPoseType().ordinal.toByte())
        nbt.putFloat(DataKeys.GENERIC_BEDROCK_SCALE, scale)
        nbt.putFloat(DataKeys.GENERIC_BEDROCK_COLLIDER_WIDTH, colliderWidth)
        nbt.putFloat(DataKeys.GENERIC_BEDROCK_COLLIDER_HEIGHT, colliderHeight)
        nbt.putBoolean(DataKeys.GENERIC_BEDROCK_SYNC_AGE, syncAge)
    }

    override fun isPickable() = true
    override fun canBeCollidedWith() = true

    override fun shouldBeSaved() = super.shouldBeSaved() && this.savesToWorld
    override fun getDimensions(pose: Pose) = EntityDimensions.scalable(colliderWidth, colliderHeight).scale(scale)
    override fun getCurrentPoseType(): PoseType = this.entityData.get(POSE_TYPE)

    override fun getAddEntityPacket(entityTrackerEntry: ServerEntity) = ClientboundCustomPayloadPacket(
        SpawnGenericBedrockPacket(
            category = category,
            aspects = aspects,
            poseType = getCurrentPoseType(),
            scale = scale,
            width = colliderWidth,
            height = colliderHeight,
            startAge = if (syncAge) tickCount else 0,
            vanillaSpawnPacket = super.getAddEntityPacket(entityTrackerEntry) as ClientboundAddEntityPacket
        )
    ) as Packet<ClientGamePacketListener>

    override fun tick() {
        super.tick()
        delegate.tick(this)
        schedulingTracker.update(1/20F)
    }
}