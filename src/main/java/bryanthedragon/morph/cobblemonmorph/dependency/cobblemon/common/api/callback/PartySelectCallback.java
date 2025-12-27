package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback

import java.util.UUID
import kotlin.jvm.functions.Function1
import net.minecraft.server.level.ServerPlayer

public class PartySelectCallback(uuid: UUID = UUID.randomUUID(),
   shownPokemon: List<PartySelectPokemonDTO>,
   cancel: (ServerPlayer) -> Unit = <unrepresentable>.INSTANCE as Function1,
   handler: (ServerPlayer, Int) -> Unit
) {
   public final val cancel: (ServerPlayer) -> Unit
   public final val handler: (ServerPlayer, Int) -> Unit
   public final val shownPokemon: List<PartySelectPokemonDTO>
   public final val uuid: UUID

   init {
      this.uuid = uuid;
      this.shownPokemon = shownPokemon;
      this.cancel = cancel;
      this.handler = handler;
   }
}
