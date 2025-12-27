package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository
import com.google.gson.Gson
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nVaryingRenderableResolver.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VaryingRenderableResolver.kt\ncom/cobblemon/mod/common/client/render/VaryingRenderableResolver\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,205:1\n533#2,4:206\n1726#2,3:210\n538#2:213\n1726#2,3:214\n766#2:217\n857#2,2:218\n1855#2,2:220\n*S KotlinDebug\n*F\n+ 1 VaryingRenderableResolver.kt\ncom/cobblemon/mod/common/client/render/VaryingRenderableResolver\n*L\n57#1:206,4\n57#1:210,3\n57#1:213\n64#1:214,3\n70#1:217\n70#1:218,2\n98#1:220,2\n*E\n"])
public class VaryingRenderableResolver<E extends Entity, M extends PoseableEntityModel<E>>(name: ResourceLocation, variations: MutableList<ModelAssetVariation>) {
   public final val models: MutableMap<ResourceLocation, Bone>
   public final val name: ResourceLocation
   public final val posers: MutableMap<Pair<ResourceLocation, ResourceLocation>, Any>
   public final lateinit var repository: VaryingModelRepository<Any, Any>
   public final val variations: MutableList<ModelAssetVariation>

   init {
      this.name = name;
      this.variations = variations;
      this.posers = new LinkedHashMap<>();
      this.models = new LinkedHashMap<>();
   }

   public fun getResolvedPoser(aspects: Set<String>): ResourceLocation {
      val var10000: ResourceLocation = this.getVariationValue(aspects, <unrepresentable>.INSTANCE);
      if (var10000 == null) {
         throw new IllegalStateException(
            "Unable to find a poser for ${this.name} with aspects ${CollectionsKt.joinToString$default(aspects, null, null, null, 0, null, null, 63, null)}. This shouldn't be possible if you've defined the fallback variation."
         );
      } else {
         return var10000;
      }
   }

   public fun getResolvedModel(aspects: Set<String>): ResourceLocation {
      val var10000: ResourceLocation = this.getVariationValue(aspects, <unrepresentable>.INSTANCE);
      if (var10000 == null) {
         throw new IllegalStateException(
            "Unable to find a model for ${this.name} with aspects ${CollectionsKt.joinToString$default(aspects, null, null, null, 0, null, null, 63, null)}. This shouldn't be possible if you've defined the fallback variation."
         );
      } else {
         return var10000;
      }
   }

   public fun getResolvedTexture(aspects: Set<String>, animationSeconds: Float): ResourceLocation {
      val var10000: ModelTextureSupplier = this.getVariationValue(aspects, <unrepresentable>.INSTANCE);
      if (var10000 != null) {
         val var3: ResourceLocation = var10000.invoke(animationSeconds);
         if (var3 != null) {
            return var3;
         }
      }

      throw new IllegalStateException(
         "Unable to find a texture for ${this.name} with aspects ${CollectionsKt.joinToString$default(aspects, null, null, null, 0, null, null, 63, null)}. This shouldn't be possible if you've defined the fallback variation."
      );
   }

   private fun <T> getVariationValue(aspects: Set<String>, selector: (ModelAssetVariation) -> Any?): Any? {
      val `iterator$iv`: java.util.ListIterator = this.variations.listIterator(this.variations.size());

      var var15: Any;
      while (true) {
         if (!`iterator$iv`.hasPrevious()) {
            var15 = null;
            break;
         }

         val `element$iv`: Any = `iterator$iv`.previous();
         val it: ModelAssetVariation = `element$iv` as ModelAssetVariation;
         val `$this$all$iv`: java.lang.Iterable = (`element$iv` as ModelAssetVariation).getAspects();
         var var10000: Boolean;
         if (`$this$all$iv` is java.util.Collection && (`$this$all$iv` as java.util.Collection).isEmpty()) {
            var10000 = true;
         } else {
            val var11: java.util.Iterator = `$this$all$iv`.iterator();

            while (true) {
               if (!var11.hasNext()) {
                  var10000 = true;
                  break;
               }

               if (!aspects.contains(var11.next() as java.lang.String)) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (var10000 && selector.invoke(it) != null) {
            var15 = `element$iv`;
            break;
         }
      }

      return (T)(if (var15 as ModelAssetVariation != null) selector.invoke(var15 as ModelAssetVariation) else null);
   }

   public fun getResolvedLayers(aspects: Set<String>): Iterable<ModelLayer> {
      val layerMaps: java.util.Map = new LinkedHashMap();

      for (ModelAssetVariation variation : this.variations) {
         val `$this$filterTo$iv$iv`: java.util.List = `$i$f$filter`.getLayers();
         if (`$this$filterTo$iv$iv` != null) {
            val `destination$iv$iv`: java.lang.Iterable = `$i$f$filter`.getAspects();
            var var10000: Boolean;
            if (`destination$iv$iv` is java.util.Collection && (`destination$iv$iv` as java.util.Collection).isEmpty()) {
               var10000 = true;
            } else {
               val var8: java.util.Iterator = `destination$iv$iv`.iterator();

               while (true) {
                  if (!var8.hasNext()) {
                     var10000 = true;
                     break;
                  }

                  if (!aspects.contains(var8.next() as java.lang.String)) {
                     var10000 = false;
                     break;
                  }
               }
            }

            if (var10000) {
               for (ModelLayer layer : layers) {
                  layerMaps.put(var16.getName(), var16);
               }
            }
         }
      }

      val var12: java.lang.Iterable = layerMaps.values();
      val var15: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if ((var19 as ModelLayer).getEnabled()) {
            var15.add(var19);
         }
      }

      return var15;
   }

   public fun getAllModels(): Set<ResourceLocation> {
      val models: java.util.Set = new LinkedHashSet();

      for (ModelAssetVariation variation : this.variations) {
         if (variation.getModel() != null) {
            models.add(variation.getModel());
         }
      }

      return models;
   }

   public fun initialize(repository: VaryingModelRepository<Any, Any>) {
      this.setRepository(repository);
      this.posers.clear();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val identifier: ResourceLocation = `element$iv` as ResourceLocation;

         try {
            val var10000: java.util.Map = this.models;
            val var10002: Any = repository.getTexturedModels().get(identifier);
            var10000.put(identifier, (var10002 as Function1).invoke(repository.isForLivingEntityRenderer()));
         } catch (var9: Exception) {
            throw new IllegalStateException("Unable to load model ${`element$iv` as ResourceLocation} for ${this.name}", var9);
         }
      }
   }

   public fun getPoser(aspects: Set<String>): Any {
      val poserName: ResourceLocation = this.getResolvedPoser(aspects);
      val var10000: Function1 = this.getRepository().getPosers().get(poserName);
      if (var10000 == null) {
         throw new IllegalStateException("No poser found for name: $poserName for ${this.name}");
      } else {
         val modelName: ResourceLocation = this.getResolvedModel(aspects);
         val existingEntityModel: PoseableEntityModel = this.posers.get(TuplesKt.to(poserName, modelName));
         val var8: PoseableEntityModel;
         if (existingEntityModel != null) {
            var8 = existingEntityModel;
         } else {
            val var9: Any = this.models.get(modelName);
            val entityModel: PoseableEntityModel = var10000.invoke(var9 as Bone) as PoseableEntityModel;
            entityModel.initializeLocatorAccess();
            entityModel.registerPoses();
            this.posers.put(TuplesKt.to(poserName, modelName), (M)entityModel);
            var8 = entityModel;
         }

         return (M)var8;
      }
   }

   public fun getTexture(aspects: Set<String>, animationSeconds: Float): ResourceLocation {
      if (this.getRepository().getPosers().get(this.getResolvedPoser(aspects)) as Function1 == null) {
         throw new IllegalStateException("No poser for ${this.name}");
      } else {
         return this.getResolvedTexture(aspects, animationSeconds);
      }
   }

   public fun getLayers(aspects: Set<String>): Iterable<ModelLayer> {
      if (this.getRepository().getPosers().get(this.getResolvedPoser(aspects)) as Function1 == null) {
         throw new IllegalStateException("No poser for ${this.name}");
      } else {
         return this.getResolvedLayers(aspects);
      }
   }

   public companion object {
      public final val GSON: Gson
   }
}
