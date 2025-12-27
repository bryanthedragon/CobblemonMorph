package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import kotlin.math.MathKt
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class SetHpInstruction(actor: BattleActor, publicMessage: BattleMessage, privateMessage: BattleMessage) : InterpreterInstruction {
   public final val actor: BattleActor
   public final val privateMessage: BattleMessage
   public final val publicMessage: BattleMessage

   init {
      this.actor = actor;
      this.publicMessage = publicMessage;
      this.privateMessage = privateMessage;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      PokemonBattle.dispatchWaiting$default(
         battle,
         0.0F,
         (
            new Function0<Unit>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  val var10000: Pair = this.this$0.getPrivateMessage().pnxAndUuid(0);
                  if (var10000 != null) {
                     val pnx: java.lang.String = var10000.component1() as java.lang.String;
                     val var9: java.lang.String = this.this$0.getPrivateMessage().argumentAt(1);
                     if (var9 != null) {
                        val var11: java.util.List = StringsKt.split$default(var9, new java.lang.String[]{"/"}, false, 0, 6, null);
                        if (var11 != null) {
                           val var12: java.lang.String = CollectionsKt.getOrNull(var11, 0) as java.lang.String;
                           if (var12 != null) {
                              val var13: java.lang.Float = StringsKt.toFloatOrNull(var12);
                              if (var13 != null) {
                                 val flatHp: Float = var13;
                                 val var14: java.lang.String = this.this$0.getPublicMessage().argumentAt(1);
                                 if (var14 != null) {
                                    val var16: java.util.List = StringsKt.split$default(var14, new java.lang.String[]{"/"}, false, 0, 6, null);
                                    if (var16 != null) {
                                       val var17: java.lang.String = CollectionsKt.getOrNull(var16, 0) as java.lang.String;
                                       if (var17 != null) {
                                          val var18: java.lang.Float = StringsKt.toFloatOrNull(var17);
                                          if (var18 != null) {
                                             val ratioHp: Float = var18 * 0.01F;
                                             val var19: BattlePokemon = this.this$0.getPrivateMessage().battlePokemon(0, this.$battle);
                                             if (var19 == null) {
                                                return;
                                             }

                                             var19.getEffectedPokemon().setCurrentHealth(MathKt.roundToInt(flatHp));
                                             PokemonBattle.sendSidedUpdate$default(
                                                this.$battle,
                                                this.this$0.getActor(),
                                                new BattleHealthChangePacket(pnx, flatHp, null, 4, null),
                                                new BattleHealthChangePacket(pnx, ratioHp, null, 4, null),
                                                false,
                                                8,
                                                null
                                             );
                                             if (!this.this$0.getPublicMessage().hasOptionalArgument("silent")) {
                                                val var20: Effect = BattleMessage.effect$default(this.this$0.getPublicMessage(), null, 1, null);
                                                if (var20 == null) {
                                                   return;
                                                }

                                                val var21: java.lang.String = var20.getId();
                                                if (var21 == null) {
                                                   return;
                                                }

                                                val var8: MutableComponent = LocalizationUtilsKt.battleLang("sethp.$var21");
                                                val var22: PokemonBattle = this.$battle;
                                                var22.broadcastChatMessage(var8 as Component);
                                             }

                                             this.$battle.getMinorBattleActions().put(var19.getUuid(), this.this$0.getPublicMessage());
                                             return;
                                          }
                                       }
                                    }
                                 }

                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         ) as Function0,
         1,
         null
      );
   }
}
