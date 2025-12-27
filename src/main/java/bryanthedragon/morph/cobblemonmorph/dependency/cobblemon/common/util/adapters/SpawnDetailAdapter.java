package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.RegisteredSpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.GsonExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSpawnDetailAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetailAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnDetailAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,85:1\n1549#2:86\n1620#2,3:87\n1603#2,9:90\n1855#2:99\n1856#2:101\n1612#2:102\n1855#2,2:104\n1855#2,2:106\n1#3:100\n1#3:103\n*S KotlinDebug\n*F\n+ 1 SpawnDetailAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnDetailAdapter\n*L\n38#1:86\n38#1:87,3\n39#1:90,9\n39#1:99\n39#1:101\n39#1:102\n59#1:104,2\n78#1:106,2\n39#1:100\n*E\n"])
public object SpawnDetailAdapter : JsonDeserializer<SpawnDetail> {
   public open fun deserialize(element: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawnDetail {
      var var55: java.util.Set;
      label124: {
         GsonExtensionsKt.singularToPluralList$default(element as JsonObject, "preset", null, 2, null);
         val var10000: JsonElement = (element as JsonObject).get("presets");
         if (var10000 != null) {
            val var54: JsonArray = var10000.getAsJsonArray();
            if (var54 != null) {
               val registeredSpawnDetail: java.lang.Iterable = var54 as java.lang.Iterable;
               val detail: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var54 as java.lang.Iterable, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  detail.add((var14 as JsonElement).getAsString());
               }

               var55 = CollectionsKt.toMutableSet(detail as java.util.List);
               if (var55 != null) {
                  break label124;
               }
            }
         }

         var55 = new LinkedHashSet();
      }

      val firstType: java.lang.Iterable = var55;
      val var29: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
         val var17: java.lang.String = var47 as java.lang.String;
         val var56: java.util.Map = SpawnDetailPresets.INSTANCE.getPresets();
         val preset: SpawnDetailPreset = var56.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var17, null, 1, null)) as SpawnDetailPreset;
         if (preset == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Unknown preset name: $var17.");
         }

         if (preset != null) {
            var29.add(preset);
         }
      }

      val presets: java.util.List = var29 as java.util.List;
      val var26: java.util.Iterator = (var29 as java.util.List).iterator();

      while (true) {
         if (var26.hasNext()) {
            val var31: java.lang.String = (var26.next() as SpawnDetailPreset).getSpawnDetailType();
            if (var31 == null) {
               continue;
            }

            var57 = var31;
            break;
         }

         var57 = null;
         break;
      }

      GsonExtensionsKt.singularToPluralList$default(element as JsonObject, "condition", null, 2, null);
      GsonExtensionsKt.singularToPluralList$default(element as JsonObject, "anticondition", null, 2, null);
      GsonExtensionsKt.singularToPluralList$default(element as JsonObject, "weightMultiplier", null, 2, null);
      if ((element as JsonObject).has("weightMultipliers")) {
         val var24: java.lang.Iterable;
         for (Object element$iv : var24) {
            val var37: JsonElement = var35 as JsonElement;
            GsonExtensionsKt.singularToPluralList$default(var37 as JsonObject, "condition", null, 2, null);
            GsonExtensionsKt.singularToPluralList$default(var37 as JsonObject, "anticondition", null, 2, null);
         }
      }

      var var59: java.lang.String = var57;
      if (var57 == null) {
         val var60: JsonElement = (element as JsonObject).get("type");
         var59 = if (var60 != null) var60.getAsString() else null;
         if (var59 == null) {
            throw new IllegalStateException("Spawn detail type name not mentioned in either presets or in spawn detail.");
         }
      }

      val var61: RegisteredSpawnDetail = SpawnDetail.Companion.getSpawnDetailTypes().get(var59);
      if (var61 == null) {
         throw new IllegalStateException("Unrecognized spawn detail type name: $var59.");
      } else {
         val var41: java.util.Iterator = presets.iterator();

         while (true) {
            if (var41.hasNext()) {
               val var63: RegisteredSpawningContext = (var41.next() as SpawnDetailPreset).getContext();
               val var45: java.lang.String = if (var63 != null) var63.getName() else null;
               if (var45 == null) {
                  continue;
               }

               var62 = var45;
               break;
            }

            var62 = null;
            break;
         }

         if (var62 == null) {
            var62 = (element as JsonObject).get("context").getAsString();
         }

         val var64: SpawningContext.Companion = SpawningContext.Companion;
         val var65: RegisteredSpawningContext = var64.getByName(var62);
         if (var65 == null) {
            throw new IllegalStateException("Unrecognized context name: $var62");
         } else {
            val var66: SpawnLoader = SpawnLoader.INSTANCE;
            val var10001: Class = SpawningCondition.Companion.getByName(var65.getDefaultCondition());
            if (var10001 == null) {
               throw new IllegalStateException("There is no spawning condition registered with the name '${var65.getDefaultCondition()}'");
            } else {
               var66.setDeserializingConditionClass(var10001);
               val var38: SpawnDetail = var61.deserializeDetail(element, ctx);

               for (Object element$iv : var42) {
                  (var50 as SpawnDetailPreset).apply(var38);
               }

               if (StringsKt.isBlank(var38.getBucket().getName())) {
                  throw new IllegalStateException("No bucket was specified for spawn: ${var38.getId()}");
               } else {
                  var38.autoLabel();
                  return var38;
               }
            }
         }
      }
   }
}
