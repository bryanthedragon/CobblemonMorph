package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import java.util.UUID
import kotlin.jvm.functions.Function3
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public abstract class AIBattleActor : BattleActor {
   public final val battleAI: BattleAI

   open fun AIBattleActor(gameId: UUID, pokemonList: MutableList<BattlePokemon>, battleAI: BattleAI) {
      super(gameId, CollectionsKt.toMutableList(pokemonList));
      this.battleAI = battleAI;
   }

   public override fun sendUpdate(packet: NetworkPacket<*>) {
      super.sendUpdate(packet);
      if (packet is BattleMakeChoicePacket) {
         this.onChoiceRequested();
      }
   }

   public open fun onChoiceRequested() {
      try {
         val var3: ShowdownActionRequest = this.getRequest();
         this.setActionResponses(
            var3.iterate(
               this.getActivePokemon(),
               (
                  new Function3<ActiveBattlePokemon, ShowdownMoveset, java.lang.Boolean, ShowdownActionResponse>(this.battleAI) {
                     {
                        super(
                           3,
                           receiver,
                           BattleAI::class.java,
                           "choose",
                           "choose(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;Z)Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;",
                           0
                        );
                     }

                     @NotNull
                     public final ShowdownActionResponse invoke(@NotNull ActiveBattlePokemon p0, @Nullable ShowdownMoveset p1, boolean p2) {
                        return (this.receiver as BattleAI).choose(p0, p1, p2);
                     }
                  }
               ) as (ActiveBattlePokemon?, ShowdownMoveset?, java.lang.Boolean?) -> ShowdownActionResponse
            )
         );
      } catch (var2: IllegalActionChoiceException) {
         Cobblemon.INSTANCE.getLOGGER().error("AI was unable to choose a move, we're going to need to pass!");
         var2.printStackTrace();
         val var10001: ShowdownActionRequest = this.getRequest();
         this.setActionResponses(var10001.iterate(this.getActivePokemon(), <unrepresentable>.INSTANCE));
      }
   }
}
