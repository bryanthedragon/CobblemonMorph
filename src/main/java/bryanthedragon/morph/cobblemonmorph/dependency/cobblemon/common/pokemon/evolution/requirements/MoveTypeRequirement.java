package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nMoveTypeRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveTypeRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/MoveTypeRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,21:1\n1747#2,3:22\n*S KotlinDebug\n*F\n+ 1 MoveTypeRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/MoveTypeRequirement\n*L\n17#1:22,3\n*E\n"])
public class MoveTypeRequirement : EvolutionRequirement {
   public final val type: ElementalType = ElementalTypes.INSTANCE.getNORMAL()

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

            if ((var4.next() as Move).getType() == this.type) {
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
