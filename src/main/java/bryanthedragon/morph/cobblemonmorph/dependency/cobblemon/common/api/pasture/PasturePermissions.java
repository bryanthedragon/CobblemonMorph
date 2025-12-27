package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public class PasturePermissions(canUnpastureOthers: Boolean, canPasture: Boolean, maxPokemon: Int) {
   public final val canPasture: Boolean
   public final val canUnpastureOthers: Boolean
   public final val maxPokemon: Int

   init {
      this.canUnpastureOthers = canUnpastureOthers;
      this.canPasture = canPasture;
      this.maxPokemon = maxPokemon;
   }

   public fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.canUnpastureOthers);
      buffer.writeBoolean(this.canPasture);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.SHORT, this.maxPokemon);
   }

   public companion object {
      public fun decode(buffer: FriendlyByteBuf): PasturePermissions {
         return new PasturePermissions(buffer.readBoolean(), buffer.readBoolean(), NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.SHORT));
      }
   }
}
