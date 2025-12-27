package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BestSpawnerConfig {
   public final val buckets: MutableList<SpawnBucket> =
      CollectionsKt.mutableListOf(
         new SpawnBucket[]{
            new SpawnBucket("common", 93.8F), new SpawnBucket("uncommon", 5.0F), new SpawnBucket("rare", 1.0F), new SpawnBucket("ultra-rare", 0.2F)
         }
      )
      public final val contextWeights: MutableMap<String, Float> =
      MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("grounded", 1.0F), TuplesKt.to("submerged", 0.99F), TuplesKt.to("surface", 0.01F)})
      public final val replaceWithNewVersion: Boolean = true
   public final val version: Int

   public companion object {
      public const val CONFIG_NAME: String
      public final val GSON: Gson

      public fun load(): BestSpawnerConfig {
         val internal: BestSpawnerConfig = this.loadInternal();
         if (!Cobblemon.INSTANCE.getConfig().getExportSpawnConfig()) {
            return internal;
         } else {
            val external: BestSpawnerConfig = this.loadExternal();
            val var10000: BestSpawnerConfig;
            if (external == null) {
               this.saveExternal();
               var10000 = internal;
            } else if (external.getReplaceWithNewVersion() && internal.getVersion() > external.getVersion()) {
               this.saveExternal();
               var10000 = internal;
            } else {
               var10000 = external;
            }

            return var10000;
         }
      }

      private fun loadInternal(): BestSpawnerConfig {
         val var10002: InputStream = Cobblemon.class.getResourceAsStream("/assets/cobblemon/spawning/best-spawner-config.json");
         val reader: InputStreamReader = new InputStreamReader(var10002);
         val config: BestSpawnerConfig = this.getGSON().fromJson(reader, BestSpawnerConfig.class) as BestSpawnerConfig;
         reader.close();
         return config;
      }

      private fun loadExternal(): BestSpawnerConfig? {
         val configFile: File = new File("config/cobblemon/spawning/best-spawner-config.json");
         configFile.getParentFile().mkdirs();
         val var10000: BestSpawnerConfig;
         if (configFile.exists()) {
            var reader: BestSpawnerConfig;
            try {
               val var5: FileReader = new FileReader(configFile);
               val e: BestSpawnerConfig = this.getGSON().fromJson(var5, BestSpawnerConfig.class) as BestSpawnerConfig;
               var5.close();
               reader = e;
            } catch (var4: Exception) {
               Cobblemon.INSTANCE.getLOGGER().error("Unable to load external Best Spawner configuration", var4);
               reader = null;
            }

            var10000 = reader;
         } else {
            var10000 = null;
         }

         return var10000;
      }

      public fun saveExternal() {
         val var10000: InputStream = Cobblemon.class.getResourceAsStream("/assets/cobblemon/spawning/best-spawner-config.json");
         val bytes: ByteArray = var10000.readAllBytes();
         var10000.close();
         val configFile: File = new File("config/cobblemon/spawning/best-spawner-config.json");
         configFile.getParentFile().mkdirs();
         configFile.createNewFile();
         val outputStream: FileOutputStream = new FileOutputStream(configFile);
         outputStream.write(bytes);
         outputStream.close();
      }
   }
}
