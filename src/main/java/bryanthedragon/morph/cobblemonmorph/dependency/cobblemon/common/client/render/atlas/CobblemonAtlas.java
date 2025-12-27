package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas

import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.resources.ResourceLocation

public class CobblemonAtlas(textureManager: TextureManager, atlasId: ResourceLocation, sourcePath: ResourceLocation) : TextureAtlasHolder(
      textureManager, atlasId, sourcePath
   )
