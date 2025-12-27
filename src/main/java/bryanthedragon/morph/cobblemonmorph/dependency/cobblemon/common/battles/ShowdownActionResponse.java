package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public abstract class ShowdownActionResponse {
   public final val type: ShowdownActionResponseType

   open fun ShowdownActionResponse(type: ShowdownActionResponseType) {
      this.type = type;
   }

   public open fun saveToBuffer(buffer: FriendlyByteBuf) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.type.ordinal());
   }

   public open fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownActionResponse {
      return this;
   }

   public abstract fun isValid(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?, forceSwitch: Boolean): Boolean {
   }

   public abstract fun toShowdownString(activeBattlePokemon: ActiveBattlePokemon, showdownMoveSet: ShowdownMoveset?): String {
   }

   public companion object {
      public fun loadFromBuffer(buffer: FriendlyByteBuf): ShowdownActionResponse {
         return (ShowdownActionResponseType.values()[NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE)].getLoader().invoke(buffer) as ShowdownActionResponse)
            .loadFromBuffer(buffer);
      }
   }
}
