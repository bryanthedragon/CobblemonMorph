package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Flavor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth

public class Nature(name: ResourceLocation, displayName: String, increasedStat: Stat?, decreasedStat: Stat?, favoriteFlavor: Flavor?, dislikedFlavor: Flavor?) {
   public final val decreasedStat: Stat?
   public final val dislikedFlavor: Flavor?
   public final val displayName: String
   public final val favoriteFlavor: Flavor?
   public final val increasedStat: Stat?
   public final val name: ResourceLocation

   init {
      this.name = name;
      this.displayName = displayName;
      this.increasedStat = increasedStat;
      this.decreasedStat = decreasedStat;
      this.favoriteFlavor = favoriteFlavor;
      this.dislikedFlavor = dislikedFlavor;
   }

   public fun modifyStat(stat: Stat, value: Int): Int {
      return if (stat == this.increasedStat)
         Mth.m_14107_((double)value * 1.1)
         else
         (if (stat == this.decreasedStat) Mth.m_14107_((double)value * 0.9) else value);
   }
}
