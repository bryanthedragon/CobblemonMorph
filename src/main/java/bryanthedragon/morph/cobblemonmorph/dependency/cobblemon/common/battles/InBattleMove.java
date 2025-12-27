package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf

public class InBattleMove {
   public final var disabled: Boolean
   public final var gimmickMove: InBattleGimmickMove?
   public final lateinit var id: String
   public final var maxpp: Int = 100
   public final lateinit var move: String
   public final var pp: Int = 100
   public final var target: MoveTarget = MoveTarget.self

   public fun getTargets(user: ActiveBattlePokemon): List<Targetable>? {
      return this.target.getTargetList().invoke(user) as MutableList<Targetable>;
   }

   public fun canBeUsed(): Boolean {
      return this.pp > 0 && !this.disabled || this.mustBeUsed();
   }

   public fun mustBeUsed(): Boolean {
      return this.maxpp == 100 && this.pp == 100 && this.target === MoveTarget.self;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getId());
      buffer.m_130070_(this.getMove());
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.pp);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.maxpp);
      buffer.m_130068_(this.target);
      buffer.writeBoolean(this.disabled);
   }

   public companion object {
      public fun loadFromBuffer(buffer: FriendlyByteBuf): InBattleMove {
         val var2: InBattleMove = new InBattleMove();
         var var10001: java.lang.String = buffer.m_130277_();
         var2.setId(var10001);
         var10001 = buffer.m_130277_();
         var2.setMove(var10001);
         var2.setPp(NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE));
         var2.setMaxpp(NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE));
         val var6: java.lang.Enum = buffer.m_130066_(MoveTarget.class);
         var2.setTarget(var6 as MoveTarget);
         var2.setDisabled(buffer.readBoolean());
         return var2;
      }
   }
}
