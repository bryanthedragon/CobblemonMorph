package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureTemplatePool.class)
public interface StructurePoolAccessor {
   @Accessor("elementCounts")
   List<Pair<StructurePoolElement, Integer>> getElementCounts();

   @Mutable
   @Accessor("elementCounts")
   void setElementCounts(List<Pair<StructurePoolElement, Integer>> elementCounts);

   @Accessor("elements")
   ObjectArrayList<StructurePoolElement> getElements();
}
