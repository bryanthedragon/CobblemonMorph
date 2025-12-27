package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBerryHelper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryHelper.kt\ncom/cobblemon/mod/common/api/berry/BerryHelper\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,48:1\n766#2:49\n857#2,2:50\n*S KotlinDebug\n*F\n+ 1 BerryHelper.kt\ncom/cobblemon/mod/common/api/berry/BerryHelper\n*L\n30#1:49\n30#1:50,2\n*E\n"])
public object BerryHelper {
   private final val CACHE_LOADER: <unrepresentable> = new CacheLoader<Holder<Biome>, java.util.List<? extends BerryBlock>>() {
      @NotNull
      public java.util.List<BerryBlock> load(@NotNull Holder<Biome> key) {
         val `$this$filter$iv`: java.lang.Iterable = BerryHelper.access$getNaturalBerries$p();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            var var18: Boolean;
            label34: {
               val berry: Berry = (`element$iv$iv` as BerryBlock).berry();
               if (berry != null) {
                  val var10000: java.util.List = berry.getSpawnConditions();
                  if (var10000 != null) {
                     val `$this$any$iv`: java.lang.Iterable = var10000;
                     if (var10000 is java.util.Collection && (var10000 as java.util.Collection).isEmpty()) {
                        var18 = false;
                        break label34;
                     }

                     for (Object element$iv : $this$any$iv) {
                        if ((`element$iv` as BerrySpawnCondition).canSpawn(berry, key)) {
                           var18 = true;
                           break label34;
                        }
                     }

                     var18 = false;
                     break label34;
                  }
               }

               var18 = false;
            }

            if (var18) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return `destination$iv$iv` as MutableList<BerryBlock>;
      }
   }

   private final val naturalBerries: List<BerryBlock>
   private final val validBerryCache: LoadingCache<Holder<Biome>, List<BerryBlock>>

   public fun getBerriesForBiome(biome: Holder<Biome>): List<BerryBlock> {
      val var10000: Any = validBerryCache.get(biome);
      return var10000 as MutableList<BerryBlock>;
   }

   public fun getNaturallyGeneratingBerries(): List<BerryBlock> {
      return naturalBerries;
   }

   @JvmStatic
   fun {
      val `$this$filter$iv`: java.lang.Iterable = CobblemonBlocks.INSTANCE.berries().values();
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         var var10: Int;
         label24: {
            val var10000: Berry = (`element$iv$iv` as BerryBlock).berry();
            if (var10000 != null) {
               val var9: java.util.List = var10000.getSpawnConditions();
               if (var9 != null) {
                  var10 = var9.size();
                  break label24;
               }
            }

            var10 = 0;
         }

         if (var10 > 0) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      naturalBerries = `destination$iv$iv` as MutableList<BerryBlock>;
      val var11: LoadingCache = CacheBuilder.newBuilder().maximumSize(4L).build(CACHE_LOADER);
      validBerryCache = var11;
   }
}
