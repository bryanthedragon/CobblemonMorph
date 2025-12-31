/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.behaviour.BehaviourEditorScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.MoLangScriptingEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.OpenBehaviourEditorPacket
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
public final class OpenBehaviourEditorHandler : ClientNetworkPacketHandler<OpenBehaviourEditorPacket> {
    override fun handle(packet: OpenBehaviourEditorPacket, Minecraft client) {
        val entity = client.level?.getEntity(packet.entityId) as? MoLangScriptingEntity
        if (entity != null && entity is LivingEntity) {
            client.setScreen(BehaviourEditorScreen(entity, packet.appliedPresets.toMutableSet()))
        }
    }
}