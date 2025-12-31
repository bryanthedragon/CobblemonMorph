/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.italicise
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.onHover
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import net.minecraft.network.chat.MutableComponent


/**
 * Italicizes the nature text and makes it include the name of the MintItem used to mint the Pokémon's nature
 */
fun reformatNatureTextIfMinted(Pokemon pokemon): MutableComponent {
    var natureText = pokemon.nature.displayName.asTranslated()
    if (pokemon.mintedNature != null) {
        CobblemonItems.mints[pokemon.mintedNature!!.displayName]?.let { mint ->
            natureText = natureText.italicise().onHover(mint.description)
        }
    }
    return natureText
}