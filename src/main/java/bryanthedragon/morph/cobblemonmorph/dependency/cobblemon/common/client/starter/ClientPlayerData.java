/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.starter

import java.util.UUID

record ClientPlayerData(
    var promptStarter: Boolean = true,
    var starterLocked: Boolean = true,
    var starterSelected: Boolean = false,
    var starterUUID uuid? = null
)