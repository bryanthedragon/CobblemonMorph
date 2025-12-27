package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.nbt.CompoundTag

public data PokemonEntitySaveEvent(pokemonEntity: PokemonEntity, nbt: CompoundTag) {
   public final val nbt: CompoundTag
   public final val pokemonEntity: PokemonEntity

   init {
      this.pokemonEntity = pokemonEntity;
      this.nbt = nbt;
   }

   public operator fun component1(): PokemonEntity {
      return this.pokemonEntity;
   }

   public operator fun component2(): CompoundTag {
      return this.nbt;
   }

   public fun copy(pokemonEntity: PokemonEntity = this.pokemonEntity, nbt: CompoundTag = this.nbt): PokemonEntitySaveEvent {
      return new PokemonEntitySaveEvent(pokemonEntity, nbt);
   }

   public override fun toString(): String {
      return "PokemonEntitySaveEvent(pokemonEntity=${this.pokemonEntity}, nbt=${this.nbt})";
   }

   public override fun hashCode(): Int {
      return this.pokemonEntity.hashCode() * 31 + this.nbt.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonEntitySaveEvent) {
         return false;
      } else {
         val var2: PokemonEntitySaveEvent = other as PokemonEntitySaveEvent;
         if (!(this.pokemonEntity == (other as PokemonEntitySaveEvent).pokemonEntity)) {
            return false;
         } else {
            return this.nbt == var2.nbt;
         }
      }
   }
}
