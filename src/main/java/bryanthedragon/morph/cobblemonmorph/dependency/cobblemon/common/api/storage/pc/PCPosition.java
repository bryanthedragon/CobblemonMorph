package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public data PCPosition(box: Int, slot: Int) : StorePosition {
   public final val box: Int
   public final val slot: Int

   init {
      this.box = box;
      this.slot = slot;
   }

   public operator fun component1(): Int {
      return this.box;
   }

   public operator fun component2(): Int {
      return this.slot;
   }

   public fun copy(box: Int = this.box, slot: Int = this.slot): PCPosition {
      return new PCPosition(box, slot);
   }

   public override fun toString(): String {
      return "PCPosition(box=${this.box}, slot=${this.slot})";
   }

   public override fun hashCode(): Int {
      return Integer.hashCode(this.box) * 31 + Integer.hashCode(this.slot);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PCPosition) {
         return false;
      } else {
         val var2: PCPosition = other as PCPosition;
         if (this.box != (other as PCPosition).box) {
            return false;
         } else {
            return this.slot == var2.slot;
         }
      }
   }

   public companion object {
      public fun FriendlyByteBuf.writePCPosition(position: PCPosition) {
         NetExtensionsKt.writeSizedInt(`$this$writePCPosition` as ByteBuf, IntSize.U_BYTE, position.getBox());
         NetExtensionsKt.writeSizedInt(`$this$writePCPosition` as ByteBuf, IntSize.U_BYTE, position.getSlot());
      }

      public fun FriendlyByteBuf.readPCPosition(): PCPosition {
         return new PCPosition(
            NetExtensionsKt.readSizedInt(`$this$readPCPosition` as ByteBuf, IntSize.U_BYTE),
            NetExtensionsKt.readSizedInt(`$this$readPCPosition` as ByteBuf, IntSize.U_BYTE)
         );
      }
   }
}
