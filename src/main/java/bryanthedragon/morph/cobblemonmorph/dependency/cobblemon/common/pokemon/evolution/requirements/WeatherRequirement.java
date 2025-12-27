package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level

public class WeatherRequirement : EntityQueryRequirement {
   public final val isRaining: Boolean?
   public final val isThundering: Boolean?

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      val world: Level = queriedEntity.m_9236_();
      return (!(this.isRaining == true) || world.m_46471_())
         && (!(this.isRaining == false) || !world.m_46471_())
         && (!(this.isThundering == true) || world.m_46470_())
         && (!(this.isThundering == false) || !world.m_46470_());
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
