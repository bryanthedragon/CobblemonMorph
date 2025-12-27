package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.elemental

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public class ElementalTypeTeraType(type: ElementalType) : TeraType {
   public open val displayName: Component
   public open val id: ResourceLocation
   public open val legalAsStatic: Boolean
   public final val type: ElementalType

   init {
      this.type = type;
      this.legalAsStatic = true;
      this.id = MiscUtilsKt.cobblemonResource(this.type.getName());
      this.displayName = this.type.getDisplayName() as Component;
   }

   public override fun showdownId(): String {
      return this.type.getName();
   }
}
