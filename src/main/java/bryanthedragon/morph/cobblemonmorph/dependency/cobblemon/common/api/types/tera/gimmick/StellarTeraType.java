package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.gimmick

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

public class StellarTeraType : TeraType {
   public open val displayName: Component
   public open val id: ResourceLocation
   public open val legalAsStatic: Boolean

   public override fun showdownId(): String {
      val var10000: java.lang.String = ID.m_135815_();
      return var10000;
   }

   public companion object {
      public final val ID: ResourceLocation
      private final val LANG: MutableComponent
   }
}
