package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle

public interface InterpreterInstruction {
   public abstract operator fun invoke(battle: PokemonBattle) {
   }
}
