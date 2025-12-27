package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import com.google.gson.Gson
import java.util.ArrayList;
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition

@SourceDebugExtension(["SMAP\nTexturedModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TexturedModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,345:1\n1603#2,9:346\n1855#2:355\n1856#2:361\n1612#2:362\n1864#2,3:363\n125#3:356\n152#3,3:357\n1#4:360\n*S KotlinDebug\n*F\n+ 1 TexturedModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/TexturedModel\n*L\n163#1:346,9\n163#1:355\n163#1:361\n163#1:362\n266#1:363,3\n165#1:356\n165#1:357,3\n163#1:360\n*E\n"])
public class TexturedModel {
   public final val formatVersion: String = "0"
   public final val geometry: List<ModelGeometry>?

   public fun create(isForLivingEntityRenderer: Boolean): LayerDefinition {
      return this.createWithUvOverride(isForLivingEntityRenderer, 0, 0, null, null);
   }

   public fun resolveParentsFromRoot(boneMap: MutableMap<String, ModelBone>, bone: ModelBone): Set<ModelBone> {
      val var10000: java.util.Set;
      if (bone.getParent() == null) {
         var10000 = SetsKt.emptySet();
      } else {
         val var4: ModelBone = boneMap.get(bone.getParent()) as ModelBone;
         if (var4 == null) {
            return SetsKt.emptySet();
         }

         var10000 = SetsKt.plus(this.resolveParentsFromRoot(boneMap, var4), bone);
      }

      return var10000;
   }

   public fun createWithUvOverride(isForLivingEntityRenderer: Boolean, u: Int, v: Int, textureWidth: Int?, textureHeight: Int?): LayerDefinition {
      val modelData: MeshDefinition = new MeshDefinition();
      val parts: HashMap = new HashMap();
      val bones: HashMap = new HashMap();

      try {
         var var10000: java.util.List = this.geometry;
         val e: ModelGeometry = var10000.get(0) as ModelGeometry;
         var10000 = e.getBones();
         val geometryBones: java.util.List = CollectionsKt.toMutableList(var10000);
         val var12: java.util.Collection = geometryBones;
         val bone: java.lang.Iterable = geometryBones;
         val modelPart: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            val bonex: ModelBone = `$this$forEachIndexed$iv` as ModelBone;
            val var71: java.util.Map = (`$this$forEachIndexed$iv` as ModelBone).getLocators();
            if (var71 == null) {
               var10000 = null;
            } else {
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(var71.size());

               for (Entry item$iv$iv : var71.entrySet()) {
                  val name: java.lang.String = `item$iv$iv`.getKey() as java.lang.String;
                  val locator: LocatorBone = `item$iv$iv`.getValue() as LocatorBone;
                  val locatorBone: ModelBone = new ModelBone();
                  locatorBone.setName("locator_$name");
                  locatorBone.setParent(bonex.getName());
                  locatorBone.setPivot(locator.getOffset());
                  locatorBone.setRotation(locator.getRotation());
                  `destination$iv$ivx`.add(locatorBone);
               }

               var10000 = `destination$iv$ivx` as java.util.List;
            }

            if (var10000 != null) {
               modelPart.add(var10000);
            }
         }

         val var73: java.util.Collection = CollectionsKt.flatten(modelPart as java.util.List);
         val var46: ModelBone = new ModelBone();
         var46.setName("locator_root");
         CollectionsKt.addAll(var12, CollectionsKt.plus(var73, var46));

         for (ModelBone bonexx : geometryBones) {
            bones.put(bonexx.getName(), bonexx);
            val var75: PartDefinition;
            if (bonexx.getParent() != null) {
               val var74: Any = parts.get(bonexx.getParent());
               var75 = var74 as PartDefinition;
            } else {
               var75 = modelData.m_171576_();
            }

            val var48: java.util.List = bonexx.getRotation();
            val var50: PartPose;
            if (bonexx.getParent() == null) {
               val var76: PartPose = PartPose.m_171419_(0.0F, if (isForLivingEntityRenderer) 24.0F else 0.0F, 0.0F);
               var50 = var76;
            } else if (var48 != null) {
               var var77: Any = bones.get(bonexx.getParent());
               val var78: Float = -((var77 as ModelBone).getPivot().get(0).floatValue() - bonexx.getPivot().get(0).floatValue());
               val var10001: Any = bones.get(bonexx.getParent());
               val var90: Float = (var10001 as ModelBone).getPivot().get(1).floatValue() - bonexx.getPivot().get(1).floatValue();
               val var10002: Any = bones.get(bonexx.getParent());
               var77 = PartPose.m_171423_(
                  var78,
                  var90,
                  -((var10002 as ModelBone).getPivot().get(2).floatValue() - bonexx.getPivot().get(2).floatValue()),
                  (float)Math.toRadians((double)(var48.get(0) as java.lang.Number).floatValue()),
                  (float)Math.toRadians((double)(var48.get(1) as java.lang.Number).floatValue()),
                  (float)Math.toRadians((double)(var48.get(2) as java.lang.Number).floatValue())
               );
               var50 = (PartPose)var77;
            } else {
               var var80: Any = bones.get(bonexx.getParent());
               val var81: Float = -((var80 as ModelBone).getPivot().get(0).floatValue() - bonexx.getPivot().get(0).floatValue());
               val var91: Any = bones.get(bonexx.getParent());
               val var92: Float = (var91 as ModelBone).getPivot().get(1).floatValue() - bonexx.getPivot().get(1).floatValue();
               val var98: Any = bones.get(bonexx.getParent());
               var80 = PartPose.m_171419_(var81, var92, -((var98 as ModelBone).getPivot().get(2).floatValue() - bonexx.getPivot().get(2).floatValue()));
               var50 = (PartPose)var80;
            }

            val var51: CubeListBuilder = CubeListBuilder.m_171558_();
            val var52: java.util.List = new ArrayList();
            val modelTransforms: java.util.List = new ArrayList();
            val var53: java.util.List = bonexx.getCubes();
            if (var53 != null) {
               for (Cube cube : boneCubes) {
                  val var83: CubeListBuilder;
                  if (var64.getRotation() != null) {
                     var83 = CubeListBuilder.m_171558_();
                  } else {
                     var83 = var51;
                  }

                  var10000 = var64.getPivot();
                  if (var10000 == null) {
                     var10000 = bonexx.getPivot();
                  }

                  if (var64.getUv() != null) {
                     var83.m_171514_(var64.getUv().get(0).intValue() + u, var64.getUv().get(1).intValue() + v);
                  }

                  var64.getMirror();
                  if (var64.getMirror()) {
                     var83.m_171480_();
                  }

                  if (var64.getSize() != null && var64.getOrigin() != null) {
                     val var93: Float = var64.getOrigin().get(0).floatValue() - (var10000.get(0) as java.lang.Number).floatValue();
                     val var99: Float = -(
                        var64.getOrigin().get(1).floatValue() - (var10000.get(1) as java.lang.Number).floatValue() + var64.getSize().get(1).floatValue()
                     );
                     val var10003: Float = var64.getOrigin().get(2).floatValue() - (var10000.get(2) as java.lang.Number).floatValue();
                     val var10004: Float = var64.getSize().get(0).floatValue();
                     val var10005: Float = var64.getSize().get(1).floatValue();
                     val var10006: Float = var64.getSize().get(2).floatValue();
                     val var10009: java.lang.Float = var64.getInflate();
                     var83.m_171488_(var93, var99, var10003, var10004, var10005, var10006, new CubeDeformation(var10009 ?: 0.0F));
                  }

                  var64.getMirror();
                  if (var64.getMirror()) {
                     var83.m_171555_(false);
                  }

                  if (!(var83 == var51)) {
                     var var94: Float = bonexx.getPivot().get(0).floatValue();
                     val var100: java.util.List = var64.getPivot();
                     var94 = -(var94 - (var100.get(0) as java.lang.Number).floatValue());
                     val var101: Float = bonexx.getPivot().get(1).floatValue() - var64.getPivot().get(1).floatValue();
                     val var102: Float = -(bonexx.getPivot().get(2).floatValue() - var64.getPivot().get(2).floatValue());
                     val var103: java.util.List = var64.getRotation();
                     val var96: PartPose = PartPose.m_171423_(
                        var94,
                        var101,
                        var102,
                        (float)Math.toRadians((double)(var103.get(0) as java.lang.Number).floatValue()),
                        (float)Math.toRadians((double)var64.getRotation().get(1).floatValue()),
                        (float)Math.toRadians((double)var64.getRotation().get(2).floatValue())
                     );
                     modelTransforms.add(var96);
                     var52.add(var83);
                  }
               }
            }

            val var56: java.util.Map = parts;
            val var60: java.lang.String = bonexx.getName();
            val var85: PartDefinition = var75.m_171599_(bonexx.getName(), var51, var50);
            var56.put(var60, var85);
            var var57: Int = 0;
            val var61: java.lang.Iterable = var52;
            var var65: Int = 0;

            for (Object item$iv : $this$forEachIndexed$iv) {
               val var68: Int = var65++;
               if (var68 < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               val part: CubeListBuilder = var67 as CubeListBuilder;
               val var87: Any = parts.get(bonexx.getName());
               (var87 as PartDefinition).m_171599_("${bonexx.getName()}${var57++}", part, modelTransforms.get(var68) as PartPose);
            }
         }

         val var89: LayerDefinition = LayerDefinition.m_171565_(
            modelData, textureWidth ?: e.getDescription().getTextureWidth(), textureHeight ?: e.getDescription().getTextureHeight()
         );
         return var89;
      } catch (var43: Exception) {
         if (this.geometry != null) {
            throw new IllegalArgumentException(
               "Error creating TexturedModelData with identifier ${this.geometry.get(0).getDescription().getIdentifier()}", var43
            );
         } else {
            throw new IllegalArgumentException("Error creating TexturedModelData", var43);
         }
      }
   }

   public companion object {
      public final val GSON: Gson

      public fun from(json: String): TexturedModel {
         try {
            val var10000: Any = this.getGSON().fromJson(json, TexturedModel.class);
            return var10000 as TexturedModel;
         } catch (var3: Exception) {
            throw new IllegalStateException("Issue loading pokemon geo: $json", var3);
         }
      }
   }
}
