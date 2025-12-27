package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.bridges.StructureProcessorListBridge;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StructureProcessorList.class)
public class StructureProcessorListMixin implements StructureProcessorListBridge {
   @Final
   @Mutable
   @Shadow
   private List<StructureProcessor> f_74422_;

   @Override
   public void append(StructureProcessor processor) {
      List<StructureProcessor> mutable = new ArrayList<>(this.f_74422_);
      mutable.add(processor);
      this.f_74422_ = ImmutableList.copyOf(mutable);
   }

   @Override
   public void clear() {
      this.f_74422_ = ImmutableList.of();
   }
}
