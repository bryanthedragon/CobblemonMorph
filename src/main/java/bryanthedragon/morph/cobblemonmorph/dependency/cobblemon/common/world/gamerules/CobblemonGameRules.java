package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonImplementation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.BooleanRuleInvoker
import net.minecraft.world.GameRules.BooleanRule
import net.minecraft.world.GameRules.Key
import net.minecraft.world.level.GameRules.Category
import net.minecraft.world.level.GameRules.Type

public object CobblemonGameRules {
   public final val DO_POKEMON_LOOT: Key<BooleanRule>
   public final val DO_POKEMON_SPAWNING: Key<BooleanRule>
   public final val SHINY_STARTERS: Key<BooleanRule>

   @JvmStatic
   fun {
      var var10000: CobblemonImplementation = Cobblemon.INSTANCE.getImplementation();
      var var10002: Category = Category.SPAWNING;
      var var10003: Type = BooleanRuleInvoker.cobblemon$create(true);
      DO_POKEMON_SPAWNING = var10000.registerGameRule("doPokemonSpawning", var10002, var10003);
      var10000 = Cobblemon.INSTANCE.getImplementation();
      var10002 = Category.DROPS;
      var10003 = BooleanRuleInvoker.cobblemon$create(true);
      DO_POKEMON_LOOT = var10000.registerGameRule("doPokemonLoot", var10002, var10003);
      var10000 = Cobblemon.INSTANCE.getImplementation();
      var10002 = Category.MISC;
      var10003 = BooleanRuleInvoker.cobblemon$create(false);
      SHINY_STARTERS = var10000.registerGameRule("doShinyStarters", var10002, var10003);
   }
}
