package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import java.util.UUID

public interface PlayerDataStoreFactory {
   public abstract fun load(uuid: UUID): PlayerData {
   }

   public abstract fun save(data: PlayerData) {
   }

   public abstract fun saveAll() {
   }

   public abstract fun onPlayerDisconnect(uuid: UUID) {
   }
}
