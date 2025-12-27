package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.phys.AABB

@SourceDebugExtension(["SMAP\nBoxCollectionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BoxCollectionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/BoxCollectionAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,69:1\n1549#2:70\n1620#2,3:71\n*S KotlinDebug\n*F\n+ 1 BoxCollectionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/BoxCollectionAdapter\n*L\n66#1:70\n66#1:71,3\n*E\n"])
public object BoxCollectionAdapter : JsonDeserializer<java.util.Collection<? extends AABB>> {
   public final val boxesByName: MutableMap<String, Collection<AABB>> = (new LinkedHashMap()) as java.util.Map

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Collection<AABB> {
      if (json.isJsonPrimitive()) {
         val var14: java.util.Collection = boxesByName.get(json.getAsString());
         if (var14 == null) {
            throw new IllegalArgumentException("Unrecognized box collection name: ${json.getAsString()}");
         } else {
            return var14;
         }
      } else {
         val var10000: JsonArray = json.getAsJsonArray();
         val `$this$map$iv`: java.lang.Iterable = var10000 as java.lang.Iterable;
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10000 as java.lang.Iterable, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add(ctx.deserialize(`item$iv$iv` as JsonElement, AABB::class.java) as AABB);
         }

         return CollectionsKt.toList(`destination$iv$iv` as java.util.List);
      }
   }

   @JvmStatic
   fun {
      boxesByName.put("standard-sprout", BerryBlock.Companion.getSTANDARD_SPROUT());
      boxesByName.put("standard-mature", BerryBlock.Companion.getSTANDARD_MATURE());
      boxesByName.put("short-sprout", BerryBlock.Companion.getSHORT_SPROUT());
      boxesByName.put("short-mature", BerryBlock.Companion.getSHORT_MATURE());
      boxesByName.put("volcano-sprout", BerryBlock.Companion.getVOLCANO_SPROUT());
      boxesByName.put("volcano-mature", BerryBlock.Companion.getVOLCANO_MATURE());
      boxesByName.put("nest-sprout", BerryBlock.Companion.getNEST_SPROUT());
      boxesByName.put("nest-mature", BerryBlock.Companion.getNEST_MATURE());
      boxesByName.put("frill-sprout", BerryBlock.Companion.getFRILL_SPROUT());
      boxesByName.put("frill-mature", BerryBlock.Companion.getFRILL_MATURE());
      boxesByName.put("block-sprout", BerryBlock.Companion.getBLOCK_SPROUT());
      boxesByName.put("block-mature", BerryBlock.Companion.getBLOCK_MATURE());
      boxesByName.put("pyramid-sprout", BerryBlock.Companion.getPYRAMID_SPROUT());
      boxesByName.put("pyramid-mature", BerryBlock.Companion.getPYRAMID_MATURE());
      boxesByName.put("tail-sprout", BerryBlock.Companion.getTAIL_SPROUT());
      boxesByName.put("tail-mature", BerryBlock.Companion.getTAIL_MATURE());
      boxesByName.put("sword-sprout", BerryBlock.Companion.getSWORD_SPROUT());
      boxesByName.put("sword-mature", BerryBlock.Companion.getSWORD_MATURE());
      boxesByName.put("platform-sprout", BerryBlock.Companion.getPLATFORM_SPROUT());
      boxesByName.put("platform-mature", BerryBlock.Companion.getPLATFORM_MATURE());
      boxesByName.put("stand-sprout", BerryBlock.Companion.getSTAND_SPROUT());
      boxesByName.put("stand-mature", BerryBlock.Companion.getSTAND_MATURE());
      boxesByName.put("cone-sprout", BerryBlock.Companion.getCONE_SPROUT());
      boxesByName.put("cone-mature", BerryBlock.Companion.getCONE_MATURE());
      boxesByName.put("squat-sprout", BerryBlock.Companion.getSQUAT_SPROUT());
      boxesByName.put("squat-mature", BerryBlock.Companion.getSQUAT_MATURE());
      boxesByName.put("lantern-sprout", BerryBlock.Companion.getLANTERN_SPROUT());
      boxesByName.put("lantern-mature", BerryBlock.Companion.getLANTERN_MATURE());
      boxesByName.put("box-sprout", BerryBlock.Companion.getBOX_SPROUT());
      boxesByName.put("box-mature", BerryBlock.Companion.getBOX_MATURE());
      boxesByName.put("blossom-sprout", BerryBlock.Companion.getBLOSSOM_SPROUT());
      boxesByName.put("blossom-mature", BerryBlock.Companion.getBLOSSOM_MATURE());
      boxesByName.put("lilypad-sprout", BerryBlock.Companion.getLILYPAD_SPROUT());
      boxesByName.put("lilypad-mature", BerryBlock.Companion.getLILYPAD_MATURE());
      boxesByName.put("tall-sprout", BerryBlock.Companion.getTALL_SPROUT());
      boxesByName.put("tall-mature", BerryBlock.Companion.getTALL_MATURE());
   }
}
