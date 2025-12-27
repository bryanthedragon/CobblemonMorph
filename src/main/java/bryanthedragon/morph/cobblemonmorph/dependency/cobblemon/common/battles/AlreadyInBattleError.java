package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.UUID
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.Entity

public class AlreadyInBattleError(actorUUID: UUID, name: Component) : BattleStartError {
   public final val actorUUID: UUID
   public final val name: Component

   init {
      this.actorUUID = actorUUID;
      this.name = name;
   }

   public override fun getMessageFor(entity: Entity): MutableComponent {
      val var10000: MutableComponent;
      if (this.actorUUID == entity.m_20148_()) {
         var10000 = LocalizationUtilsKt.battleLang("error.in_battle.personal");
      } else {
         var10000 = LocalizationUtilsKt.battleLang("error.in_battle", this.name);
      }

      return var10000;
   }
}
