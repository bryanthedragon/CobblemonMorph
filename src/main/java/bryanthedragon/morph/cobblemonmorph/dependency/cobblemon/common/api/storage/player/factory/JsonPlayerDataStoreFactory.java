package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.JsonPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MapExtensionsKt
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer

@SourceDebugExtension(["SMAP\nJsonPlayerDataStoreFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPlayerDataStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/player/factory/JsonPlayerDataStoreFactory\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,50:1\n215#2,2:51\n*S KotlinDebug\n*F\n+ 1 JsonPlayerDataStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/player/factory/JsonPlayerDataStoreFactory\n*L\n43#1:51,2\n*E\n"])
public class JsonPlayerDataStoreFactory : PlayerDataStoreFactory {
   private final val adapter: JsonPlayerData = new JsonPlayerData()
   private final val cache: MutableMap<UUID, PlayerData> = (new LinkedHashMap()) as java.util.Map

   public fun setup(server: MinecraftServer) {
      this.adapter.setup(server);
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
