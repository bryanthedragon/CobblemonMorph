package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelLayer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelVariationSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.VaryingRenderableResolver
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util.ClientDistributionUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.IdentifierExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Map.Entry
import java.util.function.BiFunction
import java.util.function.Function
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.Tuple
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nVaryingModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VaryingModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,188:1\n215#2,2:189\n215#2:191\n216#2:200\n215#2:210\n125#2:211\n152#2,3:212\n216#2:217\n19#3:192\n361#4,7:193\n1045#5:201\n1360#5:202\n1446#5,5:203\n1855#5,2:208\n1855#5,2:215\n*S KotlinDebug\n*F\n+ 1 VaryingModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/VaryingModelRepository\n*L\n62#1:189,2\n81#1:191\n81#1:200\n101#1:210\n103#1:211\n103#1:212,3\n101#1:217\n84#1:192\n85#1:193,7\n91#1:201\n91#1:202\n91#1:203,5\n95#1:208,2\n104#1:215,2\n*E\n"])
public abstract class VaryingModelRepository<E extends Entity, M extends PoseableEntityModel<E>> {
   public abstract val animationDirectories: List<String>
   public abstract val fallback: ResourceLocation
   public abstract val isForLivingEntityRenderer: Boolean
   public abstract val modelDirectories: List<String>
   public abstract val poserDirectories: List<String>
   public final val posers: MutableMap<ResourceLocation, (Bone) -> Any> = (new LinkedHashMap()) as java.util.Map
   public final val texturedModels: MutableMap<ResourceLocation, (Boolean) -> Bone> = (new LinkedHashMap()) as java.util.Map
   public abstract val title: String
   public abstract val type: String
   public abstract val variationDirectories: List<String>
   public final val variations: MutableMap<ResourceLocation, VaryingRenderableResolver<Any, Any>> = (new LinkedHashMap()) as java.util.Map

   public abstract fun loadJsonPoser(json: String): (Bone) -> Any {
   }

   public fun registerPosers(resourceManager: ResourceManager) {
      this.posers.clear();
      this.registerInBuiltPosers();
      this.registerJsonPosers(resourceManager);
   }

   public abstract fun registerInBuiltPosers() {
   }

   public open fun registerJsonPosers(resourceManager: ResourceManager) {
      label57: {
         for (java.lang.String directory : this.getPoserDirectories()) {
            val var10000: java.util.Map = resourceManager.m_214159_(directory, VaryingModelRepository::registerJsonPosers$lambda$0);

            for (Entry element$iv : var10000.entrySet()) {
               val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
               val var12: Closeable = (`element$iv`.getValue() as Resource).m_215507_();
               var var13: java.lang.Throwable = null;

               try {
                  try {
                     val var25: ByteArray = (var12 as InputStream).readAllBytes();
                     val var26: Charset = StandardCharsets.UTF_8;
                     this.posers
                        .put(
                           new ResourceLocation(identifier.m_135827_(), FilesKt.getNameWithoutExtension(new File(identifier.m_135815_()))),
                           this.loadJsonPoser(new java.lang.String(var25, var26))
                        );
                  } catch (var19: java.lang.Throwable) {
                     var13 = var19;
                     throw var19;
                  }
               } catch (var20: java.lang.Throwable) {
                  CloseableKt.closeFinally(var12, var13);
               }

               CloseableKt.closeFinally(var12, null);
            }
         }
      }
   }

   public fun inbuilt(name: String, model: (ModelPart) -> Any) {
      this.posers.put(MiscUtilsKt.cobblemonResource(name), new Function1<Bone, M>(model) {
         {
            super(1);
            this.$model = `$model`;
         }

         @NotNull
         public final M invoke(@NotNull Bone bone) {
            return (M)this.$model.invoke(bone as ModelPart);
         }
      });
   }

   public fun registerVariations(resourceManager: ResourceManager) {
      label110: {
         val nameToModelVariationSets: java.util.Map = new LinkedHashMap();

         for (java.lang.String directory : this.getVariationDirectories()) {
            var var10000: java.util.Map = resourceManager.m_214159_(`$i$f$forEach`, VaryingModelRepository::registerVariations$lambda$3);

            for (Entry element$iv : var10000.entrySet()) {
               val `$i$f$flatMapTo`: Closeable = (var8.getValue() as Resource).m_215507_();
               var var13: java.lang.Throwable = null;

               try {
                  try {
                     val var53: ByteArray = (`$i$f$flatMapTo` as InputStream).readAllBytes();
                     val var54: Charset = StandardCharsets.UTF_8;
                     val json: java.lang.String = new java.lang.String(var53, var54);
                     val var55: Gson = VaryingRenderableResolver.Companion.getGSON();
                     val var48: ModelVariationSet = var55.fromJson(json, ModelVariationSet.class) as ModelVariationSet;
                     val var51: Any = var48.getName();
                     val `value$iv`: Any = nameToModelVariationSets.get(var51);
                     if (`value$iv` == null) {
                        val var52: Any = new ArrayList();
                        nameToModelVariationSets.put(var51, var52);
                        var10000 = (java.util.Map)var52;
                     } else {
                        var10000 = (java.util.Map)`value$iv`;
                     }

                     val var57: java.util.List = var10000 as java.util.List;
                     var57.add(var48);
                  } catch (var23: java.lang.Throwable) {
                     var13 = var23;
                     throw var23;
                  }
               } catch (var24: java.lang.Throwable) {
                  CloseableKt.closeFinally(`$i$f$flatMapTo`, var13);
               }

               CloseableKt.closeFinally(`$i$f$flatMapTo`, null);
            }
         }

         for (Entry var29 : nameToModelVariationSets.entrySet()) {
            val var31: ResourceLocation = var29.getKey() as ResourceLocation;
            val var38: java.lang.Iterable = CollectionsKt.sortedWith(
               var29.getValue() as java.util.List, new VaryingModelRepository$registerVariations$$inlined$sortedBy$1()
            );
            val var41: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : var38) {
               CollectionsKt.addAll(var41, (var45 as ModelVariationSet).getVariations());
            }

            this.variations.put(var31, new VaryingRenderableResolver<>(var31, CollectionsKt.toMutableList(var41 as java.util.List)));
         }

         val var28: java.lang.Iterable;
         for (Object element$iv : var28) {
            (var34 as VaryingRenderableResolver).initialize(this);
         }
      }
   }

   public fun registerModels(resourceManager: ResourceManager) {
      var models: Int = 0;

      for (java.lang.String directory : this.getModelDirectories()) {
         for (Entry element$iv : MODEL_FACTORIES.entrySet()) {
            val key: java.lang.String = `element$iv`.getKey() as java.lang.String;
            val func: BiFunction = `element$iv`.getValue() as BiFunction;
            val var10000: java.util.Map = resourceManager.m_214159_(directory, VaryingModelRepository::registerModels$lambda$13$lambda$10);
            val `element$ivx`: java.util.Collection = new ArrayList(var10000.size());

            for (Entry item$iv$iv : var10000.entrySet()) {
               `element$ivx`.add(func.apply(`item$iv$iv`.getKey(), `item$iv$iv`.getValue()) as Tuple);
            }

            val var23: java.lang.Iterable;
            for (Object element$ivxx : var23) {
               val var26: Tuple = `element$ivxx` as Tuple;
               val var28: java.util.Map = this.texturedModels;
               val var30: Any = var26.m_14418_();
               var28.put(var30, new Function1<java.lang.Boolean, Bone>(var26) {
                  {
                     super(1);
                     this.$it = `$it`;
                  }

                  @NotNull
                  public final Bone invoke(boolean isForLivingEntityRenderer) {
                     val var10000: Any = (this.$it.m_14419_() as Function).apply(isForLivingEntityRenderer);
                     return var10000 as Bone;
                  }
               });
               models++;
            }
         }
      }

      Cobblemon.INSTANCE.getLOGGER().info("Loaded $models ${this.getTitle()} models.");
   }

   public fun reload(resourceManager: ResourceManager) {
      this.variations.clear();
      this.posers.clear();
      Cobblemon.INSTANCE.getLOGGER().info("Loading ${this.getTitle()} models...");
      this.registerModels(resourceManager);
      this.registerPosers(resourceManager);
      this.registerVariations(resourceManager);
   }

   public fun getPoser(name: ResourceLocation, aspects: Set<String>): Any {
      try {
         val var10000: VaryingRenderableResolver = this.variations.get(name);
         val poser: PoseableEntityModel = if (var10000 != null) var10000.getPoser(aspects) else null;
         if (poser != null) {
            return (M)poser;
         }
      } catch (var4: IllegalStateException) {
      }

      val var5: Any = this.variations.get(this.getFallback());
      return (M)(var5 as VaryingRenderableResolver).getPoser(aspects);
   }

   public fun getTexture(name: ResourceLocation, aspects: Set<String>, animationSeconds: Float = 0.0F): ResourceLocation {
      try {
         val var10000: VaryingRenderableResolver = this.variations.get(name);
         val texture: ResourceLocation = if (var10000 != null) var10000.getTexture(aspects, animationSeconds) else null;
         if (texture != null && ClientDistributionUtilsKt.exists(texture)) {
            return texture;
         }
      } catch (var5: IllegalStateException) {
      }

      val var6: Any = this.variations.get(this.getFallback());
      return (var6 as VaryingRenderableResolver).getTexture(aspects, animationSeconds);
   }

   public fun getTextureNoSubstitute(name: ResourceLocation, aspects: Set<String>, animationSeconds: Float = 0.0F): ResourceLocation? {
      try {
         val var10000: VaryingRenderableResolver = this.variations.get(name);
         val texture: ResourceLocation = if (var10000 != null) var10000.getTexture(aspects, animationSeconds) else null;
         if (texture != null && ClientDistributionUtilsKt.exists(texture)) {
            return texture;
         }
      } catch (var5: IllegalStateException) {
      }

      return null;
   }

   public fun getLayers(name: ResourceLocation, aspects: Set<String>): Iterable<ModelLayer> {
      try {
         val var10000: VaryingRenderableResolver = this.variations.get(name);
         val layers: java.lang.Iterable = if (var10000 != null) var10000.getLayers(aspects) else null;
         if (layers != null) {
            return layers;
         }
      } catch (var4: IllegalStateException) {
      }

      val var5: Any = this.variations.get(this.getFallback());
      return (var5 as VaryingRenderableResolver).getLayers(aspects);
   }

   @JvmStatic
   fun `registerJsonPosers$lambda$0`(path: ResourceLocation): Boolean {
      return IdentifierExtensionsKt.endsWith(path, ".json");
   }

   @JvmStatic
   fun `registerVariations$lambda$3`(path: ResourceLocation): Boolean {
      return IdentifierExtensionsKt.endsWith(path, ".json");
   }

   @JvmStatic
   fun `registerModels$lambda$13$lambda$10`(`$key`: java.lang.String, path: ResourceLocation): Boolean {
      return IdentifierExtensionsKt.endsWith(path, `$key`);
   }

   @JvmStatic
   fun `MODEL_FACTORIES$lambda$17$lambda$16$lambda$15$lambda$14`(`$texturedModel`: TexturedModel, it: java.lang.Boolean): Bone {
      return `$texturedModel`.create(it).m_171564_() as Bone;
   }

   @JvmStatic
   fun `MODEL_FACTORIES$lambda$17$lambda$16`(identifier: ResourceLocation, resource: Resource): Tuple {
      label19: {
         val var2: Closeable = resource.m_215507_();
         var var3: java.lang.Throwable = null;

         try {
            try {
               val var10000: ByteArray = (var2 as InputStream).readAllBytes();
               val var16: Charset = StandardCharsets.UTF_8;
               new Tuple(
                  new ResourceLocation(identifier.m_135827_(), FilesKt.getNameWithoutExtension(new File(identifier.m_135815_()))),
                  VaryingModelRepository::MODEL_FACTORIES$lambda$17$lambda$16$lambda$15$lambda$14
               );
            } catch (var10: java.lang.Throwable) {
               var3 = var10;
               throw var10;
            }
         } catch (var11: java.lang.Throwable) {
            CloseableKt.closeFinally(var2, var3);
         }

         CloseableKt.closeFinally(var2, null);
      }
   }

   @JvmStatic
   fun {
      val var0: java.util.Map = new LinkedHashMap();
      var0.put(".geo.json", VaryingModelRepository::MODEL_FACTORIES$lambda$17$lambda$16);
      MODEL_FACTORIES = var0;
   }

   public companion object {
      private final var MODEL_FACTORIES: MutableMap<String, BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>>

      public fun registerFactory(id: String, factory: BiFunction<ResourceLocation, Resource, Tuple<ResourceLocation, Function<Boolean, Bone>>>) {
         VaryingModelRepository.access$getMODEL_FACTORIES$cp().put(id, factory);
      }
   }
}
