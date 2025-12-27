package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.worldgen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.CobblemonBlockPredicates
import net.minecraft.core.registries.Registries
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.registries.RegisterEvent.RegisterHelper

public object CobblemonForgeBlockPredicateType {
   public fun register(event: RegisterEvent) {
      event.register(Registries.f_256774_, CobblemonForgeBlockPredicateType::register$lambda$0);
   }

   @JvmStatic
   fun `register$lambda$0`(it: RegisterHelper) {
      CobblemonBlockPredicates.INSTANCE.touch();
   }
}
