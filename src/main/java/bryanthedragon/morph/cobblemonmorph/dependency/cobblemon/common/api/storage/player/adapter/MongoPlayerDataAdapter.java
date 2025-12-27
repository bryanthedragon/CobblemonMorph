package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData

import com.google.gson.Gson
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions

import java.util.ArrayList;
import java.util.UUID

import org.bson.Document
import org.bson.conversions.Bson

public class MongoPlayerDataAdapter(mongoClient: MongoClient, databaseName: String) : PlayerDataStoreAdapter {
   private final val collection: MongoCollection<Document>

   init {
      this.collection = mongoClient.getDatabase(databaseName).getCollection("PlayerDataCollection");
   }

   public override fun load(uuid: UUID): PlayerData {
      val document: Document = this.collection.find((new Document("uuid", uuid.toString())) as Bson).first() as Document;
      val var36: PlayerData;
      if (document != null) {
         val var6: Any = gson.fromJson(document.toJson(), PlayerData.class);
         val it: PlayerData = var6 as PlayerData;
         var defaultData: java.lang.Iterable = KClasses.getMemberProperties((var6 as PlayerData).getClass()::class);
         var `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (var15 is KMutableProperty) {
               `destination$iv$iv`.add(var15);
            }
         }

         defaultData = `destination$iv$iv` as java.util.List;
         `destination$iv$iv` = new ArrayList();

         for (Object element$iv$ivx : $this$filterIsInstance$iv) {
            if ((`element$iv$ivx` as KMutableProperty).getGetter().call(new Object[]{it}) == null) {
               `destination$iv$iv`.add(`element$iv$ivx`);
            }
         }

         val newProps: java.util.List = `destination$iv$iv` as java.util.List;
         if (!(`destination$iv$iv` as java.util.List).isEmpty()) {
            val var22: PlayerData = PlayerData.Companion.defaultData(uuid);
            val var24: java.lang.Iterable;
            for (Object element$iv : var24) {
               (var28 as KMutableProperty).getSetter().call(new Object[]{it, (var28 as KMutableProperty).getGetter().call(new Object[]{var22})});
            }
         }

         var36 = var6 as PlayerData;
      } 
      else {
         val var4: PlayerData = PlayerData.Companion.defaultData(uuid);
         this.save(var4);
         var36 = var4;
      }

      return var36;
   }

   public override fun save(playerData: PlayerData) {
      val document: Document = Document.parse(gson.toJson(playerData));
      document.put("uuid", playerData.getUuid().toString());
      this.collection.replaceOne((new Document("uuid", playerData.getUuid().toString())) as Bson, document, new ReplaceOptions().upsert(true));
   }

   public companion object {
      public final val gson: Gson
   }
}
