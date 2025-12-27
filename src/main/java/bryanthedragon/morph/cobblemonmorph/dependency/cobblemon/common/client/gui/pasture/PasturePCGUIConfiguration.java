package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket.PasturePokemonDataDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.functions.Function3
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public class PasturePCGUIConfiguration(pastureId: UUID,
   limit: Int,
   pasturedPokemon: SettableObservable<List<PasturePokemonDataDTO>>,
   permissions: PasturePermissions
) : PCGUIConfiguration(<unrepresentable>.INSTANCE, (new Function3<PCGUI, StorePosition, Pokemon, Unit>(permissions, pastureId) {
      {
         super(3);
         this.$permissions = `$permissions`;
         this.$pastureId = `$pastureId`;
      }

      public final void invoke(@NotNull PCGUI pcGui, @NotNull StorePosition position, @Nullable Pokemon pokemon) {
         if (pokemon != null && !pokemon.isFainted() && pokemon.getTetheringId() == null && this.$permissions.getCanPasture()) {
            val var10000: CobblemonNetwork = CobblemonNetwork.INSTANCE;
            val var10003: UUID = pokemon.getUuid();
            var10000.sendToServer(new PasturePokemonPacket(var10003, this.$pastureId));
            pcGui.playSound(CobblemonSounds.PC_CLICK);
         }
      }
   }) as (PCGUI?, StorePosition?, Pokemon?) -> Unit, false, <unrepresentable>.INSTANCE) {
   public final val limit: Int
   public final val pastureId: UUID
   public final val pasturedPokemon: SettableObservable<List<PasturePokemonDataDTO>>
   public final var permissions: PasturePermissions

   init {
      this.pastureId = pastureId;
      this.limit = limit;
      this.pasturedPokemon = pasturedPokemon;
      this.permissions = permissions;
   }
}
