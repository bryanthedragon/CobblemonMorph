package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter.SpawnConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.AllBiomeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.PreferredBiomeCondition
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type
import java.util.HashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.reflect.KClass
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nCobblemonBerrySpawnConditionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBerrySpawnConditionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/CobblemonBerrySpawnConditionAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n1#2:59\n*E\n"])
public object CobblemonBerrySpawnConditionAdapter : SpawnConditionAdapter {
   private const val VARIANT: String = "variant"
   private final val types: HashMap<String, KClass<out BerrySpawnCondition>> = new HashMap()

   public override fun register(type: KClass<out BerrySpawnCondition>, identifier: ResourceLocation) {
      val existing: KClass = types.put(identifier.toString(), type);
      if (existing != null) {
         Cobblemon.INSTANCE
            .getLOGGER()
            .debug(
               "Replaced {} under ID {} with {} in the {}",
               (existing.getClass()::class).getQualifiedName(),
               identifier.toString(),
               type.getQualifiedName(),
               (this.getClass()::class).getQualifiedName()
            );
      }
   }

   public open fun deserialize(jElement: JsonElement, type: Type, context: JsonDeserializationContext): BerrySpawnCondition {
      val json: JsonObject = jElement.getAsJsonObject();
      var var10000: java.lang.String = json.get("variant").getAsString();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val var8: KClass = types.get(var10000);
      if (var8 == null) {
         throw new IllegalArgumentException("Cannot resolve type for variant $var10000");
      } else {
         val var9: Any = context.deserialize(json as JsonElement, JvmClassMappingKt.getJavaClass(var8));
         return var9 as BerrySpawnCondition;
      }
   }

   public open fun serialize(spawnCondition: BerrySpawnCondition, type: Type, context: JsonSerializationContext): JsonElement {
      val json: JsonObject = context.serialize(spawnCondition).getAsJsonObject();
      var var10000: java.util.Set = types.entrySet();
      val var7: java.util.Iterator = var10000.iterator();

      while (true) {
         if (var7.hasNext()) {
            val var8: Any = var7.next();
            if (!((var8 as Entry).getValue() == spawnCondition.getClass()::class)) {
               continue;
            }

            var10000 = (java.util.Set)var8;
            break;
         }

         var10000 = null;
         break;
      }

      val var12: Entry = var10000 as Entry;
      if (var10000 as Entry != null) {
         val var13: java.lang.String = var12.getKey() as java.lang.String;
         if (var13 != null) {
            json.addProperty("variant", var13);
            return json as JsonElement;
         }
      }

      throw new IllegalArgumentException("Cannot resolve variant for type ${(spawnCondition.getClass()::class).getQualifiedName()}");
   }

   @JvmStatic
   fun {
      INSTANCE.register(PreferredBiomeCondition::class, PreferredBiomeCondition.Companion.getID());
      INSTANCE.register(AllBiomeCondition::class, AllBiomeCondition.Companion.getID());
   }
}
