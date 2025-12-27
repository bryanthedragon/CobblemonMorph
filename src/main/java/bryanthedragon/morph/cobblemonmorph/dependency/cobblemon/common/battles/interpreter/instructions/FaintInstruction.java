package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFaintedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleFaintPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.Arrays
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class FaintInstruction(battle: PokemonBattle, message: BattleMessage) : InterpreterInstruction {
   public final val faintingPokemon: BattlePokemon
   public final val message: BattleMessage
   public final var waitTime: Float

   init {
      this.message = message;
      this.waitTime = 2.5F;
      val var10001: BattlePokemon = this.message.battlePokemon(0, battle);
      this.faintingPokemon = var10001;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(
         this.waitTime,
         (
            new Function0<Unit>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  val var10000: Pair = this.this$0.getMessage().pnxAndUuid(0);
                  if (var10000 != null) {
                     val pnx: java.lang.String = var10000.component1() as java.lang.String;
                     this.$battle.sendUpdate(new BattleFaintPacket(pnx));
                     this.this$0.getFaintingPokemon().getEffectedPokemon().setCurrentHealth(0);
                     this.this$0.getFaintingPokemon().sendUpdate();
                     val context: BattleContext = ShowdownInterpreter.INSTANCE.getContextFromFaint(this.this$0.getFaintingPokemon(), this.$battle);
                     val `$this$iv`: EventObservable = CobblemonEvents.BATTLE_FAINTED;
                     val `events$iv`: Array<BattleFaintedEvent> = new BattleFaintedEvent[]{
                        new BattleFaintedEvent(this.$battle, this.this$0.getFaintingPokemon(), context)
                     };
                     `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

                     for (Object element$iv$iv : events$iv) {
                        ;
                     }

                     (this.$battle.getActorAndActiveSlotFromPNX(pnx).getSecond() as ActiveBattlePokemon).setBattlePokemon(null);
                     this.this$0.getFaintingPokemon().getContextManager().add(context);
                     this.this$0
                        .getFaintingPokemon()
                        .getContextManager()
                        .clear(BattleContext.Type.STATUS, BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                     this.$battle.getMajorBattleActions().put(this.this$0.getFaintingPokemon().getUuid(), this.this$0.getMessage());
                  }
               }
            }
         ) as () -> Unit
      );
      battle.dispatchWaiting(0.5F, (new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: MutableComponent = LocalizationUtilsKt.battleLang("fainted", this.this$0.getFaintingPokemon().getName());
            this.$battle.broadcastChatMessage(TextKt.red(var10000) as Component);
         }
      }) as () -> Unit);
   }
}
