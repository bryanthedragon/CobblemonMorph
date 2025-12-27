package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

public class ItemTagCondition(tag: TagKey<Item>) : RegistryLikeTagCondition(tag)
