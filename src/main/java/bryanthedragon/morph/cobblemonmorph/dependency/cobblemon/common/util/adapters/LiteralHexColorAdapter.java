/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.*
import java.awt.Color
import java.lang.reflect.Type

/**
 * A type adapter meant to parse a hex string color code.
 *
 * @author Licious
 * @since December 5th, 2022
 */
public final class LiteralHexColorAdapter : JsonDeserializer<Color>, JsonSerializer<Color> {
    override fun deserialize(JsonElement jElement, Type type, JsonDeserializationContext context) = Color(element.asString.removePrefix("#").toInt(16))

    override fun serialize(color: Color, Type type, context: JsonSerializationContext) = JsonPrimitive("#${color.rgb.toString(16)}")
}