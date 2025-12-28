/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.pot

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.network.chat.Component

/**
 * Used for aprijuice quality.
 */
enum class CookingQuality {
    LOW {
        override fun getLang(): Component = lang("cooking.cooking_quality.low").red()
    },
    MEDIUM {
        override fun getLang(): Component = lang("cooking.cooking_quality.medium").yellow()
    },
    HIGH {
        override fun getLang(): Component = lang("cooking.cooking_quality.high").green()
    };

    abstract fun getLang(): Component
}