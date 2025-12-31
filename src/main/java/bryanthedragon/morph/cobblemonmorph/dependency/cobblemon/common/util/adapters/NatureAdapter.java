/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
public final class NatureAdapter : TypeAdapter<Nature>() {

    override fun write(writer: JsonWriter, value: Nature) {
        writer.value(value.name.toString())
    }

    override fun read(reader: JsonReader): Nature {
        return Natures.getNature(reader.nextString().asIdentifierDefaultingNamespace()) ?:
            throw IllegalStateException("Failed to resolve nature from: ${reader.nextString()}")
    }

}