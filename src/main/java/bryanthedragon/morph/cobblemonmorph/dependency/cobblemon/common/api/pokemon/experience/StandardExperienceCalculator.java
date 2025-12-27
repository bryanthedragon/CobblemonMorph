package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.LevelRequirement
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.math.MathKt

@SourceDebugExtension(["SMAP\nExperienceCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExperienceCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/experience/StandardExperienceCalculator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n*L\n1#1,45:1\n1747#2,2:46\n1749#2:52\n1229#3,2:48\n1206#3,2:50\n*S KotlinDebug\n*F\n+ 1 ExperienceCalculator.kt\ncom/cobblemon/mod/common/api/pokemon/experience/StandardExperienceCalculator\n*L\n35#1:46,2\n35#1:52\n37#1:48,2\n37#1:50,2\n*E\n"])
public object StandardExperienceCalculator : ExperienceCalculator {
   public override fun calculate(battlePokemon: BattlePokemon, opponentPokemon: BattlePokemon, participationMultiplier: Double): Int {
      val baseExp: Int = opponentPokemon.getOriginalPokemon().getForm().getBaseExperienceYield();
      val opponentLevel: Int = opponentPokemon.getEffectedPokemon().getLevel();
      val term1: Double = baseExp * opponentLevel / 5.0;
      val term2: Double = 1 * participationMultiplier;
      val term3: Double = Math.pow(
         (2.0 * (double)opponentLevel + (double)10) / (double)(opponentLevel + battlePokemon.getEffectedPokemon().getLevel() + 10), 2.5
      );
      val luckyEggMultiplier: Double = if (battlePokemon.getEffectedPokemon().heldItemNoCopy$common().m_204117_(CobblemonItemTags.LUCKY_EGG))
         Cobblemon.INSTANCE.getConfig().getLuckyEggMultiplier()
         else
         1.0;
      val affectionMultiplier: java.lang.Iterable = battlePokemon.getEffectedPokemon().getEvolutionProxy().server();
      var var44: Boolean;
      if (affectionMultiplier is java.util.Collection && (affectionMultiplier as java.util.Collection).isEmpty()) {
         var44 = false;
      } else {
         val gimmickBoost: java.util.Iterator = affectionMultiplier.iterator();

         while (true) {
            if (!gimmickBoost.hasNext()) {
               var44 = false;
               break;
            }

            val requirements: Sequence = CollectionsKt.asSequence((gimmickBoost.next() as Evolution).getRequirements());
            var var30: java.util.Iterator = requirements.iterator();

            while (true) {
               if (!var30.hasNext()) {
                  var44 = false;
                  break;
               }

               if (var30.next() as EvolutionRequirement is LevelRequirement) {
                  var44 = true;
                  break;
               }
            }

            label61: {
               if (var44) {
                  var30 = requirements.iterator();

                  while (true) {
                     if (!var30.hasNext()) {
                        var44 = true;
                        break;
                     }

                     if (!(var30.next() as EvolutionRequirement).check(battlePokemon.getEffectedPokemon())) {
                        var44 = false;
                        break;
                     }
                  }

                  if (var44) {
                     var44 = true;
                     break label61;
                  }
               }

               var44 = false;
            }

            if (var44) {
               var44 = true;
               break;
            }
         }
      }

      return MathKt.roundToInt(
         (term1 * term2 * term3 + (double)1)
            * 1.0
            * luckyEggMultiplier
            * (if (var44) 1.2 else 1.0)
            * (if (battlePokemon.getEffectedPokemon().getFriendship() >= 220) 1.2 else 1.0)
            * (double)Cobblemon.INSTANCE.getConfig().getExperienceMultiplier()
      );
   }
}
