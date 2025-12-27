package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net

import net.minecraft.network.FriendlyByteBuf

public interface Encodable {
   public abstract fun encode(buffer: FriendlyByteBuf) {
   }
}
