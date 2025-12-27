package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nUseMoveRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UseMoveRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/UseMoveRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n800#2,11:43\n1747#2,3:54\n*S KotlinDebug\n*F\n+ 1 UseMoveRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/UseMoveRequirement\n*L\n35#1:43,11\n36#1:54,3\n*E\n"])
public class UseMoveRequirement(move: MoveTemplate, amount: Int) : EvolutionRequirement {
   public final val amount: Int
   public final val move: MoveTemplate

   init {
      this.move = move;
      this.amount = amount;
   }

   public constructor() : this(Moves.INSTANCE.getByNameOrDummy(""), 1)
   public override fun check(pokemon: Pokemon): Boolean {
      var `$this$any$iv`: java.lang.Iterable = pokemon.getEvolutionProxy().current().progress();
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$any$iv) {
         if (`element$iv$iv` is UseMoveEvolutionProgress) {
            `element$iv`.add(`element$iv$iv`);
         }
      }

      `$this$any$iv` = `element$iv` as java.util.List;
      var var10000: Boolean;
      if (`element$iv` as java.util.List is java.util.Collection && ((`element$iv` as java.util.List) as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         val `$this$filterIsInstanceTo$iv$iv`: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!`$this$filterIsInstanceTo$iv$iv`.hasNext()) {
               var10000 = false;
               break;
            }

            val var12: UseMoveEvolutionProgress = `$this$filterIsInstanceTo$iv$iv`.next() as UseMoveEvolutionProgress;
            if (var12.currentProgress().getMove() == this.move && var12.currentProgress().getAmount() >= this.amount) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
