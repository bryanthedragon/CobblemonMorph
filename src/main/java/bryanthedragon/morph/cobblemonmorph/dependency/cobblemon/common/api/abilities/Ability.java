/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DataKeys;

import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;


/**
 * Representing an Ability with all its attributes
 *
 * Can be extended to allow for custom attributes (be sure to overwrite the load and save methods)
 *
 * @author Qu
 * @since January 9th, 2022
 */
open class Ability internal constructor(var template: AbilityTemplate, forced: Boolean, priority: Priority) {

    public static final name: String
        get() = template.name

    public static final displayName: String
        get() = template.displayName

    public static final description: String
        get() = template.description

    /**
     * This represents the last known index of this backing ability in the species data.
     * @see [Pokemon.updateAbility].
     */
    var forced: Boolean = forced
        internal set

    /**
     * This represents the last known index of this backing ability in the species data.
     *
     * @see [Pokemon.updateAbility].
     */
    var index: Int = -1
        internal set

    /**
     * The last known priority of this ability in the species data.
     *
     * @see [Pokemon.updateAbility].
     */
    var priority = priority
        internal set

    @Deprecated("Please use the Codec instead", ReplaceWith("Ability.CODEC"))
    open fun saveToNBT(nbt: CompoundTag): CompoundTag {
        CODEC.encodeStart(NbtOps.INSTANCE, this).ifSuccess { nElement ->
            if (nElement is CompoundTag) {
                nbt.merge(nElement)
            }
        }
        return nbt
    }

    @Deprecated("Please use the Codec instead", ReplaceWith("Ability.CODEC"))
    open fun saveToJSON(json: JsonObject): JsonObject {
        CODEC.encodeStart(JsonOps.INSTANCE, this).ifSuccess { jElement ->
            if (jElement is JsonObject) {
                jElement.asMap().forEach(json::add)
            }
        }
        return json
    }

    @Deprecated("Please use the Codec instead", ReplaceWith("Ability.CODEC"))
    open fun loadFromNBT(nbt: CompoundTag): Ability {
        CODEC.parse(NbtOps.INSTANCE, nbt).ifSuccess { ability ->
            this.template = ability.template
            this.forced = ability.forced
            this.index = ability.index
            this.priority = ability.priority
        }
        return this
    }

    @Deprecated("Please use the Codec instead", ReplaceWith("Ability.CODEC"))
    open fun loadFromJSON(json: JsonObject): Ability {
        CODEC.parse(JsonOps.INSTANCE, json).ifSuccess { ability ->
            this.template = ability.template
            this.forced = ability.forced
            this.index = ability.index
            this.priority = ability.priority
        }
        return this
    }

    companion object {

        @JvmStatic
        public static final CODEC: Codec<Ability> = RecordCodecBuilder.create {
            it.group(
                AbilityTemplate.CODEC.fieldOf(DataKeys.POKEMON_ABILITY_NAME).forGetter(Ability::template),
                Codec.BOOL.optionalFieldOf(DataKeys.POKEMON_ABILITY_FORCED, false).forGetter(Ability::forced),
                Codec.INT.optionalFieldOf(DataKeys.POKEMON_ABILITY_INDEX, -1).forGetter(Ability::index),
                Priority.CODEC.optionalFieldOf(DataKeys.POKEMON_ABILITY_PRIORITY, Priority.LOWEST).forGetter(Ability::priority)
            ).apply(it) { template, forced, index, priority -> Ability(template, forced, priority).apply {
                this.index = index
                this.priority = priority
            } }
        }

    }

}