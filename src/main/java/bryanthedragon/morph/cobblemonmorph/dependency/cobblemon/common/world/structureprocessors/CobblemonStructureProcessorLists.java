/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/world/structureprocessors/CobblemonStructureProcessorLists;", "", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorList;", "kotlin.jvm.PlatformType", "CROP_TO_BERRY", "Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonStructureProcessorLists {
    @NotNull
    public static final CobblemonStructureProcessorLists INSTANCE = new CobblemonStructureProcessorLists();
    @JvmField
    public static final ResourceKey<StructureProcessorList> CROP_TO_BERRY = ResourceKey.m_135785_((ResourceKey)Registries.f_257011_, (ResourceLocation)MiscUtils.cobblemonResource("crop_to_berry"));

    private CobblemonStructureProcessorLists() {
    }
}

