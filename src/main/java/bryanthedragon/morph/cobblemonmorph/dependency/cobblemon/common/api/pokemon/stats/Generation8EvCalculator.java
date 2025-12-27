package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import java.util.LinkedHashMap
import java.util.Map.Entry
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

public object Generation8EvCalculator : EvCalculator {
   private final val powerItems: Map<Stats, TagKey<Item>> =
      MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to(Stats.SPEED, CobblemonItemTags.POWER_ANKLET),
            TuplesKt.to(Stats.SPECIAL_DEFENCE, CobblemonItemTags.POWER_BAND),
            TuplesKt.to(Stats.DEFENCE, CobblemonItemTags.POWER_BELT),
            TuplesKt.to(Stats.ATTACK, CobblemonItemTags.POWER_BRACER),
            TuplesKt.to(Stats.SPECIAL_ATTACK, CobblemonItemTags.POWER_LENS),
            TuplesKt.to(Stats.HP, CobblemonItemTags.POWER_WEIGHT)
         }
      )

   public override fun calculate(battlePokemon: BattlePokemon, opponentPokemon: BattlePokemon): Map<Stat, Int> {
      val heldItem: ItemStack = battlePokemon.getEffectedPokemon().heldItemNoCopy$common();
      val evYield: java.util.Map = new LinkedHashMap();

      for (Entry var6 : opponentPokemon.getOriginalPokemon().getForm().getEvYield().entrySet()) {
         val stat: Stat = var6.getKey() as Stat;
         evYield.put(
            stat,
            evYield.getOrDefault(stat, 0).intValue()
               + (var6.getValue() as java.lang.Number).intValue()
               + (if (!heldItem.m_41619_() && heldItem.m_204117_(powerItems.get(stat))) 8 else 0)
         );
      }

      return evYield;
   }

   override fun calculate(battlePokemon: BattlePokemon): MutableMap<Stat, Int> {
      return EvCalculator.DefaultImpls.calculate(this, battlePokemon);
   }
}
