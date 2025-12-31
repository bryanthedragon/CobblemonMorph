/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

public class SpawnPokeballPacket(
    val pokeBall: PokeBall,
    val aspects: Set<String>,
    vanillaSpawnPacket: ClientboundAddEntityPacket
) : SpawnExtraDataEntityPacket<SpawnPokeballPacket, EmptyPokeBallEntity>(vanillaSpawnPacket) {

    override val ResourceLocation id = ID

    override fun encodeEntityData(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(this.pokeBall.name)
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect)}
    }

    override fun applyData(entity: EmptyPokeBallEntity, level: ClientLevel) {
        entity.pokeBall = this.pokeBall
        entity.aspects = this.aspects
    }

    override fun checkType(Entity entity): Boolean = entity is EmptyPokeBallEntity

    final class Companion {
        val ID = cobblemonResource("spawn_empty_pokeball_entity")
        fun decode(RegistryFriendlyByteBuf buffer): SpawnPokeballPacket {
            val pokeBall = PokeBalls.getPokeBall(buffer.readIdentifier())!!
            val aspects = buffer.readList { it.readString() }.toSet()
            val vanillaPacket = decodeVanillaPacket(buffer)

            return SpawnPokeballPacket(pokeBall, aspects, vanillaPacket)
        }
    }
}
