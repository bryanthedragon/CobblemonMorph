package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome

public class BiomeTagCondition(tag: TagKey<Biome>) : RegistryLikeTagCondition(tag)
