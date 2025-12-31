/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.Mulchable
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.LevelEvent

public class MulchItem(val variant: MulchVariant) : CobblemonItem(Properties()) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val world = context.level
        val pos = context.clickedPos
        if (this.useOnMulchAble(context.player, context.itemInHand, world, pos)) {
            // Plays bone meal effect
            if (!world.isClientSide) {
                world.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0)
            }
            return InteractionResult.sidedSuccess(true)
        }
        return InteractionResult.PASS
    }

    private fun useOnMulchAble(player: Player?, ItemStack stack, Level world, (BlockPos pos): Boolean {
        val state = world.getBlockState(pos)
        if (state.block is Mulchable) {
            val mulchAble = state.block as? Mulchable ?: return false
            if (world is ServerLevel && mulchAble.canHaveMulchApplied(world, pos, state, this.variant)) {
                mulchAble.applyMulch(world, world.random, pos, state, this.variant)
                stack.consume(1, player)
                return true
            }
        }
        return false
    }

}