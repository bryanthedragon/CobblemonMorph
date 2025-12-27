package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public data PartyPosition(slot: Int) : StorePosition {
   public final val slot: Int

   init {
      this.slot = slot;
   }

   public operator fun component1(): Int {
      return this.slot;
   }

   public fun copy(slot: Int = this.slot): PartyPosition {
      return new PartyPosition(slot);
   }

   public override fun toString(): String {
      return "PartyPosition(slot=${this.slot})";
   }

   public override fun hashCode(): Int {
      return Integer.hashCode(this.slot);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PartyPosition) {
         return false;
      } else {
         return this.slot == (other as PartyPosition).slot;
      }
   }

   public companion object {
      public fun FriendlyByteBuf.writePartyPosition(position: PartyPosition) {
         NetExtensionsKt.writeSizedInt(`$this$writePartyPosition` as ByteBuf, IntSize.U_BYTE, position.getSlot());
      }

      public fun FriendlyByteBuf.readPartyPosition(): PartyPosition {
         return new PartyPosition(NetExtensionsKt.readSizedInt(`$this$readPartyPosition` as ByteBuf, IntSize.U_BYTE));
      }
   }
}
