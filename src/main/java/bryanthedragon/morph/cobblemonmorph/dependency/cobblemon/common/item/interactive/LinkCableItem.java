package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.EntityInteraction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction.Ownership
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Item.Properties

@SourceDebugExtension(["SMAP\nLinkCableItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LinkCableItem.kt\ncom/cobblemon/mod/common/item/interactive/LinkCableItem\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n800#2,11:34\n1855#2:45\n1726#2,3:46\n1856#2:49\n*S KotlinDebug\n*F\n+ 1 LinkCableItem.kt\ncom/cobblemon/mod/common/item/interactive/LinkCableItem\n*L\n22#1:34,11\n22#1:45\n25#1:46,3\n22#1:49\n*E\n"])
public class LinkCableItem : CobblemonItem(new Properties()), PokemonEntityInteraction {
   public open val accepted: Set<Ownership> = SetsKt.setOf(PokemonEntityInteraction.Ownership.OWNER)

   public override fun processInteraction(player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
      val pokemon: Pokemon = entity.getPokemon();
      val `$this$forEach$iv`: java.lang.Iterable = pokemon.getLockedEvolutions();
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filterIsInstance$iv) {
         if (`$this$all$iv` is TradeEvolution) {
            `element$iv`.add(`$this$all$iv`);
         }
      }

      for (Object element$ivx : $this$filterIsInstance$iv) {
         val var20: TradeEvolution = `element$ivx` as TradeEvolution;
         val var22: java.lang.Iterable = (`element$ivx` as TradeEvolution).getRequirements();
         var var10000: Boolean;
         if (var22 is java.util.Collection && (var22 as java.util.Collection).isEmpty()) {
            var10000 = true;
         } else {
            val var13: java.util.Iterator = var22.iterator();

            while (true) {
               if (!var13.hasNext()) {
                  var10000 = true;
                  break;
               }

               if (!(var13.next() as EvolutionRequirement).check(pokemon)) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (var10000 && var20.evolve(pokemon)) {
            EntityInteraction.DefaultImpls.consumeItem$default(this, player, stack, 0, 4, null);
            return true;
         }
      }

      return false;
   }

   override fun getSound(): SoundEvent? {
      return PokemonEntityInteraction.DefaultImpls.getSound(this);
   }

   override fun onInteraction(player: ServerPlayer, entity: PokemonEntity, stack: ItemStack): Boolean {
      return PokemonEntityInteraction.DefaultImpls.onInteraction(this, player, entity, stack);
   }

   override fun consumeItem(player: ServerPlayer, stack: ItemStack, amount: Int) {
      PokemonEntityInteraction.DefaultImpls.consumeItem(this, player, stack, amount);
   }
}
