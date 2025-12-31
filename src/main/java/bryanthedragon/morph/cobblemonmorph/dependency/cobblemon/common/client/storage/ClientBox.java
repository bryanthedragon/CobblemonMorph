/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.POKEMON_PER_BOX
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.gui.PCBoxWallpaperRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

public class ClientBox(var name : MutableComponent?, var wallpaper : ResourceLocation) : Iterable<Pokemon?> {
    constructor() : this(null, PCBoxWallpaperRepository.defaultWallpaper)

    val slots = MutableList<Pokemon?>(POKEMON_PER_BOX) { null }
    override fun iterator() = slots.iterator()
}