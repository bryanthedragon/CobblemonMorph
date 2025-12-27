package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public abstract class IntUpdatePacket<T extends NetworkPacket<T>> : SingleUpdatePacket<Integer, T> {
   open fun IntUpdatePacket(pokemon: () -> Pokemon, value: Int) {
      super(pokemon, value);
   }

   public abstract fun getSize(): IntSize {
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, this.getSize(), this.getValue().intValue());
   }
}
