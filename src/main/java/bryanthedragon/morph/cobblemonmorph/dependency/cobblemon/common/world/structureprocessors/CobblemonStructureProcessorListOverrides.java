/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006Rs\u0010\u000b\u001a^\u0012(\u0012&\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t \n*\u0012\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t\u0018\u00010\b0\b \n*.\u0012(\u0012&\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t \n*\u0012\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t\u0018\u00010\b0\b\u0018\u00010\u00070\u00078\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/world/structureprocessors/CobblemonStructureProcessorListOverrides;", "", "Lnet/minecraft/server/MinecraftServer;", "server", "", "register", "(Lnet/minecraft/server/MinecraftServer;)V", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorList;", "kotlin.jvm.PlatformType", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonStructureProcessorListOverrides {
    @NotNull
    public static final CobblemonStructureProcessorListOverrides INSTANCE = new CobblemonStructureProcessorListOverrides();
    private static final ResourceKey<Registry<StructureProcessorList>> registryKey = Registries.f_257011_;

    private CobblemonStructureProcessorListOverrides() {
    }

    public final ResourceKey<Registry<StructureProcessorList>> getRegistryKey() {
        return registryKey;
    }

    public final void register(@NotNull MinecraftServer server) {
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
    }
}

