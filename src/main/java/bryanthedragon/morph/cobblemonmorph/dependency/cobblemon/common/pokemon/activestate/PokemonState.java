/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.getPlayer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.isPokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.playSoundServer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.UUIDUtil
import java.util.UUID
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level

sealed class PokemonState {
    final class Companion {

        // If we ever move to need more NBT/JSON save/load types other than ShoulderedState we need a registry Codec.
        val states = mapOf(
            "inactive" to InactivePokemonState.class,
            "sent-out" to SentOutState.class,
            ShoulderedState.ID to ShoulderedState.class
        )

        fun fromBuffer(RegistryFriendlyByteBuf buffer): PokemonState {
            val type = buffer.readString()
            return states[type]?.newInstance()?.readFromBuffer(buffer) ?: InactivePokemonState()
        }
    }

    val String name
        get() = states.entries.find { it.value == this.class }!!.key

    open fun getIcon(Pokemon pokemon): ResourceLocation? = null

    open fun writeToNBT(CompoundTag nbt): CompoundTag? {
        nbt.putString(DataKeys.POKEMON_STATE_TYPE, name)
        return nbt
    }

    open fun readFromNBT(CompoundTag nbt): PokemonState = this
    open fun writeToJSON(JsonObject json): JsonObject? = json

    open fun readFromJSON(JsonObject json): PokemonState = this
    open fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(name)
    }
    open fun readFromBuffer(RegistryFriendlyByteBuf buffer): PokemonState = this
}
public class InactivePokemonState : PokemonState() {
    override fun writeToNBT(CompoundTag nbt) = null
    override fun equals(other: Any?) = other === this || other is InactivePokemonState
    override fun hashCode() = 0
    final class Companion {
        @JvmStatic
        val CODEC: Codec<InactivePokemonState> = Codec.unit { InactivePokemonState() }
    }
}

sealed class ActivePokemonState : PokemonState() {
    abstract val entity: PokemonEntity?
    abstract fun recall()
}
public class SentOutState() : ActivePokemonState() {
    private var entityId: Int = -1
    private var dimension = Level.OVERWORLD

    override val entity: PokemonEntity?
        get() = Cobblemon.getLevel(dimension)?.getEntity(entityId) as? PokemonEntity

    constructor(entity: PokemonEntity): this() {
        this.entityId = entity.id
        this.dimension = entity.level().dimension()
    }

    override fun getIcon(Pokemon pokemon): ResourceLocation {
        val isBeingRidden = (pokemon.entity?.countPlayerPassengers() ?: 0) > 0
        val icon = if (isBeingRidden) "mounted" else "released"

        return cobblemonResource("textures/gui/party/party_icon_${icon}.png")
    }

    override fun writeToNBT(CompoundTag nbt) = null
    override fun writeToJSON(JsonObject json) = null

    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        super.writeToBuffer(buffer)
        buffer.writeInt(entityId)
        buffer.writeString(dimension.location().toString())
    }

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer): SentOutState {
        super.readFromBuffer(buffer)
        entityId = buffer.readInt()
        dimension = ResourceKey.create(ResourceKey.createRegistryKey(dimension.location()), ResourceLocation.parse(buffer.readString()))
        return this
    }

    fun update(entity: PokemonEntity) {
        entityId =  entity.id
        dimension = entity.level().dimension()
    }

    override fun recall() {
        entity?.discard()
    }
}
public class ShoulderedState() : ActivePokemonState() {
    var isLeftShoulder = false
    lateinit var UUID playerUUID
    lateinit var pokemonUUID uuid
    var stateId = UUID.randomUUID()

    constructor(UUID playerUUID, isLeftShoulder: Boolean, pokemonUUID uuid): this() {
        this.isLeftShoulder = isLeftShoulder
        this.playerUUID = playerUUID
        this.pokemonUUID = pokemonUUID
    }

    override val entity: PokemonEntity? = null

    override fun getIcon(Pokemon pokemon): ResourceLocation {
        val suffix = if (isLeftShoulder) "left" else "right"
        return cobblemonResource("textures/gui/party/party_icon_shoulder_$suffix.png")
    }
    override fun writeToNBT(CompoundTag nbt): CompoundTag {
        super.writeToNBT(nbt)
        nbt.putBoolean(DataKeys.POKEMON_STATE_SHOULDER, isLeftShoulder)
        nbt.putUUID(DataKeys.POKEMON_STATE_PLAYER_UUID, playerUUID)
        nbt.putUUID(DataKeys.POKEMON_STATE_ID, stateId)
        nbt.putUUID(DataKeys.POKEMON_STATE_POKEMON_UUID, pokemonUUID)
        return nbt
    }

    override fun readFromNBT(CompoundTag nbt): PokemonState {
        super.readFromNBT(nbt)
        isLeftShoulder = nbt.getBoolean(DataKeys.POKEMON_STATE_SHOULDER)
        playerUUID = nbt.getUUID(DataKeys.POKEMON_STATE_PLAYER_UUID)
        stateId = nbt.getUUID(DataKeys.POKEMON_STATE_ID)
        pokemonUUID = nbt.getUUID(DataKeys.POKEMON_STATE_POKEMON_UUID)
        return this
    }

    override fun writeToJSON(JsonObject json): JsonObject? {
        super.writeToJSON(json)
        json.addProperty(DataKeys.POKEMON_STATE_SHOULDER, isLeftShoulder)
        json.addProperty(DataKeys.POKEMON_STATE_PLAYER_UUID, playerUUID.toString())
        json.addProperty(DataKeys.POKEMON_STATE_ID, stateId.toString())
        json.addProperty(DataKeys.POKEMON_STATE_POKEMON_UUID, pokemonUUID.toString())
        return json
    }

    override fun readFromJSON(JsonObject json): PokemonState {
        super.readFromJSON(json)
        isLeftShoulder = json.get(DataKeys.POKEMON_STATE_SHOULDER).asBoolean
        playerUUID = UUID.fromString(json.get(DataKeys.POKEMON_STATE_PLAYER_UUID).asString)
        stateId = UUID.fromString(json.get(DataKeys.POKEMON_STATE_ID).asString)
        pokemonUUID = UUID.fromString(json.get(DataKeys.POKEMON_STATE_POKEMON_UUID).asString)
        return this
    }

    override fun writeToBuffer(RegistryFriendlyByteBuf buffer) {
        super.writeToBuffer(buffer)
        buffer.writeBoolean(isLeftShoulder)
        buffer.writeUUID(playerUUID)
        buffer.writeUUID(stateId)
        buffer.writeUUID(pokemonUUID)
    }

    override fun readFromBuffer(RegistryFriendlyByteBuf buffer): PokemonState {
        super.readFromBuffer(buffer)
        isLeftShoulder = buffer.readBoolean()
        playerUUID = buffer.readUUID()
        stateId = buffer.readUUID()
        pokemonUUID = buffer.readUUID()
        return this
    }

    /**
     * Removes the cobblemon from the player's shoulder. (currently not used)
     */
    override fun recall() {
        val player = playerUUID.getPlayer() ?: return
        val nbt = if (isLeftShoulder) player.shoulderEntityLeft else player.shoulderEntityRight
        if (this.isShoulderedPokemon(nbt)) {
            player.level().playSoundServer(player.position(), SoundEvents.CANDLE_FALL)
            if (isLeftShoulder) {
                player.shoulderEntityLeft = CompoundTag()
            } else {
                player.shoulderEntityRight = CompoundTag()
            }
            this.removeShoulderEffects(player)
        }
    }

    private fun removeShoulderEffects(ServerPlayer player) {
        val partyPokemon = player.party().find { pokemon -> pokemon.uuid == this.pokemonUUID }
        partyPokemon?.form?.shoulderEffects?.forEach { effect -> effect.removeEffect(partyPokemon, player, isLeftShoulder) }
    }

    private fun isShoulderedPokemon(CompoundTag nbt): Boolean = nbt.isPokemonEntity()
            && nbt.getCompound(DataKeys.POKEMON)
            .getCompound(DataKeys.POKEMON_STATE)
            .getUUID(DataKeys.POKEMON_STATE_ID) == this.stateId

    fun isStillShouldered(ServerPlayer player) = isShoulderedPokemon(if (isLeftShoulder) player.shoulderEntityLeft else player.shoulderEntityRight)

    final class Companion {

        internal const val ID = "shouldered"
        // If we ever move to need more NBT/JSON save/load we need a registry Codec.
        @JvmStatic
        val CODEC: Codec<ShoulderedState> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.STRING.fieldOf(DataKeys.POKEMON_STATE_TYPE).forGetter { ID }, // Keep me for the sake of if we ever migrate to a registry.
                Codec.BOOL.fieldOf(DataKeys.POKEMON_STATE_SHOULDER).forGetter(ShoulderedState::isLeftShoulder),
                UUIDUtil.LENIENT_CODEC
                    .fieldOf(DataKeys.POKEMON_STATE_PLAYER_UUID).forGetter(ShoulderedState::playerUUID),
                UUIDUtil.LENIENT_CODEC.fieldOf(DataKeys.POKEMON_STATE_ID)
                    .forGetter(ShoulderedState::stateId),
                UUIDUtil.LENIENT_CODEC
                    .fieldOf(DataKeys.POKEMON_STATE_POKEMON_UUID).forGetter(ShoulderedState::pokemonUUID)
            ).apply(instance) { _, isLeftShoulder, playerUuid, stateId, pokemonUuid ->
                val state = ShoulderedState(playerUuid, isLeftShoulder, pokemonUuid)
                state.stateId = stateId
                return@apply state
            }
        }
    }
}