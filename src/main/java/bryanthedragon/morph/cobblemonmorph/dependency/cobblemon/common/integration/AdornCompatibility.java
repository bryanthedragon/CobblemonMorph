/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import juuxel.adorn.block.variant.BlockVariant
import juuxel.adorn.block.variant.CompatBlockVariantSet

public class AdornCompatibility : CompatBlockVariantSet() {
    override fun getWoodVariants(): List<BlockVariant?>? {
        return this.createVariants({ BlockVariant.Wood(it) }, "apricorn")
    }

    override fun getModId(): String? {
        return Cobblemon.MODID
    }
}