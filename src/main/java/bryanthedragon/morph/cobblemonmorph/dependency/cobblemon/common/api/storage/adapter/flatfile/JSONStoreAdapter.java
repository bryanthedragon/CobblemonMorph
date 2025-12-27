package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.PrintWriter
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nJSONStoreAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JSONStoreAdapter.kt\ncom/cobblemon/mod/common/api/storage/adapter/flatfile/JSONStoreAdapter\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,62:1\n17#2:63\n*S KotlinDebug\n*F\n+ 1 JSONStoreAdapter.kt\ncom/cobblemon/mod/common/api/storage/adapter/flatfile/JSONStoreAdapter\n*L\n49#1:63\n*E\n"])
public open class JSONStoreAdapter(rootFolder: String,
   useNestedFolders: Boolean,
   folderPerClass: Boolean,
   gson: Gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
) : OneToOneFileStoreAdapter(rootFolder, useNestedFolders, folderPerClass, "json") {
   private final val gson: Gson

   init {
      this.gson = gson;
   }

   public open fun <E : StorePosition, T : PokemonStore<Any>> serialize(store: Any): JsonObject {
      return store.saveToJSON(new JsonObject());
   }

   public open fun save(file: File, serialized: JsonObject) {
      val pw: PrintWriter = new PrintWriter(file);
      pw.write(this.gson.toJson(serialized as JsonElement));
      pw.flush();
      pw.close();
   }

   public override fun <E, T : PokemonStore<Any>> load(file: File, storeClass: Class<out Any>, uuid: UUID): Any? {
      var br: PokemonStore;
      try {
         val var12: BufferedReader = new BufferedReader(new FileReader(file));
         val e: JsonObject = this.gson.fromJson(var12, JsonObject.class) as JsonObject;
         var12.close();

         var var13: PokemonStore;
         try {
            var13 = storeClass.getConstructor(UUID.class, UUID.class).newInstance(uuid, uuid) as PokemonStore;
         } catch (var10: NoSuchMethodException) {
            var13 = storeClass.getConstructor(UUID.class).newInstance(uuid) as PokemonStore;
         }

         var13.loadFromJSON(e);
         br = var13;
      } catch (var11: Exception) {
         br = null;
      }

      return (T)br;
   }
}
