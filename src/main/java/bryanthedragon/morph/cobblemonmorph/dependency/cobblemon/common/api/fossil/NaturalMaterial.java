/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition
import net.minecraft.resources.ResourceLocation

record NaturalMaterial(
    val content: Int = 0,
    val item: ResourceLocation?,
    val tag: ItemTagCondition? = null,
    val ResourceLocation returnItem? = null
)
