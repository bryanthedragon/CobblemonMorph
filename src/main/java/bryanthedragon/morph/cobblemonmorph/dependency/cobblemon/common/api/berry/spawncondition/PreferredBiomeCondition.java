package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome

@SourceDebugExtension(["SMAP\nPreferredBiomeCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PreferredBiomeCondition.kt\ncom/cobblemon/mod/common/api/berry/spawncondition/PreferredBiomeCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1747#2,3:34\n*S KotlinDebug\n*F\n+ 1 PreferredBiomeCondition.kt\ncom/cobblemon/mod/common/api/berry/spawncondition/PreferredBiomeCondition\n*L\n26#1:34,3\n*E\n"])
public class PreferredBiomeCondition(minGroveSize: Int, maxGroveSize: Int) : BerrySpawnCondition {
   public final val maxGroveSize: Int
   public final val minGroveSize: Int

   init {
      this.minGroveSize = minGroveSize;
      this.maxGroveSize = maxGroveSize;
   }

   public override fun getGroveSize(random: RandomSource): Int {
      return random.m_216332_(this.minGroveSize, this.maxGroveSize);
   }

   public override fun canSpawn(berry: Berry, biome: Holder<Biome>): Boolean {
      val `$this$any$iv`: java.lang.Iterable = berry.getPreferredBiomeTags();
      var var10000: Boolean;
      if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         val var6: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var6.hasNext()) {
               var10000 = false;
               break;
            }

            if (biome.m_203656_(var6.next() as TagKey)) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   public companion object {
      public final val ID: ResourceLocation
   }
}
