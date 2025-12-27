package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable

public interface ClientDataSynchronizer<T> : Decodable, Encodable {
   public abstract fun shouldSynchronize(other: Any): Boolean {
   }
}
