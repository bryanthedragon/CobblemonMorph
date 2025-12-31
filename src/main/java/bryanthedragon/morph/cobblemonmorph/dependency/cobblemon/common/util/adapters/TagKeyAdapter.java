/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.*
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import java.lang.reflect.Type

/**
 * An adapter for [TagKey]s.
 * [TagKey]s are just [ResourceLocation]s attached to a certain registry.
 *
 * @param T The type of the [Registry] this [TagKey] belongs to.
 * @property key The [ResourceKey] used to create new [TagKey]s.
 *
 * @author Licious
 * @since July 2nd, 2022
 */
public class TagKeyAdapter<T>(private val key: ResourceKey<Registry<T>>) : JsonDeserializer<TagKey<T>>, JsonSerializer<TagKey<T>> {

    override fun deserialize(JsonElement jElement, Type type, JsonDeserializationContext ctx): TagKey<T> {
        val identifier = ResourceLocation.parse(element.asString.replace("#", ""))
        return TagKey.create(this.key, identifier)
    }

    override fun serialize(tagKey: TagKey<T>, Type type, JsonSerializationContext ctx): JsonElement {
        return JsonPrimitive(tagKey.location.toString())
    }

}