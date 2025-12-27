package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.Entity

public class CanceledError(reason: MutableComponent?) : BattleStartError {
   public final val reason: MutableComponent?

   init {
      this.reason = reason;
   }

   public override fun getMessageFor(entity: Entity): MutableComponent {
      var var10000: MutableComponent = this.reason;
      if (this.reason == null) {
         var10000 = LocalizationUtilsKt.battleLang("error.canceled");
      }

      return var10000;
   }
}
