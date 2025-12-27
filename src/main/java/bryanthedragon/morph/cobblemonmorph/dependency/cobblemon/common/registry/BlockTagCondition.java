package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

public class BlockTagCondition(tag: TagKey<Block>) : RegistryLikeTagCondition(tag)
