package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec

import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import net.minecraft.network.FriendlyByteBuf

public interface CodecMapped {
   public abstract fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
   }

   public abstract fun readFromBuffer(buffer: FriendlyByteBuf) {
   }

   public abstract fun writeToBuffer(buffer: FriendlyByteBuf) {
   }
}
