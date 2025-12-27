package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move

public class BattleMove(move: Move) {
   public final var disabled: Boolean
   public final val move: Move

   init {
      this.move = move;
   }
}
