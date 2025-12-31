package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.countable.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.context.countable.CountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;

public record BattleCountableContext(PokemonBattle battle, int times) : CountableContext(times) {

}
