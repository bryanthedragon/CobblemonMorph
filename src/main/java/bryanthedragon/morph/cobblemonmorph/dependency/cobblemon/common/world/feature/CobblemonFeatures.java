package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.feature.Feature

public object CobblemonFeatures : PlatformRegistry<Registry<Feature<?>>, ResourceKey<Registry<Feature<?>>>, Feature<?>> {
   public final val APRICORN_TREE_FEATURE: ApricornTreeFeature = INSTANCE.create("apricorn_tree", new ApricornTreeFeature()) as ApricornTreeFeature
   public final val BERRY_GROVE_FEATURE: BerryGroveFeature = INSTANCE.create("berry_grove", new BerryGroveFeature()) as BerryGroveFeature
   public final val MINT_FEATURE: MintBlockFeature = INSTANCE.create("mint", new MintBlockFeature()) as MintBlockFeature
   public open val registry: Registry<Feature<*>>
   public open val registryKey: ResourceKey<Registry<Feature<*>>>

   @JvmStatic
   fun {
      val var10000: Registry = BuiltInRegistries.f_256810_;
      registry = var10000;
      val var0: ResourceKey = Registries.f_256833_;
      registryKey = var0;
   }
}
