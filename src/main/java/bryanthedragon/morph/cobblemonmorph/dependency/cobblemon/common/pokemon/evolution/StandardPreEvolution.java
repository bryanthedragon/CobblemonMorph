package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species

public data StandardPreEvolution(species: Species, form: FormData) : PreEvolution {
   public open val form: FormData
   public open val species: Species

   init {
      this.species = species;
      this.form = form;
   }

   public operator fun component1(): Species {
      return this.species;
   }

   public operator fun component2(): FormData {
      return this.form;
   }

   public fun copy(species: Species = this.species, form: FormData = this.form): StandardPreEvolution {
      return new StandardPreEvolution(species, form);
   }

   public override fun toString(): String {
      return "StandardPreEvolution(species=${this.species}, form=${this.form})";
   }

   public override fun hashCode(): Int {
      return this.species.hashCode() * 31 + this.form.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is StandardPreEvolution) {
         return false;
      } else {
         val var2: StandardPreEvolution = other as StandardPreEvolution;
         if (!(this.species == (other as StandardPreEvolution).species)) {
            return false;
         } else {
            return this.form == var2.form;
         }
      }
   }
}
