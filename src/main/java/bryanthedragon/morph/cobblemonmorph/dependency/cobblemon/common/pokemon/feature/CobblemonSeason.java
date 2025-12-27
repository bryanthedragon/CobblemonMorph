package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature;

import java.util.EnumSet;

import kotlin.jvm.JvmStatic;

public enum CobblemonSeason {
   SPRING,
   AUTUMN,
   SUMMER,
   WINTER   
   @JvmStatic
   public CobblemonSeason.Companion Companion = new CobblemonSeason.Companion(null);
   @JvmStatic
   private EnumSet<CobblemonSeason> ALL_VALUES = EnumSet.allOf(CobblemonSeason.class);

   public companion object {
      public final val ALL_VALUES: EnumSet<CobblemonSeason>
   }
}
