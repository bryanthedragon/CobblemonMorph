package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.resources.ResourceLocation

public object CobblemonResources {
   public final val DEFAULT_LARGE: ResourceLocation = new ResourceLocation("uniform")
   public final val PHASE_BEAM: ResourceLocation = MiscUtilsKt.cobblemonResource("textures/phase_beam.png")
   public final val RED: ResourceLocation = MiscUtilsKt.cobblemonResource("textures/red.png")
   public final val WHITE: ResourceLocation = MiscUtilsKt.cobblemonResource("textures/white.png")
}
