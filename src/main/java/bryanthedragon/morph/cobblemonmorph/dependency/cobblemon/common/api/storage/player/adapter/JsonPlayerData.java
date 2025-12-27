package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData

import com.google.gson.Gson

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.PrintWriter
import java.nio.file.Path
import java.util.ArrayList;
import java.util.UUID

import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.storage.LevelResource

public class JsonPlayerData : PlayerDataStoreAdapter {
   public final lateinit var savePath: Path
   public final var useNestedStructure: Boolean = true

   public fun setup(server: MinecraftServer) {
      val var10001: Path = server.m_129843_(LevelResource.f_78176_).getParent();
      this.setSavePath(var10001);
   }

   public fun getSubFile(uuid: UUID): String {
      var var6: java.lang.String;
      if (this.useNestedStructure) {
         var6 = uuid.toString();
         var6 = var6.substring(0, 2);
         var6 = "$var6/$uuid.json";
      } 
      else {
         var6 = "$uuid.json";
      }
      return var6;
   }

   private fun file(uuid: UUID): File {
      return this.getSavePath().resolve("cobblemonplayerdata/${this.getSubFile(uuid)}").toFile();
   }

   public override fun load(uuid: UUID): PlayerData {
      val playerFile: File = this.file(uuid);
      playerFile.getParentFile().mkdirs();
      val var38: PlayerData;
      if (playerFile.exists()) {
         val var10000: Gson = gson;
         val var18: Any = var10000.fromJson(new BufferedReader(new FileReader(playerFile)), PlayerData.class);
         val var19: PlayerData = var18 as PlayerData;
         var defaultData: java.lang.Iterable = KClasses.getMemberProperties((var18 as PlayerData).getClass()::class);
         var `destination$iv$iv`: java.util.Collection = new ArrayList();
         for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (var13 is KMutableProperty) {
               `destination$iv$iv`.add(var13);
            }
         }

         defaultData = `destination$iv$iv` as java.util.List;
         `destination$iv$iv` = new ArrayList();

         for (Object element$iv$ivx : $this$filterIsInstance$iv) {
            if ((`element$iv$ivx` as KMutableProperty).getGetter().call(new Object[]{var19}) == null) {
               `destination$iv$iv`.add(`element$iv$ivx`);
            }
         }

         val newProps: java.util.List = `destination$iv$iv` as java.util.List;
         if (!(`destination$iv$iv` as java.util.List).isEmpty()) {
            val var23: PlayerData = PlayerData.Companion.defaultData(uuid);
            val var25: java.lang.Iterable;
            for (Object element$iv : var25) {
               (var29 as KMutableProperty).getSetter().call(new Object[]{var19, (var29 as KMutableProperty).getGetter().call(new Object[]{var23})});
            }
         }
         var38 = var18 as PlayerData;
      } 
      else {
         val var3: PlayerData = PlayerData.Companion.defaultData(uuid);
         this.save(var3);
         var38 = var3;
      }
      return var38;
   }

   public override fun save(playerData: PlayerData) {
      this.file(playerData.getUuid()).getParentFile().mkdirs();
      val pw: PrintWriter = new PrintWriter(this.file(playerData.getUuid()));
      pw.write(gson.toJson(playerData));
      pw.flush();
      pw.close();
   }

   public companion object {
      public final val gson: Gson
   }
}
