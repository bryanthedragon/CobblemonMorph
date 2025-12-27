package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.MongoPlayerDataAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MapExtensionsKt
import com.mongodb.client.MongoClient
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension

public class MongoPlayerDataStoreFactory(mongoClient: MongoClient, databaseName: String) : PlayerDataStoreFactory {
   private final val adapter: MongoPlayerDataAdapter
   private final val cache: MutableMap<UUID, PlayerData> = (new LinkedHashMap()) as java.util.Map

   init {
      this.adapter = new MongoPlayerDataAdapter(mongoClient, databaseName);
   }

   public override fun load(uuid: UUID): PlayerData {
      val var3: PlayerData;
      if (this.cache.containsKey(uuid)) {
         val var10000: Any = this.cache.get(uuid);
         var3 = var10000 as PlayerData;
      } else {
         val data: PlayerData = this.adapter.load(uuid);
         this.cache.put(uuid, data);
         var3 = data;
      }

      return var3;
   }

   public override fun save(data: PlayerData) {
      this.adapter.save(data);
   }

   public override fun saveAll() {
      for (Entry element$iv : this.cache.entrySet()) {
         this.adapter.save(`element$iv`.getValue() as PlayerData);
      }

      MapExtensionsKt.removeIf(this.cache, <unrepresentable>.INSTANCE);
   }

   public override fun onPlayerDisconnect(uuid: UUID) {
      this.cache.remove(uuid);
   }
}
