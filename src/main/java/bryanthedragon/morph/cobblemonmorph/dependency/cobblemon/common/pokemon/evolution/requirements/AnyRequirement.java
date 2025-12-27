package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nAnyRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AnyRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/AnyRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,24:1\n1747#2,3:25\n*S KotlinDebug\n*F\n+ 1 AnyRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/AnyRequirement\n*L\n20#1:25,3\n*E\n"])
public class AnyRequirement(possibilities: Collection<EvolutionRequirement>) : EvolutionRequirement {
   public final val possibilities: Collection<EvolutionRequirement>

   init {
      this.possibilities = possibilities;
   }

   public override fun check(pokemon: Pokemon): Boolean {
      val `$this$any$iv`: java.lang.Iterable = this.possibilities;
      var var10000: Boolean;
      if (this.possibilities is java.util.Collection && this.possibilities.isEmpty()) {
         var10000 = false;
      } else {
         val var4: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            if ((var4.next() as EvolutionRequirement).check(pokemon)) {
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
