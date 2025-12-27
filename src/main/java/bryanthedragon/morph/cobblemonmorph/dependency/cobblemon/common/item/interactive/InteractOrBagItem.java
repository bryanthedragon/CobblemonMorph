package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import kotlin.jvm.functions.Function3
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull

public interface InteractOrBagItem {
   public abstract fun canUseOverworld(pokemon: Pokemon): Boolean {
   }

   public abstract fun canUseBattle(battlePokemon: BattlePokemon): Boolean {
   }

   public abstract fun getBagItem(stack: ItemStack): BagItem? {
   }

   public open fun onRegularUse(world: ServerLevel, user: ServerPlayer, hand: InteractionHand): InteractionResultHolder<ItemStack> {
   }

   public open fun onBattleUse(player: ServerPlayer, battlePokemon: BattlePokemon, stack: ItemStack): Boolean {
   }

   public open fun checkBattleItem(
      player: ServerPlayer,
      battle: PokemonBattle,
      actor: BattleActor,
      battlePokemon: BattlePokemon,
      stack: ItemStack,
      hand: InteractionHand
   ): Boolean {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nInteractOrBagItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractOrBagItem.kt\ncom/cobblemon/mod/common/item/interactive/InteractOrBagItem$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,90:1\n1549#2:91\n1620#2,2:92\n1622#2:95\n1#3:94\n*S KotlinDebug\n*F\n+ 1 InteractOrBagItem.kt\ncom/cobblemon/mod/common/item/interactive/InteractOrBagItem$DefaultImpls\n*L\n58#1:91\n58#1:92,2\n58#1:95\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun onRegularUse(`$this`: InteractOrBagItem, world: ServerLevel, user: ServerPlayer, hand: InteractionHand): InteractionResultHolder<ItemStack> {
         val var10000: InteractionResultHolder = InteractionResultHolder.m_19090_(user.m_21120_(hand));
         return var10000;
      }

      @JvmStatic
      fun onBattleUse(`$this`: InteractOrBagItem, player: ServerPlayer, battlePokemon: BattlePokemon, stack: ItemStack): Boolean {
         val battle: PokemonBattle = battlePokemon.getActor().getBattle();
         val var10000: BagItem = `$this`.getBagItem(stack);
         if (var10000 == null) {
            return false;
         } else if (!battlePokemon.getActor().canFitForcedAction()) {
            val var25: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
            player.m_213846_(TextKt.red(var25) as Component);
            return false;
         } else if (!var10000.canUse(battle, battlePokemon)) {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var10001) as Component);
            return false;
         } else {
            val turn: Int = battle.getTurn();
            val var24: MoveSelectCallbacks = MoveSelectCallbacks.INSTANCE;
            val `$this$map$iv`: java.lang.Iterable = battlePokemon.getMoveSet();
            val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

            for (Object item$iv$iv : $this$map$iv) {
               val enabled: Boolean = (`item$iv$iv` as Move).getCurrentPp() < (`item$iv$iv` as Move).getMaxPp();
               val var17: MoveSelectDTO = new MoveSelectDTO(`item$iv$iv` as Move, false, 2, null);
               var17.setEnabled(enabled);
               `destination$iv$iv`.add(var17);
            }

            MoveSelectCallbacks.create$default(
               var24,
               player,
               null,
               `destination$iv$iv` as java.util.List,
               null,
               (
                  new Function3<ServerPlayer, Integer, MoveSelectDTO, Unit>(player, stack, battlePokemon, battle, turn, var10000) {
                     {
                        super(3);
                        this.$player = `$player`;
                        this.$stack = `$stack`;
                        this.$battlePokemon = `$battlePokemon`;
                        this.$battle = `$battle`;
                        this.$turn = `$turn`;
                        this.$bagItem = `$bagItem`;
                     }

                     public final void invoke(@NotNull ServerPlayer var1, int var2, @NotNull MoveSelectDTO move) {
                        val var10000: java.lang.Iterable = this.$player.m_6167_();
                        if (CollectionsKt.contains(var10000, this.$stack)
                           && !this.$stack.m_41619_()
                           && this.$battlePokemon.getActor().canFitForcedAction()
                           && this.$battle.getTurn() == this.$turn) {
                           this.$battlePokemon
                              .getActor()
                              .forceChoose(new BagItemActionResponse(this.$bagItem, this.$battlePokemon, move.getMoveTemplate().getName()));
                           if (!this.$player.m_7500_()) {
                              this.$stack.m_41774_(1);
                           }
                        }
                     }
                  }
               ) as Function3,
               10,
               null
            );
            return true;
         }
      }

      @JvmStatic
      fun checkBattleItem(
         `$this`: InteractOrBagItem,
         player: ServerPlayer,
         battle: PokemonBattle,
         actor: BattleActor,
         battlePokemon: BattlePokemon,
         stack: ItemStack,
         hand: InteractionHand
      ): Boolean {
         val var10000: BagItem = `$this`.getBagItem(stack);
         if (var10000 == null) {
            return false;
         } else if (!actor.canFitForcedAction()) {
            val var8: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
            player.m_213846_(TextKt.red(var8) as Component);
            return false;
         } else if (!var10000.canUse(battle, battlePokemon)) {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var10001) as Component);
            return false;
         } else {
            return player.m_21120_(hand) === stack;
         }
      }
   }
}
