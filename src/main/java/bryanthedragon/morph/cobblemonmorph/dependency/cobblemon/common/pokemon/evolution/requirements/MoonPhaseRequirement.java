package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.MoonPhase
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level

public class MoonPhaseRequirement(moonPhase: MoonPhase) : EntityQueryRequirement {
   public final val moonPhase: MoonPhase

   init {
      this.moonPhase = moonPhase;
   }

   public constructor() : this(MoonPhase.FULL_MOON)
   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      var moonPhase: Boolean;
      try {
         val var10000: MoonPhase.Companion = MoonPhase.Companion;
         val var10001: Level = queriedEntity.m_9236_();
         moonPhase = this.moonPhase === var10000.ofWorld(var10001);
      } catch (var5: IndexOutOfBoundsException) {
         moonPhase = false;
      }

      return moonPhase;
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
