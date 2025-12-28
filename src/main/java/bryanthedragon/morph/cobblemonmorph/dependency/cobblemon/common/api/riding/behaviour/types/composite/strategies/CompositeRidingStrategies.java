/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.strategies

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.behaviour.types.composite.CompositeSettings
import net.minecraft.resources.ResourceLocation
final class CompositeRidingStrategies {
    val strategies = mutableMapOf<ResourceLocation, CompositeRidingStrategy<out CompositeSettings>>()

    init {
        register(RunStrategy.key, RunStrategy)
        register(JumpStrategy.key, JumpStrategy)
        register(FallStrategy.key, FallStrategy)
    }

    fun register(key: ResourceLocation, strategy: CompositeRidingStrategy<out CompositeSettings>) {
        if (strategies.contains(key)) error("Strategy already registered to key $key")
        strategies[key] = strategy
    }

    fun get(key: ResourceLocation): CompositeRidingStrategy<CompositeSettings> {
        if (!strategies.contains(key)) error("Strategy not registered to key $key")
        return strategies[key]!! as CompositeRidingStrategy<CompositeSettings>
    }
}
