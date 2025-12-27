package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function1

public open class PCGUIConfiguration(exitFunction: (PCGUI) -> Unit = <unrepresentable>.INSTANCE as Function1,
   selectOverride: ((PCGUI, StorePosition, Pokemon?) -> Unit)? = null,
   showParty: Boolean = true,
   canSelect: (Pokemon) -> Boolean = <unrepresentable>.INSTANCE as Function1
) {
   public final val canSelect: (Pokemon) -> Boolean
   public final val exitFunction: (PCGUI) -> Unit
   public final val selectOverride: ((PCGUI, StorePosition, Pokemon?) -> Unit)?
   public final val showParty: Boolean

   init {
      this.exitFunction = exitFunction;
      this.selectOverride = selectOverride;
      this.showParty = showParty;
      this.canSelect = canSelect;
   }

   open fun PCGUIConfiguration() {
      this(null, null, false, null, 15, null);
   }
}
