package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import java.util.UUID
import kotlin.jvm.functions.Function1
import net.minecraft.server.level.ServerPlayer

public class PartyMoveSelectCallback(uuid: UUID = UUID.randomUUID(),
   pokemon: List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>,
   cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
   handler: (ServerPlayer, Int, PartySelectPokemonDTO, Int, MoveSelectDTO) -> Unit
) {
   public final val cancel: (ServerPlayer) -> Unit
   public final val handler: (ServerPlayer, Int, PartySelectPokemonDTO, Int, MoveSelectDTO) -> Unit
   public final val pokemon: List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>>
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.pokemon = pokemon;
      this.cancel = cancel;
      this.handler = handler;
   }
}
