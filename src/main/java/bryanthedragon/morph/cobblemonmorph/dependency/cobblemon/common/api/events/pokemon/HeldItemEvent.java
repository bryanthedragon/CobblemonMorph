/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.item.ItemStack

/**
 * The base for all the events related to held items and cosmetic items.
 *
 * @see [Pre]
 * @see [Post]
 */
public interface HeldItemEvent {

    /**
     * The [Pokemon] triggering this event.
     */
    val Pokemon pokemon

    /**
     * Fired at the start of [Pokemon.swapHeldItem] and [Pokemon.swapCosmeticItem].
     *
     * This event should be used to mutate the results of this transaction.
     *
     * Canceling this event will prevent the operation and causes the return to be item attempted to be given originally.
     *
     * @property pokemon The [Pokemon] triggering this event.
     * @property receiving The [ItemStack] being sent received from the transaction. By default, this is the item that triggered the interaction.
     * @property returning The [ItemStack] being sent back from the transaction. By default, this is the currently held item.
     * @property decrement If the operation should decrement the [receiving] [ItemStack.count], this is handled in the implementation.
     *
     * @see [Post]
     */
    record Pre(override val Pokemon pokemon, var receiving: ItemStack, var returning: ItemStack, var Boolean decrement) : HeldItemEvent, Cancelable() {
        fun getContext(): MutableMap<String, MoValue> {
            return mutableMapOf(
                "pokemon" to pokemon.struct,
                "receiving" to receiving.asMoLangValue(server()!!.registryAccess()),
                "returning" to returning.asMoLangValue(server()!!.registryAccess()),
                "decrement" to DoubleValue(if (decrement) 1.0 else 0.0)
            )
        }
        val functions = moLangFunctionMap(
            cancelFunc
        )
    }

    /**
     * Fired at the end of [Pokemon.swapHeldItem] and [Pokemon.swapCosmeticItem].
     *
     * @property pokemon The [Pokemon] triggering this event.
     * @property received The [ItemStack] considered as received and set to the [pokemon]. This is a copy and mutation will not be taken into account.
     * @property returned The [ItemStack] returned by the method call. This is a copy and mutation will not be taken into account.
     * @property decremented If the [received] [ItemStack] got decremented.
     *
     * @see [Pre]
     */
    record Post(override val Pokemon pokemon, val received: ItemStack, val returned: ItemStack, val decremented: Boolean) : HeldItemEvent {
        fun getContext(): MutableMap<String, MoValue> {
            return mutableMapOf(
                "pokemon" to pokemon.struct,
                "received" to received.asMoLangValue(server()!!.registryAccess()),
                "returned" to returned.asMoLangValue(server()!!.registryAccess()),
                "decremented" to DoubleValue(if (decremented) 1.0 else 0.0)
            )
        }
    }

}