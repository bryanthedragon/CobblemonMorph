package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nTerastallizeInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TerastallizeInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/TerastallizeInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"])
public class TerastallizeInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val var6: Effect = this.message.effectAt(1);
         if (var6 != null) {
            val var7: ElementalType = ElementalTypes.INSTANCE.get(var6.getId());
            if (var7 != null) {
               val type: ElementalType = var7;
               PokemonBattle.dispatchWaiting$default(battle, 0.0F, (new Function0<Unit>(var10000, battle, type, this) {
                  {
                     super(0);
                     this.$battlePokemon = `$battlePokemon`;
                     this.$battle = `$battle`;
                     this.$type = `$type`;
                     this.this$0 = `$receiver`;
                  }

                  public final void invoke() {
                     val pokemonName: MutableComponent = this.$battlePokemon.getName();
                     val var10000: PokemonBattle = this.$battle;
                     val var10001: MutableComponent = LocalizationUtilsKt.battleLang("terastallize", pokemonName, this.$type.getDisplayName());
                     var10000.broadcastChatMessage(TextKt.yellow(var10001) as Component);
                     this.$battle.getMinorBattleActions().put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
                  }
               }) as Function0, 1, null);
               return;
            }
         }
      }
   }
}
