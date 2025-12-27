package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net

import net.minecraft.network.FriendlyByteBuf

public interface Decodable {
   public abstract fun decode(buffer: FriendlyByteBuf) {
   }
}
