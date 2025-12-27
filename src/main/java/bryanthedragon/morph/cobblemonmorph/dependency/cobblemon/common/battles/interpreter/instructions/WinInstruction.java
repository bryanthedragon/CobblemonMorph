package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.Arrays
import java.util.NoSuchElementException
import java.util.UUID
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nWinInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WinInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/WinInstruction\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n1549#2:75\n1620#2,3:76\n1549#2:79\n1620#2,3:80\n766#2:83\n857#2,2:84\n1549#2:86\n1620#2,3:87\n2661#2,7:90\n1549#2:97\n1620#2,3:98\n2661#2,7:101\n1747#2,3:108\n*S KotlinDebug\n*F\n+ 1 WinInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/WinInstruction\n*L\n39#1:75\n39#1:76,3\n40#1:79\n40#1:80,3\n41#1:83\n41#1:84,2\n42#1:86\n42#1:87,3\n42#1:90,7\n43#1:97\n43#1:98,3\n43#1:101,7\n44#1:108,3\n*E\n"])
public class WinInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: java.lang.String = this.message.argumentAt(0);
      if (var10000 != null) {
         val var18: java.lang.Iterable = StringsKt.split$default(var10000, new java.lang.String[]{"&"}, false, 0, 6, null);
         val losersText: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var18, 10));

         for (Object item$iv$iv : $this$map$iv) {
            losersText.add(StringsKt.trim(`$i$f$any` as java.lang.String).toString());
         }

         val `$this$map$ivx`: java.lang.Iterable = losersText as java.util.List;
         val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(losersText as java.util.List, 10));

         for (Object item$iv$iv : $this$map$ivx) {
            val var10001: UUID = UUID.fromString(var46 as java.lang.String);
            val var72: BattleActor = battle.getActor(var10001);
            `destination$iv$ivx`.add(var72);
         }

         val var19: java.util.List = `destination$iv$ivx` as java.util.List;
         val var22: java.lang.Iterable = battle.getActors();
         val `destination$iv$ivxx`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if (!var19.contains(var54 as BattleActor)) {
               `destination$iv$ivxx`.add(var54);
            }
         }

         val var21: java.util.List = `destination$iv$ivxx` as java.util.List;
         val `$this$map$ivxx`: java.lang.Iterable = var19;
         val `destination$iv$ivxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));

         for (Object item$iv$iv : $this$map$ivxx) {
            `destination$iv$ivxxx`.add((var61 as BattleActor).getName());
         }

         val var36: java.util.Iterator = (`destination$iv$ivxxx` as java.util.List).iterator();
         if (!var36.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
         } else {
            var var43: Any = var36.next();

            while (iterator$iv.hasNext()) {
               var43 = TextKt.plus(TextKt.plus(var43 as MutableComponent, " & "), (var36.next() as MutableComponent) as Component);
            }

            val var23: MutableComponent = var43 as MutableComponent;
            val `$this$map$ivxxx`: java.lang.Iterable = var21;
            val `destination$iv$ivxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var21, 10));

            for (Object item$iv$iv : $this$map$ivxxx) {
               `destination$iv$ivxxxx`.add((var67 as BattleActor).getName());
            }

            var43 = (`destination$iv$ivxxxx` as java.util.List).iterator();
            if (!var43.hasNext()) {
               throw new UnsupportedOperationException("Empty collection can't be reduced.");
            } else {
               var `accumulator$ivx`: Any = var43.next();

               while (accumulator$iv.hasNext()) {
                  `accumulator$ivx` = TextKt.plus(TextKt.plus(`accumulator$ivx` as MutableComponent, " & "), (var43.next() as MutableComponent) as Component);
               }

               val var27: MutableComponent = `accumulator$ivx` as MutableComponent;
               val var39: java.lang.Iterable = battle.getShowdownMessages();
               var var73: Boolean;
               if (var39 is java.util.Collection && (var39 as java.util.Collection).isEmpty()) {
                  var73 = false;
               } else {
                  `accumulator$ivx` = var39.iterator();

                  while (true) {
                     if (!`accumulator$ivx`.hasNext()) {
                        var73 = false;
                        break;
                     }

                     if (StringsKt.contains$default(`accumulator$ivx`.next() as java.lang.String, "capture", false, 2, null)) {
                        var73 = true;
                        break;
                     }
                  }
               }

               val var33: Boolean = var73;
               battle.dispatch((new Function0<DispatchResult>(battle, var33, var21, var27, var23) {
                  {
                     super(0);
                     this.$battle = `$battle`;
                     this.$wasCaught = `$wasCaught`;
                     this.$losers = `$losers`;
                     this.$losersText = `$losersText`;
                     this.$winnersText = `$winnersText`;
                  }

                  @NotNull
                  public final DispatchResult invoke() {
                     if (this.$battle.isPvW()) {
                        val `$i$f$any`: java.util.Iterator = this.$battle.getActors().iterator();

                        var `element$iv`: Any;
                        do {
                           if (!`$i$f$any`.hasNext()) {
                              throw new NoSuchElementException("Collection contains no element matching the predicate.");
                           }

                           `element$iv` = `$i$f$any`.next();
                        } while (((BattleActor)element$iv).getType() != ActorType.WILD);

                        val var14: BattlePokemon = CollectionsKt.first((`element$iv` as BattleActor).getPokemonList()) as BattlePokemon;
                        if (!this.$wasCaught) {
                           val var17: java.lang.Iterable = this.$losers;
                           var var10000: Boolean;
                           if (this.$losers is java.util.Collection && this.$losers.isEmpty()) {
                              var10000 = false;
                           } else {
                              `element$iv` = var17.iterator();

                              while (true) {
                                 if (!`element$iv`.hasNext()) {
                                    var10000 = false;
                                    break;
                                 }

                                 if ((`element$iv`.next() as BattleActor).getUuid() == var14.getUuid()) {
                                    var10000 = true;
                                    break;
                                 }
                              }
                           }

                           if (var10000) {
                              val var33: PokemonEntity = var14.getEffectedPokemon().getEntity();
                              if (var33 != null) {
                                 label90: {
                                    val var27: java.lang.Iterable;
                                    for (Object element$ivx : var27) {
                                       if ((`element$ivx` as BattleActor).getType() === ActorType.PLAYER) {
                                          var34 = `element$ivx`;
                                          break label90;
                                       }
                                    }

                                    var34 = null;
                                 }

                                 var33.setKiller(if ((var34 as? PlayerBattleActor) != null) (var34 as? PlayerBattleActor).getEntity() else null);
                              }
                           }
                        }
                     }

                     if (this.$wasCaught) {
                        return DispatchResultKt.getGO();
                     } else {
                        var var36: Boolean;
                        label80: {
                           if (this.$battle.isPvW()) {
                              val `$this$any$ivx`: java.lang.Iterable = this.$losers;
                              if (this.$losers is java.util.Collection && this.$losers.isEmpty()) {
                                 var36 = false;
                              } else {
                                 val var20: java.util.Iterator = `$this$any$ivx`.iterator();

                                 while (true) {
                                    if (!var20.hasNext()) {
                                       var36 = false;
                                       break;
                                    }

                                    if (var20.next() as BattleActor is PlayerBattleActor) {
                                       var36 = true;
                                       break;
                                    }
                                 }
                              }

                              if (var36) {
                                 var36 = true;
                                 break label80;
                              }
                           }

                           var36 = false;
                        }

                        val var38: MutableComponent;
                        if (var36) {
                           val var37: MutableComponent = LocalizationUtilsKt.battleLang("lose", this.$losersText);
                           var38 = TextKt.red(var37);
                        } else {
                           val var39: MutableComponent = LocalizationUtilsKt.battleLang("win", this.$winnersText);
                           var38 = TextKt.gold(var39);
                        }

                        this.$battle.broadcastChatMessage(var38 as Component);
                        return new WaitDispatch(2.0F);
                     }
                  }
               }) as () -> DispatchResult);
               battle.dispatchGo(
                  (
                     new Function0<Unit>(battle, var19, var21, var33) {
                        {
                           super(0);
                           this.$battle = `$battle`;
                           this.$winners = `$winners`;
                           this.$losers = `$losers`;
                           this.$wasCaught = `$wasCaught`;
                        }

                        public final void invoke() {
                           this.$battle.end();
                           val `$this$iv`: EventObservable = CobblemonEvents.BATTLE_VICTORY;
                           val `events$iv`: Array<BattleVictoryEvent> = new BattleVictoryEvent[]{
                              new BattleVictoryEvent(this.$battle, this.$winners, this.$losers, this.$wasCaught)
                           };
                           `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

                           for (Object element$iv$iv : events$iv) {
                              ;
                           }

                           ShowdownInterpreter.INSTANCE.getLastCauser().remove(this.$battle.getBattleId());
                        }
                     }
                  ) as () -> Unit
               );
            }
         }
      }
   }
}
