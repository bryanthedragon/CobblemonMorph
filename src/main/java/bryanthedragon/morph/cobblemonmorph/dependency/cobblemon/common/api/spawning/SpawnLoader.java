package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BiomeLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BlockLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DropEntryAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EitherIdentifierOrTagAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FluidLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.NbtCompoundAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PossibleHeldItemAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegisteredSpawningContextAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnBucketAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnDetailAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningConditionAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mojang.datafixers.util.Either
import java.lang.reflect.Type
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.material.Fluid

public object SpawnLoader {
   public final var deserializingConditionClass: Class<out SpawningCondition<*>>?
   public final val gson: Gson

   @JvmStatic
   fun {
      val var7: GsonBuilder = new GsonBuilder()
         .setPrettyPrinting()
         .disableHtmlEscaping()
         .setLenient()
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Biome.class}).getType(), BiomeLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Block.class}).getType(), BlockLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Fluid.class}).getType(), FluidLikeConditionAdapter.INSTANCE
         );
      val var11: Type = TypeToken.getParameterized(
            Either::class.java, new Type[]{ResourceLocation.class, TypeToken.getParameterized(TagKey::class.java, new Type[]{Structure.class}).getType()}
         )
         .getType();
      val var12: ResourceKey = Registries.f_256944_;
      gson = var7.registerTypeAdapter(var11, new EitherIdentifierOrTagAdapter(var12))
         .registerTypeAdapter(RegisteredSpawningContext::class.java, RegisteredSpawningContextAdapter.INSTANCE)
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(SpawnDetail::class.java, SpawnDetailAdapter.INSTANCE)
         .registerTypeAdapter(DropEntry::class.java, DropEntryAdapter.INSTANCE)
         .registerTypeAdapter(SpawningCondition::class.java, SpawningConditionAdapter.INSTANCE)
         .registerTypeAdapter(TimeRange::class.java, new IntRangesAdapter<>(TimeRange.Companion.getTimeRanges(), <unrepresentable>.INSTANCE))
         .registerTypeAdapter(MoonPhaseRange::class.java, new IntRangesAdapter<>(MoonPhaseRange.Companion.getMoonPhaseRanges(), <unrepresentable>.INSTANCE))
         .registerTypeAdapter(ItemDropMethod::class.java, ItemDropMethod.Companion.getAdapter())
         .registerTypeAdapter(PokemonProperties::class.java, PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter())
         .registerTypeAdapter(SpawnBucket::class.java, SpawnBucketAdapter.INSTANCE)
         .registerTypeAdapter(CompoundTag::class.java, NbtCompoundAdapter.INSTANCE)
         .registerTypeAdapter(IntRange::class.java, IntRangeAdapter.INSTANCE)
         .registerTypeAdapter(PossibleHeldItem::class.java, PossibleHeldItemAdapter.INSTANCE)
         .create();
   }
}
