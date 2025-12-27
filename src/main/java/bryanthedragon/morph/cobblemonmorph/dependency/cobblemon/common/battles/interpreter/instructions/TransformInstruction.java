package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.EffectTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.TransformEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleTransformPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public class TransformInstruction(battle: PokemonBattle, message: BattleMessage) : InterpreterInstruction {
   public final val battle: PokemonBattle
   public final val expectedTarget: BattlePokemon?
   public final val message: BattleMessage

   init {
      this.battle = battle;
      this.message = message;
      this.expectedTarget = this.message.battlePokemon(0, this.battle);
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: Pair = this.message.pnxAndUuid(0);
      if (var10000 != null) {
         val pnx: java.lang.String = var10000.component1() as java.lang.String;
         val actor: BattleActor = battle.getActorAndActiveSlotFromPNX(pnx).component1() as BattleActor;
         val var7: BattlePokemon = this.message.battlePokemon(0, battle);
         if (var7 != null) {
            val var8: BattlePokemon = this.message.battlePokemon(1, battle);
            if (var8 != null) {
               val targetPokemon: BattlePokemon = var8;
               ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, BattleMessage.effect$default(this.message, null, 1, null), var7);
               battle.dispatch(
                  (
                     new Function0<DispatchResult>(var7, targetPokemon, battle) {
                        {
                           super(0);
                           this.$pokemon = `$pokemon`;
                           this.$targetPokemon = `$targetPokemon`;
                           this.$battle = `$battle`;
                        }

                        @NotNull
                        public final DispatchResult invoke() {
                           val var10000: PokemonEntity = this.$pokemon.getEntity();
                           if (var10000 == null) {
                              return DispatchResultKt.getGO();
                           } else {
                              val future: CompletableFuture = new TransformEffect(this.$targetPokemon.getEffectedPokemon(), this.$battle.getStarted())
                                 .start(var10000);
                              return new UntilDispatch((new Function0<java.lang.Boolean>(future) {
                                 {
                                    super(0);
                                    this.$future = `$future`;
                                 }

                                 @NotNull
                                 public final java.lang.Boolean invoke() {
                                    return this.$future == null || this.$future.isDone();
                                 }
                              }) as () -> java.lang.Boolean);
                           }
                        }
                     }
                  ) as () -> DispatchResult
               );
               PokemonBattle.dispatchWaiting$default(
                  battle,
                  0.0F,
                  (
                     new Function0<Unit>(var7, targetPokemon, battle, this, actor, pnx) {
                        {
                           super(0);
                           this.$pokemon = `$pokemon`;
                           this.$targetPokemon = `$targetPokemon`;
                           this.$battle = `$battle`;
                           this.this$0 = `$receiver`;
                           this.$actor = `$actor`;
                           this.$pnx = `$pnx`;
                        }

                        public final void invoke() {
                           var var14: PokemonProperties;
                           label19: {
                              val var10000: PokemonEntity = this.$pokemon.getEntity();
                              if (var10000 != null) {
                                 val var12: EffectTracker = var10000.getEffects();
                                 if (var12 != null) {
                                    val var13: MocKEffect = var12.getMockEffect();
                                    if (var13 != null) {
                                       var14 = var13.getMock();
                                       break label19;
                                    }
                                 }
                              }

                              var14 = null;
                           }

                           val pokemonName: MutableComponent = this.$pokemon.getName();
                           val targetPokemonName: MutableComponent = this.$targetPokemon.getName();
                           if (var14 != null) {
                              val var7: java.lang.String = this.$pnx;
                              val var8: BattlePokemon = this.$pokemon;
                              PokemonBattle.sendSidedUpdate$default(
                                 this.$battle,
                                 this.$actor,
                                 new BattleTransformPokemonPacket(this.$pnx, this.$pokemon, var14, true),
                                 new BattleTransformPokemonPacket(var7, var8, var14, false),
                                 false,
                                 8,
                                 null
                              );
                           }

                           val lang: MutableComponent = LocalizationUtilsKt.battleLang("transform", pokemonName, targetPokemonName);
                           val var15: PokemonBattle = this.$battle;
                           var15.broadcastChatMessage(lang as Component);
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
