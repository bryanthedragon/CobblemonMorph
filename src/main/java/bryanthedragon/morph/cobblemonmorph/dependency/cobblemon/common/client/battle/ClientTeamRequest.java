/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.requests.ClientPlayerActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.resources.ResourceLocation
import java.util.*

record ClientTeamRequest(
    override val requestID: UUID,
    override val senderID: UUID,
    override val expiryTime: Int
) : ClientPlayerActionRequest(expiryTime) {
    override val texture: ResourceLocation = cobblemonResource("textures/particle/request/icon_team.png")
}