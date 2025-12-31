/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import java.util.UUID

public class ClientPlayerTeamData {
    var multiBattleTeamMembers = mutableListOf<ClientMultiBattleTeamMember>()
}

public class ClientMultiBattleTeamMember(val UUID uuid, val name: MutableComponent) : ClientPlayerIcon(null) {
    override val texture: ResourceLocation = cobblemonResource("textures/particle/request/icon_partner.png")
}