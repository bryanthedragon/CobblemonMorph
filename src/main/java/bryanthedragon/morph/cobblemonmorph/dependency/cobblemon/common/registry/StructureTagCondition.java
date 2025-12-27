package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition
import net.minecraft.tags.TagKey
import net.minecraft.world.level.levelgen.structure.Structure

public class StructureTagCondition(tag: TagKey<Structure>) : RegistryLikeTagCondition(tag)
