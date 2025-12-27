package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities
import net.minecraft.nbt.CompoundTag

public fun CompoundTag.isPokemonEntity(): Boolean {
   return `$this$isPokemonEntity`.m_128461_("id").equals(CobblemonEntities.POKEMON_KEY.toString());
}
