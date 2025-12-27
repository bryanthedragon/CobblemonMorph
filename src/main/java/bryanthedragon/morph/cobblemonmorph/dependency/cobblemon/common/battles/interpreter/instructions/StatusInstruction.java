package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class StatusInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      var var10000: Pair = this.message.pnxAndUuid(0);
      if (var10000 != null) {
         val pnx: java.lang.String = var10000.component1() as java.lang.String;
         val var7: BattlePokemon = this.message.battlePokemon(0, battle);
         if (var7 != null) {
            label29: {
               var10000 = this.message.actorAndActivePokemonFromOptional(battle, "of");
               if (var10000 != null) {
                  val var9: ActiveBattlePokemon = var10000.getSecond() as ActiveBattlePokemon;
                  if (var9 != null) {
                     var10 = var9.getBattlePokemon();
                     break label29;
                  }
               }

               var10 = null;
            }

            val var11: java.lang.String = this.message.argumentAt(1);
            if (var11 != null) {
               val var12: Status = Statuses.INSTANCE.getStatus(var11);
               if (var12 == null) {
                  Cobblemon.INSTANCE.getLOGGER().error("Unrecognized status: $var11");
               } else {
                  val var13: ShowdownInterpreter = ShowdownInterpreter.INSTANCE;
                  val var10002: Effect = BattleMessage.effect$default(this.message, null, 1, null);
                  var var10003: BattlePokemon = var10;
                  if (var10 == null) {
                     var10003 = var7;
                  }

                  var13.broadcastOptionalAbility(battle, var10002, var10003);
                  PokemonBattle.dispatchWaiting$default(
                     battle,
                     0.0F,
                     (
                        new Function0<Unit>(var12, var7, battle, pnx, this) {
                           {
                              super(0);
                              this.$status = `$status`;
                              this.$pokemon = `$pokemon`;
                              this.$battle = `$battle`;
                              this.$pnx = `$pnx`;
                              this.this$0 = `$receiver`;
                           }

                           public final void invoke() {
                              if (this.$status is PersistentStatus) {
                                 this.$pokemon.getEffectedPokemon().applyStatus(this.$status as PersistentStatus);
                                 this.$battle.sendUpdate(new BattlePersistentStatusPacket(this.$pnx, this.$status as PersistentStatus));
                              }

                              val var10000: PokemonBattle = this.$battle;
                              val var4: MutableComponent = MiscUtilsKt.asTranslated(this.$status.getApplyMessage(), this.$pokemon.getName());
                              var10000.broadcastChatMessage(var4 as Component);
                              this.$pokemon
                                 .getContextManager()
                                 .add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.STATUS, this.$battle));
                              this.$battle.getMinorBattleActions().put(this.$pokemon.getUuid(), this.this$0.getMessage());
                           }
                        }
                     ) as Function0,
                     1,
                     null
                  );
               }
            }
         }
      }
   }
}
