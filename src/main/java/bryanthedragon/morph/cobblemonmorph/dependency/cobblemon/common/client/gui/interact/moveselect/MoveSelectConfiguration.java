package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO
import net.minecraft.network.chat.MutableComponent

public class MoveSelectConfiguration(title: MutableComponent,
   moves: List<MoveSelectDTO>,
   onCancel: (MoveSelectGUI) -> Unit,
   onBack: (MoveSelectGUI) -> Unit,
   onSelect: (MoveSelectGUI, MoveSelectDTO) -> Unit
) {
   public final val moves: List<MoveSelectDTO>
   public final val onBack: (MoveSelectGUI) -> Unit
   public final val onCancel: (MoveSelectGUI) -> Unit
   public final val onSelect: (MoveSelectGUI, MoveSelectDTO) -> Unit
   public final val title: MutableComponent

   init {
      this.title = title;
      this.moves = moves;
      this.onCancel = onCancel;
      this.onBack = onBack;
      this.onSelect = onSelect;
   }
}
