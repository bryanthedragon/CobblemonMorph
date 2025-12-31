package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.calculator;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;

public class GenerationEvoCalculator {
    public final class Generation8EvCalculator : EvCalculator {

        private final powerItems = mapOf(Stats.SPEED to CobblemonItemTags.POWER_ANKLET, Stats.SPECIAL_DEFENCE to CobblemonItemTags.POWER_BAND, Stats.DEFENCE to CobblemonItemTags.POWER_BELT, Stats.ATTACK to CobblemonItemTags.POWER_BRACER, Stats.SPECIAL_ATTACK to CobblemonItemTags.POWER_LENS, Stats.HP to CobblemonItemTags.POWER_WEIGHT);

        Map<Stat, Int> calculate(BattlePokemon battlePokemon, BattlePokemon opponentPokemon) {
            val heldItem = battlePokemon.effectedPokemon.heldItemNoCopy();
            val evYield = mutableMapOf<Stat, Int>();
            for ((stat, value) in opponentPokemon.originalPokemon.form.evYield) {
                val boost = if (!heldItem.isEmpty && heldItem.`is`(powerItems[stat])) 8 else 0
                evYield[stat] = evYield.getOrDefault(stat, 0) + value + boost;
            }
            return evYield;
        }
    }
}
