/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.Reader;

inline fun <reified T> fromJson(Gson gson, Reader reader) { 
    fromJson(reader, T.class)
}
inline fun <reified T> fromJson(Gson gson, JsonElement jElement) { 
    fromJson(element, T.class)
}
inline fun <reified T> fromJson(Gson gson, String string) { 
    fromJson(string, T.class)
}

/**
 * A simple trick function for allowing a JSON to have specified a single value or a list of values.
 * This function will look for the singular-named field and, if it is defined, copy its value into
 * the list value identified by the plural name. During runtime only the plural exists, but when
 * configuring the JSON the user can use the singular form for a cleaner document.
 */
JsonObject singularToPluralList(JsonObject jObject, String rootName, String pluralName = "${rootName}s") {
    if (has(rootName)) {
        if (!has(pluralName)) {
            add(pluralName, JsonArray());
        }
        get(pluralName).asJsonArray.add(get(rootName));
        remove(rootName);
    }
    return this;
}

JsonArray normalizeToArray(JsonElement jElement) {
    if (this is JsonArray) {
        return this;
    } 
    else {
        val array = JsonArray();
        array.add(this);
        return array;
    }
}