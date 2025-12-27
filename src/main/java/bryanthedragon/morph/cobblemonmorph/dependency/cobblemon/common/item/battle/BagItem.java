package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public interface BagItem {
   public val itemName: String

   public abstract fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
   }

   public abstract fun getShowdownInput(actor: BattleActor, battlePokemon: BattlePokemon, data: String?): String {
   }

   public open fun canStillUse(player: ServerPlayer, battle: PokemonBattle, actor: BattleActor, target: BattlePokemon, stack: ItemStack): Boolean {
   }

   public companion object {
      public final val EMPTY: BagItem =
         (
            new BagItem() {
               @NotNull
               private final java.lang.String itemName;

               {
                  this.itemName = "name";
               }

               @NotNull
               @Override
               public java.lang.String getItemName() {
                  return this.itemName;
               }

               @Override
               public boolean canUse(@NotNull PokemonBattle battle, @NotNull BattlePokemon target) {
                  return true;
               }

               @NotNull
               @Override
               public java.lang.String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable java.lang.String data) {
                  return "none";
               }

               @Override
               public boolean canStillUse(
                  @NotNull ServerPlayer player,
                  @NotNull PokemonBattle battle,
                  @NotNull BattleActor actor,
                  @NotNull BattlePokemon target,
                  @NotNull ItemStack stack
               ) {
                  return BagItem.DefaultImpls.canStillUse(this, player, battle, actor, target, stack);
               }
            }
         ) as BagItem
      }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun canStillUse(`$this`: BagItem, player: ServerPlayer, battle: PokemonBattle, actor: BattleActor, target: BattlePokemon, stack: ItemStack): Boolean {
         val var10000: java.lang.Iterable = player.m_6167_();
         return CollectionsKt.contains(var10000, stack) && stack.m_41613_() > 0 && `$this`.canUse(battle, target) && actor.canFitForcedAction();
      }
   }
}
