package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.function.Function
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

@SourceDebugExtension(["SMAP\nCobblemonHeldItemManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonHeldItemManager.kt\ncom/cobblemon/mod/common/pokemon/helditem/CobblemonHeldItemManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,178:1\n1#2:179\n*E\n"])
public object CobblemonHeldItemManager : BaseCobblemonHeldItemManager {
   private final val giveItemEffect: Set<String> =
      SetsKt.setOf(new java.lang.String[]{"pickup", "recycle", "magician", "pickpocket", "thief", "covet", "harvest", "bestow", "switcheroo", "trick"})
      private final val remaps: MutableMap<Item, String> = (new LinkedHashMap()) as java.util.Map
   private final val stackRemaps: MutableList<Function<ItemStack, String?>> = (new ArrayList()) as java.util.List
   private final val takeItemEffect: Set<String> = SetsKt.setOf(new java.lang.String[]{"magician", "pickpocket", "covet", "bestow"})

   internal override fun load() {
      super.load$common();
      Cobblemon.INSTANCE.getLOGGER().info("Imported {} held item IDs from showdown", this.loadedItemCount());
   }

   public override fun showdownId(pokemon: BattlePokemon): String? {
      val itemStack: ItemStack = pokemon.getEffectedPokemon().heldItemNoCopy$common();
      if (remaps.containsKey(itemStack.m_41720_())) {
         return remaps.get(itemStack.m_41720_());
      } else {
         for (Function remap : stackRemaps) {
            val id: java.lang.String = remap.apply(itemStack) as java.lang.String;
            if (id != null) {
               return id;
            }
         }

         val var6: java.lang.String = super.showdownId(pokemon);
         return if (var6 == null && pokemon.getEffectedPokemon().heldItemNoCopy$common().m_41619_()) "" else var6;
      }
   }

   public override fun handleStartInstruction(pokemon: BattlePokemon, battle: PokemonBattle, battleMessage: BattleMessage) {
      val var10000: Effect = battleMessage.effectAt(1);
      if (var10000 != null) {
         val var20: java.lang.String = var10000.getId();
         if (var20 != null) {
            val consumeHeldItems: Boolean = this.shouldConsumeItem(pokemon, battle, var20);
            if (battleMessage.hasOptionalArgument("silent")) {
               if (consumeHeldItems) {
                  this.take(pokemon, var20);
               }

               return;
            }

            val effect: Effect = BattleMessage.effect$default(battleMessage, null, 1, null);
            val battlerName: MutableComponent = pokemon.getName();
            if (effect == null) {
               val var29: MutableComponent = LocalizationUtilsKt.battleLang("item.$var20", battlerName);
               battle.broadcastChatMessage(var29 as Component);
               return;
            }

            label106: {
               val var21: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(battleMessage, battle, null, 2, null);
               if (var21 != null) {
                  val var22: MutableComponent = var21.getName();
                  if (var22 != null) {
                     var23 = var22 as Component;
                     break label106;
                  }
               }

               var23 = Component.m_130674_("UNKNOWN");
            }

            var effectId: java.lang.String;
            label101: {
               var itemName: Component;
               label100: {
                  label99: {
                     label98: {
                        itemName = this.nameOf(var20);
                        effectId = effect.getId();
                        switch (effectId.hashCode()) {
                           case -1108625161:
                              if (effectId.equals("pickpocket")) {
                                 break label100;
                              }
                              break;
                           case -988476804:
                              if (effectId.equals("pickup")) {
                                 break label99;
                              }
                              break;
                           case -346775423:
                              if (effectId.equals("switcheroo")) {
                                 break label98;
                              }
                              break;
                           case -69865079:
                              if (effectId.equals("magician")) {
                                 break label100;
                              }
                              break;
                           case 94852025:
                              if (effectId.equals("covet")) {
                                 break label100;
                              }
                              break;
                           case 110330838:
                              if (effectId.equals("thief")) {
                                 break label100;
                              }
                              break;
                           case 110628691:
                              if (effectId.equals("trick")) {
                                 break label98;
                              }
                              break;
                           case 1082880659:
                              if (effectId.equals("recycle")) {
                                 break label99;
                              }
                           default:
                        }

                        val var25: java.lang.String = "item.$effectId";
                        val var18: Array<Any> = new Object[]{battlerName, itemName, null};
                        var18[2] = var23;
                        var24 = LocalizationUtilsKt.battleLang(var25, var18);
                        break label101;
                     }

                     var24 = LocalizationUtilsKt.battleLang("item.trick", battlerName, itemName);
                     break label101;
                  }

                  var24 = LocalizationUtilsKt.battleLang("item.recycle", battlerName, itemName);
                  break label101;
               }

               val var19: Array<Any> = new Object[]{battlerName, itemName, null};
               var19[2] = var23;
               var24 = LocalizationUtilsKt.battleLang("item.thief", var19);
            }

            battle.broadcastChatMessage(var24 as Component);
            if (takeItemEffect.contains(effectId) && giveItemEffect.contains(effectId) && !consumeHeldItems) {
               return;
            }

            if (battle.isPvP() && !consumeHeldItems) {
               return;
            }

            if (giveItemEffect.contains(effectId) && (pokemon.getActor() is PlayerBattleActor || consumeHeldItems)) {
               this.give(pokemon, var20);
            }

            if (takeItemEffect.contains(effectId) && (pokemon.getActor() !is PlayerBattleActor || consumeHeldItems)) {
               val var26: Pair = BattleMessage.actorAndActivePokemonFromOptional$default(battleMessage, battle, null, 2, null);
               if (var26 != null) {
                  val var27: ActiveBattlePokemon = var26.getSecond() as ActiveBattlePokemon;
                  if (var27 != null) {
                     val var28: BattlePokemon = var27.getBattlePokemon();
                     if (var28 != null) {
                        this.take(var28, var20);
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public override fun handleEndInstruction(pokemon: BattlePokemon, battle: PokemonBattle, battleMessage: BattleMessage) {
      val var10000: Effect = battleMessage.effectAt(1);
      if (var10000 != null) {
         val var15: java.lang.String = var10000.getId();
         if (var15 != null) {
            val consumeHeldItems: Boolean = this.shouldConsumeItem(pokemon, battle, var15);
            if (battleMessage.hasOptionalArgument("silent")) {
               if (consumeHeldItems) {
                  this.take(pokemon, var15);
               }

               return;
            }

            val battlerName: MutableComponent = pokemon.getName();
            val itemName: Component = this.nameOf(var15);
            if (battleMessage.hasOptionalArgument("eat")) {
               val var10001: MutableComponent = LocalizationUtilsKt.battleLang("item.eat", battlerName, itemName);
               battle.broadcastChatMessage(var10001 as Component);
               if (consumeHeldItems) {
                  this.take(pokemon, var15);
               }

               return;
            }

            label67: {
               val var16: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(battleMessage, battle, null, 2, null);
               if (var16 != null) {
                  val var17: MutableComponent = var16.getName();
                  if (var17 != null) {
                     var18 = var17 as Component;
                     break label67;
                  }
               }

               var18 = Component.m_130674_("UNKNOWN");
            }

            val effect: Effect = BattleMessage.effect$default(battleMessage, null, 1, null);
            var var20: MutableComponent;
            if ((if (effect != null) effect.getId() else null) != null) {
               val var19: java.lang.String = "enditem.${effect.getId()}";
               val var11: Array<Any> = new Object[]{battlerName, itemName, null};
               var11[2] = var18;
               var20 = LocalizationUtilsKt.battleLang(var19, var11);
            } else {
               label60: {
                  label59: {
                     switch (var15.hashCode()) {
                        case -362369778:
                           if (var15.equals("electricseed")) {
                              break label59;
                           }
                           break;
                        case 880781690:
                           if (var15.equals("roomservice")) {
                              break label59;
                           }
                           break;
                        case 987810420:
                           if (var15.equals("grassyseed")) {
                              break label59;
                           }
                           break;
                        case 1287412621:
                           if (var15.equals("mistyseed")) {
                              break label59;
                           }
                           break;
                        case 1473685510:
                           if (var15.equals("psychicseed")) {
                              break label59;
                           }
                           break;
                        case 1548556824:
                           if (var15.equals("boosterenergy")) {
                              break label59;
                           }
                        default:
                     }

                     var20 = LocalizationUtilsKt.battleLang("enditem.$var15", battlerName);
                     break label60;
                  }

                  var20 = LocalizationUtilsKt.battleLang("enditem.generic", battlerName, itemName);
               }
            }

            if (consumeHeldItems) {
               this.take(pokemon, var15);
            }

            battle.broadcastChatMessage(var20 as Component);
            return;
         }
      }
   }

   public override fun shouldConsumeItem(pokemon: BattlePokemon, battle: PokemonBattle, showdownId: String): Boolean {
      val tag: TagKey = if (battle.isPvP())
         CobblemonItemTags.CONSUMED_IN_PVP_BATTLE
         else
         (if (battle.isPvN()) CobblemonItemTags.CONSUMED_IN_NPC_BATTLE else CobblemonItemTags.CONSUMED_IN_WILD_BATTLE);
      return pokemon.getEffectedPokemon().heldItem().m_204117_(tag);
   }

   public fun registerRemap(item: Item, showdownId: String) {
      remaps.put(item, showdownId);
   }

   public fun registerStackRemap(remap: Function<ItemStack, String?>) {
      stackRemaps.add(remap);
   }
}
