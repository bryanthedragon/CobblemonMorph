package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapter
import java.util.UUID

public interface FileStoreAdapter<S> : CobblemonAdapter<S> {
   public abstract fun <E : StorePosition, T : PokemonStore<Any>> serialize(store: Any): Any {
   }

   public abstract fun save(storeClass: Class<out PokemonStore<*>>, uuid: UUID, serialized: Any) {
   }
}
