package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction

public class PartyStorageSlot(x: Int, y: Int, parent: StorageWidget, party: ClientParty, position: PartyPosition, onPress: PressAction) : StorageSlot(
      x, y, parent, onPress
   ) {
   private final val parent: StorageWidget
   private final val party: ClientParty
   public final val position: PartyPosition

   init {
      this.parent = parent;
      this.party = party;
      this.position = position;
   }

   public override fun getPokemon(): Pokemon? {
      return this.party.get(this.position);
   }

   public override fun shouldRender(): Boolean {
      val grabbedSlot: GrabbedStorageSlot = this.parent.getGrabbedSlot();
      return grabbedSlot == null || !(grabbedSlot.getPokemon() == this.getPokemon());
   }
}
