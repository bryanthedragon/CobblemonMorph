package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import java.util.UUID
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation

public class PastureLink(linkId: UUID, pcId: UUID, dimension: ResourceLocation, pos: BlockPos, permissions: PasturePermissions) {
   public final val dimension: ResourceLocation
   public final val linkId: UUID
   public final val pcId: UUID
   public final val permissions: PasturePermissions
   public final val pos: BlockPos

   init {
      this.linkId = linkId;
      this.pcId = pcId;
      this.dimension = dimension;
      this.pos = pos;
      this.permissions = permissions;
   }

   public fun getPC(): PCStore {
      return Cobblemon.INSTANCE.getStorage().getPC(this.pcId);
   }
}
