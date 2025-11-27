/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonStructureProcessorLists;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.RandomizedStructureMappedBlockStatePairProcessor;
import com.mojang.serialization.Codec;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\rJ3\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\b8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015RK\u0010\u0018\u001a6\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0017*\b\u0012\u0002\b\u0003\u0018\u00010\b0\b \u0017*\u001a\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003 \u0017*\b\u0012\u0002\b\u0003\u0018\u00010\b0\b\u0018\u00010\u00160\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/world/structureprocessors/CobblemonProcessorTypes;", "", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessor;", "T", "", "id", "Lcom/mojang/serialization/Codec;", "codec", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorType;", "register", "(Ljava/lang/String;Lcom/mojang/serialization/Codec;)Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorType;", "", "touch", "()V", "Lcom/cobblemon/mod/common/world/structureprocessors/RandomizedStructureMappedBlockStatePairProcessor;", "RANDOM_POOLED_STATES", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorType;", "Lcom/cobblemon/mod/common/world/structureprocessors/CobblemonStructureProcessorLists;", "lists", "Lcom/cobblemon/mod/common/world/structureprocessors/CobblemonStructureProcessorLists;", "getLists", "()Lcom/cobblemon/mod/common/world/structureprocessors/CobblemonStructureProcessorLists;", "Lnet/minecraft/core/Registry;", "kotlin.jvm.PlatformType", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "<init>", "common"})
public final class CobblemonProcessorTypes {
    @NotNull
    public static final CobblemonProcessorTypes INSTANCE = new CobblemonProcessorTypes();
    private static final Registry<StructureProcessorType<?>> registry = BuiltInRegistries.f_256897_;
    @NotNull
    private static final CobblemonStructureProcessorLists lists = CobblemonStructureProcessorLists.INSTANCE;
    @JvmField
    @NotNull
    public static final StructureProcessorType<RandomizedStructureMappedBlockStatePairProcessor> RANDOM_POOLED_STATES = INSTANCE.register("random_pooled_states", RandomizedStructureMappedBlockStatePairProcessor.Companion.getCODEC());

    private CobblemonProcessorTypes() {
    }

    public final Registry<StructureProcessorType<?>> getRegistry() {
        return registry;
    }

    @NotNull
    public final CobblemonStructureProcessorLists getLists() {
        return lists;
    }

    @NotNull
    public final <T extends StructureProcessor> StructureProcessorType<T> register(@NotNull String id, @NotNull Codec<T> codec2) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(codec2, (String)"codec");
        Object object = Registry.m_122965_(registry, (ResourceLocation)MiscUtils.cobblemonResource(id), () -> CobblemonProcessorTypes.register$lambda$0(codec2));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"register(registry, cobbl\u2026eProcessorType { codec })");
        return (StructureProcessorType)object;
    }

    public final void touch() {
    }

    private static final Codec register$lambda$0(Codec $codec) {
        Intrinsics.checkNotNullParameter((Object)$codec, (String)"$codec");
        return $codec;
    }
}

