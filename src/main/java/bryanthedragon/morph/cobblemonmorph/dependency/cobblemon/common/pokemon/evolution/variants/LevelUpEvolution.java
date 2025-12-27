package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PassiveEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.LinkedHashSet

public open class LevelUpEvolution(id: String,
      result: PokemonProperties,
      optional: Boolean,
      consumeHeldItem: Boolean,
      requirements: MutableSet<EvolutionRequirement>,
      learnableMoves: MutableSet<MoveTemplate>,
      permanent: Boolean
   ) :
   PassiveEvolution {
   public open var consumeHeldItem: Boolean
   public open val id: String
   public open val learnableMoves: MutableSet<MoveTemplate>
   public open var optional: Boolean
   public open val permanent: Boolean
   public open val requirements: MutableSet<EvolutionRequirement>
   public open val result: PokemonProperties

   init {
      this.id = id;
      this.result = result;
      this.optional = optional;
      this.consumeHeldItem = consumeHeldItem;
      this.requirements = requirements;
      this.learnableMoves = learnableMoves;
      this.permanent = permanent;
   }

   public constructor() : this("id", new PokemonProperties(), true, true, new LinkedHashSet<>(), new LinkedHashSet<>(), false)
   public override operator fun equals(other: Any?): Boolean {
      return other is LevelUpEvolution && StringsKt.equals((other as LevelUpEvolution).getId(), this.getId(), true);
   }

   public override fun hashCode(): Int {
      return 31 * this.getId().hashCode() + "level_up".hashCode();
   }

   override fun attemptEvolution(pokemon: Pokemon): Boolean {
      return PassiveEvolution.DefaultImpls.attemptEvolution(this, pokemon);
   }

   override fun test(pokemon: Pokemon): Boolean {
      return PassiveEvolution.DefaultImpls.test(this, pokemon);
   }

   override fun evolve(pokemon: Pokemon): Boolean {
      return PassiveEvolution.DefaultImpls.evolve(this, pokemon);
   }

   override fun forceEvolve(pokemon: Pokemon) {
      PassiveEvolution.DefaultImpls.forceEvolve(this, pokemon);
   }

   override fun evolutionMethod(pokemon: Pokemon) {
      PassiveEvolution.DefaultImpls.evolutionMethod(this, pokemon);
   }

   override fun applyTo(pokemon: Pokemon) {
      PassiveEvolution.DefaultImpls.applyTo(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
      public const val ALTERNATIVE_ADAPTER_VARIANT: String
   }
}
