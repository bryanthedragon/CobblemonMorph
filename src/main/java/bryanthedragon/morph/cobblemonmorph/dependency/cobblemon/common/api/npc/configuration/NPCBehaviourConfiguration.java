/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.configuration

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf

class NPCBehaviourConfiguration {
    var canBeHurt = true

    fun encode(buffer: RegistryFriendlyByteBuf) {

    }

    fun decode(buffer: RegistryFriendlyByteBuf) {

    }

    fun saveToNBT(nbt: CompoundTag) {

    }

    fun loadFromNBT(nbt: CompoundTag) {

    }
}