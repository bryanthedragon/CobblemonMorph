package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import net.minecraft.world.entity.LivingEntity

public class TimeRangeRequirement : EntityQueryRequirement {
   public final val range: TimeRange = new TimeRange(new IntRange(0, 23999))

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      return this.range.contains((int)(queriedEntity.m_9236_().m_46468_() % (long)24000));
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
      private const val DAY_DURATION: Int
   }
}
