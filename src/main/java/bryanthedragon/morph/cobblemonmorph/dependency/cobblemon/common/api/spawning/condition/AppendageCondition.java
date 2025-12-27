package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import org.jetbrains.annotations.NotNull

public interface AppendageCondition {
   public abstract fun fits(ctx: SpawningContext): Boolean {
   }

   @SourceDebugExtension(["SMAP\nAppendageCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AppendageCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n766#2:60\n857#2,2:61\n1549#2:63\n1620#2,3:64\n*S KotlinDebug\n*F\n+ 1 AppendageCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$Companion\n*L\n56#1:60\n56#1:61,2\n56#1:63\n56#1:64,3\n*E\n"])
   public companion object {
      private final val appendages: MutableList<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AppendageCondition.RegisteredAppendageCondition> =
         (new ArrayList()) as java.util.List

      public fun registerAppendage(conditionClass: Class<out SpawningCondition<*>>, appendageClass: Class<out AppendageCondition>) {
         this.registerAppendage(appendageClass, (new Function1<Object, java.lang.Boolean>(conditionClass) {
            {
               super(1, receiver, Class::class.java, "isInstance", "isInstance(Ljava/lang/Object;)Z", 0);
            }

            @NotNull
            public final java.lang.Boolean invoke(Object p0) {
               return (this.receiver as Class).isInstance(p0);
            }
         }) as (SpawningCondition<?>?) -> java.lang.Boolean);
      }

      public fun registerAppendage(appendageClass: Class<out AppendageCondition>, spawningConditionFits: (SpawningCondition<*>) -> Boolean) {
         appendages.add(new AppendageCondition.RegisteredAppendageCondition(appendageClass, spawningConditionFits));
      }

      public fun getAppendages(spawningCondition: SpawningCondition<*>): List<Class<out AppendageCondition>> {
         var `$this$map$iv`: java.lang.Iterable = appendages;
         var `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if ((`item$iv$iv` as AppendageCondition.RegisteredAppendageCondition).getSpawningConditionFits().invoke(spawningCondition) as java.lang.Boolean) {
               `destination$iv$iv`.add(`item$iv$iv`);
            }
         }

         `$this$map$iv` = `destination$iv$iv` as java.util.List;
         `destination$iv$iv` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$iv` as java.util.List, 10));

         for (Object item$iv$iv : $this$filter$iv) {
            `destination$iv$iv`.add((var17 as AppendageCondition.RegisteredAppendageCondition).getClazz());
         }

         return `destination$iv$iv` as MutableList<Class<? extends AppendageCondition>>;
      }
   }

   private class RegisteredAppendageCondition(clazz: Class<out AppendageCondition>, spawningConditionFits: (SpawningCondition<*>) -> Boolean) {
      public final val clazz: Class<out AppendageCondition>
      public final val spawningConditionFits: (SpawningCondition<*>) -> Boolean

      init {
         this.clazz = clazz;
         this.spawningConditionFits = spawningConditionFits;
      }
   }
}
