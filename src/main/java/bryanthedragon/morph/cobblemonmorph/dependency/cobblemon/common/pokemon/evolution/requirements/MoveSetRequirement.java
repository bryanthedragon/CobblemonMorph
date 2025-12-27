package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nMoveSetRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSetRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/MoveSetRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,29:1\n1747#2,3:30\n*S KotlinDebug\n*F\n+ 1 MoveSetRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/MoveSetRequirement\n*L\n25#1:30,3\n*E\n"])
public class MoveSetRequirement : EvolutionRequirement {
   public final val move: MoveTemplate = Moves.INSTANCE.getByNameOrDummy("tackle")

   public override fun check(pokemon: Pokemon): Boolean {
      val `$this$any$iv`: java.lang.Iterable = pokemon.getMoveSet().getMoves();
      var var10000: Boolean;
      if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         val var4: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            if (StringsKt.equals((var4.next() as Move).getName(), this.move.getName(), true)) {
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
