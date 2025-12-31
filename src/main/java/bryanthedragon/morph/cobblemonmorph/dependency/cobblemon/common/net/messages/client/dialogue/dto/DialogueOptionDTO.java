/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeText
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.MutableComponent

public class DialogueOptionDTO(
    var text: MutableComponent = "".text(),
    var value: String = "",
    var selectable: Boolean = true
): Encodable, Decodable {
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeText(text)
        buffer.writeString(value)
        buffer.writeBoolean(selectable)
    }

    override fun decode(RegistryFriendlyByteBuf buffer) {
        text = buffer.readText().copy()
        value = buffer.readString()
        selectable = buffer.readBoolean()
    }
}