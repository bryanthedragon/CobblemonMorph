package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import net.minecraft.advancements.critereon.NbtPredicate
import net.minecraft.world.item.Item

public object NbtItemPredicateAdapter : JsonDeserializer<NbtItemPredicate>, JsonSerializer<NbtItemPredicate> {
   private final val CONDITION_TYPE: Type = TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Item.class}).getType()
   private const val ITEM: String = "item"
   private const val NBT: String = "nbt"

   public open fun deserialize(jElement: JsonElement, type: Type, context: JsonDeserializationContext): NbtItemPredicate {
      if (jElement.isJsonPrimitive()) {
         var var10002: Any = context.deserialize(jElement, CONDITION_TYPE);
         var10002 = var10002 as RegistryLikeCondition;
         val var10003: NbtPredicate = NbtPredicate.f_57471_;
         return new NbtItemPredicate((RegistryLikeCondition<Item>)var10002, var10003);
      } else {
         val jObject: JsonObject = jElement.getAsJsonObject();
         val itemCondition: RegistryLikeCondition = context.deserialize(jObject.get("item"), CONDITION_TYPE) as RegistryLikeCondition;
         val nbtPredicate: NbtPredicate = NbtPredicate.m_57481_(jObject.get("nbt"));
         return new NbtItemPredicate(itemCondition, nbtPredicate);
      }
   }

   public open fun serialize(predicate: NbtItemPredicate, type: Type, context: JsonSerializationContext): JsonElement {
      val serializedItemCondition: JsonElement = context.serialize(predicate.getItem(), CONDITION_TYPE);
      if (predicate.getNbt() == NbtPredicate.f_57471_) {
         return serializedItemCondition;
      } else {
         val var5: JsonObject = new JsonObject();
         var5.add("item", serializedItemCondition);
         var5.add("nbt", predicate.getNbt().m_57476_());
         return var5 as JsonElement;
      }
   }
}
