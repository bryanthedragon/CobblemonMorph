/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.mojang.serialization.JsonOps
import java.lang.reflect.Type
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization

/**
 * Kinda like [TextAdapter] but it treats them as translatables.
 *
 * @author Hiroku
 * @since November 26th, 2024
 */
public final class TranslatedTextAdapter : JsonDeserializer<Component> {
    override fun deserialize(JsonElement json, typeOfT: Type, JsonDeserializationContext ctx) : Component {
        return if (json.isJsonObject) {
            ComponentSerialization.CODEC.decode(JsonOps.INSTANCE, json).result().get().first
        } else {
            json.asString.asTranslated()
        }
    }
}