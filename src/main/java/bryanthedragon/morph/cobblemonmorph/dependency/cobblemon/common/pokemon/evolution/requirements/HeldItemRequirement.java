package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemIdentifierCondition
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

public class HeldItemRequirement(itemCondition: NbtItemPredicate) : EvolutionRequirement {
   public final val itemCondition: NbtItemPredicate

   init {
      this.itemCondition = itemCondition;
   }

   public constructor() : this(new NbtItemPredicate(new ItemIdentifierCondition(new ResourceLocation("air")), null, 2, null))
   public override fun check(pokemon: Pokemon): Boolean {
      val var10000: RegistryLikeCondition = this.itemCondition.getItem();
      val var10001: Item = pokemon.heldItemNoCopy$common().m_41720_();
      val var10002: DefaultedRegistry = BuiltInRegistries.f_257033_;
      return var10000.fits(var10001, var10002 as Registry<Item>) && this.itemCondition.getNbt().m_57479_(pokemon.heldItemNoCopy$common());
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
