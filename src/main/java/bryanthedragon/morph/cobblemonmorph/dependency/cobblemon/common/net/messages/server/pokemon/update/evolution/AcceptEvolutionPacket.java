/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import java.util.UUID
import net.minecraft.network.RegistryFriendlyByteBuf

public class AcceptEvolutionPacket(val pokemonUUID uuid, val evolutionId: String) : NetworkPacket<AcceptEvolutionPacket> {

    constructor(Pokemon pokemon, evolution: EvolutionDisplay) : this(pokemon.uuid, evolution.id)

    override val id = ID

    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(this.pokemonUUID)
        buffer.writeString(this.evolutionId)
    }

    final class Companion {
        val ID = cobblemonResource("accept_evolution")
        fun decode(RegistryFriendlyByteBuf buffer) = AcceptEvolutionPacket(buffer.readUUID(), buffer.readString())
    }
}