package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.bridges;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public interface StructureProcessorListBridge {
   void append(StructureProcessor processor);

   void clear();
}
