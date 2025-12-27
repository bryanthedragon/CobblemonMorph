package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nAspectCriterion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AspectCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/AspectCriterionCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,45:1\n1855#2,2:46\n1855#2,2:48\n1726#2,3:50\n*S KotlinDebug\n*F\n+ 1 AspectCriterion.kt\ncom/cobblemon/mod/common/advancement/criterion/AspectCriterionCondition\n*L\n27#1:46,2\n34#1:48,2\n42#1:50,3\n*E\n"])
public class AspectCriterionCondition(id: ResourceLocation, predicate: ContextAwarePredicate) : SimpleCriterionCondition(id, predicate) {
   public final var aspects: MutableList<String> = (new ArrayList()) as java.util.List
   public final var pokemon: ResourceLocation = new ResourceLocation("cobblemon:pikachu")

   public override fun toJson(json: JsonObject) {
      val var2: JsonArray = new JsonArray(this.aspects.size());
      val it: JsonArray = var2;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         it.add(`element$iv` as java.lang.String);
      }

      json.add("aspects", var2 as JsonElement);
      json.addProperty("pokemon", this.pokemon.toString());
   }

   public override fun fromJson(json: JsonObject) {
      this.aspects.clear();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val element: JsonElement = `element$iv` as JsonElement;
         val var8: java.util.List = this.aspects;
         val var10001: java.lang.String = element.getAsString();
         var8.add(var10001);
      }

      val var9: java.lang.String = json.get("pokemon").getAsString();
      this.pokemon = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var9, null, 1, null);
   }

   public open fun matches(player: ServerPlayer, context: MutableMap<ResourceLocation, MutableSet<String>>): Boolean {
      val caughtAspects: java.util.Set = context.getOrDefault(this.pokemon, new LinkedHashSet());
      val `$this$all$iv`: java.lang.Iterable = this.aspects;
      var var10000: Boolean;
      if (this.aspects is java.util.Collection && this.aspects.isEmpty()) {
         var10000 = true;
      } else {
         val var6: java.util.Iterator = `$this$all$iv`.iterator();

         while (true) {
            if (!var6.hasNext()) {
               var10000 = true;
               break;
            }

            if (!caughtAspects.contains(var6.next() as java.lang.String)) {
               var10000 = false;
               break;
            }
         }
      }

      return var10000;
   }
}
