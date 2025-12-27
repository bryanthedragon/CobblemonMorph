package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item.Properties
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public class XStatItem(stat: Stat, stages: Int = 2) : CobblemonItem(new Properties()), SimpleBagItemConvertible {
   public open val bagItem: BagItem
   public final val stat: Stat

   init {
      this.stat = stat;
      this.bagItem = new BagItem(this, stages) {
         @NotNull
         private final java.lang.String itemName;

         {
            this.this$0 = `$receiver`;
            this.$stages = `$stages`;
            this.itemName = "item.cobblemon.x_${`$receiver`.getStat().getIdentifier().m_135815_()}";
         }

         @NotNull
         @Override
         public java.lang.String getItemName() {
            return this.itemName;
         }

         @Override
         public boolean canUse(@NotNull PokemonBattle battle, @NotNull BattlePokemon target) {
            return target.getHealth() > 0;
         }

         @NotNull
         @Override
         public java.lang.String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable java.lang.String data) {
            Pokemon.incrementFriendship$default(battlePokemon.getEffectedPokemon(), 1, false, 2, null);
            return "x_stat ${this.this$0.getStat().getShowdownId()} ${this.$stages}";
         }

         @Override
         public boolean canStillUse(
            @NotNull ServerPlayer player, @NotNull PokemonBattle battle, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack
         ) {
            return BagItem.DefaultImpls.canStillUse(this, player, battle, actor, target, stack);
         }
      };
   }

   override fun getBagItem(stack: ItemStack): BagItem? {
      return SimpleBagItemConvertible.DefaultImpls.getBagItem(this, stack);
   }

   override fun handleInteraction(player: ServerPlayer, battlePokemon: BattlePokemon, stack: ItemStack): Boolean {
      return SimpleBagItemConvertible.DefaultImpls.handleInteraction(this, player, battlePokemon, stack);
   }
}
