package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors

import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList

public object CobblemonStructureProcessorListOverrides {
   public final val registryKey: ResourceKey<Registry<StructureProcessorList>> = Registries.f_257011_

   public fun register(server: MinecraftServer) {
   }
}
