package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import com.google.gson.JsonObject

public class DummyPlayerDataExtension(json: JsonObject) : PlayerDataExtension {
   public final val json: JsonObject

   init {
      this.json = json;
   }

   public override fun name(): String {
      val var10000: java.lang.String = this.json.get(PlayerDataExtension.Companion.getNAME_KEY()).getAsString();
      return var10000;
   }

   public override fun serialize(): JsonObject {
      return this.json;
   }

   public override fun deserialize(json: JsonObject): PlayerDataExtension {
      return new DummyPlayerDataExtension(json);
   }
}
