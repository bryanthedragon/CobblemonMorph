/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;

import java.util.Collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.mojang.serialization.JsonOps;

import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

public class JsonExtensions {

    // fun Collection<String>.toJsonArray(): JsonArray
    @Override
    JsonArray toJsonArray(Collection<String> collectionString) {
        val array = JsonArray();
        if (isEmpty()) {
            return array;
        }
        forEach { array.add(it); }
        return array;
    }

    // fun Collection<Boolean>.toJsonArray(): JsonArray
    @Override    
    JsonArray toJsonArray(Collection<Boolean> collectionBoolean) {
        val array = JsonArray();
        if (isEmpty())
            return array;
        forEach { array.add(it); }
        return array;
    }
    // fun Collection<Number>.toJsonArray(): JsonArray
    @Override
    JsonArray toJsonArray(Collection<Number> collectionNum) {
        val array = JsonArray();
        if (isEmpty())
            return array;
        forEach { array.add(it); }
        return array;
    }

    // fun Collection<JsonElement>.toJsonArray(): JsonArray
    @Override
    JsonArray toJsonArray(Collection<JsonElement> jCollectObject) {
        val array = JsonArray();
        if (isEmpty()){
            return array;
        }
        forEach { array.add(it); }
        return array;
    }
    
    // fun JsonObject.isEmpty() = size() <= 0
    
    fun isEmpty(JsonObject jObject){
        size() <= 0;
    }
    
    // fun JsonObject.isNotEmpty() = size() > 0
    
    fun isNotEmpty(JsonObject jObject) {
        size() > 0;
    }

    // fun JsonElement.asNbt() = JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, this)

    Tag asNbt(JsonElement jElement) { 
        JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, this);
    }

    // fun <T : Enum<T>> Array<T>.getFromJSON(JsonElement jElement, String name): T

    T <T extends Enum<T>> getFromJSON(Array<T> array, JsonElement jElement, String name) {
        val type = (element as JsonObject).get(name).asString;
        return first; { type.equals(it.name, ignoreCase = true); }
    }

    // fun JsonObject.getFirst(vararg names: String ): JsonElement? {

    JsonElement getFirst(JsonObject jObject, vararg String names) {
        for (name in names) {
            val element = get(name);
            if (element != null) {
                return element;
            }
        }
        return null;
    }

    // fun JsonArray.asExpressionLike(): ExpressionLike { return map { it.asString }.asExpressionLike()}

    ExpressionLike asExpressionLike(JsonArray jArray) {
        return map; it.asString.asExpressionLike();
    }
}