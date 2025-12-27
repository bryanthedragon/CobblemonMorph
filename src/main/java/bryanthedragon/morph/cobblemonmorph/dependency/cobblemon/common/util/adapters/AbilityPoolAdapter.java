package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nAbilityPoolAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbilityPoolAdapter.kt\ncom/cobblemon/mod/common/util/adapters/AbilityPoolAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1855#2:37\n1856#2:39\n1#3:38\n*S KotlinDebug\n*F\n+ 1 AbilityPoolAdapter.kt\ncom/cobblemon/mod/common/util/adapters/AbilityPoolAdapter\n*L\n29#1:37\n29#1:39\n*E\n"])
public object AbilityPoolAdapter : JsonDeserializer<AbilityPool> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): AbilityPool {
      val pool: AbilityPool = new AbilityPool();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val element: JsonElement = `element$iv` as JsonElement;
         val var11: java.util.Iterator = PotentialAbility.Companion.getTypes().iterator();

         var var16: PotentialAbility;
         while (true) {
            if (!var11.hasNext()) {
               var16 = null;
               break;
            }

            val it: PotentialAbilityType = var11.next() as PotentialAbilityType;
            val var15: PotentialAbility = it.parseFromJSON(element);
            if (var15 != null) {
               var16 = var15;
               break;
            }
         }

         if (var16 == null) {
            throw new IllegalStateException("Failed to interpret ability: $json");
         }

         pool.add(var16.getPriority(), var16);
      }

      return pool;
   }
}
