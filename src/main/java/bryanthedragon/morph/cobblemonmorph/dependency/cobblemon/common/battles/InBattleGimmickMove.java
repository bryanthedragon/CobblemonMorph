package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import net.minecraft.network.FriendlyByteBuf

public class InBattleGimmickMove {
   public final var disabled: Boolean
   public final lateinit var move: String
   public final var target: MoveTarget = MoveTarget.self

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.getMove());
      buffer.m_130068_(this.target);
      buffer.writeBoolean(this.disabled);
   }

   public companion object {
      public fun loadFromBuffer(buffer: FriendlyByteBuf): InBattleGimmickMove {
         val var2: InBattleGimmickMove = new InBattleGimmickMove();
         val var10001: java.lang.String = buffer.m_130277_();
         var2.setMove(var10001);
         val var5: java.lang.Enum = buffer.m_130066_(MoveTarget.class);
         var2.setTarget(var5 as MoveTarget);
         var2.setDisabled(buffer.readBoolean());
         return var2;
      }
   }
}
