/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={StructureTemplatePool.class})
public interface StructurePoolAccessor {
    @Accessor(value="elementCounts")
    public List<Pair<StructurePoolElement, Integer>> getElementCounts();

    @Mutable
    @Accessor(value="elementCounts")
    public void setElementCounts(List<Pair<StructurePoolElement, Integer>> var1);

    @Accessor(value="elements")
    public ObjectArrayList<StructurePoolElement> getElements();
}

