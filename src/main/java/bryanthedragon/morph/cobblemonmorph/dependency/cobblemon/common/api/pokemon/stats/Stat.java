package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public interface Stat {
   public val displayName: Component
   public val identifier: ResourceLocation
   public val showdownId: String
   public val type: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat.Type

   public enum Type {
      PERMANENT,
      BATTLE_ONLY   }
}
