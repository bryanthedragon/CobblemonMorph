package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.LinkedHashSet
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

public class BoostInstruction(instructionSet: InstructionSet, message: BattleMessage, remainingLines: Iterator<BattleMessage>, isBoost: Boolean = true) :
   ActionEffectInstruction {
   public open var future: CompletableFuture<*>
   public open var holds: MutableSet<String>
   public open val id: ResourceLocation
   public final val instructionSet: InstructionSet
   public final val isBoost: Boolean
   public final val message: BattleMessage
   public final val remainingLines: Iterator<BattleMessage>

   init {
      this.instructionSet = instructionSet;
      this.message = message;
      this.remainingLines = remainingLines;
      this.isBoost = isBoost;
      val var10001: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
      this.future = var10001;
      this.holds = new LinkedHashSet<>();
      this.id = MiscUtilsKt.cobblemonResource("boost");
   }

   public override fun preActionEffect(battle: PokemonBattle) {
   }

   public override fun runActionEffect(battle: PokemonBattle, runtime: MoLangRuntime) {
      battle.dispatch(
         (
            new Function0<DispatchResult>(this, battle, runtime) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
                  this.$runtime = `$runtime`;
               }

               @NotNull
               public final DispatchResult invoke() {
                  val actionEffect: ActionEffectTimeline = if (this.this$0.isBoost())
                     BoostInstruction.Companion.getBOOST_EFFECT()
                     else
                     BoostInstruction.Companion.getUNBOOST_EFFECT();
                  val providers: java.util.List = CollectionsKt.mutableListOf(new Object[]{this.$battle});
                  val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 == null) {
                     return DispatchResultKt.getGO();
                  } else {
                     val var11: PokemonEntity = var10000.getEffectedPokemon().getEntity();
                     if (var11 != null) {
                        providers.add(new UsersProvider(var11));
                     }

                     val context: ActionEffectContext = new ActionEffectContext(actionEffect, null, providers, this.$runtime, false, false, null, 114, null);
                     this.this$0.setFuture(actionEffect.run(context));
                     this.this$0.setHolds(context.getHolds());
                     this.this$0.getFuture().thenApply(<unrepresentable>::invoke$lambda$1);
                     return DispatchResultKt.getGO();
                  }
               }

               private static final Unit invoke$lambda$1(BoostInstruction this$0, Object it) {
                  `this$0`.getHolds().clear();
                  return Unit.INSTANCE;
               }
            }
         ) as () -> DispatchResult
      );
   }

   public override fun postActionEffect(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val var8: java.lang.String = this.message.argumentAt(1);
         if (var8 != null) {
            val var9: java.lang.String = this.message.argumentAt(2);
            if (var9 != null) {
               val stages: Int = Integer.parseInt(var9);
               val stat: Component = Stats.Companion.getStat(var8).getDisplayName();
               val severity: java.lang.String = Stats.Companion.getSeverity(stages);
               val rootKey: java.lang.String = if (this.isBoost) "boost" else "unboost";
               battle.dispatch(
                  (
                     new Function0<DispatchResult>(this, rootKey, severity, var10000, stat, battle, stages) {
                        {
                           super(0);
                           this.this$0 = `$receiver`;
                           this.$rootKey = `$rootKey`;
                           this.$severity = `$severity`;
                           this.$pokemon = `$pokemon`;
                           this.$stat = `$stat`;
                           this.$battle = `$battle`;
                           this.$stages = `$stages`;
                        }

                        @NotNull
                        public final DispatchResult invoke() {
                           val var12: MutableComponent = if (this.this$0.getMessage().hasOptionalArgument("zeffect"))
                              LocalizationUtilsKt.battleLang("${this.$rootKey}.${this.$severity}.zeffect", this.$pokemon.getName(), this.$stat)
                              else
                              LocalizationUtilsKt.battleLang("${this.$rootKey}.${this.$severity}", this.$pokemon.getName(), this.$stat);
                           val var14: PokemonBattle = this.$battle;
                           var14.broadcastChatMessage(var12 as Component);
                           val var11: BattleContext.Type = if (this.this$0.isBoost()) BattleContext.Type.BOOST else BattleContext.Type.UNBOOST;
                           val context: BattleContext = ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), var11, this.$battle);
                           val var4: Int = this.$stages;
                           val var5: BattlePokemon = this.$pokemon;

                           for (int var6 = 0; var6 < var4; var6++) {
                              var5.getContextManager().add(context);
                           }

                           this.$battle.getMinorBattleActions().put(this.$pokemon.getUuid(), this.this$0.getMessage());
                           return new UntilDispatch((new Function0<java.lang.Boolean>(this.this$0) {
                              {
                                 super(0);
                                 this.this$0 = `$receiver`;
                              }

                              @NotNull
                              public final java.lang.Boolean invoke() {
                                 return !this.this$0.getHolds().contains("effects");
                              }
                           }) as () -> java.lang.Boolean);
                        }
                     }
                  ) as () -> DispatchResult
               );
            }
         }
      }
   }

   override fun invoke(battle: PokemonBattle) {
      ActionEffectInstruction.DefaultImpls.invoke(this, battle);
   }

   override fun addMolangQueries(runtime: MoLangRuntime) {
      ActionEffectInstruction.DefaultImpls.addMolangQueries(this, runtime);
   }

   @JvmStatic
   fun {
      var var10000: Any = ActionEffects.INSTANCE.getActionEffects().get(MiscUtilsKt.cobblemonResource("boost"));
      BOOST_EFFECT = var10000 as ActionEffectTimeline;
      var10000 = ActionEffects.INSTANCE.getActionEffects().get(MiscUtilsKt.cobblemonResource("unboost"));
      UNBOOST_EFFECT = var10000 as ActionEffectTimeline;
   }

   public companion object {
      public final val BOOST_EFFECT: ActionEffectTimeline
      public final val UNBOOST_EFFECT: ActionEffectTimeline
   }
}
