package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashMap
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

public object CobblemonSherds {
   public final val BYGONE_SHERD: CobblemonSherd = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("bygone_pottery_pattern"), CobblemonItems.BYGONE_SHERD)
   public final val CAPTURE_SHERD: CobblemonSherd = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("capture_pottery_pattern"), CobblemonItems.CAPTURE_SHERD)
   public final val DOME_SHERD: CobblemonSherd = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("dome_pottery_pattern"), CobblemonItems.DOME_SHERD)
   public final val HELIX_SHERD: CobblemonSherd = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("helix_pottery_pattern"), CobblemonItems.HELIX_SHERD)
   public final val NOSTALGIC_SHERD: CobblemonSherd =
      INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("nostalgic_pottery_pattern"), CobblemonItems.NOSTALGIC_SHERD)
      public final val SUSPICIOUS_SHERD: CobblemonSherd =
      INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("suspicious_pottery_pattern"), CobblemonItems.SUSPICIOUS_SHERD)
      public final val allSherds: MutableList<CobblemonSherd> = (new ArrayList()) as java.util.List
   public final val sherdToPattern: MutableMap<Item, ResourceKey<String>> = (new LinkedHashMap()) as java.util.Map

   public fun addSherd(patternId: ResourceLocation, item: Item): CobblemonSherd {
      val sherd: CobblemonSherd = new CobblemonSherd(patternId, item);
      val registryKey: ResourceKey = ResourceKey.m_135785_(Registries.f_271200_, patternId);
      val var10000: java.util.Map = sherdToPattern;
      var10000.put(item, registryKey);
      allSherds.add(sherd);
      return sherd;
   }

   public fun registerSherds() {
      val registry: Registry = BuiltInRegistries.f_271353_;

      for (CobblemonSherd sherd : allSherds) {
         Registry.m_194579_(registry, ResourceKey.m_135785_(Registries.f_271200_, sherd.getPatternId()), sherd.getPatternId().m_135815_());
      }
   }
}
