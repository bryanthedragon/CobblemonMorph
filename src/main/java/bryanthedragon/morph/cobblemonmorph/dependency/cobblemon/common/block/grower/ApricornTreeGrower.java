/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.grower

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn
import net.minecraft.world.level.block.grower.TreeGrower
import java.util.*

class ApricornTreeGrower(apricorn: Apricorn) : TreeGrower(
    apricorn.serializedName,
    Optional.empty(),
    Optional.of(apricorn.configuredFeature()),
    Optional.empty()
)