package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.worldgen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.serialization.Codec
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.gen.GenerationStep.Feature
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraftforge.common.world.BiomeModifier
import net.minecraftforge.common.world.BiomeModifier.Phase
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.registries.ForgeRegistries.Keys
import net.minecraftforge.registries.RegisterEvent.RegisterHelper
import net.minecraftforge.server.ServerLifecycleHooks

@SourceDebugExtension(["SMAP\nCobblemonBiomeModifiers.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBiomeModifiers.kt\ncom/cobblemon/mod/forge/worldgen/CobblemonBiomeModifiers\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,65:1\n1855#2,2:66\n*S KotlinDebug\n*F\n+ 1 CobblemonBiomeModifiers.kt\ncom/cobblemon/mod/forge/worldgen/CobblemonBiomeModifiers\n*L\n54#1:66,2\n*E\n"])
internal object CobblemonBiomeModifiers : BiomeModifier {
   private final var codec: Codec<out BiomeModifier>?
   private final val entries: ArrayList<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.worldgen.CobblemonBiomeModifiers.Entry> = new ArrayList()

   public fun register(event: RegisterEvent) {
      event.register(Keys.BIOME_MODIFIER_SERIALIZERS, CobblemonBiomeModifiers::register$lambda$0);
   }

   public fun add(feature: ResourceKey<PlacedFeature>, step: Feature, validTag: TagKey<Biome>?) {
      entries.add(new CobblemonBiomeModifiers.Entry(feature, step, validTag));
   }

   public open fun modify(arg: Holder<Biome>, phase: Phase, builder: Builder) {
      if (phase === Phase.ADD) {
         val registry: Registry = ServerLifecycleHooks.getCurrentServer().m_206579_().m_175515_(Registries.f_256988_);

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val entry: CobblemonBiomeModifiers.Entry = `element$iv` as CobblemonBiomeModifiers.Entry;
            if ((`element$iv` as CobblemonBiomeModifiers.Entry).getValidTag() == null
               || arg.m_203656_((`element$iv` as CobblemonBiomeModifiers.Entry).getValidTag())) {
               builder.getGenerationSettings().m_255419_(entry.getStep(), Holder.m_205709_(registry.m_6246_(entry.getFeature())));
            }
         }
      }
   }

   public open fun codec(): Codec<out BiomeModifier> {
      var var10000: Codec = codec;
      if (codec == null) {
         var10000 = Codec.unit(INSTANCE);
      }

      return var10000;
   }

   @JvmStatic
   fun `register$lambda$0`(`this$0`: CobblemonBiomeModifiers, helper: RegisterHelper) {
      codec = Codec.unit(INSTANCE);
      helper.register(MiscUtilsKt.cobblemonResource("inject_coded"), codec);
   }

   private data class Entry(feature: ResourceKey<PlacedFeature>, step: Feature, validTag: TagKey<Biome>?) {
      public final val feature: ResourceKey<PlacedFeature>
      public final val step: Feature
      public final val validTag: TagKey<Biome>?

      init {
         this.feature = feature;
         this.step = step;
         this.validTag = validTag;
      }

      public operator fun component1(): ResourceKey<PlacedFeature> {
         return this.feature;
      }

      public operator fun component2(): Feature {
         return this.step;
      }

      public operator fun component3(): TagKey<Biome>? {
         return this.validTag;
      }

      public fun copy(feature: ResourceKey<PlacedFeature> = ..., step: Feature = ..., validTag: TagKey<Biome>? = ...): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.worldgen.CobblemonBiomeModifiers.Entry {
         return new CobblemonBiomeModifiers.Entry(feature, step, validTag);
      }

      public override fun toString(): String {
         return "Entry(feature=${this.feature}, step=${this.step}, validTag=${this.validTag})";
      }

      public override fun hashCode(): Int {
         return (this.feature.hashCode() * 31 + this.step.hashCode()) * 31 + (if (this.validTag == null) 0 else this.validTag.hashCode());
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is CobblemonBiomeModifiers.Entry) {
            return false;
         } else {
            val var2: CobblemonBiomeModifiers.Entry = other as CobblemonBiomeModifiers.Entry;
            if (!(this.feature == (other as CobblemonBiomeModifiers.Entry).feature)) {
               return false;
            } else if (this.step != var2.step) {
               return false;
            } else {
               return this.validTag == var2.validTag;
            }
         }
      }
   }
}
