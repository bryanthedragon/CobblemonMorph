package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelOption
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.Orientation
import com.google.common.collect.Multimap
import java.util.UUID

public data PokemonInteractionGUICreationEvent(pokemonID: UUID, mountShoulder: Boolean, options: Multimap<Orientation, InteractWheelOption>) {
   public final val mountShoulder: Boolean
   public final val options: Multimap<Orientation, InteractWheelOption>
   public final val pokemonID: UUID

   init {
      this.pokemonID = pokemonID;
      this.mountShoulder = mountShoulder;
      this.options = options;
   }

   public fun addFillingOption(option: InteractWheelOption) {
      this.options.put(this.getNextFreeOrientation(), option);
   }

   public fun addOption(orientation: Orientation, option: InteractWheelOption) {
      this.options.put(orientation, option);
   }

   private fun getNextFreeOrientation(): Orientation {
      var largest: Orientation = Orientation.TOP_LEFT;

      for (Orientation orientation : Orientation.values()) {
         if (!this.options.containsKey(orientation)) {
            return orientation;
         }

         if (this.options.get(orientation).size() < this.options.get(largest).size()) {
            largest = orientation;
         }
      }

      return largest;
   }

   public operator fun component1(): UUID {
      return this.pokemonID;
   }

   public operator fun component2(): Boolean {
      return this.mountShoulder;
   }

   public operator fun component3(): Multimap<Orientation, InteractWheelOption> {
      return this.options;
   }

   public fun copy(
      pokemonID: UUID = this.pokemonID,
      mountShoulder: Boolean = this.mountShoulder,
      options: Multimap<Orientation, InteractWheelOption> = this.options
   ): PokemonInteractionGUICreationEvent {
      return new PokemonInteractionGUICreationEvent(pokemonID, mountShoulder, options);
   }

   public override fun toString(): String {
      return "PokemonInteractionGUICreationEvent(pokemonID=${this.pokemonID}, mountShoulder=${this.mountShoulder}, options=${this.options})";
   }

   public override fun hashCode(): Int {
      val var10000: Int = this.pokemonID.hashCode() * 31;
      var var10001: Byte = this.mountShoulder;
      if (this.mountShoulder) {
         var10001 = 1;
      }

      return (var10000 + var10001) * 31 + this.options.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonInteractionGUICreationEvent) {
         return false;
      } else {
         val var2: PokemonInteractionGUICreationEvent = other as PokemonInteractionGUICreationEvent;
         if (!(this.pokemonID == (other as PokemonInteractionGUICreationEvent).pokemonID)) {
            return false;
         } else if (this.mountShoulder != var2.mountShoulder) {
            return false;
         } else {
            return this.options == var2.options;
         }
      }
   }
}
