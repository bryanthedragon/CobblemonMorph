package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player

import com.google.gson.JsonObject

public interface PlayerDataExtension {
   public abstract fun name(): String {
   }

   public abstract fun serialize(): JsonObject {
   }

   public abstract fun deserialize(json: JsonObject): PlayerDataExtension {
   }

   public companion object {
      public final val NAME_KEY: String = "name"
   }
}
