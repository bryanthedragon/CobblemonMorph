/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readItemStack
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeItemStack
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.world.item.ItemStack

public class HeldItemUpdatePacket(pokemon: () -> Pokemon?, value: ItemStack): SingleUpdatePacket<ItemStack, HeldItemUpdatePacket>(pokemon, value) {

    override val id = ID

    override fun encodeValue(RegistryFriendlyByteBuf buffer) {
        buffer.writeItemStack(this.value)
    }

    override fun set(Pokemon pokemon, value: ItemStack) { pokemon.swapHeldItem(this.value, false) }

    final class Companion {
        val ID = cobblemonResource("held_item_update")
        fun decode(RegistryFriendlyByteBuf buffer): HeldItemUpdatePacket {
            val pokemon = decodePokemon(buffer)
            val stack = buffer.readItemStack()
            return HeldItemUpdatePacket(pokemon, stack)
        }
    }

}