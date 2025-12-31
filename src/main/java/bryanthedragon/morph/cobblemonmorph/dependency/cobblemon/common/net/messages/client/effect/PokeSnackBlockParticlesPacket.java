/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect.PokeSnackBlockParticlesHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.core.BlockPos
import net.minecraft.network.RegistryFriendlyByteBuf

/**
 * Handled by [PokeSnackBlockParticlesHandler].
 */
public class PokeSnackBlockParticlesPacket(
    val blockBlockPos pos,
    val entity(BlockPos pos?
) : NetworkPacket<PokeSnackBlockParticlesPacket> {
    override val id = ID

    final class Companion {
        val ID = cobblemonResource("poke_snack_block_particles")
        fun decode(RegistryFriendlyByteBuf buffer): PokeSnackBlockParticlesPacket =
            PokeSnackBlockParticlesPacket(
                buffer.readBlockPos(),
                buffer.readNullable { buffer.readBlockPos() }
            )
    }

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(blockPos)
        buffer.writeNullable(entityPos) { _, v -> buffer.writeBlockPos(v) }
    }
}
