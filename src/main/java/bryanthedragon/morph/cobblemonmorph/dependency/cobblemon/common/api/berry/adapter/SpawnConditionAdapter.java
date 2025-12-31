/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CobblemonBerrySpawnConditionAdapter
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import net.minecraft.resources.ResourceLocation
import kotlin.reflect.KClass

/**
 * A type adapter for [GrowthFactor]s.
 * For the default implementation see [CobblemonBerrySpawnConditionAdapter].
 *
 * @author Apion
 * @since August 14, 2023
 */
public interface SpawnConditionAdapter : JsonDeserializer<BerrySpawnCondition>, JsonSerializer<BerrySpawnCondition> {
    /**
     * Register a [BerrySpawnCondition] to be used by this adapter.
     *
     * @param type The [KClass] of the [BerrySpawnCondition].
     * @param identifier The expected [ResourceLocation] in the parsed JSON.
     */
    fun register(type: KClass<out BerrySpawnCondition>, ResourceLocation identifier)
}
