package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.database

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapterParent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.JSONStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.NBTStoreAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import java.io.File
import java.util.Date
import java.util.UUID
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.storage.LevelResource
import org.bson.Document
import org.bson.conversions.Bson

public open class MongoDBStoreAdapter(mongoClient: MongoClient, databaseName: String) : CobblemonAdapterParent<JsonObject>, FileStoreAdapter<JsonObject> {
   protected final val databaseName: String
   protected final val gson: Gson
   protected final val mongoClient: MongoClient

   init {
      this.mongoClient = mongoClient;
      this.databaseName = databaseName;
      this.gson = this.createGson();
   }

   public open fun <E : StorePosition, T : PokemonStore<Any>> serialize(store: Any): JsonObject {
      return store.saveToJSON(new JsonObject());
   }

   public open fun save(storeClass: Class<out PokemonStore<*>>, uuid: UUID, serialized: JsonObject) {
      val document: Document = Document.parse(this.gson.toJson(serialized as JsonElement));
      (document as java.util.Map).put("uuid", uuid.toString());
      (document as java.util.Map).put("lastUpdated", new Date());
      this.getCollection(storeClass).replaceOne((new Document("uuid", uuid.toString())) as Bson, document, new ReplaceOptions().upsert(true));
   }

   public override fun <E : StorePosition, T : PokemonStore<Any>> provide(storeClass: Class<Any>, uuid: UUID): Any? {
      val var10000: MinecraftServer = DistributionUtilsKt.server();
      val pokemonStoreRoot: File = var10000.m_129843_(LevelResource.f_78182_).resolve("pokemon").toFile();
      var var10002: java.lang.String = pokemonStoreRoot.getAbsolutePath();
      val jsonAdapter: JSONStoreAdapter = new JSONStoreAdapter(var10002, true, true, null, 8, null);
      var10002 = pokemonStoreRoot.getAbsolutePath();
      val nbtAdapter: NBTStoreAdapter = new NBTStoreAdapter(var10002, true, true);
      val document: Document = this.getCollection(storeClass).find((new Document("uuid", uuid.toString())) as Bson).first() as Document;
      if (document != null) {
         val var16: JsonObject = this.gson.fromJson(document.toJson(), JsonObject.class) as JsonObject;

         var var12: PokemonStore;
         try {
            var12 = storeClass.getConstructor(UUID.class, UUID.class).newInstance(uuid, uuid) as PokemonStore;
         } catch (var15: NoSuchMethodException) {
            var12 = storeClass.getConstructor(UUID.class).newInstance(uuid) as PokemonStore;
         }

         var12.loadFromJSON(var16);
         return (T)var12;
      } else {
         val nbtStore: PokemonStore = nbtAdapter.provide(storeClass, uuid);
         if (nbtStore != null) {
            this.save(storeClass, uuid, this.serialize(nbtStore));
            return (T)nbtStore;
         } else {
            val jsonStore: PokemonStore = jsonAdapter.provide(storeClass, uuid);
            if (jsonStore != null) {
               this.save(storeClass, uuid, this.serialize(jsonStore));
               return (T)jsonStore;
            } else {
               return null;
            }
         }
      }
   }

   protected open fun createGson(): Gson {
      return new Gson();
   }

   protected open fun getCollection(storeClass: Class<out PokemonStore<*>>): MongoCollection<Document> {
      val var10000: MongoCollection = this.mongoClient
         .getDatabase(this.databaseName)
         .getCollection(
            if (storeClass == PlayerPartyStore::class.java)
               "PlayerPartyCollection"
               else
               (if (storeClass == PCStore::class.java) "PCCollection" else "OtherCollection")
         );
      return var10000;
   }
}
