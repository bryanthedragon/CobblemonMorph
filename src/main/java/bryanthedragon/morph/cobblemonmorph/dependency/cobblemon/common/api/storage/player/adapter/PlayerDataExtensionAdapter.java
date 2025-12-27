package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.DummyPlayerDataExtension
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtensionRegistry

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer

import java.lang.reflect.Type

public object PlayerDataExtensionAdapter : JsonSerializer<PlayerDataExtension>, JsonDeserializer<PlayerDataExtension> {
   public open fun serialize(src: PlayerDataExtension, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      return src.serialize() as JsonElement;
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): PlayerDataExtension {
      val jObject: JsonObject = json.getAsJsonObject();
      val extension: JsonElement = jObject.get(PlayerDataExtension.Companion.getNAME_KEY());
      if (extension == null) {
         throw new IllegalStateException("PlayerDataExtension without name");
      } 
      else {
         val var10000: PlayerDataExtensionRegistry = PlayerDataExtensionRegistry.INSTANCE;
         val var10001: java.lang.String = extension.getAsString();
         val var7: Class = var10000.get(var10001);
         val var9: PlayerDataExtension;
         if (var7 != null) {
            val var8: PlayerDataExtension = var7.getDeclaredConstructor().newInstance() as PlayerDataExtension;
            var9 = var8.deserialize(jObject);
         } 
         else {
            Cobblemon.INSTANCE.getLOGGER().info("No PlayerDataExtension registered with name ${extension.getAsString()}, loading data with a dummy extension.");
            var9 = new DummyPlayerDataExtension(jObject);
         }

         return var9;
      }
   }
}
