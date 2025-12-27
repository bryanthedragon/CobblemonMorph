package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ShaderRegistryData
import java.util.ArrayList;
import java.util.function.Consumer
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.server.packs.resources.ResourceProvider

public object CobblemonShaders {
   public final lateinit var PARTICLE_BLEND: ShaderInstance
   public final lateinit var PARTICLE_CUTOUT: ShaderInstance
   public final val SHADERS_TO_REGISTER: MutableList<Pair<(ResourceProvider) -> ShaderRegistryData, Consumer<ShaderInstance>>> =
      (new ArrayList()) as java.util.List

   private fun registerShader(shader: (ResourceProvider) -> ShaderRegistryData, callback: Consumer<ShaderInstance>) {
      SHADERS_TO_REGISTER.add(new Pair(shader, callback));
   }

   public fun init() {
      this.registerShader(<unrepresentable>.INSTANCE, CobblemonShaders::init$lambda$0);
      this.registerShader(<unrepresentable>.INSTANCE, CobblemonShaders::init$lambda$1);
   }

   @JvmStatic
   fun `init$lambda$0`(it: ShaderInstance) {
      INSTANCE.setPARTICLE_BLEND(it);
   }

   @JvmStatic
   fun `init$lambda$1`(it: ShaderInstance) {
      INSTANCE.setPARTICLE_CUTOUT(it);
   }
}
