package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.ContextEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemIdentifierCondition
import java.util.LinkedHashSet
import net.minecraft.advancements.critereon.NbtPredicate
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

public open class ItemInteractionEvolution(id: String,
      result: PokemonProperties,
      requiredContext: NbtItemPredicate,
      optional: Boolean,
      consumeHeldItem: Boolean,
      requirements: MutableSet<EvolutionRequirement>,
      learnableMoves: MutableSet<MoveTemplate>
   ) :
   ContextEvolution<ItemInteractionEvolution.ItemInteractionContext, NbtItemPredicate> {
   public open var consumeHeldItem: Boolean
   public open val id: String
   public open val learnableMoves: MutableSet<MoveTemplate>
   public open var optional: Boolean
   public open val requiredContext: NbtItemPredicate
   public open val requirements: MutableSet<EvolutionRequirement>
   public open val result: PokemonProperties

   init {
      this.id = id;
      this.result = result;
      this.requiredContext = requiredContext;
      this.optional = optional;
      this.consumeHeldItem = consumeHeldItem;
      this.requirements = requirements;
      this.learnableMoves = learnableMoves;
   }

   public constructor()  {
      val var10002: PokemonProperties = new PokemonProperties();
      val var10005: RegistryLikeCondition = new ItemIdentifierCondition(new ResourceLocation("minecraft", "fish"));
      val var10006: NbtPredicate = NbtPredicate.f_57471_;
      this("id", var10002, new NbtItemPredicate(var10005, var10006), true, true, new LinkedHashSet<>(), new LinkedHashSet<>());
   }

   public open fun testContext(pokemon: Pokemon, context: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.ItemInteractionEvolution.ItemInteractionContext): Boolean {
      val var10000: RegistryLikeCondition = this.getRequiredContext().getItem();
      val var10001: Item = context.getStack().m_41720_();
      val var10002: Registry = context.getWorld().m_9598_().m_175515_(Registries.f_256913_);
      return var10000.fits(var10001, var10002) && this.getRequiredContext().getNbt().m_57479_(context.getStack());
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is ItemInteractionEvolution && StringsKt.equals((other as ItemInteractionEvolution).getId(), this.getId(), true);
   }

   public override fun hashCode(): Int {
      return 31 * this.getId().hashCode() + "item_interact".hashCode();
   }

   open fun attemptEvolution(pokemon: Pokemon, context: ItemInteractionEvolution.ItemInteractionContext): Boolean {
      return ContextEvolution.DefaultImpls.attemptEvolution(this, pokemon, context);
   }

   override fun test(pokemon: Pokemon): Boolean {
      return ContextEvolution.DefaultImpls.test(this, pokemon);
   }

   override fun evolve(pokemon: Pokemon): Boolean {
      return ContextEvolution.DefaultImpls.evolve(this, pokemon);
   }

   override fun forceEvolve(pokemon: Pokemon) {
      ContextEvolution.DefaultImpls.forceEvolve(this, pokemon);
   }

   override fun evolutionMethod(pokemon: Pokemon) {
      ContextEvolution.DefaultImpls.evolutionMethod(this, pokemon);
   }

   override fun applyTo(pokemon: Pokemon) {
      ContextEvolution.DefaultImpls.applyTo(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }

   public data ItemInteractionContext(stack: ItemStack, world: Level) {
      public final val stack: ItemStack
      public final val world: Level

      init {
         this.stack = stack;
         this.world = world;
      }

      public operator fun component1(): ItemStack {
         return this.stack;
      }

      public operator fun component2(): Level {
         return this.world;
      }

      public fun copy(stack: ItemStack = this.stack, world: Level = this.world): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.ItemInteractionEvolution.ItemInteractionContext {
         return new ItemInteractionEvolution.ItemInteractionContext(stack, world);
      }

      public override fun toString(): String {
         return "ItemInteractionContext(stack=${this.stack}, world=${this.world})";
      }

      public override fun hashCode(): Int {
         return this.stack.hashCode() * 31 + this.world.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is ItemInteractionEvolution.ItemInteractionContext) {
            return false;
         } else {
            val var2: ItemInteractionEvolution.ItemInteractionContext = other as ItemInteractionEvolution.ItemInteractionContext;
            if (!(this.stack == (other as ItemInteractionEvolution.ItemInteractionContext).stack)) {
               return false;
            } else {
               return this.world == var2.world;
            }
         }
      }
   }
}
