package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
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

public class CantInstruction(message: BattleMessage) : ActionEffectInstruction {
   public open var future: CompletableFuture<*>
   public open var holds: MutableSet<String>
   public open val id: ResourceLocation
   public final val message: BattleMessage

   init {
      this.message = message;
      val var10001: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
      this.future = var10001;
      this.holds = new LinkedHashSet<>();
      this.id = MiscUtilsKt.cobblemonResource("cant");
   }

   public override fun preActionEffect(battle: PokemonBattle) {
   }

   public override fun runActionEffect(battle: PokemonBattle, runtime: MoLangRuntime) {
      battle.dispatch((new Function0<DispatchResult>(this, battle, runtime) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
            this.$runtime = `$runtime`;
         }

         @NotNull
         public final DispatchResult invoke() {
            val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 == null) {
               return DispatchResultKt.getGO();
            } else {
               val var14: Effect = this.this$0.getMessage().effectAt(1);
               if (var14 != null) {
                  val var15: java.lang.String = var14.getId();
                  if (var15 != null) {
                     val name: MutableComponent = var10000.getName();
                     val status: Status = Statuses.INSTANCE.getStatus(var15);
                     if (status != null) {
                        val var16: ActionEffectTimeline = status.getActionEffect();
                        if (var16 != null) {
                           val providers: java.util.List = CollectionsKt.mutableListOf(new Object[]{this.$battle});
                           val var17: PokemonEntity = var10000.getEffectedPokemon().getEntity();
                           if (var17 != null) {
                              providers.add(new UsersProvider(var17));
                           }

                           val var11: ActionEffectContext = new ActionEffectContext(var16, null, providers, this.$runtime, false, false, null, 114, null);
                           this.this$0.setFuture(var16.run(var11));
                           this.this$0.setHolds(var11.getHolds());
                           this.this$0.getFuture().thenApply(<unrepresentable>::invoke$lambda$1);
                           return DispatchResultKt.getGO();
                        }
                     }

                     return DispatchResultKt.getGO();
                  }
               }

               return DispatchResultKt.getGO();
            }
         }

         private static final Unit invoke$lambda$1(CantInstruction this$0, Object it) {
            `this$0`.getHolds().clear();
            return Unit.INSTANCE;
         }
      }) as () -> DispatchResult);
   }

   public override fun postActionEffect(battle: PokemonBattle) {
      battle.dispatch((new Function0<DispatchResult>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         @NotNull
         public final DispatchResult invoke() {
            val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 == null) {
               return DispatchResultKt.getGO();
            } else {
               val var13: Effect = this.this$0.getMessage().effectAt(1);
               if (var13 != null) {
                  val var14: java.lang.String = var13.getId();
                  if (var14 != null) {
                     var name: MutableComponent;
                     label64: {
                        name = var10000.getName();
                        val var15: MoveTemplate = this.this$0.getMessage().moveAt(2);
                        if (var15 != null) {
                           var16 = var15.getDisplayName();
                           if (var16 != null) {
                              break label64;
                           }
                        }

                        var16 = TextKt.text("(Unrecognized: ${this.this$0.getMessage().argumentAt(2)})");
                     }

                     label59: {
                        label58: {
                           label72: {
                              switch (var14.hashCode()) {
                                 case -1172185585:
                                    if (var14.equals("armortail")) {
                                       break label58;
                                    }
                                    break label72;
                                 case 101678:
                                    if (!var14.equals("frz")) {
                                       break label72;
                                    }
                                    break;
                                 case 110753:
                                    if (!var14.equals("par")) {
                                       break label72;
                                    }
                                    break;
                                 case 113975:
                                    if (!var14.equals("slp")) {
                                       break label72;
                                    }
                                    break;
                                 case 3075808:
                                    if (var14.equals("damp")) {
                                       break label58;
                                    }
                                    break label72;
                                 case 1267143666:
                                    if (var14.equals("queenlymajesty")) {
                                       break label58;
                                    }
                                    break label72;
                                 case 1984633331:
                                    if (var14.equals("dazzling")) {
                                       break label58;
                                    }
                                 default:
                                    break label72;
                              }

                              label43: {
                                 val var17: Status = Statuses.INSTANCE.getStatus(var14);
                                 if (var17 != null) {
                                    val var18: ResourceLocation = var17.getName();
                                    if (var18 != null) {
                                       var19 = var18.m_135815_();
                                       break label43;
                                    }
                                 }

                                 var19 = null;
                              }

                              if (var19 == null) {
                                 return DispatchResultKt.getGO();
                              }

                              var21 = LocalizationUtilsKt.lang("status.$var19.is", name);
                              break label59;
                           }

                           var21 = LocalizationUtilsKt.battleLang("cant.$var14", name, var16);
                           break label59;
                        }

                        var21 = LocalizationUtilsKt.battleLang("cant.generic", name, var16);
                     }

                     val var23: PokemonBattle = this.$battle;
                     var23.broadcastChatMessage(TextKt.red(var21) as Component);
                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
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

               return DispatchResultKt.getGO();
            }
         }
      }) as () -> DispatchResult);
   }

   override fun invoke(battle: PokemonBattle) {
      ActionEffectInstruction.DefaultImpls.invoke(this, battle);
   }

   override fun addMolangQueries(runtime: MoLangRuntime) {
      ActionEffectInstruction.DefaultImpls.addMolangQueries(this, runtime);
   }
}
