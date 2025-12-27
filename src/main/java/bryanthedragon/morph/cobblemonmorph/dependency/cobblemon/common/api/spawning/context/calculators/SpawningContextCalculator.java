package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.level.block.state.BlockState;

public interface SpawningContextCalculator<I extends SpawningContextInput, O extends SpawningContext> {
   public val name: String

   public abstract fun calculate(input: Any): Any? {
   }

   @SourceDebugExtension(["SMAP\nSpawningContextCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContextCalculator.kt\ncom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n800#2,11:69\n*S KotlinDebug\n*F\n+ 1 SpawningContextCalculator.kt\ncom/cobblemon/mod/common/api/spawning/context/calculators/SpawningContextCalculator$Companion\n*L\n40#1:69,11\n*E\n"])
   public companion object {
      private final val calculators: PrioritizedList<SpawningContextCalculator<*, *>> = new PrioritizedList()
      public final val isAirCondition: (BlockState) -> Boolean = <unrepresentable>.INSTANCE as Function1
      public final val isLavaCondition: (BlockState) -> Boolean = <unrepresentable>.INSTANCE as Function1
      public final val isSolidCondition: (BlockState) -> Boolean = <unrepresentable>.INSTANCE as Function1
      public final val isWaterCondition: (BlockState) -> Boolean = <unrepresentable>.INSTANCE as Function1

      public final val prioritizedAreaCalculators: List<AreaSpawningContextCalculator<*>>
         public final get() {
            val `$this$filterIsInstance$iv`: java.lang.Iterable = calculators;
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filterIsInstance$iv) {
               if (`element$iv$iv` is AreaSpawningContextCalculator) {
                  `destination$iv$iv`.add(`element$iv$iv`);
               }
            }

            return `destination$iv$iv` as MutableList<AreaSpawningContextCalculator<?>>;
         }


      public fun register(calculator: SpawningContextCalculator<*, *>, priority: Priority = Priority.NORMAL) {
         calculators.add(priority, calculator);
      }

      public fun unregister(calculator: SpawningContextCalculator<*, *>) {
         calculators.remove(calculator);
      }
   }
}
