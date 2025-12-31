/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.FloatingState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

/**
 * A Pokémon that can absolutely, under many circumstances, be rendered (or else!!!).
 *
 * @author Hiroku
 * @since August 1st, 2022
 */
public record RenderablePokemon(Species species, Set<String> aspects, ItemStack heldItem = ItemStack.EMPTY) {
    final form: FormData by lazy { species.getForm(aspects) };

    RegistryFriendlyByteBuf saveToBuffer(RegistryFriendlyByteBuf buffer) {
        buffer.writeIdentifier(species.resourceIdentifier);
        buffer.writeSizedInt(IntSize.U_BYTE, aspects.size);
        aspects.forEach(buffer::writeString);
        buffer.writeItemStack(heldItem);
        return buffer;
    }

    boolean equals(other: Any?) {
        return if (other is RenderablePokemon) (other.species.resourceIdentifier == this.species.resourceIdentifier && other.aspects == this.aspects && other.heldItem == this.heldItem) 
        else 
            false
    }

    final class Companion {
        RenderablePokemon loadFromBuffer(RegistryFriendlyByteBuf buffer) {
            final species = PokemonSpecies.getByIdentifier(buffer.readIdentifier())!!;
            final aspects = mutableSetOf<String>();
            repeat(times = buffer.readSizedInt(IntSize.U_BYTE)) {
                aspects.add(buffer.readString());
            }
            val heldItem = buffer.readItemStack();
            return RenderablePokemon(species, aspects, heldItem);
        }
    }
}