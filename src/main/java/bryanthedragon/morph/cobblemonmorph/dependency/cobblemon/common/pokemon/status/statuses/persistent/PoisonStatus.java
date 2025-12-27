package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.random.Random
import net.minecraft.server.level.ServerPlayer

public class PoisonStatus : PersistentStatus(
      MiscUtilsKt.cobblemonResource("poison"), "psn", "cobblemon.status.poison.apply", "cobblemon.status.poison.cure", new IntRange(180, 300)
   ) {
   public override fun onSecondPassed(player: ServerPlayer, pokemon: Pokemon, random: Random) {
      if (!pokemon.isFainted() && random.nextInt(15) == 0) {
         pokemon.setCurrentHealth(
            pokemon.getCurrentHealth()
               - Math.max(1, (int)Math.rint((double)pokemon.getHp() * 0.05)) * (if (pokemon.getAbility().getTemplate().getName() == "poisonheal") -1 else 1)
         );
         if (pokemon.getCurrentHealth() == pokemon.getHp()) {
            pokemon.setStatus(null);
         }
      }
   }
}
