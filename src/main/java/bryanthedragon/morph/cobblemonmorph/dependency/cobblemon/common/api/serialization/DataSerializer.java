package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization

import com.google.gson.JsonElement
import net.minecraft.nbt.Tag

public interface DataSerializer<N extends Tag, J extends JsonElement> {
   public abstract fun loadFromNBT(nbt: Any) {
   }

   public abstract fun saveToNBT(): Any {
   }

   public abstract fun loadFromJson(json: Any) {
   }

   public abstract fun saveToJson(): Any {
   }
}
