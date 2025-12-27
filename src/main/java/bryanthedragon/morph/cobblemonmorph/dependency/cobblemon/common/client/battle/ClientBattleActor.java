package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.network.chat.MutableComponent;

public class ClientBattleActor(showdownId: String, displayName: MutableComponent, uuid: UUID, type: ActorType) {
   public final val activePokemon: MutableList<ActiveClientBattlePokemon>
   public final val displayName: MutableComponent
   public final var pokemon: MutableList<Pokemon>
   public final val showdownId: String
   public final lateinit var side: ClientBattleSide
   public final val type: ActorType
   public final val uuid: UUID

   init {
      this.showdownId = showdownId;
      this.displayName = displayName;
      this.uuid = uuid;
      this.type = type;
      this.pokemon = new ArrayList<>();
      this.activePokemon = new ArrayList<>();
   }
}
