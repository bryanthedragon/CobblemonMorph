package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nInstructionSet.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet$getNextInstruction$1\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet$getMostRecentInstruction$1\n*L\n1#1,55:1\n42#1,6:73\n27#1,3:120\n800#2,11:56\n533#2,6:67\n800#2,11:79\n288#2:90\n289#2:92\n800#2,11:94\n288#2,2:105\n800#2,11:107\n288#2,2:118\n800#2,11:123\n533#2,4:134\n538#2:139\n1855#2,2:140\n42#3:91\n1#4:93\n27#5:138\n*S KotlinDebug\n*F\n+ 1 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet\n*L\n38#1:73,6\n50#1:120,3\n29#1:56,11\n29#1:67,6\n38#1:79,11\n38#1:90\n38#1:92\n47#1:94,11\n47#1:105,2\n47#1:107,11\n47#1:118,2\n50#1:123,11\n50#1:134,4\n50#1:139\n53#1:140,2\n38#1:91\n50#1:138\n*E\n"])
public class InstructionSet {
   public final val instructions: MutableList<InterpreterInstruction> = (new ArrayList()) as java.util.List

   public fun getSubsequentInstructions(instruction: InterpreterInstruction): List<InterpreterInstruction> {
      return CollectionsKt.toList(this.instructions.subList(this.instructions.indexOf(instruction) + 1, this.instructions.size()));
   }

   public fun getPreviousInstructions(instruction: InterpreterInstruction): List<InterpreterInstruction> {
      return CollectionsKt.toList(this.instructions.subList(0, this.instructions.indexOf(instruction)));
   }

   public fun findInstructionsCausedBy(causerInstruction: CauserInstruction): List<InterpreterInstruction> {
      val thisCauseIndex: Int = this.instructions.indexOf(causerInstruction);
      if (thisCauseIndex == this.instructions.size() - 1) {
         return CollectionsKt.emptyList();
      } else {
         val `comparedTo$iv`: InterpreterInstruction = causerInstruction as InterpreterInstruction;
         val `index$iv`: Int = this.getInstructions().indexOf(causerInstruction as InterpreterInstruction);
         var var10000: Any;
         if (CollectionsKt.last(this.getInstructions()) == `comparedTo$iv`) {
            var10000 = null;
         } else {
            val `$this$firstOrNull$iv$iv`: java.lang.Iterable = this.getInstructions().subList(`index$iv` + 1, this.getInstructions().size());
            val `element$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
               if (`element$iv$iv$iv` is CauserInstruction) {
                  `element$iv$iv`.add(`element$iv$iv$iv`);
               }
            }

            val `$this$filterIsInstanceTo$iv$iv$iv`: java.util.Iterator = (`element$iv$iv` as java.util.List).iterator();

            while (true) {
               if (!`$this$filterIsInstanceTo$iv$iv$iv`.hasNext()) {
                  var10000 = null;
                  break;
               }

               val var20: Any = `$this$filterIsInstanceTo$iv$iv$iv`.next();
               if (true) {
                  var10000 = var20;
                  break;
               }
            }
         }

         var10000 = if (var10000 as CauserInstruction != null) this.instructions.indexOf((var10000 as CauserInstruction) as InterpreterInstruction) else null;
         return this.instructions.subList(thisCauseIndex + 1, (int)(var10000 ?: this.instructions.size()));
      }
   }

   public fun getMostRecentCauser(comparedTo: InterpreterInstruction): CauserInstruction? {
      val `$this$lastOrNull$iv$iv`: java.lang.Iterable = this.getInstructions().subList(0, this.getInstructions().indexOf(comparedTo));
      val `element$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
         if (`element$iv$iv$iv` is CauserInstruction) {
            `element$iv$iv`.add(`element$iv$iv$iv`);
         }
      }

      val `iterator$iv$iv`: java.util.ListIterator = (`element$iv$iv` as java.util.List).listIterator((`element$iv$iv` as java.util.List).size());

      var var10000: Any;
      while (true) {
         if (`iterator$iv$iv`.hasPrevious()) {
            val var15: Any = `iterator$iv$iv`.previous();
            if (false) {
               continue;
            }

            var10000 = var15;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as CauserInstruction;
   }

   public fun execute(battle: PokemonBattle) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as InterpreterInstruction).invoke(battle);
      }
   }
}
