package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import java.util.UUID
import kotlin.jvm.functions.Function1
import net.minecraft.server.level.ServerPlayer

public class MoveSelectCallback(uuid: UUID = UUID.randomUUID(),
   shownMoves: List<MoveSelectDTO>,
   cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
   handler: (ServerPlayer, Int, MoveSelectDTO) -> Unit
) {
   public final val cancel: (ServerPlayer) -> Unit
   public final val handler: (ServerPlayer, Int, MoveSelectDTO) -> Unit
   public final val shownMoves: List<MoveSelectDTO>
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.shownMoves = shownMoves;
      this.cancel = cancel;
      this.handler = handler;
   }
}
