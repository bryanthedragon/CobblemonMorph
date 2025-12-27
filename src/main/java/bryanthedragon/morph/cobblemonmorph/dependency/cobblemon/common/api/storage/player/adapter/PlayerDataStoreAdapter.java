package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData

import java.util.UUID

public interface PlayerDataStoreAdapter {
   public abstract fun load(uuid: UUID): PlayerData {
   }

   public abstract fun save(playerData: PlayerData) {
   }
}
