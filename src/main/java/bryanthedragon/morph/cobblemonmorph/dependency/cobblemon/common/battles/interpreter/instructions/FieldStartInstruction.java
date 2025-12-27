package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.Locale
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nFieldStartInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FieldStartInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/FieldStartInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"])
public class FieldStartInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: Effect = this.message.effectAt(0);
      if (var10000 != null) {
         val source: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.message, battle, null, 2, null);
         if (source != null) {
            ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, BattleMessage.effect$default(this.message, null, 1, null), source);
         }

         battle.dispatchWaiting(
            1.5F,
            (
               new Function0<Unit>(var10000, source, battle, this) {
                  {
                     super(0);
                     this.$effect = `$effect`;
                     this.$source = `$source`;
                     this.$battle = `$battle`;
                     this.this$0 = `$receiver`;
                  }

                  public final void invoke() {
                     var type: Array<Any>;
                     var var10000: java.lang.String;
                     var var10003: MutableComponent;
                     label11: {
                        var10000 = "fieldstart.${this.$effect.getId()}";
                        type = new Object[1];
                        if (this.$source != null) {
                           var10003 = this.$source.getName();
                           if (var10003 != null) {
                              break label11;
                           }
                        }

                        var10003 = Component.m_237113_("UNKNOWN");
                     }

                     type[0] = var10003;
                     val lang: MutableComponent = LocalizationUtilsKt.battleLang(var10000, type);
                     val var5: PokemonBattle = this.$battle;
                     var5.broadcastChatMessage(lang as Component);
                     var10000 = StringsKt.substringAfterLast$default(this.$effect.getRawData(), " ", null, 2, null).toUpperCase(Locale.ROOT);
                     this.$battle
                        .getContextManager()
                        .add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.valueOf(var10000), this.$battle));
                  }
               }
            ) as () -> Unit
         );
      }
   }
}
