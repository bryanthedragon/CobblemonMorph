package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nPartyCheckCriterion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartyCheckCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/PartyCheckCriterion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Iterators.kt\nkotlin/collections/CollectionsKt__IteratorsKt\n*L\n1#1,54:1\n1855#2,2:55\n1855#2,2:57\n1855#2,2:59\n32#3,2:61\n*S KotlinDebug\n*F\n+ 1 PartyCheckCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/PartyCheckCriterion\n*L\n24#1:55,2\n30#1:57,2\n38#1:59,2\n45#1:61,2\n*E\n"])
public class PartyCheckCriterion(id: ResourceLocation, entity: ContextAwarePredicate) : SimpleCriterionCondition(id, entity) {
   public final val party: MutableList<ResourceLocation> = (new ArrayList()) as java.util.List

   public override fun toJson(json: JsonObject) {
      val var2: JsonArray = new JsonArray(this.party.size());
      val it: JsonArray = var2;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         it.add((`element$iv` as ResourceLocation).toString());
      }

      json.add("party", var2 as JsonElement);
   }

   public override fun fromJson(json: JsonObject) {
      this.party.clear();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val element: JsonElement = `element$iv` as JsonElement;
         val var8: java.util.List = this.party;
         val var10001: java.lang.String = element.getAsString();
         var8.add(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10001, null, 1, null));
      }
   }

   public open fun matches(player: ServerPlayer, context: PartyCheckContext): Boolean {
      val playerParty: PlayerPartyStore = PlayerExtensionsKt.party(player);
      val matches: java.util.List = new ArrayList();

      val partyCount: java.lang.Iterable;
      for (Object element$iv : partyCount) {
         val `element$ivx`: ResourceLocation = `element$iv` as ResourceLocation;
         if (`element$iv` as ResourceLocation == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default("any", null, 1, null)) {
            matches.add(`element$ivx`);
         }
      }

      val var12: Int = CollectionsKt.count(playerParty);
      if (matches.containsAll(this.party) && this.party.size() == var12 && matches.size() == var12) {
         return true;
      } else {
         val var15: java.util.Iterator = playerParty.iterator();

         while (var15.hasNext()) {
            val var17: Pokemon = var15.next() as Pokemon;
            if (this.party.contains(var17.getSpecies().getResourceIdentifier())) {
               matches.add(var17.getSpecies().getResourceIdentifier());
            }
         }

         return matches.containsAll(this.party) && matches.size() == var12;
      }
   }
}
