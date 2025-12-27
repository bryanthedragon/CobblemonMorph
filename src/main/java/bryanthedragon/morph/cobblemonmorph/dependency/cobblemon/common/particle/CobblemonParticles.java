package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry
import net.minecraft.core.Registry
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

public object CobblemonParticles : PlatformRegistry<Registry<ParticleType<?>>, ResourceKey<Registry<ParticleType<?>>>, ParticleType<?>> {
   public final val SNOWSTORM_PARTICLE_TYPE: SnowstormParticleType = INSTANCE.create("snowstorm", new SnowstormParticleType()) as SnowstormParticleType
   public open val registry: Registry<ParticleType<*>>
   public open val registryKey: ResourceKey<Registry<ParticleType<*>>>

   @JvmStatic
   fun {
      val var10000: Registry = BuiltInRegistries.f_257034_;
      registry = var10000;
      val var0: ResourceKey = Registries.f_256890_;
      registryKey = var0;
   }
}
