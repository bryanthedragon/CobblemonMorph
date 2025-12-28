/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events

import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.MoValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack

/**
 * Events related to a [ServerPlayer].
 * As implied by the name these are fired on the server side.
 *
 * @author Licious
 * @since February 15th, 2023
 */
interface ServerPlayerEvent {

    /**
     * The [ServerPlayer] triggering the platform specific events.
     */
    val player: ServerPlayer

    val context: MutableMap<String, MoValue>
        get() = mutableMapOf("player" to player.asMoLangValue())

    /**
     * Fired when the [player] logs in.
     */
    record Login(override val player: ServerPlayer) : ServerPlayerEvent

    /**
     * Fired when the [player] logs out.
     */
    record Logout(override val player: ServerPlayer) : ServerPlayerEvent

    /**
     * Fired when the [player] dies.
     * If canceled the death will be prevented but healing is required in order to not be stuck in a loop.
     */
    record Death(override val player: ServerPlayer) : ServerPlayerEvent, Cancelable()

    /**
     * Fired when the [player] right clicks a block.
     * When canceled no interaction will occur.
     *
     * @property pos The [BlockPos] of the targeted block.
     * @property hand The [InteractionHand] that hit the block.
     * @property face The [Direction] of the block if any.
     */
    record RightClickBlock(override val player: ServerPlayer, val pos: BlockPos, val hand: InteractionHand, val face: Direction?) : ServerPlayerEvent, Cancelable() {
        override val context: MutableMap<String, MoValue>
            get() = super.context.apply {
                put("x", DoubleValue(pos.x))
                put("y", DoubleValue(pos.y))
                put("z", DoubleValue(pos.z))
                put("hand", StringValue(hand.name))
                face?.let { put("face", StringValue(it.name)) }
            }

        val functions = moLangFunctionMap(cancelFunc)
    }

    /**
     * Fired when the [player] right clicks an entity.
     * When canceled no interaction will occur.
     *
     * @property item The [ItemStack] clicked on the [entity].
     * @property hand The [InteractionHand] that clicked the [entity].
     * @property entity The [Entity] the [player] clicked.
     */
    record RightClickEntity(override val player: ServerPlayer, val item: ItemStack, val hand: InteractionHand, val entity: Entity): ServerPlayerEvent, Cancelable() {
        override val context: MutableMap<String, MoValue>
            get() = super.context.apply {
                put("item", item.asMoLangValue(player.registryAccess()))
                put("hand", StringValue(hand.name))
                put("entity", entity.asMostSpecificMoLangValue())
            }

        val functions = moLangFunctionMap(cancelFunc)
    }

    record AdvancementEarned(override val player: ServerPlayer, val advancement: AdvancementHolder): ServerPlayerEvent {
        override val context: MutableMap<String, MoValue>
            get() = super.context.apply {
                put("advancement", StringValue(advancement.id.toString()))
            }
    }
}