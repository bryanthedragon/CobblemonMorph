package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters.EvolutionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.BlockClickEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.ItemInteractionEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.LevelUpEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type
import java.util.LinkedHashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.reflect.KClass

@SourceDebugExtension(["SMAP\nCobbledEvolutionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledEvolutionAdapter.kt\ncom/cobblemon/mod/common/pokemon/evolution/adapters/CobblemonEvolutionAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,61:1\n1#2:62\n*E\n"])
public object CobblemonEvolutionAdapter : EvolutionAdapter {
   private const val VARIANT: String = "variant"
   private final val types: MutableMap<String, KClass<out Evolution>> = (new LinkedHashMap()) as java.util.Map

   public override fun <T : Evolution> registerType(id: String, type: KClass<Any>) {
      val var10000: java.util.Map = types;
      val var10001: java.lang.String = id.toLowerCase(Locale.ROOT);
      var10000.put(var10001, type);
   }

   public open fun deserialize(jsonIn: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Evolution {
      val json: JsonObject = jsonIn.getAsJsonObject();
      var var10000: java.lang.String = json.get("variant").getAsString();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val var8: KClass = types.get(var10000);
      if (var8 == null) {
         throw new IllegalArgumentException("Cannot resolve type for variant $var10000");
      } else {
         val var9: Any = context.deserialize(json as JsonElement, JvmClassMappingKt.getJavaClass(var8));
         return var9 as Evolution;
      }
   }

   public open fun serialize(src: Evolution, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      val json: JsonObject = context.serialize(src, src.getClass()).getAsJsonObject();
      val var7: java.util.Iterator = types.entrySet().iterator();

      var var10000: Any;
      while (true) {
         if (var7.hasNext()) {
            val var8: Any = var7.next();
            if (!((var8 as Entry).getValue() == src.getClass()::class)) {
               continue;
            }

            var10000 = (Entry)var8;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000;
      if (var10000 != null) {
         val var12: java.lang.String = var10000.getKey() as java.lang.String;
         if (var12 != null) {
            json.addProperty("variant", var12);
            return json as JsonElement;
         }
      }

      throw new IllegalArgumentException("Cannot resolve variant for type ${(src.getClass()::class).getQualifiedName()}");
   }

   @JvmStatic
   fun {
      INSTANCE.registerType("level_up", LevelUpEvolution::class);
      INSTANCE.registerType("trade", TradeEvolution::class);
      INSTANCE.registerType("item_interact", ItemInteractionEvolution::class);
      INSTANCE.registerType("passive", LevelUpEvolution::class);
      INSTANCE.registerType("block_click", BlockClickEvolution::class);
   }
}
