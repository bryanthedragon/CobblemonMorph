/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.BehaviourConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.TaskConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntityDimensionsAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClasses.getByIdentifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCInteractConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.NPCVariationProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.WeightedAspect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.NPCRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mojang.datafixers.util.Either
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.sensing.SensorType
import net.minecraft.world.entity.schedule.Activity
import net.minecraft.world.item.Item
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.AABB
public final class NPCClasses : JsonDataRegistry<NPCClass> {

    override val id = cobblemonResource("npc")
    override val type = PackType.SERVER_DATA

    override val Gson gson = GsonBuilder()
        .registerTypeAdapter(EntityDimensions.class, EntityDimensionsAdapter)
        .registerTypeAdapter(AABB.class, BoxAdapter)
        .registerTypeAdapter(IntRange.class, IntRangeAdapter)
        .registerTypeAdapter(PokemonProperties.class, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(TimeRange.class, IntRangesAdapter(TimeRange.timeRanges) { TimeRange(*it) })
        .registerTypeAdapter(ItemDropMethod.class, ItemDropMethod.adapter)
        .registerTypeAdapter(SleepDepth.class, SleepDepth.adapter)
        .registerTypeAdapter(DropEntry.class, DropEntryAdapter)
        .registerTypeAdapter(CompoundTag.class, NbtCompoundAdapter)
        .registerTypeAdapter(NPCPartyProvider.class, NPCPartyProviderAdapter)
        .registerTypeAdapter(NPCInteractConfiguration.class, NPCInteractConfigurationAdapter)
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .registerTypeAdapter(NPCVariationProvider.class, NPCVariationProviderAdapter)
        .registerTypeAdapter(MoValue.class, MoValueAdapter)
        .registerTypeAdapter(NPCClass.class, NPCClassAdapter)
        .registerTypeAdapter(Activity.class, ActivityAdapter)
        .registerTypeAdapter(Component.class, TranslatedTextAdapter)
        .registerTypeAdapter(WeightedAspect.class, WeightedAspectAdapter)
        .registerTypeAdapter(MemoryModuleType.class, MemoryModuleTypeAdapter)
        .registerTypeAdapter(SensorType.class, SensorTypeAdapter)
        .registerTypeAdapter(BehaviourConfig.class, BehaviourConfigAdapter)
        .registerTypeAdapter(TaskConfig.class, TaskConfigAdapter)
        .registerTypeAdapter(
            TypeToken.getParameterized(Either.class, Expression.class, MoLangConfigVariable.class).type,
            ExpressionOrEntityVariableAdapter
        )
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Biome.class).type, BiomeLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Block.class).type, BlockLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Item.class).type, ItemLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(Either.class, ResourceLocation.class, ExpressionLike.class).type, NPCScriptAdapter)
        .disableHtmlEscaping()
        .enableComplexMapKeySerialization()
        .create()

    override val typeToken: TypeToken<NPCClass> = TypeToken.get(NPCClass.class)
    override val resourcePath = "npcs"
    override val observable = SimpleObservable<NPCClasses>()
    private val npcClassesByIdentifier = hashMapOf<ResourceLocation, NPCClass>()

    @JvmStatic
    val classes: Collection<NPCClass>
        get() = this.npcClassesByIdentifier.values

    /**
     * Finds an NPC class by the pathname of their [ResourceLocation].
     * This method exists for the convenience of finding Cobble default NPC classes.
     * This uses [getByIdentifier] using the [Cobblemon.MODID] as the namespace and the [name] as the path.
     *
     * @param name The path of the NPC class asset.
     * @return The [NPCClass] if existing.
     */
    fun getByName(String name) = this.getByIdentifier(cobblemonResource(name))

    /**
     * Finds an [NPCClass] by its unique [ResourceLocation].
     *
     * @param identifier The unique [NPCClass.id] of the [NPCClass].
     * @return The [NPCClass] if existing.
     */
    @JvmStatic
    fun getByIdentifier(ResourceLocation identifier) = this.npcClassesByIdentifier[identifier]

    /**
     * Counts the currently loaded NPC classes.
     *
     * @return The loaded NPC class amount.
     */
    @JvmStatic
    fun count() = this.npcClassesByIdentifier.size

    /**
     * Picks a random [NPCClass].
     *
     * @throws [NoSuchElementException] if there are no NPC classes loaded.
     *
     * @return A randomly selected [Species].
     */
    @JvmStatic
    fun random(): NPCClass = this.npcClassesByIdentifier.values.random()

    fun dummy(): NPCClass {
        val dummy = NPCClass()
        dummy.id = cobblemonResource("dummy")
        return dummy
    }

    override fun reload(data: Map<ResourceLocation, NPCClass>) {
        this.npcClassesByIdentifier.clear()
        data.forEach { (identifier, species) ->
            species.id = identifier
            // shortcut so they don't have to state the resource identifier if they don't wanna
            if (species.resourceIdentifier.path == "dummy") {
                species.resourceIdentifier = identifier
            }
            this.npcClassesByIdentifier[identifier] = species
        }
    }

    override fun sync(ServerPlayer player) {
        NPCRegistrySyncPacket(npcClassesByIdentifier.values.toList()).sendToPlayer(player)
    }
}