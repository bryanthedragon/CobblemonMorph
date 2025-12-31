/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter.GrowthFactorAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry.BiomeDownfallGrowthFactor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry.BiomeTemperatureGrowthFactor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry.PreferredBiomeGrowthFactor
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import net.minecraft.resources.ResourceLocation
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * The Cobblemon implementation of [GrowthFactorAdapter].
 *
 * @author Licious
 * @since December 2nd, 2022
 */
public final class CobblemonGrowthFactorAdapter : GrowthFactorAdapter {

    private const val VARIANT = "variant"
    private val types = hashMapOf<String, KClass<out GrowthFactor>>()

    init {
        this.register(BiomeDownfallGrowthFactor::class, BiomeDownfallGrowthFactor.ID)
        this.register(BiomeTemperatureGrowthFactor::class, BiomeTemperatureGrowthFactor.ID)
        this.register(PreferredBiomeGrowthFactor::class, PreferredBiomeGrowthFactor.ID)
    }

    override fun register(type: KClass<out GrowthFactor>, ResourceLocation identifier) {
        val existing = this.types.put(identifier.toString(), type)
        if (existing != null) {
            Cobblemon.LOGGER.debug("Replaced {} under ID {} with {} in the {}", existing::class.qualifiedName, identifier.toString(), type.qualifiedName, this::class.qualifiedName)
        }
    }

    override fun deserialize(jElement: JsonElement, Type type, JsonDeserializationContext context): GrowthFactor {
        val json = jElement.asJsonObject
        val variant = json.get(VARIANT).asString.lowercase()
        val registeredType = this.types[variant] ?: throw IllegalArgumentException("Cannot resolve type for variant $variant")
        return context.deserialize(json, registeredType.java)
    }

    override fun serialize(factor: GrowthFactor, Type type, context: JsonSerializationContext): JsonElement {
        val json = context.serialize(factor).asJsonObject
        val variant = this.types.entries.find { it.value == factor::class }?.key ?: throw IllegalArgumentException("Cannot resolve variant for type ${factor::class.qualifiedName}")
        json.addProperty(VARIANT, variant)
        return json
    }
}
