/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf

public class AbilityRegistrySyncPacket(abilities: Collection<AbilityTemplate>) : DataRegistrySyncPacket<AbilityTemplate, AbilityRegistrySyncPacket>(abilities) {

    override val id = ID

    override fun encodeEntry(RegistryFriendlyByteBuf buffer, entry: AbilityTemplate) {
        buffer.writeString(entry.name)
        buffer.writeString(entry.displayName)
        buffer.writeString(entry.description)
    }

    override fun decodeEntry(RegistryFriendlyByteBuf buffer): AbilityTemplate {
        return AbilityTemplate(
            name = buffer.readString(),
            displayName = buffer.readString(),
            description = buffer.readString()
        )
    }

    override fun synchronizeDecoded(entries: Collection<AbilityTemplate>) {
        Abilities.receiveSyncPacket(entries)
    }

    final class Companion {
        val ID = cobblemonResource("ability_sync")
        fun decode(RegistryFriendlyByteBuf buffer): AbilityRegistrySyncPacket = AbilityRegistrySyncPacket(emptyList()).apply { decodeBuffer(buffer) }
    }
}