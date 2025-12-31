/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.entry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.readString
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.resources.ResourceLocation

/**
 * Controls a toggle in the Pokédex GUI that iterates through some set of aspects that
 * presumably match a species feature or other aspect provider on the server side. This is
 * used to let the player check out the different variations of a Pokémon.
 *
 * @author Hiroku
 * @since August 25th, 2025
 */
public class PokedexCosmeticVariation {
    var displayName: String = "cobblemon.pokedex.variation.cosmetic"
    var icon: ResourceLocation = cobblemonResource("textures/gui/pokedex/variation/cosmetic.png")
    Set<String> aspects = emptySet()

    fun clone() = PokedexCosmeticVariation().also {
        it.displayName = displayName
        it.icon = icon
        it.aspects = aspects.toSet()
    }

    fun encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeString(displayName)
        buffer.writeResourceLocation(icon)
        buffer.writeCollection(aspects) { _, aspect -> buffer.writeString(aspect) }
    }

    fun decode(RegistryFriendlyByteBuf buffer) {
        displayName = buffer.readString()
        icon = buffer.readResourceLocation()
        aspects = buffer.readList { buffer.readString() }.toSet()
    }
}
