package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class MinecraftDedicatedServerMixin implements ServerInterface {
   @Inject(method = "shouldCancelSpawn", at = @At("HEAD"), cancellable = true)
   public void cobblemon$allowPokemonSpawns(Entity entity, CallbackInfoReturnable<Boolean> callback) {
      if (entity instanceof PokemonEntity) {
         callback.setReturnValue(false);
      }
   }
}
