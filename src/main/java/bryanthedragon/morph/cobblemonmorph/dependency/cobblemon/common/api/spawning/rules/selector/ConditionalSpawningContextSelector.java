package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nConditionalSpawningContextSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConditionalSpawningContextSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/ConditionalSpawningContextSelector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,25:1\n2624#2,3:26\n1747#2,3:29\n*S KotlinDebug\n*F\n+ 1 ConditionalSpawningContextSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/ConditionalSpawningContextSelector\n*L\n19#1:26,3\n22#1:29,3\n*E\n"])
public class ConditionalSpawningContextSelector : SpawningContextSelector {
   public final var anticonditions: MutableList<SpawningCondition<*>> = (new ArrayList()) as java.util.List
   public final var conditions: MutableList<SpawningCondition<*>> = (new ArrayList()) as java.util.List

   public override fun selects(ctx: SpawningContext): Boolean {
      if (!this.conditions.isEmpty()) {
         val `$this$any$iv`: java.lang.Iterable = this.conditions;
         var var10000: Boolean;
         if (this.conditions is java.util.Collection && this.conditions.isEmpty()) {
            var10000 = true;
         } else {
            val var4: java.util.Iterator = `$this$any$iv`.iterator();

            while (true) {
               if (!var4.hasNext()) {
                  var10000 = true;
                  break;
               }

               if ((var4.next() as SpawningCondition).isSatisfiedBy(ctx)) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (var10000) {
            return false;
         }
      }

      if (!this.anticonditions.isEmpty()) {
         val var8: java.lang.Iterable = this.anticonditions;
         var var14: Boolean;
         if (this.anticonditions is java.util.Collection && this.anticonditions.isEmpty()) {
            var14 = false;
         } else {
            val var10: java.util.Iterator = var8.iterator();

            while (true) {
               if (!var10.hasNext()) {
                  var14 = false;
                  break;
               }

               if ((var10.next() as SpawningCondition).isSatisfiedBy(ctx)) {
                  var14 = true;
                  break;
               }
            }
         }

         if (var14) {
            return false;
         }
      }

      return true;
   }
}
