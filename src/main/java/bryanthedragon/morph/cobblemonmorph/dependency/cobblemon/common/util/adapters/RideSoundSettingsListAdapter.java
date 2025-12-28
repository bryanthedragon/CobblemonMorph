/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.sound.RideSoundSettingsList
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.sound.RideSoundSettings
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Class to control playing all ride sounds
 *
 * @author Jackowes
 * @since April 26th, 2025
 */final class RideSoundSettingsListAdapter: JsonDeserializer<RideSoundSettingsList?> {
    override fun deserialize(
        element: JsonElement,
        type: Type,
        context: JsonDeserializationContext
    ): RideSoundSettingsList {
        val sounds = when {
            element.isJsonArray -> {
                element.asJsonArray.map {
                    context.deserialize(it, RideSoundSettings::class.java)
                }
            }
            element.isJsonObject -> {
                listOf(context.deserialize<RideSoundSettings>(element, RideSoundSettings::class.java))
            }
            else -> {
                emptyList()
            }
        }
        return RideSoundSettingsList(sounds)
    }
}