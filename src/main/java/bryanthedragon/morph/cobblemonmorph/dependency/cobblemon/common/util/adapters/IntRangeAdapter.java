/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ranges.ints.IntRange;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adapts an IntRange into a simple hyphenated integer pair string. IntRange(2, 4) is serialized as
 * "2-4", and one-element ranges are serialized as single integers such that IntRange(10, 10)
 * serializes as "10".
 *
 * @author Hiroku, Qu
 * @since February 14th, 2022
 */
public final class IntRangeAdapter  implements JsonSerializer<IntRange>, JsonDeserializer<IntRange> {

    private static final Pattern PATTERN = Pattern.compile("(-?\\d+)-?(-?\\d+)?");

    public JsonElement serialize(IntRange range, Type type, JsonSerializationContext ctx) {
        if (range.first() == range.last()) {
            return new JsonPrimitive(range.first());
        }
        else {
            return new JsonPrimitive(range.first() + "-" + range.last());
        }
    }

    public IntRange deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        Matcher matcher = PATTERN.matcher(json.getAsString());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid IntRange: " + json.getAsString());
        }

        int start = Integer.parseInt(matcher.group(1));
        String endGroup = matcher.group(2);

        if (endGroup == null || endGroup.isEmpty()) {
            return new IntRange(start, start);
        }

        int end = Integer.parseInt(endGroup);
        return new IntRange(start, end);
    }
}