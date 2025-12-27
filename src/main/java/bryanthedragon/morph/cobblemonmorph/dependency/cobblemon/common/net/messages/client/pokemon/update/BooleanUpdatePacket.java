package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf

public abstract class BooleanUpdatePacket<T extends NetworkPacket<T>> : SingleUpdatePacket<java.lang.Boolean, T> {
   open fun BooleanUpdatePacket(pokemon: () -> Pokemon, value: Boolean) {
      super(pokemon, value);
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.getValue());
   }
}
