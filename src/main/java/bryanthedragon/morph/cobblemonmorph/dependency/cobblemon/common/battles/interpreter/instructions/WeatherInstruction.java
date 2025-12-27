package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nWeatherInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WeatherInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/WeatherInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"])
public class WeatherInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: Effect = this.message.effectAt(0);
      if (var10000 != null) {
         val var6: java.lang.String = var10000.getId();
         if (var6 != null) {
            val source: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.message, battle, null, 2, null);
            if (source != null) {
               ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, BattleMessage.effect$default(this.message, null, 1, null), source);
            }

            battle.dispatchWaiting(
               1.5F,
               (
                  new Function0<Unit>(this, var6, battle) {
                     {
                        super(0);
                        this.this$0 = `$receiver`;
                        this.$weather = `$weather`;
                        this.$battle = `$battle`;
                     }

                     public final void invoke() {
                        val var10000: MutableComponent;
                        if (this.this$0.getMessage().hasOptionalArgument("upkeep")) {
                           var10000 = LocalizationUtilsKt.battleLang("weather.${this.$weather}.upkeep");
                        } else if (!(this.$weather == "none")) {
                           this.$battle
                              .getContextManager()
                              .add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.WEATHER, this.$battle));
                           var10000 = LocalizationUtilsKt.battleLang("weather.${this.$weather}.start");
                        } else {
                           val var6: java.util.Collection = this.$battle.getContextManager().get(BattleContext.Type.WEATHER);
                           if (var6 == null) {
                              return;
                           }

                           val var7: java.util.Iterator = var6.iterator();
                           if (var7 == null) {
                              return;
                           }

                           val var8: BattleContext = var7.next() as BattleContext;
                           if (var8 == null) {
                              return;
                           }

                           val var9: java.lang.String = var8.getId();
                           if (var9 == null) {
                              return;
                           }

                           this.$battle.getContextManager().clear(BattleContext.Type.WEATHER);
                           var10000 = LocalizationUtilsKt.battleLang("weather.$var9.end");
                        }

                        val var11: PokemonBattle = this.$battle;
                        var11.broadcastChatMessage(var10000 as Component);
                     }
                  }
               ) as () -> Unit
            );
            return;
         }
      }
   }
}
