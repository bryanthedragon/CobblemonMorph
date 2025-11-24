/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  com.mojang.datafixers.util.Either
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.IntRange
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.MoonPhaseRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BiomeLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BlockLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DropEntryAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EitherIdentifierOrTagAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FluidLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.NbtCompoundAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PossibleHeldItemAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegisteredSpawningContextAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnBucketAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnDetailAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningConditionAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.datafixers.util.Either;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R0\u0010\u0004\u001a\u0010\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u0003\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001f\u0010\f\u001a\n \u000b*\u0004\u0018\u00010\n0\n8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/spawning/SpawnLoader;", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "deserializingConditionClass", "Ljava/lang/Class;", "getDeserializingConditionClass", "()Ljava/lang/Class;", "setDeserializingConditionClass", "(Ljava/lang/Class;)V", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
public final class SpawnLoader {
    @NotNull
    public static final SpawnLoader INSTANCE = new SpawnLoader();
    private static final Gson gson;
    @Nullable
    private static Class<? extends SpawningCondition<?>> deserializingConditionClass;

    private SpawnLoader() {
    }

    public final Gson getGson() {
        return gson;
    }

    @Nullable
    public final Class<? extends SpawningCondition<?>> getDeserializingConditionClass() {
        return deserializingConditionClass;
    }

    public final void setDeserializingConditionClass(@Nullable Class<? extends SpawningCondition<?>> clazz) {
        deserializingConditionClass = clazz;
    }

    static {
        Type[] typeArray = new Type[]{Biome.class};
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setLenient().registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)BiomeLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Block.class};
        GsonBuilder gsonBuilder2 = gsonBuilder.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)BlockLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Fluid.class};
        GsonBuilder gsonBuilder3 = gsonBuilder2.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)FluidLikeConditionAdapter.INSTANCE);
        typeArray = new Type[2];
        typeArray[0] = ResourceLocation.class;
        Type[] typeArray2 = new Type[]{Structure.class};
        typeArray[1] = TypeToken.getParameterized((Type)((Type)((Object)TagKey.class)), (Type[])typeArray2).getType();
        Type type = TypeToken.getParameterized((Type)((Type)((Object)Either.class)), (Type[])typeArray).getType();
        ResourceKey resourceKey = Registries.f_256944_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"STRUCTURE");
        gson = gsonBuilder3.registerTypeAdapter(type, new EitherIdentifierOrTagAdapter(resourceKey)).registerTypeAdapter((Type)((Object)RegisteredSpawningContext.class), (Object)RegisteredSpawningContextAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)SpawnDetail.class), (Object)SpawnDetailAdapter.INSTANCE).registerTypeAdapter((Type)((Object)DropEntry.class), (Object)DropEntryAdapter.INSTANCE).registerTypeAdapter((Type)((Object)SpawningCondition.class), (Object)SpawningConditionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)TimeRange.class), new IntRangesAdapter<TimeRange>(TimeRange.Companion.getTimeRanges(), gson.1.INSTANCE)).registerTypeAdapter((Type)((Object)MoonPhaseRange.class), new IntRangesAdapter<MoonPhaseRange>(MoonPhaseRange.Companion.getMoonPhaseRanges(), gson.2.INSTANCE)).registerTypeAdapter((Type)((Object)ItemDropMethod.class), ItemDropMethod.Companion.getAdapter()).registerTypeAdapter((Type)((Object)PokemonProperties.class), (Object)PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter()).registerTypeAdapter((Type)((Object)SpawnBucket.class), (Object)SpawnBucketAdapter.INSTANCE).registerTypeAdapter((Type)((Object)CompoundTag.class), (Object)NbtCompoundAdapter.INSTANCE).registerTypeAdapter((Type)((Object)IntRange.class), (Object)IntRangeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)PossibleHeldItem.class), (Object)PossibleHeldItemAdapter.INSTANCE).create();
    }
}

