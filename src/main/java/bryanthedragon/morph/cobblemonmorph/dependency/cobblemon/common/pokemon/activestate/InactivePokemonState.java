package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate

import net.minecraft.nbt.CompoundTag

public class InactivePokemonState : PokemonState() {
   public open fun writeToNBT(nbt: CompoundTag): Nothing? {
      return null;
   }
}
