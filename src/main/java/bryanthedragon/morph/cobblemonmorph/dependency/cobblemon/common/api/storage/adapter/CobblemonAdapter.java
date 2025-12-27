package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import java.util.UUID

public interface CobblemonAdapter<S> {
   public abstract fun <E : StorePosition, T : PokemonStore<Any>> load(storeClass: Class<Any>, uuid: UUID): Any? {
   }
}
