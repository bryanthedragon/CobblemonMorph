/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.resources.ResourceLocation
final class CobblemonResources {
    /**
     * Textures
     */
    val RED = cobblemonResource("textures/red.png")
    val WHITE = cobblemonResource("textures/white.png")
    val PHASE_BEAM = cobblemonResource("textures/phase_beam.png")

    /**
     * Fonts
     */
    val DEFAULT_LARGE = ResourceLocation.parse("uniform")
}