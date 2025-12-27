package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PokemonInteractContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItemActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

public interface BagItemConvertible {
   public abstract fun getBagItem(stack: ItemStack): BagItem? {
   }

   public open fun handleInteraction(player: ServerPlayer, battlePokemon: BattlePokemon, stack: ItemStack): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun handleInteraction(`$this`: BagItemConvertible, player: ServerPlayer, battlePokemon: BattlePokemon, stack: ItemStack): Boolean {
         val battle: PokemonBattle = battlePokemon.getActor().getBattle();
         val var10000: BagItem = `$this`.getBagItem(stack);
         if (var10000 == null) {
            return false;
         } else if (!battlePokemon.getActor().canFitForcedAction()) {
            val var7: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.cannot");
            player.m_213846_(TextKt.red(var7) as Component);
            return false;
         } else if (!var10000.canUse(battle, battlePokemon)) {
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.invalid");
            player.m_213846_(TextKt.red(var10001) as Component);
            return false;
         } else {
            battlePokemon.getActor().forceChoose(new BagItemActionResponse(var10000, battlePokemon, null, 4, null));
            stack.m_41774_(1);
            val var6: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPOKEMON_INTERACT();
            val var10004: PokemonEntity = battlePokemon.getEntity();
            val var8: ResourceLocation = var10004.getPokemon().getSpecies().getResourceIdentifier();
            val var10005: ResourceLocation = BuiltInRegistries.f_257033_.m_7981_(stack.m_41720_());
            var6.trigger(player, new PokemonInteractContext(var8, var10005));
            return true;
         }
      }
   }
}
