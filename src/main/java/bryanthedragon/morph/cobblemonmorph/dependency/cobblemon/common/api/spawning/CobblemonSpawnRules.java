/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.SpawnRule
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.FilterRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.LocationRuleCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.WeightTweakRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType

/**
 * All of the [SpawnRule]s that will be applied as global [SpawningInfluence]s.
 *
 * @author Hiroku
 * @since September 30th, 2023
 */
public final class CobblemonSpawnRules : JsonDataRegistry<SpawnRule> {
    override val gson = GsonBuilder()
        .registerTypeAdapter(SpawnRuleComponent.class, SpawnRuleComponentAdapter)
        .registerTypeAdapter(SpawnDetailSelector.class, SpawnDetailSelectorAdapter)
        .registerTypeAdapter(SpawnablePositionSelector.class, SpawnablePositionSelectorAdapter)
        .registerTypeAdapter(SpawningCondition.class, SpawningConditionAdapter)
        .registerTypeAdapter(Expression.class, ExpressionAdapter)
        .registerTypeAdapter(ExpressionLike.class, ExpressionLikeAdapter)
        .registerTypeAdapter(Component.class, TextAdapter)
        .create()

    override val typeToken = TypeToken.get(SpawnRule.class)
    override val resourcePath = "spawn_rules"

    init {
        SpawnRuleComponent.register<WeightTweakRuleComponent>("weight")
        SpawnRuleComponent.register<FilterRuleComponent>("filter")
        SpawnRuleComponent.register<LocationRuleCalculator>("location")

        SpawnDetailSelector.register<ExpressionSpawnDetailSelector>("expression")

        SpawnablePositionSelector.register<ExpressionSpawnablePositionSelector>("expression")
        SpawnablePositionSelector.register<ConditionalSpawnablePositionSelector>("conditional")
    }

    val rules = mutableMapOf<ResourceLocation, SpawnRule>()

    override fun reload(data: Map<ResourceLocation, SpawnRule>) {
        rules.clear()
        rules.putAll(data)
        data.forEach { (id, value) -> value.id = id }
        observable.emit(this)
    }

    override val ResourceLocation id = cobblemonResource("spawn_rules")
    override val type: PackType = PackType.SERVER_DATA
    override val observable = SimpleObservable<CobblemonSpawnRules>()

    override fun sync(ServerPlayer player) {}
}