package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.Entity

public class BusyError(targetName: MutableComponent) : BattleStartError {
   public final val targetName: MutableComponent

   init {
      this.targetName = targetName;
   }

   public override fun getMessageFor(entity: Entity): MutableComponent {
      return LocalizationUtilsKt.battleLang("errors.busy", this.targetName);
   }
}
