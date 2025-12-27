package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPacketListener.class)
public interface ClientPlayNetworkHandlerInvoker {
   @Invoker
   void callPlaySpawnSound(Entity entity);
}
