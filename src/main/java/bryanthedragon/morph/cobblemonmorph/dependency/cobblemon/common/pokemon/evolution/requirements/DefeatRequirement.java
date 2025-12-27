package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DefeatEvolutionProgress
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nDefeatRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefeatRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/DefeatRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n800#2,11:42\n1747#2,3:53\n*S KotlinDebug\n*F\n+ 1 DefeatRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/DefeatRequirement\n*L\n34#1:42,11\n35#1:53,3\n*E\n"])
public class DefeatRequirement(target: PokemonProperties, amount: Int) : EvolutionRequirement {
   public final val amount: Int
   public final val target: PokemonProperties

   init {
      this.target = target;
      this.amount = amount;
   }

   public constructor() : this(new PokemonProperties(), 0)
   public override fun check(pokemon: Pokemon): Boolean {
      var `$this$any$iv`: java.lang.Iterable = pokemon.getEvolutionProxy().current().progress();
      val `element$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$any$iv) {
         if (`element$iv$iv` is DefeatEvolutionProgress) {
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

            val var12: DefeatEvolutionProgress = `$this$filterIsInstanceTo$iv$iv`.next() as DefeatEvolutionProgress;
            if (StringsKt.equals(var12.currentProgress().getTarget().getOriginalString(), this.target.getOriginalString(), true)
               && var12.currentProgress().getAmount() >= this.amount) {
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
