/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClass
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePositionType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mojang.datafixers.util.Either
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.material.Fluid

/**
 * Object responsible for actually deserializing spawns. You should probably
 * rely on this object for it as it would make your code better future proofed.
 *
 * @author Hiroku
 * @since January 31st, 2022
 */
public final class SpawnLoader {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .setLenient()
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
        .registerTypeAdapter(SpawnablePositionType.class, RegisteredSpawnablePositionAdapter)
        .registerTypeAdapter(ResourceLocation.class, IdentifierAdapter)
        .registerTypeAdapter(SpawnDetail.class, SpawnDetailAdapter)
        .registerTypeAdapter(DropEntry.class, DropEntryAdapter)
        .registerTypeAdapter(SpawningCondition.class, SpawningConditionAdapter)
        .registerTypeAdapter(TimeRange.class, IntRangesAdapter(TimeRange.timeRanges) { TimeRange(*it) })
        .registerTypeAdapter(MoonPhaseRange.class, IntRangesAdapter(MoonPhaseRange.moonPhaseRanges) { MoonPhaseRange(*it) })
        .registerTypeAdapter(ItemDropMethod.class, ItemDropMethod.adapter)
        .registerTypeAdapter(PokemonProperties.class, pokemonPropertiesShortAdapter)
        .registerTypeAdapter(SpawnBucket.class, SpawnBucketAdapter)
        .registerTypeAdapter(CompoundTag.class, NbtCompoundAdapter)
        .registerTypeAdapter(IntRange.class, IntRangeAdapter)
        .registerTypeAdapter(PossibleHeldItem.class, PossibleHeldItemAdapter)
        .registerTypeAdapter(NPCClass.class, NPCClassReferenceAdapter)
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .create()

    var deserializingConditionClass: Class<out SpawningCondition<*>>? = null
}
