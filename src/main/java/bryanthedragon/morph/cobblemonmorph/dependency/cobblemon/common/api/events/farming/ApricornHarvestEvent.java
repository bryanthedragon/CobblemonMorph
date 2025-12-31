/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming

import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.toArrayStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.worldRegistry
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.state.BlockState

/**
 * Event fired when an Apricorn is harvested.
 */
public class ApricornHarvestEvent(
    val ServerPlayer player,
    val apricorn: Apricorn,
    val ServerLevel world,
    val (BlockPos pos
) {
    fun getBlock(): BlockState {
        return world.getBlockState(pos)
    }

    val context = mutableMapOf(
        "player" to player.asMoLangValue(),
        "apricorn" to StringValue(apricorn.name),
        "world" to world.worldRegistry.wrapAsHolder(world).asMoLangValue(Registries.DIMENSION),
        "pos" to pos.toArrayStruct()
    )
}