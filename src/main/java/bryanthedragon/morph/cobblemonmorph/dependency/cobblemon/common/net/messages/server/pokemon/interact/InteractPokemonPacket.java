/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.interact

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractTypePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.pokemon.interact.InteractPokemonHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readEnumConstant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeEnumConstant
import net.minecraft.network.RegistryFriendlyByteBuf
import java.util.UUID

/**
 * Tells the server to handle Pokémon interaction.
 *
 * Handled by [InteractPokemonHandler].
 *
 * @author Village
 * @since January 7th, 2023
 */
public class InteractPokemonPacket(val UUID pokemonId, val interactType: InteractTypePokemon) : NetworkPacket<InteractPokemonPacket> {
    override val id = ID
    override fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUUID(pokemonID)
        buffer.writeEnumConstant(interactType)
    }
    final class Companion {
        val ID = cobblemonResource("interact_pokemon")
        fun decode(RegistryFriendlyByteBuf buffer) = InteractPokemonPacket(buffer.readUUID(), buffer.readEnumConstant(InteractTypePokemon.class))
    }
}