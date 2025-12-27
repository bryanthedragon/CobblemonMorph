package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.template.EntityQueryRequirement
import java.util.Map.Entry
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementProgress
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

public class PlayerHasAdvancementRequirement(requiredAdvancement: ResourceLocation) : EntityQueryRequirement {
   public final val requiredAdvancement: ResourceLocation

   init {
      this.requiredAdvancement = requiredAdvancement;
   }

   public override fun check(pokemon: Pokemon, queriedEntity: LivingEntity): Boolean {
      val var10000: ServerPlayer = queriedEntity as? ServerPlayer;
      if ((queriedEntity as? ServerPlayer) == null) {
         return false;
      } else {
         val var6: java.util.Map = var10000.m_8960_().f_263740_;

         for (Entry entry : var6.entrySet()) {
            if ((entry.getKey() as Advancement).m_138327_() == this.requiredAdvancement && (entry.getValue() as AdvancementProgress).m_8193_()) {
               return true;
            }
         }

         return false;
      }
   }

   override fun check(pokemon: Pokemon): Boolean {
      return EntityQueryRequirement.DefaultImpls.check(this, pokemon);
   }

   public companion object {
      public final val ADAPTER_VARIANT: String
   }
}
