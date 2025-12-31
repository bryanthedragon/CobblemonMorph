/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone

/**
 * A simple interface to allow coded models to specifically state what their root bone is. This is the top of a hierarchy
 * that barely ever gets used nowadays.
 *
 * @author Hiroku
 * @since December 5th, 2021
 */
public interface ModelFrame {
    val rootPart: Bone
}