package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.biome.Biome

public class BiomeRequirement : EntityQueryRequirement {
   public final val biomeAnticondition: RegistryLikeCondition<Biome>?
   public final val biomeCondition: RegistryLikeCondition<Biome>?

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      val biome: Biome = queriedEntity.m_9236_().m_204166_(queriedEntity.m_20183_()).m_203334_() as Biome;
      val registry: Registry = queriedEntity.m_9236_().m_9598_().m_175515_(Registries.f_256952_);
      if (this.biomeCondition != null) {
         val var10000: RegistryLikeCondition = this.biomeCondition;
         if (!var10000.fits(biome, registry)) {
            return false;
         }
      }

      if (this.biomeAnticondition == null) {
         return true;
      } else {
         val var5: RegistryLikeCondition = this.biomeAnticondition;
         return !var5.fits(biome, registry);
      }
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
