package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public class MoveSelectDTO(moveTemplate: MoveTemplate, enabled: Boolean, pp: Int = -1, ppMax: Int = -1) {
   public final var enabled: Boolean
   public final val moveTemplate: MoveTemplate
   public final val pp: Int
   public final val ppMax: Int

   init {
      this.moveTemplate = moveTemplate;
      this.enabled = enabled;
      this.pp = pp;
      this.ppMax = ppMax;
   }

   @JvmOverloads
   public constructor(move: Move, enabled: Boolean = true) : this(move.getTemplate(), enabled, move.getCurrentPp(), move.getMaxPp())
   @JvmOverloads
   public constructor(move: InBattleMove, enabled: Boolean = true) : this(
         Moves.INSTANCE.getByNameOrDummy(move.getMove()), enabled, move.getPp(), move.getMaxpp()
      )
   public constructor(buffer: FriendlyByteBuf)  {
      val var10001: Moves = Moves.INSTANCE;
      val var10002: java.lang.String = buffer.m_130277_();
      this(
         var10001.getByNameOrDummy(var10002),
         buffer.readBoolean(),
         NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.BYTE),
         NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.BYTE)
      );
   }

   public fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.moveTemplate.getName());
      buffer.writeBoolean(this.enabled);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.BYTE, this.pp);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.BYTE, this.ppMax);
   }

   @JvmOverloads
   fun MoveSelectDTO(move: Move) {
      this(move, false, 2, null);
   }

   @JvmOverloads
   fun MoveSelectDTO(move: InBattleMove) {
      this(move, false, 2, null);
   }
}
