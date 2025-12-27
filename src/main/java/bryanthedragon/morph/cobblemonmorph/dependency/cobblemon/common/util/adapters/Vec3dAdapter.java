package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import net.minecraft.world.phys.Vec3

public object Vec3dAdapter : JsonDeserializer<Vec3> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Vec3 {
      val array: JsonArray = json.getAsJsonArray();
      return new Vec3(array.get(0).getAsDouble(), array.get(1).getAsDouble(), array.get(2).getAsDouble());
   }
}
