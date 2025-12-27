package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization

import net.minecraft.network.FriendlyByteBuf

public interface BufferSerializer {
   public abstract fun saveToBuffer(buffer: FriendlyByteBuf, toClient: Boolean) {
   }

   public abstract fun loadFromBuffer(buffer: FriendlyByteBuf) {
   }
}
