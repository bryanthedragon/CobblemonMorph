/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass

/**
 * Saves and loads [Requirement]s with JSON.
 * For the default implementation see [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonRequirementAdapter].
 *
 * @author Licious
 * @since March 21st, 2022
 */
public interface RequirementAdapter : JsonDeserializer<Requirement>, JsonSerializer<Requirement> {

    /**
     * Registers the given type of [Requirement] to it's associated ID for deserialization.
     *
     * @param T The type of [Requirement].
     * @param id The id of the evolution event.
     * @param type The [KClass] of the type.
     */
    fun <T : Requirement> registerType(id: String, type: KClass<T>)

}