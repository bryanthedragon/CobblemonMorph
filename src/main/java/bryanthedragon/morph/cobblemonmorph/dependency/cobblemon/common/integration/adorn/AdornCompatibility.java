package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.integration.adorn

import juuxel.adorn.block.variant.BlockVariant
import juuxel.adorn.block.variant.BlockVariantSet
import juuxel.adorn.block.variant.BlockVariant.Wood
import org.jetbrains.annotations.ApiStatus.Internal

@Internal
public object AdornCompatibility : BlockVariantSet {
   public open val woodVariants: List<BlockVariant> = CollectionsKt.listOf(new Wood("cobblemon/apricorn"))
}
