package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO
import net.minecraft.network.chat.MutableComponent

public class PartySelectConfiguration(title: MutableComponent,
   pokemon: List<PartySelectPokemonDTO>,
   onCancel: (PartySelectGUI) -> Unit,
   onBack: (PartySelectGUI) -> Unit,
   onSelect: (PartySelectGUI, PartySelectPokemonDTO) -> Unit
) {
   public final val onBack: (PartySelectGUI) -> Unit
   public final val onCancel: (PartySelectGUI) -> Unit
   public final val onSelect: (PartySelectGUI, PartySelectPokemonDTO) -> Unit
   public final val pokemon: List<PartySelectPokemonDTO>
   public final val title: MutableComponent

   init {
      this.title = title;
      this.pokemon = pokemon;
      this.onCancel = onCancel;
      this.onBack = onBack;
      this.onSelect = onSelect;
   }
}
