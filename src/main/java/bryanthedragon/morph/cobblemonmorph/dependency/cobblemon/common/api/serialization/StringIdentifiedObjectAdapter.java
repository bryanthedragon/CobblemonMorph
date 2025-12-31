/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Simple adapter which expects a String and will turn into an object based on some resolver.
 *
 * @author Hiroku
 * @since July 18th, 2022
 */
public class StringIdentifiedObjectAdapter<T>(val fromString: (String) -> T, val toString: ((T) -> String)? = null) extends JsonDeserializer<T>, JsonSerializer<T> {
    fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) {
        fromString(json.asString);
    }
    JsonElement serialize(T src, Type type?, JsonSerializationContext ctx?) ? {
        val stringValue = toString?.invoke(src) ?: throw IllegalStateException("No toString function provided for serialization.");
        return JsonPrimitive(stringValue);
    }
}