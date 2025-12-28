/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel

import net.minecraft.resources.ResourceLocation
import org.joml.Vector3f

record InteractWheelOption(
    val iconResource: ResourceLocation,
    val secondaryIconResource: ResourceLocation? = null,
    val enabled: Boolean = true,
    val tooltipText: String?,
    val colour: () -> Vector3f? = { null },
    val onPress: () -> Unit
)