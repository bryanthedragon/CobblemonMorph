package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.LinkedHashSet
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.TextureAtlasHolder

public object CobblemonAtlases {
   public final val BERRY_SPRITE_ATLAS: TextureAtlasHolder = INSTANCE.register("textures/atlas/berries.png", "berries")
   public final val atlases: MutableSet<TextureAtlasHolder> = (new LinkedHashSet()) as java.util.Set

   public fun register(atlasId: String, sourcePath: String): TextureAtlasHolder {
      val var10002: TextureManager = Minecraft.m_91087_().m_91097_();
      val atlas: CobblemonAtlas = new CobblemonAtlas(var10002, MiscUtilsKt.cobblemonResource(atlasId), MiscUtilsKt.cobblemonResource(sourcePath));
      atlases.add(atlas);
      return atlas;
   }
}
