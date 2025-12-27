package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon

public interface BattleContext {
   public val id: String
   public val origin: BattlePokemon?
   public val turn: Int
   public val type: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext.Type

   public enum Type(damaging: Boolean, exclusive: Boolean) {
      ITEM(true, true),
      STATUS(true, false),
      VOLATILE(true, false),
      HAZARD(true, false),
      WEATHER(true, true),
      ROOM(false, true),
      SPORT(false, false),
      TERRAIN(false, true),
      GRAVITY(false, true),
      TAILWIND(false, true),
      SCREEN(false, false),
      FAINT(false, false),
      BOOST(false, false),
      UNBOOST(false, false),
      MISC(false, false)
      public final val damaging: Boolean
      public final val exclusive: Boolean

      init {
         this.damaging = damaging;
         this.exclusive = exclusive;
      }
   }
}
