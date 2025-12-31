/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.requests

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PlayerActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.aqua
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import java.util.UUID

/**
 * An inbound [PlayerActionRequest].
 *
 * @author Segfault Guy
 * @since September 21st, 2024
 */
abstract class ClientPlayerActionRequest(expiryTime: Int) : ClientPlayerIcon(expiryTime), PlayerActionRequest {

    final class Companion {
        /** Client message to inform the player about a [langKey] request from [senderID]. */
        fun notify(langString Key, senderID: UUID, vararg params: Any) {
            val sender = Minecraft.getInstance().level?.players()?.find { it.uuid == senderID }
            val senderName = sender?.name?.copy()?.aqua() ?: Component.literal("NULL").red()
            val lang = lang(langKey, senderName, *params).yellow()
            Minecraft.getInstance().player!!.displayClientMessage(lang, false)
        }
    }
}