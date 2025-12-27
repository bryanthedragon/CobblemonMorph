package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public object TextAdapter : JsonDeserializer<Component> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): MutableComponent {
      val var10000: java.lang.String = json.getAsString();
      return TextKt.text(var10000);
   }
}
