package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction

public class BoxStorageSlot(x: Int, y: Int, parent: StorageWidget, pc: ClientPC, position: PCPosition, onPress: PressAction) : StorageSlot(x, y, parent, onPress) {
   private final val parent: StorageWidget
   private final val pc: ClientPC
   public final val position: PCPosition

   init {
      this.parent = parent;
      this.pc = pc;
      this.position = position;
   }

   public override fun getPokemon(): Pokemon? {
      return this.pc.get(this.position);
   }

   public override fun shouldRender(): Boolean {
      val grabbedSlot: GrabbedStorageSlot = this.parent.getGrabbedSlot();
      return grabbedSlot == null || !(grabbedSlot.getPokemon() == this.getPokemon());
   }
}
