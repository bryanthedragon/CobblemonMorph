package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item

import net.minecraft.resources.ResourceLocation

public class Berry(name: ResourceLocation, spicy: Int, dry: Int, sweet: Int, bitter: Int, sour: Int) {
   public final val bitter: Int
   public final val dry: Int
   public final val name: ResourceLocation
   public final val sour: Int
   public final val spicy: Int
   public final val sweet: Int

   init {
      this.name = name;
      this.spicy = spicy;
      this.dry = dry;
      this.sweet = sweet;
      this.bitter = bitter;
      this.sour = sour;
   }
}
