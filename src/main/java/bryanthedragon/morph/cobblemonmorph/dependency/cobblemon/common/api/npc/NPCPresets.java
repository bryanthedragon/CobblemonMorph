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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.BehaviourConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.config.task.TaskConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntityDimensionsAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.MoLangConfigVariable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration.NPCInteractConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.NPCVariationProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.variation.WeightedAspect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
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
public final class NPCPresets : JsonDataRegistry<NPCPreset> {

    override val id = cobblemonResource("npc_preset")
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
        .registerTypeAdapter(WeightedAspect.class, WeightedAspectAdapter)
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .registerTypeAdapter(NPCVariationProvider.class, NPCVariationProviderAdapter)
        .registerTypeAdapter(Activity.class, ActivityAdapter)
        .registerTypeAdapter(MoValue.class, MoValueAdapter)
        .registerTypeAdapter(Component.class, TranslatedTextAdapter)
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

    override val typeToken: TypeToken<NPCPreset> = TypeToken.get(NPCPreset.class)
    override val resourcePath = "npc_presets"
    override val observable = SimpleObservable<NPCPresets>()
    private val npcPresetsByIdentifier = mutableMapOf<ResourceLocation, NPCPreset>()

    override fun sync(ServerPlayer player) {
        // TODO probably do want to sync the presets
    }

    override fun reload(data: Map<ResourceLocation, NPCPreset>) {
        npcPresetsByIdentifier.clear()
        npcPresetsByIdentifier.putAll(data)
        observable.emit(this)
    }

    @JvmStatic
    fun getPreset(ResourceLocation identifier): NPCPreset? {
        return npcPresetsByIdentifier[identifier]
    }
}