package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public class VolatileCuringBerryItem(block: BerryBlock, volatileStatus: String) : BerryItem(block), PokemonSelectingItem {
   public open val bagItem: BagItem
   public final val volatileStatus: String

   init {
      this.volatileStatus = volatileStatus;
      this.bagItem = new BagItem(this) {
         {
            this.this$0 = `$receiver`;
         }

         @NotNull
         @Override
         public java.lang.String getItemName() {
            val var10000: Berry = this.this$0.berry();
            return "item.cobblemon.${var10000.getIdentifier().m_135815_()}";
         }

         @Override
         public boolean canUse(@NotNull PokemonBattle battle, @NotNull BattlePokemon target) {
            return true;
         }

         @NotNull
         @Override
         public java.lang.String getShowdownInput(@NotNull BattleActor actor, @NotNull BattlePokemon battlePokemon, @Nullable java.lang.String data) {
            return "cure_volatile ${this.this$0.getVolatileStatus()}";
         }

         @Override
         public boolean canStillUse(
            @NotNull ServerPlayer player, @NotNull PokemonBattle battle, @NotNull BattleActor actor, @NotNull BattlePokemon target, @NotNull ItemStack stack
         ) {
            return BagItem.DefaultImpls.canStillUse(this, player, battle, actor, target, stack);
         }
      };
   }

   public override fun canUseOnPokemon(pokemon: Pokemon): Boolean {
      return false;
   }

   public open fun applyToPokemon(player: ServerPlayer, stack: ItemStack, pokemon: Pokemon): Nothing? {
      return null;
   }

   public override fun interactGeneral(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
      return InteractionResultHolder.m_19098_(stack);
   }

   public override fun applyToBattlePokemon(player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon) {
      PokemonSelectingItem.DefaultImpls.applyToBattlePokemon(this, player, stack, battlePokemon);
      player.m_6330_(CobblemonSounds.BERRY_EAT, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   override fun use(player: ServerPlayer, stack: ItemStack): InteractionResultHolder<ItemStack> {
      return PokemonSelectingItem.DefaultImpls.use(this, player, stack);
   }

   override fun canUseOnBattlePokemon(battlePokemon: BattlePokemon): Boolean {
      return PokemonSelectingItem.DefaultImpls.canUseOnBattlePokemon(this, battlePokemon);
   }

   override fun interactWithSpecificBattle(player: ServerPlayer, stack: ItemStack, battlePokemon: BattlePokemon): InteractionResultHolder<ItemStack> {
      return PokemonSelectingItem.DefaultImpls.interactWithSpecificBattle(this, player, stack, battlePokemon);
   }

   override fun interactGeneralBattle(player: ServerPlayer, stack: ItemStack, actor: BattleActor): InteractionResultHolder<ItemStack> {
      return PokemonSelectingItem.DefaultImpls.interactGeneralBattle(this, player, stack, actor);
   }
}
