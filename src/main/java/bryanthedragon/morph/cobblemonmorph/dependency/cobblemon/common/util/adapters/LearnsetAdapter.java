/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
public final class LearnsetAdapter : JsonDeserializer<Learnset> {
    override fun deserialize(JsonElement json, Type type, JsonDeserializationContext ctx): Learnset {
        val array = json.asJsonArray
        val learnset = Learnset()
        for (element in array) {
            var added = false
            interpreterLoop@
            for (interpreter in Learnset.interpreters) {
                if (interpreter.loadMove(element, learnset)) {
                    added = true
                    break@interpreterLoop
                }
            }

            if (!added) {
                LOGGER.error("Unable to load entry from learnset: $element")
            }
        }
        return learnset
    }
}