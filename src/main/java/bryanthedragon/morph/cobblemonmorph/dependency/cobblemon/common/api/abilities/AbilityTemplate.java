/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.CodecUtils
import com.google.gson.JsonObject
import com.mojang.serialization.Codec
import net.minecraft.nbt.CompoundTag

/**
 * This represents the base of an Ability.
 * To build an Ability you MUST use its template.
 *
 * @param name: The English name used to load / find it (spaces -> _)
 */
public class AbilityTemplate(
    val String name = "",
    var builder: (AbilityTemplate, forced: Boolean, Priority priority) -> Ability = { template, forced, priority -> Ability(template, forced, priority) },
    val displayName: String = "cobblemon.ability.$name",
    val description: String = "cobblemon.ability.$name.desc"
) {
    /**
     * Returns the Ability or if applicable the extension connected to this template
     */
    fun create(forced: Boolean = false, Priority priority = Priority.LOWEST) = builder(this, forced, priority)

    /**
     * Returns the Ability and loads the given NBT Tag into it.
     *
     * Ability extensions need to write and read their needed data from here.
     */
    fun create(CompoundTag nbt) = create().loadFromNBT(nbt)

    /**
     * Returns the Ability and loads the given JSON object into it.
     *
     * Ability extensions need to write and read their needed data from here.
     */
    fun create(JsonObject json) = create().loadFromJSON(json)

    final class Companion {

        @JvmStatic
        val CODEC: Codec<AbilityTemplate> = CodecUtils.createByStringCodec(
            Abilities::getOrDummy,
            AbilityTemplate::name
        ) { id -> "No ability for ID $id" }
    }

}