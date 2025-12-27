package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleReplacePokemonPacket
import kotlin.jvm.functions.Function0

public class ReplaceInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: Pair = this.message.pnxAndUuid(0);
      if (var10000 != null) {
         val pnx: java.lang.String = var10000.component1() as java.lang.String;
         val var3: Pair = battle.getActorAndActiveSlotFromPNX(pnx);
         val actor: BattleActor = var3.component1() as BattleActor;
         val activePokemon: ActiveBattlePokemon = var3.component2() as ActiveBattlePokemon;
         val var7: BattlePokemon = this.message.battlePokemon(0, battle);
         if (var7 != null) {
            val pokemon: BattlePokemon = var7;
            battle.dispatchGo(
               (
                  new Function0<Unit>(pokemon, battle, actor, pnx, activePokemon) {
                     {
                        super(0);
                        this.$pokemon = `$pokemon`;
                        this.$battle = `$battle`;
                        this.$actor = `$actor`;
                        this.$pnx = `$pnx`;
                        this.$activePokemon = `$activePokemon`;
                     }

                     public final void invoke() {
                        val entity: PokemonEntity = this.$pokemon.getEntity();
                        if (entity != null) {
                           val var10000: MocKEffect = entity.getEffects().getMockEffect();
                           if (var10000 != null) {
                              var10000.end(entity);
                           }
                        }

                        PokemonBattle.sendSidedUpdate$default(
                           this.$battle,
                           this.$actor,
                           new BattleReplacePokemonPacket(this.$pnx, this.$pokemon, true),
                           new BattleReplacePokemonPacket(this.$pnx, this.$pokemon, false),
                           false,
                           8,
                           null
                        );
                        this.$activePokemon.setIllusion(null);
                     }
                  }
               ) as () -> Unit
            );
         }
      }
   }
}
