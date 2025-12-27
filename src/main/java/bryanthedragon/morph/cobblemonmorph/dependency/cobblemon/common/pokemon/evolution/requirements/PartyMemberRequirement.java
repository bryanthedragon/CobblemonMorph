package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nPartyMemberRequirement.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyMemberRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/PartyMemberRequirement\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,36:1\n1747#2,3:37\n*S KotlinDebug\n*F\n+ 1 PartyMemberRequirement.kt\ncom/cobblemon/mod/common/pokemon/evolution/requirements/PartyMemberRequirement\n*L\n33#1:37,3\n*E\n"])
public class PartyMemberRequirement : EvolutionRequirement {
   public final val contains: Boolean = true
   public final val target: PokemonProperties = new PokemonProperties()

   public override fun check(pokemon: Pokemon): Boolean {
      val var10000: StoreCoordinates = pokemon.getStoreCoordinates().get();
      val `$this$any$iv`: PokemonStore = if (var10000 != null) var10000.getStore() else null;
      val var11: PartyStore = `$this$any$iv` as? PartyStore;
      if ((`$this$any$iv` as? PartyStore) == null) {
         return false;
      } else {
         val var10: java.lang.Iterable = var11;
         var var12: Boolean;
         if (var11 is java.util.Collection && (var11 as java.util.Collection).isEmpty()) {
            var12 = false;
         } else {
            val var6: java.util.Iterator = var10.iterator();

            while (true) {
               if (!var6.hasNext()) {
                  var12 = false;
                  break;
               }

               val member: Pokemon = var6.next() as Pokemon;
               if (!(member.getUuid() == pokemon.getUuid()) && this.target.matches(member)) {
                  var12 = true;
                  break;
               }
            }
         }

         return this.contains == var12;
      }
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
