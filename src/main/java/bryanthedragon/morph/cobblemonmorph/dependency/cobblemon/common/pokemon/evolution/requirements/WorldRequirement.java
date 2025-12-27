package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity

public class WorldRequirement : EntityQueryRequirement {
   public final val identifier: ResourceLocation = new ResourceLocation("minecraft:the_overworld")

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      return queriedEntity.m_9236_().m_46472_().m_135782_() == this.identifier;
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
