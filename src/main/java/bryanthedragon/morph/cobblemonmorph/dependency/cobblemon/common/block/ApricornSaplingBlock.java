package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.grower.ApricornTreeGrower
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.world.level.block.SaplingBlock

public class ApricornSaplingBlock(properties: Settings, apricorn: Apricorn) : SaplingBlock(new ApricornTreeGrower(apricorn), properties)
