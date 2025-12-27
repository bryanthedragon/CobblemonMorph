package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.Locale
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nHealInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/HealInstruction\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,98:1\n1549#2:99\n1620#2,3:100\n1549#2:103\n1620#2,3:104\n*S KotlinDebug\n*F\n+ 1 HealInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/HealInstruction\n*L\n39#1:99\n39#1:100,3\n40#1:103\n40#1:104,3\n*E\n"])
public class HealInstruction(actor: BattleActor, publicMessage: BattleMessage, privateMessage: BattleMessage) : InterpreterInstruction {
   public final val actor: BattleActor
   public final val privateMessage: BattleMessage
   public final val publicMessage: BattleMessage

   init {
      this.actor = actor;
      this.publicMessage = publicMessage;
      this.privateMessage = privateMessage;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: Pair = this.privateMessage.pnxAndUuid(0);
      val pnx: java.lang.String = if (var10000 != null) var10000.getFirst() as java.lang.String else null;
      val var29: BattlePokemon = this.privateMessage.battlePokemon(0, battle);
      if (var29 != null) {
         val var30: java.lang.String = this.privateMessage.argumentAt(1);
         if (var30 != null) {
            val var32: java.util.List = StringsKt.split$default(var30, new java.lang.String[]{" "}, false, 0, 6, null);
            if (var32 != null) {
               val var33: java.lang.String = CollectionsKt.getOrNull(var32, 0) as java.lang.String;
               if (var33 == null) {
                  return;
               }

               val var19: java.lang.Iterable = StringsKt.split$default(var33, new java.lang.String[]{"/"}, false, 0, 6, null);
               val `$this$mapTo$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  val var35: java.lang.Float = StringsKt.toFloatOrNull(`item$iv$iv` as java.lang.String);
                  if (var35 == null) {
                     return;
                  }

                  `$this$mapTo$iv$iv`.add(var35);
               }

               val newHealth: java.util.List = `$this$mapTo$iv$iv` as java.util.List;
               val `$this$map$ivx`: java.lang.Iterable = StringsKt.split$default(var33, new java.lang.String[]{"/"}, false, 0, 6, null);
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$ivx`, 10));

               for (Object item$iv$iv : $this$map$ivx) {
                  val var37: java.lang.Float = StringsKt.toFloatOrNull(var27 as java.lang.String);
                  if (var37 == null) {
                     return;
                  }

                  `destination$iv$ivx`.add(var37 / (newHealth.get(1) as java.lang.Number).floatValue());
               }

               val var20: java.util.List = `destination$iv$ivx` as java.util.List;
               val var23: Effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
               ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, var23, var29);
               PokemonBattle.dispatchWaiting$default(
                  battle,
                  0.0F,
                  (
                     new Function0<Unit>(pnx, battle, this, newHealth, var20, var29, var23, var32) {
                        {
                           super(0);
                           this.$pnx = `$pnx`;
                           this.$battle = `$battle`;
                           this.this$0 = `$receiver`;
                           this.$newHealth = `$newHealth`;
                           this.$newHealthRatio = `$newHealthRatio`;
                           this.$battlePokemon = `$battlePokemon`;
                           this.$effect = `$effect`;
                           this.$rawHpAndStatus = `$rawHpAndStatus`;
                        }

                        public final void invoke() {
                           if (this.$pnx != null) {
                              PokemonBattle.sendSidedUpdate$default(
                                 this.$battle,
                                 this.this$0.getActor(),
                                 new BattleHealthChangePacket(this.$pnx, this.$newHealth.get(0).floatValue(), this.$newHealth.get(1)),
                                 new BattleHealthChangePacket(this.$pnx, this.$newHealthRatio.get(0).floatValue(), null, 4, null),
                                 false,
                                 8,
                                 null
                              );
                           }

                           val silent: Boolean = this.this$0.getPrivateMessage().hasOptionalArgument("silent");
                           if (!silent) {
                              var var10000: MutableComponent;
                              if (this.this$0.getPrivateMessage().hasOptionalArgument("zeffect")) {
                                 var10000 = LocalizationUtilsKt.battleLang("heal.zeffect", this.$battlePokemon.getName());
                              } else if (this.this$0.getPrivateMessage().hasOptionalArgument("wisher")) {
                                 val var34: java.lang.String = this.this$0.getPrivateMessage().optionalArgument("wisher");
                                 val var35: java.lang.String = var34.toLowerCase(Locale.ROOT);
                                 val showdownId: java.lang.String = ShowdownIdentifiable.Companion.getREGEX$common().replace(var35, "");
                                 val var8: java.util.Iterator = this.this$0.getActor().getPokemonList().iterator();

                                 while (true) {
                                    if (var8.hasNext()) {
                                       val `element$iv`: Any = var8.next();
                                       if (!((`element$iv` as BattlePokemon).getEffectedPokemon().showdownId() == showdownId)) {
                                          continue;
                                       }

                                       var10000 = (MutableComponent)`element$iv`;
                                       break;
                                    }

                                    var10000 = null;
                                    break;
                                 }

                                 var var25: Array<Any>;
                                 var var10003: MutableComponent;
                                 label72: {
                                    val var18: BattlePokemon = var10000 as BattlePokemon;
                                    var25 = new Object[1];
                                    if (var18 != null) {
                                       var10003 = var18.getName();
                                       if (var10003 != null) {
                                          break label72;
                                       }
                                    }

                                    var10003 = this.this$0.getActor().nameOwned(var34);
                                 }

                                 var25[0] = var10003;
                                 var10000 = LocalizationUtilsKt.battleLang("heal.wish", var25);
                              } else if (this.this$0.getPrivateMessage().hasOptionalArgument("from")) {
                                 val var31: Effect = this.$effect;
                                 if (WhenMappings.$EnumSwitchMapping$0[var31.getType().ordinal()] != 1) {
                                    if (this.$effect.getId() == "drain") {
                                       val var32: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(
                                          this.this$0.getPrivateMessage(), this.$battle, null, 2, null
                                       );
                                       if (var32 == null) {
                                          return;
                                       }

                                       var10000 = LocalizationUtilsKt.battleLang("heal.drain", var32.getName());
                                    } else {
                                       var10000 = LocalizationUtilsKt.battleLang("heal.${this.$effect.getId()}", this.$battlePokemon.getName());
                                    }
                                 } else {
                                    label107: {
                                       label91: {
                                          val wisher: java.lang.String = this.$effect.getId();
                                          switch (wisher.hashCode()) {
                                             case 1328235077:
                                                if (wisher.equals("blacksludge")) {
                                                   break label91;
                                                }
                                                break;
                                             case 1756801656:
                                                if (wisher.equals("leftovers")) {
                                                   break label91;
                                                }
                                                break;
                                             case 1803082547:
                                                if (wisher.equals("shellbell")) {
                                                   break label91;
                                                }
                                             default:
                                          }

                                          var10000 = LocalizationUtilsKt.battleLang("heal.item", this.$battlePokemon.getName(), this.$effect.getTypelessData());
                                          break label107;
                                       }

                                       var10000 = LocalizationUtilsKt.battleLang(
                                          "heal.leftovers", this.$battlePokemon.getName(), this.$effect.getTypelessData()
                                       );
                                    }
                                 }
                              } else {
                                 var10000 = LocalizationUtilsKt.battleLang("heal.generic", this.$battlePokemon.getName());
                              }

                              val var37: PokemonBattle = this.$battle;
                              var37.broadcastChatMessage(var10000 as Component);
                           }

                           this.$battle.getMinorBattleActions().put(this.$battlePokemon.getUuid(), this.this$0.getPrivateMessage());
                           this.$battlePokemon.getEffectedPokemon().setCurrentHealth((int)this.$newHealth.get(0).floatValue());
                           val var38: java.lang.String = CollectionsKt.getOrNull(this.$rawHpAndStatus, 1) as java.lang.String;
                           if (var38 != null) {
                              val var39: Status = Statuses.INSTANCE.getStatus(var38);
                              if (var39 != null) {
                                 if (var39 is PersistentStatus) {
                                    val var40: PersistentStatusContainer = this.$battlePokemon.getEffectedPokemon().getStatus();
                                    if (!((if (var40 != null) var40.getStatus() else null) == var39)) {
                                       this.$battlePokemon.getEffectedPokemon().applyStatus(var39 as PersistentStatus);
                                       if (this.$pnx != null) {
                                          this.$battle.sendUpdate(new BattlePersistentStatusPacket(this.$pnx, var39 as PersistentStatus));
                                       }

                                       if (!silent) {
                                          val var16: java.lang.String = var39.getApplyMessage();
                                          val var19: PokemonBattle = this.$battle;
                                          val var10001: MutableComponent = MiscUtilsKt.asTranslated(var16, this.$battlePokemon.getName());
                                          var19.broadcastChatMessage(var10001 as Component);
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
               return;
            }
         }
      }
   }
}
