/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 */
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

@Mixin(value={StructureProcessorList.class})
public class StructureProcessorListMixin
implements StructureProcessorListBridge {
    @Final
    @Mutable
    @Shadow
    private List<StructureProcessor> f_74422_;

    @Override
    public void append(StructureProcessor processor) {
        ArrayList<StructureProcessor> mutable = new ArrayList<StructureProcessor>(this.f_74422_);
        mutable.add(processor);
        this.f_74422_ = ImmutableList.copyOf(mutable);
    }

    @Override
    public void clear() {
        this.f_74422_ = ImmutableList.of();
    }
}

