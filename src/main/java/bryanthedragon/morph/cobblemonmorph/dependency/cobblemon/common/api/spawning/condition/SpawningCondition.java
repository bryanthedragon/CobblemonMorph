package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.MoonPhaseRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt
import com.mojang.datafixers.util.Either
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.StructureManager
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.structure.Structure

@SourceDebugExtension(["SMAP\nSpawningCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,134:1\n2624#2,3:135\n1747#2,3:138\n2624#2,3:141\n*S KotlinDebug\n*F\n+ 1 SpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningCondition\n*L\n85#1:135,3\n99#1:138,3\n105#1:141,3\n*E\n"])
public abstract class SpawningCondition<T extends SpawningContext> {
   public final var appendages: MutableList<AppendageCondition> = (new ArrayList()) as java.util.List
   public final var biomes: MutableSet<RegistryLikeCondition<Biome>>?
   public final var canSeeSky: Boolean?
   public final var dimensions: MutableList<ResourceLocation>?
   public final var isRaining: Boolean?
   public final var isThundering: Boolean?
   public final var maxLight: Int?
   public final var maxSkyLight: Int?
   public final var maxX: Float?
   public final var maxY: Float?
   public final var maxZ: Float?
   public final var minLight: Int?
   public final var minSkyLight: Int?
   public final var minX: Float?
   public final var minY: Float?
   public final var minZ: Float?
   public final var moonPhase: MoonPhaseRange?
   public final var structures: MutableList<Either<ResourceLocation, TagKey<Structure>>>?
   public final var timeRange: TimeRange?

   public abstract fun contextClass(): Class<out Any> {
   }

   public fun contextMatches(ctx: SpawningContext): Boolean {
      return this.contextClass().isAssignableFrom(ctx.getClass());
   }

   public fun isSatisfiedBy(ctx: SpawningContext): Boolean {
      return this.contextMatches(ctx) && this.fits((T)ctx);
   }

   protected open fun fits(ctx: Any): Boolean {
      if (ctx.getPosition().m_123341_() < SimpleMathExtensionsKt.orMin(this.minX) || ctx.getPosition().m_123341_() > SimpleMathExtensionsKt.orMax(this.maxX)) {
         return false;
      } else if (ctx.getPosition().m_123342_() < SimpleMathExtensionsKt.orMin(this.minY)
         || ctx.getPosition().m_123342_() > SimpleMathExtensionsKt.orMax(this.maxY)) {
         return false;
      } else if (!(ctx.getPosition().m_123343_() < SimpleMathExtensionsKt.orMin(this.minZ))
         && !(ctx.getPosition().m_123343_() > SimpleMathExtensionsKt.orMax(this.maxZ))) {
         if (this.dimensions != null) {
            var var10000: java.util.List = this.dimensions;
            if (!var10000.isEmpty()) {
               var10000 = this.dimensions;
               if (!var10000.contains(ctx.getWorld().m_220362_().m_135782_())) {
                  return false;
               }
            }
         }

         if (this.moonPhase != null) {
            val var25: MoonPhaseRange = this.moonPhase;
            if (!var25.contains(ctx.getMoonPhase())) {
               return false;
            }
         }

         if (this.biomes != null) {
            var var26: java.util.Set = this.biomes;
            if (!var26.isEmpty()) {
               var26 = this.biomes;
               val `$this$any$iv`: java.lang.Iterable = var26;
               var var28: Boolean;
               if (var26 is java.util.Collection && (var26 as java.util.Collection).isEmpty()) {
                  var28 = true;
               } else {
                  val var4: java.util.Iterator = `$this$any$iv`.iterator();

                  while (true) {
                     if (!var4.hasNext()) {
                        var28 = true;
                        break;
                     }

                     if ((var4.next() as RegistryLikeCondition).fits(ctx.getBiome(), ctx.getBiomeRegistry())) {
                        var28 = false;
                        break;
                     }
                  }
               }

               if (var28) {
                  return false;
               }
            }
         }

         if (ctx.getLight() > SimpleMathExtensionsKt.orMax(this.maxLight) || ctx.getLight() < SimpleMathExtensionsKt.orMin(this.minLight)) {
            return false;
         } else if (ctx.getSkyLight() <= SimpleMathExtensionsKt.orMax(this.maxSkyLight) && ctx.getSkyLight() >= SimpleMathExtensionsKt.orMin(this.minSkyLight)) {
            if (this.timeRange != null) {
               val var29: TimeRange = this.timeRange;
               if (!var29.contains((int)(ctx.getWorld().m_46468_() % (long)24000))) {
                  return false;
               }
            }

            if (this.canSeeSky != null && !(this.canSeeSky == ctx.getCanSeeSky())) {
               return false;
            } else {
               if (this.isRaining != null) {
                  val var30: Boolean = ctx.getWorld().m_46471_();
                  val var10001: java.lang.Boolean = this.isRaining;
                  if (var30 != var10001) {
                     return false;
                  }
               }

               if (this.isThundering != null) {
                  val var31: Boolean = ctx.getWorld().m_46470_();
                  val var37: java.lang.Boolean = this.isThundering;
                  if (var31 != var37) {
                     return false;
                  }
               }

               val var13: java.lang.Iterable = this.appendages;
               var var32: Boolean;
               if (this.appendages is java.util.Collection && this.appendages.isEmpty()) {
                  var32 = false;
               } else {
                  label181: {
                     for (Object element$iv : $this$any$iv) {
                        if (!(var18 as AppendageCondition).fits(ctx)) {
                           var32 = true;
                           break label181;
                        }
                     }

                     var32 = false;
                  }
               }

               if (var32) {
                  return false;
               } else {
                  if (this.structures != null) {
                     val var33: java.util.List = this.structures;
                     if (!var33.isEmpty()) {
                        val var34: java.util.List = this.structures;
                        val var19: StructureManager = ctx.getWorld().m_215010_();
                        val var21: SpawningContext.StructureChunkCache = ctx.getStructureCache(ctx.getPosition());
                        val `$this$none$ivx`: java.lang.Iterable = var34;
                        if (var34 is java.util.Collection && (var34 as java.util.Collection).isEmpty()) {
                           var32 = true;
                        } else {
                           val var9: java.util.Iterator = `$this$none$ivx`.iterator();

                           while (true) {
                              if (!var9.hasNext()) {
                                 var32 = true;
                                 break;
                              }

                              val var35: Any = (var9.next() as Either)
                                 .map(SpawningCondition::fits$lambda$5$lambda$4$lambda$2, SpawningCondition::fits$lambda$5$lambda$4$lambda$3);
                              if (var35 as java.lang.Boolean) {
                                 var32 = false;
                                 break;
                              }
                           }
                        }

                        if (var32) {
                           return false;
                        }
                     }
                  }

                  return true;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public open fun copyFrom(other: SpawningCondition<*>, merger: Merger) {
      var var10001: java.util.Collection = merger.merge(this.dimensions, other.dimensions);
      this.dimensions = if (var10001 != null) CollectionsKt.toMutableList(var10001) else null;
      var10001 = merger.merge(this.biomes, other.biomes);
      this.biomes = if (var10001 != null) CollectionsKt.toMutableSet(var10001) else null;
      this.moonPhase = merger.mergeSingle(this.moonPhase, other.moonPhase);
      this.canSeeSky = merger.mergeSingle(this.canSeeSky, other.canSeeSky);
      this.minX = merger.mergeSingle(this.minX, other.minX);
      this.minY = merger.mergeSingle(this.minY, other.minY);
      this.minZ = merger.mergeSingle(this.minZ, other.minZ);
      this.maxX = merger.mergeSingle(this.maxX, other.maxX);
      this.maxY = merger.mergeSingle(this.maxY, other.maxY);
      this.maxZ = merger.mergeSingle(this.maxZ, other.maxZ);
      this.minLight = merger.mergeSingle(this.minLight, other.minLight);
      this.maxLight = merger.mergeSingle(this.maxLight, other.maxLight);
      this.minSkyLight = merger.mergeSingle(this.minSkyLight, other.minSkyLight);
      this.maxSkyLight = merger.mergeSingle(this.maxSkyLight, other.maxSkyLight);
      this.timeRange = merger.mergeSingle(this.timeRange, other.timeRange);
      var10001 = merger.merge(this.structures, other.structures);
      this.structures = if (var10001 != null) CollectionsKt.toMutableList(var10001) else null;
   }

   @JvmStatic
   fun `fits$lambda$5$lambda$4$lambda$2`(`$tmp0`: Function1, p0: Any): java.lang.Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   @JvmStatic
   fun `fits$lambda$5$lambda$4$lambda$3`(`$tmp0`: Function1, p0: Any): java.lang.Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   public companion object {
      public final val conditionTypes: MutableMap<String, Class<out SpawningCondition<*>>>

      public fun getByName(name: String): Class<out SpawningCondition<*>>? {
         return this.getConditionTypes().get(name);
      }

      public fun <T : SpawningContext, C : SpawningCondition<Any>> register(name: String, clazz: Class<Any>) {
         this.getConditionTypes().put(name, clazz);
      }
   }
}
