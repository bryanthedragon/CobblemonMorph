package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.ContextEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.LinkedHashSet

public open class TradeEvolution(id: String,
      result: PokemonProperties,
      requiredContext: PokemonProperties,
      optional: Boolean,
      consumeHeldItem: Boolean,
      requirements: MutableSet<EvolutionRequirement>,
      learnableMoves: MutableSet<MoveTemplate>
   ) :
   ContextEvolution<Pokemon, PokemonProperties> {
   public open var consumeHeldItem: Boolean
   public open val id: String
   public open val learnableMoves: MutableSet<MoveTemplate>
   public open var optional: Boolean
   public open val requiredContext: PokemonProperties
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

   public constructor() : this("id", new PokemonProperties(), new PokemonProperties(), true, true, new LinkedHashSet<>(), new LinkedHashSet<>())
   public open fun testContext(pokemon: Pokemon, context: Pokemon): Boolean {
      return this.getRequiredContext().matches(context);
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is TradeEvolution && StringsKt.equals((other as TradeEvolution).getId(), this.getId(), true);
   }

   public override fun hashCode(): Int {
      return 31 * this.getId().hashCode() + "trade".hashCode();
   }

   open fun attemptEvolution(pokemon: Pokemon, context: Pokemon): Boolean {
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
}
