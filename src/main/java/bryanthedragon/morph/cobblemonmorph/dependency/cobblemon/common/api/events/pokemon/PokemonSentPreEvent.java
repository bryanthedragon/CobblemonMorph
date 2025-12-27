package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.Vec3

public data PokemonSentPreEvent(pokemon: Pokemon, level: ServerLevel, position: Vec3) : Cancelable {
   public final val level: ServerLevel
   public final val pokemon: Pokemon
   public final val position: Vec3

   init {
      this.pokemon = pokemon;
      this.level = level;
      this.position = position;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): ServerLevel {
      return this.level;
   }

   public operator fun component3(): Vec3 {
      return this.position;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, level: ServerLevel = this.level, position: Vec3 = this.position): PokemonSentPreEvent {
      return new PokemonSentPreEvent(pokemon, level, position);
   }

   public override fun toString(): String {
      return "PokemonSentPreEvent(pokemon=${this.pokemon}, level=${this.level}, position=${this.position})";
   }

   public override fun hashCode(): Int {
      return (this.pokemon.hashCode() * 31 + this.level.hashCode()) * 31 + this.position.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonSentPreEvent) {
         return false;
      } else {
         val var2: PokemonSentPreEvent = other as PokemonSentPreEvent;
         if (!(this.pokemon == (other as PokemonSentPreEvent).pokemon)) {
            return false;
         } else if (!(this.level == var2.level)) {
            return false;
         } else {
            return this.position == var2.position;
         }
      }
   }
}
