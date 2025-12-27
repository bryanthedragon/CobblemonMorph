package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.BattleCountableCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CaughtPokemonCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountablePokemonTypeContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.LevelUpCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckCriterion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PickStarterCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PlantTumblestoneCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractCriterion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCountableCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.TradePokemonCriterionCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.advancements.CriterionTrigger

public object CobblemonCriteria {
   public final val CATCH_POKEMON: SimpleCriterionTrigger<CountablePokemonTypeContext, CaughtPokemonCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<CountablePokemonTypeContext, CaughtPokemonCriterionCondition>(
               MiscUtilsKt.cobblemonResource("catch_pokemon"), CaughtPokemonCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val CATCH_SHINY_POKEMON: SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition>(
               MiscUtilsKt.cobblemonResource("catch_shiny_pokemon"), SimpleCountableCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val COLLECT_ASPECT: AspectCriterionTrigger =
      INSTANCE.create((new AspectCriterionTrigger(MiscUtilsKt.cobblemonResource("aspects_collected"), AspectCriterionCondition.class)) as CriterionTrigger) as AspectCriterionTrigger
      public final val DEFEAT_POKEMON: SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition>(
               MiscUtilsKt.cobblemonResource("pokemon_defeated"), SimpleCountableCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val EGG_HATCH: SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<CountableContext, SimpleCountableCriterionCondition>(
               MiscUtilsKt.cobblemonResource("eggs_hatched"), SimpleCountableCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val EVOLVE_POKEMON: SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<EvolvePokemonContext, EvolvePokemonCriterionCondition>(
               MiscUtilsKt.cobblemonResource("pokemon_evolved"), EvolvePokemonCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val LEVEL_UP: SimpleCriterionTrigger<LevelUpContext, LevelUpCriterionCondition> =
      INSTANCE.create(
         (new SimpleCriterionTrigger<LevelUpContext, LevelUpCriterionCondition>(MiscUtilsKt.cobblemonResource("level_up"), LevelUpCriterionCondition.class)) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val PARTY_CHECK: SimpleCriterionTrigger<PartyCheckContext, PartyCheckCriterion> =
      INSTANCE.create(
         (new SimpleCriterionTrigger<PartyCheckContext, PartyCheckCriterion>(MiscUtilsKt.cobblemonResource("party"), PartyCheckCriterion.class)) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val PASTURE_USE: SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> =
      INSTANCE.create(
         (new SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition>(MiscUtilsKt.cobblemonResource("pasture_use"), PickStarterCriterionCondition.class)) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val PICK_STARTER: SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition>(
               MiscUtilsKt.cobblemonResource("pick_starter"), PickStarterCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val PLANT_TUMBLESTONE: SimpleCriterionTrigger<PlantTumblestoneContext, PlantTumblestoneCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<PlantTumblestoneContext, PlantTumblestoneCriterionCondition>(
               MiscUtilsKt.cobblemonResource("plant_tumblestone"), PlantTumblestoneCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val POKEMON_INTERACT: SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<PokemonInteractContext, PokemonInteractCriterion>(
               MiscUtilsKt.cobblemonResource("pokemon_interact"), PokemonInteractCriterion.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val RESURRECT_POKEMON: SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<Pokemon, PickStarterCriterionCondition>(
               MiscUtilsKt.cobblemonResource("resurrect_pokemon"), PickStarterCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val TRADE_POKEMON: SimpleCriterionTrigger<TradePokemonContext, TradePokemonCriterionCondition> =
      INSTANCE.create(
         (
            new SimpleCriterionTrigger<TradePokemonContext, TradePokemonCriterionCondition>(
               MiscUtilsKt.cobblemonResource("trade_pokemon"), TradePokemonCriterionCondition.class
            )
         ) as CriterionTrigger
      ) as SimpleCriterionTrigger
      public final val WIN_BATTLE: BattleCountableCriterionTrigger =
      INSTANCE.create(
         (new BattleCountableCriterionTrigger(MiscUtilsKt.cobblemonResource("battles_won"), BattleCountableCriterionCondition.class)) as CriterionTrigger
      ) as BattleCountableCriterionTrigger

   private fun <T : CriterionTrigger<*>> create(criteria: Any): Any {
      return (T)Cobblemon.INSTANCE.getImplementation().registerCriteria(criteria);
   }
}
