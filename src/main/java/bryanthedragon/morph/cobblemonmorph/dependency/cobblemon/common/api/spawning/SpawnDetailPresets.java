/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClass
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePositionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mojang.datafixers.util.Either
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
import net.minecraft.tags.TagKey
import net.minecraft.server.packs.PackType
import net.minecraft.server.level.ServerPlayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.material.Fluid

/**
 * Data registry for [SpawnDetailPreset]s. These help the maintainability of spawn files by allowing common presets
 * to be defined separately to the spawns that obey it. You can register custom presets either programmatically or
 * by adding preset JSONs to the spawn_detail_presets data folder.
 *
 * @author Hiroku
 * @since December 9th, 2022
 */
public final class SpawnDetailPresets : JsonDataRegistry<SpawnDetailPreset> {
    val GSON = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .disableHtmlEscaping()
        .registerTypeAdapter(SpawnBucket.class, SpawnBucketAdapter)
        .registerTypeAdapter(SpawnablePositionType.class, RegisteredSpawnablePositionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Biome.class).type, BiomeLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Block.class).type, BlockLikeConditionAdapter)
        .registerTypeAdapter(TypeToken.getParameterized(RegistryLikeCondition.class, Fluid.class).type, FluidLikeConditionAdapter)
        .registerTypeAdapter(
            TypeToken.getParameterized(
                Either.class,
                ResourceLocation.class,
                TypeToken.getParameterized(
                    TagKey.class,
                    Structure.class
                ).type
            ).type,
            EitherIdentifierOrTagAdapter(Registries.STRUCTURE)
        )
        .registerTypeAdapter(SpawnDetailPreset.class, SpawnDetailPresetAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(SpawningCondition.class, SpawningConditionAdapter)
        .registerTypeAdapter(TimeRange.class, IntRangesAdapter(TimeRange.timeRanges) { TimeRange(*it) })
        .registerTypeAdapter(MoonPhaseRange.class, IntRangesAdapter(MoonPhaseRange.moonPhaseRanges) { MoonPhaseRange(*it) })
        .registerTypeAdapter(PokemonProperties.class, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(PossibleHeldItem.class, PossibleHeldItemAdapter)
        .registerTypeAdapter(NPCClass.class, NPCClassReferenceAdapter)
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .create()

    val presetTypes = mutableMapOf<String, Class<out SpawnDetailPreset>>()
    fun <T : SpawnDetailPreset> registerPresetType(String name, detailClass: Class<T>) {
        presetTypes[name] = detailClass
    }

    override val Gson gson = GSON
    override val typeToken = TypeToken.get(SpawnDetailPreset.class)
    override val resourcePath = "spawn_detail_presets"
    override val id = cobblemonResource(resourcePath)
    override val type = PackType.SERVER_DATA
    override val observable = SimpleObservable<SpawnDetailPresets>()

    var presets = mutableMapOf<ResourceLocation, SpawnDetailPreset>()

    override fun sync(ServerPlayer player) {}
    override fun reload(data: Map<ResourceLocation, SpawnDetailPreset>) {
        this.presets = data.toMutableMap()
        Cobblemon.LOGGER.info("Loaded ${presets.size} spawn detail presets.")
    }
}
