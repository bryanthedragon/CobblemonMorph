package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

public object CobblemonBlockTags {
   public final val ALL_HANGING_SIGNS: TagKey<Block> = INSTANCE.createTag("all_hanging_signs")
   public final val ALL_SIGNS: TagKey<Block> = INSTANCE.createTag("all_signs")
   public final val APRICORNS: TagKey<Block> = INSTANCE.createTag("apricorns")
   public final val APRICORN_LEAVES: TagKey<Block> = INSTANCE.createTag("apricorn_leaves")
   public final val APRICORN_LOGS: TagKey<Block> = INSTANCE.createTag("apricorn_logs")
   public final val APRICORN_SAPLINGS: TagKey<Block> = INSTANCE.createTag("apricorn_saplings")
   public final val BERRY_REPLACEABLE: TagKey<Block> = INSTANCE.createTag("berry_replaceable")
   public final val BERRY_SOIL: TagKey<Block> = INSTANCE.createTag("berry_soil")
   public final val BERRY_WILD_SOIL: TagKey<Block> = INSTANCE.createTag("berry_wild_soil")
   public final val CEILING_HANGING_SIGNS: TagKey<Block> = INSTANCE.createTag("ceiling_hanging_signs")
   public final val CROPS: TagKey<Block> = INSTANCE.createTag("crops")
   public final val DRIPSTONE_GROWABLE: TagKey<Block> = INSTANCE.createTag("dripstone_growable")
   public final val DRIPSTONE_REPLACEABLES: TagKey<Block> = INSTANCE.createTag("dripstone_replaceables")
   public final val FLOWERS: TagKey<Block> = INSTANCE.createTag("flowers")
   public final val MEDICINAL_LEEK_PLANTABLE: TagKey<Block> = INSTANCE.createTag("medicinal_leek_plantable")
   public final val MINTS: TagKey<Block> = INSTANCE.createTag("mints")
   public final val ROOTS: TagKey<Block> = INSTANCE.createTag("roots")
   public final val ROOTS_SPREADABLE: TagKey<Block> = INSTANCE.createTag("roots_spreadable")
   public final val SEES_SKY: TagKey<Block> = INSTANCE.createTag("sees_sky")
   public final val SIGNS: TagKey<Block> = INSTANCE.createTag("signs")
   public final val SMALL_FLOWERS: TagKey<Block> = INSTANCE.createTag("small_flowers")
   public final val SNOW_BLOCK: TagKey<Block> = INSTANCE.createTag("snow_block")
   public final val STANDING_SIGNS: TagKey<Block> = INSTANCE.createTag("standing_signs")
   public final val TUMBLESTONE_HEAT_SOURCE: TagKey<Block> = INSTANCE.createTag("tumblestone_heat_source")
   public final val WALL_HANGING_SIGNS: TagKey<Block> = INSTANCE.createTag("wall_hanging_signs")
   public final val WALL_SIGNS: TagKey<Block> = INSTANCE.createTag("wall_signs")

   private fun createTag(name: String): TagKey<Block> {
      return TagKey.m_203882_(Registries.f_256747_, MiscUtilsKt.cobblemonResource(name));
   }
}
