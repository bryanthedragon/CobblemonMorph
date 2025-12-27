package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.ContextEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.BlockIdentifierCondition
import java.util.LinkedHashSet
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

public open class BlockClickEvolution(id: String,
      result: PokemonProperties,
      requiredContext: RegistryLikeCondition<Block>,
      optional: Boolean,
      consumeHeldItem: Boolean,
      requirements: MutableSet<EvolutionRequirement>,
      learnableMoves: MutableSet<MoveTemplate>
   ) :
   ContextEvolution<BlockClickEvolution.BlockInteractionContext, RegistryLikeCondition<Block>> {
   public open var consumeHeldItem: Boolean
   public open val id: String
   public open val learnableMoves: MutableSet<MoveTemplate>
   public open var optional: Boolean
   public open val requiredContext: RegistryLikeCondition<Block>
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

   public constructor() : this(
         "id",
         new PokemonProperties(),
         new BlockIdentifierCondition(new ResourceLocation("minecraft", "dirt")),
         true,
         true,
         new LinkedHashSet<>(),
         new LinkedHashSet<>()
      )
   public open fun testContext(pokemon: Pokemon, context: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.BlockClickEvolution.BlockInteractionContext): Boolean {
      val var10000: RegistryLikeCondition = this.getRequiredContext();
      val var10001: Block = context.getBlock();
      val var10002: Registry = context.getWorld().m_9598_().m_175515_(Registries.f_256747_);
      return var10000.fits(var10001, var10002);
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is BlockClickEvolution && StringsKt.equals((other as BlockClickEvolution).getId(), this.getId(), true);
   }

   public override fun hashCode(): Int {
      return 31 * this.getId().hashCode() + "block_click".hashCode();
   }

   open fun attemptEvolution(pokemon: Pokemon, context: BlockClickEvolution.BlockInteractionContext): Boolean {
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

   public data BlockInteractionContext(block: Block, world: Level) {
      public final val block: Block
      public final val world: Level

      init {
         this.block = block;
         this.world = world;
      }

      public operator fun component1(): Block {
         return this.block;
      }

      public operator fun component2(): Level {
         return this.world;
      }

      public fun copy(block: Block = this.block, world: Level = this.world): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.BlockClickEvolution.BlockInteractionContext {
         return new BlockClickEvolution.BlockInteractionContext(block, world);
      }

      public override fun toString(): String {
         return "BlockInteractionContext(block=${this.block}, world=${this.world})";
      }

      public override fun hashCode(): Int {
         return this.block.hashCode() * 31 + this.world.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is BlockClickEvolution.BlockInteractionContext) {
            return false;
         } else {
            val var2: BlockClickEvolution.BlockInteractionContext = other as BlockClickEvolution.BlockInteractionContext;
            if (!(this.block == (other as BlockClickEvolution.BlockInteractionContext).block)) {
               return false;
            } else {
               return this.world == var2.world;
            }
         }
      }
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
