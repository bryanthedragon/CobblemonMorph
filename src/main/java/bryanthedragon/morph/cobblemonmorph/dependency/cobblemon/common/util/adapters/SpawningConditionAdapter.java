package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BasicSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSpawningConditionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningConditionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawningConditionAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1855#2,2:65\n*S KotlinDebug\n*F\n+ 1 SpawningConditionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawningConditionAdapter\n*L\n53#1:65,2\n*E\n"])
public object SpawningConditionAdapter : JsonDeserializer<SpawningCondition<?>> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawningCondition<*> {
      val var10000: JsonElement = json.getAsJsonObject().get("type");
      val name: java.lang.String = if (var10000 != null) var10000.getAsString() else null;
      val var16: SpawningCondition;
      if (name == null) {
         var16 = if (SpawnLoader.INSTANCE.getDeserializingConditionClass() == null)
            ctx.deserialize(json, BasicSpawningCondition::class.java) as SpawningCondition
            else
            ctx.deserialize(json, SpawnLoader.INSTANCE.getDeserializingConditionClass()) as SpawningCondition;
      } else {
         val appendageClasses: Class = SpawningCondition.Companion.getByName(name);
         if (appendageClasses == null) {
            throw new IllegalStateException("Unrecognized spawning condition type: $name");
         }

         val var17: Any = ctx.deserialize(json, appendageClasses);
         var16 = (var17 as Void) as SpawningCondition;
      }

      val condition: SpawningCondition = var16;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: Class = `element$iv` as Class;

         try {
            val var18: java.util.List = condition.getAppendages();
            val var10001: Any = ctx.deserialize(json, it);
            var18.add(var10001);
         } catch (var14: Exception) {
            Cobblemon.INSTANCE.getLOGGER().error("Unable to deserialize appendage condition of type: ${(`element$iv` as Class).getSimpleName()}");
            throw var14;
         }
      }

      return condition;
   }
}
