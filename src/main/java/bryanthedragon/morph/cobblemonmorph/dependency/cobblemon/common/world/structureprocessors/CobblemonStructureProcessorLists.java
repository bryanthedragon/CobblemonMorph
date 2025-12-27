package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList

public object CobblemonStructureProcessorLists {
   public final val CROP_TO_BERRY: ResourceKey<StructureProcessorList> =
      ResourceKey.m_135785_(Registries.f_257011_, MiscUtilsKt.cobblemonResource("crop_to_berry"))
   }
