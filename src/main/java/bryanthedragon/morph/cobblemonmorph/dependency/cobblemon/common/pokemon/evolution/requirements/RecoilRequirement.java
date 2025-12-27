package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nRecoilRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecoilRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/RecoilRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,42:1\n800#2,11:43\n1747#2,3:54\n*S KotlinDebug\n*F\n+ 1 RecoilRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/RecoilRequirement\n*L\n35#1:43,11\n36#1:54,3\n*E\n"])
public class RecoilRequirement(amount: Int) : EvolutionRequirement {
   public final val amount: Int

   init {
      this.amount = amount;
   }

   public constructor() : this(0)
   public override fun check(pokemon: Pokemon): Boolean {
      var `$this$any$iv`: java.lang.Iterable = pokemon.getEvolutionProxy().current().progress();
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$any$iv) {
         if (`element$iv$iv` is RecoilEvolutionProgress) {
            `element$iv`.add(`element$iv$iv`);
         }
      }

      `$this$any$iv` = `element$iv` as java.util.List;
      val var10000: Boolean;
      if (`element$iv` as java.util.List is java.util.Collection && ((`element$iv` as java.util.List) as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         for (Object element$ivx : $this$any$iv) {
            if ((`element$ivx` as RecoilEvolutionProgress).currentProgress().getRecoil() >= this.amount) {
               return true;
            }
         }

         var10000 = false;
      }

      return var10000;
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
