package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import net.minecraft.tags.FluidTags
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

public class SwimBehaviour {
   public final val avoidsWater: Boolean
   public final val canBreatheUnderlava: Boolean
   public final val canBreatheUnderwater: Boolean
   public final val canSwimInLava: Boolean = true
   public final val canSwimInWater: Boolean = true
   public final val canWalkOnLava: Boolean
   public final val canWalkOnWater: Boolean
   public final val hurtByLava: Boolean = true
   public final val swimSpeed: Float = 0.3F

   public fun canWalkOnFluid(tag: TagKey<Fluid>): Boolean {
      return if (tag == FluidTags.f_13131_) this.canWalkOnWater else tag == FluidTags.f_13132_ && this.canWalkOnLava;
   }

   public fun canBreatheUnderFluid(tag: TagKey<Fluid>): Boolean {
      return if (tag == FluidTags.f_13131_) this.canBreatheUnderwater else tag == FluidTags.f_13132_ && this.canBreatheUnderlava;
   }

   public fun canSwimInFluid(tag: TagKey<Fluid>): Boolean {
      return if (tag == FluidTags.f_13131_) this.canSwimInWater else tag == FluidTags.f_13132_ && this.canSwimInLava;
   }
}
