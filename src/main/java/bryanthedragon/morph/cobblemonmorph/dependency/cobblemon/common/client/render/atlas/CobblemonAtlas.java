/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas

import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation

public class CobblemonAtlas(
    textureManager: TextureManager,
    atlasResourceLocation id,
    sourcePath: ResourceLocation
) : TextureAtlasHolder(
    textureManager,
    atlasId,
    sourcePath
)
