package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TamableAnimal.class)
public class TameableEntityMixin {
   @Redirect(method = "onDeath", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z"))
   public boolean cobblemon$checkIfPokemonBeforeSendingMessage(Level world) {
      return world.f_46443_ || this.getClass().isAssignableFrom(PokemonEntity.class);
   }
}
