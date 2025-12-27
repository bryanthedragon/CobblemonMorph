package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.AABB

public class AreaRequirement : EntityQueryRequirement {
   public final val box: AABB

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      return this.box.m_82390_(queriedEntity.m_20182_());
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
