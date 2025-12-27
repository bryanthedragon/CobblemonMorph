package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.IdentifierExtensionsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.LinkedHashMap
import kotlin.jvm.functions.Function2
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager

public object BedrockParticleEffectRepository {
   private final val GSON: Gson = new GsonBuilder().create()
   private final val effects: MutableMap<ResourceLocation, BedrockParticleEffect> = (new LinkedHashMap()) as java.util.Map

   public fun loadEffects(resourceManager: ResourceManager) {
      Cobblemon.INSTANCE.getLOGGER().info("Loading particle effects...");
      effects.clear();
      resourceManager.m_214159_("bedrock/particles", BedrockParticleEffectRepository::loadEffects$lambda$0)
         .forEach(BedrockParticleEffectRepository::loadEffects$lambda$1);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded ${effects.size()} particle effects");
   }

   public fun getEffect(identifier: ResourceLocation): BedrockParticleEffect? {
      return effects.get(identifier);
   }

   @JvmStatic
   fun `loadEffects$lambda$0`(path: ResourceLocation): Boolean {
      return IdentifierExtensionsKt.endsWith(path, ".particle.json");
   }

   @JvmStatic
   fun `loadEffects$lambda$1`(`$tmp0`: Function2, p0: Any, p1: Any) {
      `$tmp0`.invoke(p0, p1);
   }
}
