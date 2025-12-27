package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry

import com.mojang.brigadier.arguments.ArgumentType

import java.util.HashMap

import net.minecraft.advancements.CriterionTrigger
import net.minecraft.command.argument.serialize.ArgumentSerializer.ArgumentTypeProperties
import net.minecraft.commands.synchronization.ArgumentTypeInfo
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.PreparableReloadListener
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.tags.TagKey
import net.minecraft.world.GameRules.Category
import net.minecraft.world.GameRules.Key
import net.minecraft.world.GameRules.Rule
import net.minecraft.world.GameRules.Type
import net.minecraft.world.gen.GenerationStep.Feature
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.placement.PlacedFeature

public interface CobblemonImplementation {
   public val modAPI: ModAPI
   public val networkManager: NetworkManager

   public abstract fun environment(): Environment {
   }

   public abstract fun isModInstalled(id: String): Boolean {
   }

   public abstract fun registerPermissionValidator() {
   }

   public abstract fun registerSoundEvents() {
   }

   public abstract fun registerItems() {
   }

   public abstract fun registerBlocks() {
   }

   public abstract fun registerEntityTypes() {
   }

   public abstract fun registerEntityAttributes() {
   }

   public abstract fun registerBlockEntityTypes() {
   }

   public abstract fun registerWorldGenFeatures() {
   }

   public abstract fun registerParticles() {
   }

   public abstract fun addFeatureToWorldGen(feature: ResourceKey<PlacedFeature>, step: Feature, validTag: TagKey<Biome>?) {
   }

   public abstract fun <A : ArgumentType<*>, T : ArgumentTypeProperties<Any>> registerCommandArgument(
      identifier: ResourceLocation,
      argumentClass: KClass<Any>,
      serializer: ArgumentTypeInfo<Any, Any>
   ) {
   }

   public abstract fun <T : Rule<Any>> registerGameRule(name: String, category: Category, type: Type<Any>): Key<Any> {
   }

   public abstract fun <T : CriterionTrigger<*>> registerCriteria(criteria: Any): Any {
   }

   public abstract fun registerResourceReloader(
      identifier: ResourceLocation,
      reloader: PreparableReloadListener,
      type: PackType,
      dependencies: Collection<ResourceLocation>
   ) {
   }

   public abstract fun server(): MinecraftServer? {
   }

   public abstract fun <T> reloadJsonRegistry(registry: JsonDataRegistry<Any>, manager: ResourceManager): HashMap<ResourceLocation, Any> {
   }

   public abstract fun registerCompostable(item: ItemLike, chance: Float) {
   }

   public abstract fun registerBuiltinResourcePack(id: ResourceLocation, title: Component, activationBehaviour: ResourcePackActivationBehaviour) {
   }
}
