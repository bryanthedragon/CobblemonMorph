package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species

internal data class CobblemonEvolutionDisplay(id: String, species: Species, aspects: Set<String>) : EvolutionDisplay {
   public open val aspects: Set<String>
   public open val id: String
   public open val species: Species

   init {
      this.id = id;
      this.species = species;
      this.aspects = aspects;
   }

   public constructor(id: String, pokemon: Pokemon) : this(id, pokemon.getSpecies(), pokemon.getAspects())
   public operator fun component1(): String {
      return this.id;
   }

   public operator fun component2(): Species {
      return this.species;
   }

   public operator fun component3(): Set<String> {
      return this.aspects;
   }

   public fun copy(id: String = this.id, species: Species = this.species, aspects: Set<String> = this.aspects): CobblemonEvolutionDisplay {
      return new CobblemonEvolutionDisplay(id, species, aspects);
   }

   public override fun toString(): String {
      return "CobblemonEvolutionDisplay(id=${this.id}, species=${this.species}, aspects=${this.aspects})";
   }

   public override fun hashCode(): Int {
      return (this.id.hashCode() * 31 + this.species.hashCode()) * 31 + this.aspects.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is CobblemonEvolutionDisplay) {
         return false;
      } else {
         val var2: CobblemonEvolutionDisplay = other as CobblemonEvolutionDisplay;
         if (!(this.id == (other as CobblemonEvolutionDisplay).id)) {
            return false;
         } else if (!(this.species == var2.species)) {
            return false;
         } else {
            return this.aspects == var2.aspects;
         }
      }
   }
}
