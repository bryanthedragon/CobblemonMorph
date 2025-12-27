package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import net.minecraft.resources.ResourceLocation

public open class Status(name: ResourceLocation, showdownName: String = "", applyMessage: String, removeMessage: String) {
   public final val applyMessage: String
   public final val name: ResourceLocation
   public final val removeMessage: String
   public final val showdownName: String

   init {
      this.name = name;
      this.showdownName = showdownName;
      this.applyMessage = applyMessage;
      this.removeMessage = removeMessage;
   }

   public fun getActionEffect(): ActionEffectTimeline? {
      return ActionEffects.INSTANCE.getActionEffects().get(this.name);
   }
}
