/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * A specific type of update for a Pokémon which updates a single boolean value
 *
 * @author Deltric
 * @since January 13th, 2022
 */
abstract class BooleanUpdatePacket<T : NetworkPacket<T>>(pokemon: () -> Pokemon?, value: Boolean) : SingleUpdatePacket<Boolean, T>(pokemon, value) {
    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeBoolean(this.value)
    }
}