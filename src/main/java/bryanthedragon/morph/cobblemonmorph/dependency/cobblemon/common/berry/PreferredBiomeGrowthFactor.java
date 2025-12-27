package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

@SourceDebugExtension(["SMAP\nBiomeGrowthFactor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BiomeGrowthFactor.kt\ncom/cobblemon/mod/common/berry/PreferredBiomeGrowthFactor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,53:1\n1747#2,3:54\n*S KotlinDebug\n*F\n+ 1 BiomeGrowthFactor.kt\ncom/cobblemon/mod/common/berry/PreferredBiomeGrowthFactor\n*L\n37#1:54,3\n*E\n"])
public class PreferredBiomeGrowthFactor(bonusYield: IntRange) : GrowthFactor {
   public final val bonusYield: IntRange

   init {
      this.bonusYield = bonusYield;
   }

   public override fun validateArguments() {
      if (this.bonusYield.getFirst() < 0 || this.bonusYield.getLast() < 0) {
         throw new IllegalArgumentException("${ID} bonusYield must be a positive range");
      }
   }

   public override fun isValid(world: LevelReader, state: BlockState, pos: BlockPos): Boolean {
      var biome: Holder;
      var var14: java.util.List;
      label30: {
         biome = world.m_204166_(pos);
         val var10000: Block = state.m_60734_();
         val var13: Berry = (var10000 as BerryBlock).berry();
         if (var13 != null) {
            var14 = var13.getPreferredBiomeTags();
            if (var14 != null) {
               break label30;
            }
         }

         var14 = CollectionsKt.emptyList();
      }

      val `$this$any$iv`: java.lang.Iterable = var14;
      var var15: Boolean;
      if (var14 is java.util.Collection && (var14 as java.util.Collection).isEmpty()) {
         var15 = false;
      } else {
         val var9: java.util.Iterator = `$this$any$iv`.iterator();

         while (true) {
            if (!var9.hasNext()) {
               var15 = false;
               break;
            }

            if (biome.m_203656_(var9.next() as TagKey)) {
               var15 = true;
               break;
            }
         }
      }

      return var15;
   }

   public override fun yield(): Int {
      return RangesKt.random(this.bonusYield, Random.Default as Random);
   }

   public override fun minYield(): Int {
      return this.bonusYield.getFirst();
   }

   public override fun maxYield(): Int {
      return this.bonusYield.getLast();
   }

   public companion object {
      public final val ID: ResourceLocation
   }
}
