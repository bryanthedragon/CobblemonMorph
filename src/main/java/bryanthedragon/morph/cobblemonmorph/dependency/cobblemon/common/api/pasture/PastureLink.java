/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import java.util.*
import net.minecraft.core.BlockPos
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation

class PastureLink(val linkId: UUID, val pcId: UUID, val dimension: ResourceLocation, val pos: BlockPos, val permissions: PasturePermissions) {
    fun getPC(registryAccess: RegistryAccess) = Cobblemon.storage.getPC(pcId, registryAccess)
}