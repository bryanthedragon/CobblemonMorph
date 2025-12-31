/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame

import net.minecraft.client.model.geom.ModelPart

public interface QuadrupedFrame : ModelFrame {
    val foreLeftLeg: ModelPart
    val foreRightLeg: ModelPart
    val hindLeftLeg: ModelPart
    val hindRightLeg: ModelPart
}