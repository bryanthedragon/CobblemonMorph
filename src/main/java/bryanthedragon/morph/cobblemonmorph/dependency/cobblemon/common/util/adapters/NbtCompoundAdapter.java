package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.ArrayList;
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.nbt.SnbtPrinterTagVisitor
import net.minecraft.nbt.Tag

public object NbtCompoundAdapter : JsonDeserializer<CompoundTag>, JsonSerializer<CompoundTag> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): CompoundTag {
      return NbtUtils.m_178024_(json.getAsString());
   }

   public open fun serialize(nbt: CompoundTag, type: Type, ctx: JsonSerializationContext): JsonElement {
      return (new JsonPrimitive(new SnbtPrinterTagVisitor("", 0, new ArrayList()).m_178141_(nbt as Tag))) as JsonElement;
   }
}
