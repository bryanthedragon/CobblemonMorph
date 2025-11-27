/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.SpreadBuilder
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntityDimensionsAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.adapters.MoveTemplateAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.adapter.ShoulderEffectAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroupAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.adapters.ElementalTypeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.CobblemonEvolutionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.CobblemonPreEvolutionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.CobblemonRequirementAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters.NbtItemPredicateAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.AbilityPoolAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.AbilityTemplateAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BiomeLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BlockLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.DropEntryAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EggGroupAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.ItemLikeConditionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LazySetAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LearnsetAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.NbtCompoundAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegistryElementAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.StructureLikeConditionAdapter;
import com.google.common.collect.HashBasedTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001NB\t\b\u0002\u00a2\u0006\u0004\bL\u0010MJ\r\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000f\u0010\u0010J!\u0010\u0013\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0013\u0010\u0014J\r\u0010\u0015\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J#\u0010\u001a\u001a\u00020\u00192\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001cH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\u00020 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\n8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u001d\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020)8\u0006\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R \u0010/\u001a\b\u0012\u0004\u0012\u00020\u00000.8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u001a\u00103\u001a\u00020\u00078\u0016X\u0096D\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u0002078F\u00a2\u0006\u0006\u001a\u0004\b8\u00109Rp\u0010<\u001a^\u0012\f\u0012\n ;*\u0004\u0018\u00010\u00070\u0007\u0012\f\u0012\n ;*\u0004\u0018\u00010\u00030\u0003\u0012\f\u0012\n ;*\u0004\u0018\u00010\u00020\u0002 ;*.\u0012\f\u0012\n ;*\u0004\u0018\u00010\u00070\u0007\u0012\f\u0012\n ;*\u0004\u0018\u00010\u00030\u0003\u0012\f\u0012\n ;*\u0004\u0018\u00010\u00020\u0002\u0018\u00010:0:8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010=R0\u0010@\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00020>j\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0002`?8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b@\u0010AR\u001a\u0010C\u001a\u00020B8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bE\u0010FR \u0010H\u001a\b\u0012\u0004\u0012\u00020\u00020G8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bH\u0010I\u001a\u0004\bJ\u0010K\u00a8\u0006O"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/PokemonSpecies;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/pokemon/Species;", "", "count", "()I", "species", "", "createShowdownName", "(Lcom/cobblemon/mod/common/pokemon/Species;)Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "getByIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/pokemon/Species;", "name", "getByName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/Species;", "ndex", "namespace", "getByPokedexNumber", "(ILjava/lang/String;)Lcom/cobblemon/mod/common/pokemon/Species;", "random", "()Lcom/cobblemon/mod/common/pokemon/Species;", "", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "implemented", "Ljava/util/List;", "getImplemented", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "", "getSpecies", "()Ljava/util/Collection;", "Lcom/google/common/collect/HashBasedTable;", "kotlin.jvm.PlatformType", "speciesByDex", "Lcom/google/common/collect/HashBasedTable;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "speciesByIdentifier", "Ljava/util/HashMap;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "ShowdownSpecies", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonSpecies.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,261:1\n215#2,2:262\n*S KotlinDebug\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies\n*L\n178#1:262,2\n*E\n"})
public final class PokemonSpecies
implements JsonDataRegistry<Species> {
    @NotNull
    public static final PokemonSpecies INSTANCE = new PokemonSpecies();
    @NotNull
    private static final ResourceLocation id = MiscUtils.cobblemonResource("species");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final TypeToken<Species> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final SimpleObservable<PokemonSpecies> observable;
    @NotNull
    private static final HashMap<ResourceLocation, Species> speciesByIdentifier;
    private static final HashBasedTable<String, Integer, Species> speciesByDex;
    @NotNull
    private static final List<Species> implemented;

    private PokemonSpecies() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<Species> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @NotNull
    public SimpleObservable<PokemonSpecies> getObservable() {
        return observable;
    }

    @NotNull
    public final Collection<Species> getSpecies() {
        Collection<Species> collection = speciesByIdentifier.values();
        Intrinsics.checkNotNullExpressionValue(collection, (String)"speciesByIdentifier.values");
        return collection;
    }

    @NotNull
    public final List<Species> getImplemented() {
        return implemented;
    }

    @Nullable
    public final Species getByName(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return this.getByIdentifier(MiscUtils.cobblemonResource(name));
    }

    @Nullable
    public final Species getByPokedexNumber(int ndex, @NotNull String namespace) {
        Intrinsics.checkNotNullParameter((Object)namespace, (String)"namespace");
        return (Species)speciesByDex.get((Object)namespace, (Object)ndex);
    }

    public static /* synthetic */ Species getByPokedexNumber$default(PokemonSpecies pokemonSpecies, int n, String string, int n2, Object object) {
        if ((n2 & 2) != 0) {
            string = "cobblemon";
        }
        return pokemonSpecies.getByPokedexNumber(n, string);
    }

    @Nullable
    public final Species getByIdentifier(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        return speciesByIdentifier.get(identifier);
    }

    public final int count() {
        return speciesByIdentifier.size();
    }

    @NotNull
    public final Species random() {
        return (Species)CollectionsKt.random((Collection)implemented, (Random)((Random)Random.Default));
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, Species> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        speciesByIdentifier.clear();
        implemented.clear();
        speciesByDex.clear();
        Map<ResourceLocation, Species> $this$forEach$iv = data;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<ResourceLocation, Species>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ResourceLocation, Species> element$iv;
            Map.Entry<ResourceLocation, Species> entry = element$iv = iterator.next();
            boolean bl = false;
            ResourceLocation identifier = entry.getKey();
            Species species = entry.getValue();
            species.setResourceIdentifier(identifier);
            if (speciesByIdentifier.put(identifier, species) != null) {
                Species old;
                boolean bl2 = false;
                Species cfr_ignored_0 = (Species)speciesByDex.remove((Object)old.getResourceIdentifier().m_135827_(), (Object)old.getNationalPokedexNumber());
            }
            speciesByDex.put((Object)species.getResourceIdentifier().m_135827_(), (Object)species.getNationalPokedexNumber(), (Object)species);
            if (!species.getImplemented()) continue;
            implemented.add(species);
        }
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        new SpeciesRegistrySyncPacket(CollectionsKt.toList((Iterable)this.getSpecies())).sendToPlayer(player);
    }

    private final String createShowdownName(Species species) {
        if (Intrinsics.areEqual((Object)species.getResourceIdentifier().m_135827_(), (Object)"cobblemon")) {
            return species.getName();
        }
        return species.getResourceIdentifier().m_135827_() + ":" + species.getName();
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Type[] typeArray = new Type[]{Evolution.class};
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter((Type)((Object)Stat.class), (Object)Cobblemon.INSTANCE.getStatProvider().getTypeAdapter()).registerTypeAdapter((Type)((Object)ElementalType.class), (Object)ElementalTypeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)AbilityTemplate.class), (Object)AbilityTemplateAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ShoulderEffect.class), (Object)ShoulderEffectAdapter.INSTANCE).registerTypeAdapter((Type)((Object)MoveTemplate.class), (Object)MoveTemplateAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ExperienceGroup.class), (Object)ExperienceGroupAdapter.INSTANCE).registerTypeAdapter((Type)((Object)EntityDimensions.class), (Object)EntityDimensionsAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Learnset.class), (Object)LearnsetAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Evolution.class), (Object)CobblemonEvolutionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)AABB.class), (Object)BoxAdapter.INSTANCE).registerTypeAdapter((Type)((Object)AbilityPool.class), (Object)AbilityPoolAdapter.INSTANCE).registerTypeAdapter((Type)((Object)EvolutionRequirement.class), (Object)CobblemonRequirementAdapter.INSTANCE).registerTypeAdapter((Type)((Object)PreEvolution.class), (Object)CobblemonPreEvolutionAdapter.INSTANCE).registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)Set.class)), (Type[])typeArray).getType(), new LazySetAdapter(Reflection.getOrCreateKotlinClass(Evolution.class))).registerTypeAdapter((Type)((Object)IntRange.class), (Object)IntRangeAdapter.INSTANCE).registerTypeAdapter((Type)((Object)PokemonProperties.class), (Object)PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter()).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).registerTypeAdapter((Type)((Object)TimeRange.class), new IntRangesAdapter<TimeRange>(TimeRange.Companion.getTimeRanges(), gson.1.INSTANCE)).registerTypeAdapter((Type)((Object)ItemDropMethod.class), ItemDropMethod.Companion.getAdapter()).registerTypeAdapter((Type)((Object)SleepDepth.class), SleepDepth.Companion.getAdapter()).registerTypeAdapter((Type)((Object)DropEntry.class), (Object)DropEntryAdapter.INSTANCE).registerTypeAdapter((Type)((Object)CompoundTag.class), (Object)NbtCompoundAdapter.INSTANCE);
        typeArray = new Type[]{Biome.class};
        GsonBuilder gsonBuilder2 = gsonBuilder.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)BiomeLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Block.class};
        GsonBuilder gsonBuilder3 = gsonBuilder2.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)BlockLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Item.class};
        GsonBuilder gsonBuilder4 = gsonBuilder3.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)ItemLikeConditionAdapter.INSTANCE);
        typeArray = new Type[]{Structure.class};
        Gson gson2 = gsonBuilder4.registerTypeAdapter(TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType(), (Object)StructureLikeConditionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)EggGroup.class), (Object)EggGroupAdapter.INSTANCE).registerTypeAdapter((Type)((Object)MobEffect.class), new RegistryElementAdapter(gson.2.INSTANCE)).registerTypeAdapter((Type)((Object)NbtItemPredicate.class), (Object)NbtItemPredicateAdapter.INSTANCE).disableHtmlEscaping().enableComplexMapKeySerialization().create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GsonBuilder()\n        .r\u2026ation()\n        .create()");
        gson = gson2;
        TypeToken typeToken = TypeToken.get(Species.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(Species::class.java)");
        PokemonSpecies.typeToken = typeToken;
        resourcePath = "species";
        observable = new SimpleObservable();
        speciesByIdentifier = new HashMap();
        speciesByDex = HashBasedTable.create();
        implemented = new ArrayList();
        Observable.DefaultImpls.subscribe$default(SpeciesAdditions.INSTANCE.getObservable(), null, 1.INSTANCE, 1, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010E\u001a\u00020D\u0012\b\u0010G\u001a\u0004\u0018\u00010F\u00a2\u0006\u0004\bH\u0010IR#\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR#\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\f0\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u0005\u001a\u0004\b\u000e\u0010\u0007R\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000bR\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001d\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u00168\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0018\u001a\u0004\b\u001c\u0010\u001aR\u0019\u0010\u001d\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\t\u001a\u0004\b\u001e\u0010\u000bR\u001d\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00168\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u0018\u001a\u0004\b \u0010\u001aR\u0019\u0010!\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b!\u0010\t\u001a\u0004\b\"\u0010\u000bR%\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u0005\u001a\u0004\b%\u0010\u0007R\u0017\u0010&\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u0019\u0010*\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R\u0017\u0010.\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b.\u0010\t\u001a\u0004\b/\u0010\u000bR\u0017\u00100\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b0\u0010\u0013\u001a\u0004\b1\u0010\u0015R\u0017\u00102\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R\u001d\u00106\u001a\b\u0012\u0004\u0012\u00020\u00030\u00168\u0006\u00a2\u0006\f\n\u0004\b6\u0010\u0018\u001a\u0004\b7\u0010\u001aR\u0019\u00108\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b8\u0010\t\u001a\u0004\b9\u0010\u000bR\u0019\u0010:\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b:\u0010\t\u001a\u0004\b;\u0010\u000bR\u001f\u0010<\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00168\u0006\u00a2\u0006\f\n\u0004\b<\u0010\u0018\u001a\u0004\b=\u0010\u001aR\u0019\u0010>\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b>\u0010\t\u001a\u0004\b?\u0010\u000bR\u001d\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00030\u00168\u0006\u00a2\u0006\f\n\u0004\b@\u0010\u0018\u001a\u0004\bA\u0010\u001aR\u0017\u0010B\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\bB\u0010'\u001a\u0004\bC\u0010)\u00a8\u0006J"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/PokemonSpecies$ShowdownSpecies;", "", "", "", "abilities", "Ljava/util/Map;", "getAbilities", "()Ljava/util/Map;", "baseSpecies", "Ljava/lang/String;", "getBaseSpecies", "()Ljava/lang/String;", "", "baseStats", "getBaseStats", "canGigantamax", "getCanGigantamax", "", "cannotDynamax", "Z", "getCannotDynamax", "()Z", "", "eggGroups", "Ljava/util/List;", "getEggGroups", "()Ljava/util/List;", "evos", "getEvos", "forme", "getForme", "formeOrder", "getFormeOrder", "gender", "getGender", "", "genderRatio", "getGenderRatio", "heightm", "F", "getHeightm", "()F", "maxHP", "Ljava/lang/Integer;", "getMaxHP", "()Ljava/lang/Integer;", "name", "getName", "nfe", "getNfe", "num", "I", "getNum", "()I", "otherFormes", "getOtherFormes", "preevo", "getPreevo", "requiredItem", "getRequiredItem", "requiredItems", "getRequiredItems", "requiredMove", "getRequiredMove", "types", "getTypes", "weightkg", "getWeightkg", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nPokemonSpecies.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies$ShowdownSpecies\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,261:1\n1549#2:262\n1620#2,3:263\n1549#2:268\n1620#2,2:269\n1622#2:272\n1549#2:273\n1620#2,3:274\n37#3,2:266\n1#4:271\n*S KotlinDebug\n*F\n+ 1 PokemonSpecies.kt\ncom/cobblemon/mod/common/api/pokemon/PokemonSpecies$ShowdownSpecies\n*L\n208#1:262\n208#1:263,3\n216#1:268\n216#1:269,2\n216#1:272\n221#1:273\n221#1:274,3\n209#1:266,2\n*E\n"})
    public static final class ShowdownSpecies {
        private final int num;
        @NotNull
        private final String name;
        @NotNull
        private final String baseSpecies;
        @Nullable
        private final String forme;
        @NotNull
        private final List<String> otherFormes;
        @NotNull
        private final List<String> formeOrder;
        @NotNull
        private final Map<String, String> abilities;
        @NotNull
        private final List<String> types;
        @Nullable
        private final String preevo;
        @NotNull
        private final List<String> evos;
        private final boolean nfe;
        @NotNull
        private final List<String> eggGroups;
        @Nullable
        private final String gender;
        @Nullable
        private final Map<String, Float> genderRatio;
        @NotNull
        private final Map<String, Integer> baseStats;
        private final float heightm;
        private final float weightkg;
        @Nullable
        private final Integer maxHP;
        @Nullable
        private final String canGigantamax;
        private final boolean cannotDynamax;
        @Nullable
        private final String requiredMove;
        @Nullable
        private final String requiredItem;
        @Nullable
        private final List<String> requiredItems;

        /*
         * WARNING - void declaration
         */
        public ShowdownSpecies(@NotNull Species species, @Nullable FormData form2) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            Map map;
            List list;
            Set<Evolution> set2;
            Object it;
            Object $this$mapTo$iv$iv;
            List list2;
            Object it2;
            Collection collection;
            Object item$iv$iv2;
            boolean $i$f$mapTo;
            Collection destination$iv$iv;
            Object $this$map$iv;
            ShowdownSpecies showdownSpecies;
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            this.num = species.getNationalPokedexNumber();
            this.name = form2 != null ? INSTANCE.createShowdownName(species) + "-" + form2.getName() : INSTANCE.createShowdownName(species);
            this.baseSpecies = form2 != null ? INSTANCE.createShowdownName(species) : this.name;
            FormData formData = form2;
            this.forme = formData != null ? formData.getName() : null;
            ShowdownSpecies showdownSpecies2 = this;
            if (form2 == null && !((Collection)species.getForms()).isEmpty()) {
                void $this$mapTo$iv$iv2;
                Iterable iterable = species.getForms();
                showdownSpecies = showdownSpecies2;
                boolean $i$f$map = false;
                SpreadBuilder spreadBuilder = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (Object item$iv$iv2 : $this$mapTo$iv$iv2) {
                    FormData formData2 = (FormData)item$iv$iv2;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    collection.add(this.name + "-" + ((FormData)it2).getName());
                }
                v2 = (List)destination$iv$iv;
                showdownSpecies2 = showdownSpecies;
            } else {
                v2 = showdownSpecies2.otherFormes = CollectionsKt.emptyList();
            }
            if (form2 == null && !((Collection)this.otherFormes).isEmpty()) {
                $this$map$iv = new SpreadBuilder(2);
                $this$map$iv.add((Object)this.name);
                Collection $this$toTypedArray$iv = this.otherFormes;
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                $this$map$iv.addSpread((Object)thisCollection$iv.toArray(new String[0]));
                list2 = CollectionsKt.arrayListOf((Object[])$this$map$iv.toArray((Object[])new String[$this$map$iv.size()]));
            } else {
                list2 = CollectionsKt.emptyList();
            }
            this.formeOrder = list2;
            $this$map$iv = new SpreadBuilder[]{TuplesKt.to((Object)"0", (Object)"No Ability"), TuplesKt.to((Object)"1", (Object)"No Ability"), TuplesKt.to((Object)"H", (Object)"No Ability"), TuplesKt.to((Object)"S", (Object)"No Ability")};
            this.abilities = MapsKt.mapOf((Pair[])$this$map$iv);
            Object object = form2;
            if (object == null || (object = ((FormData)object).getTypes()) == null) {
                object = species.getTypes();
            }
            $this$map$iv = object;
            showdownSpecies = this;
            boolean $i$f$map = false;
            Object $i$f$toTypedArray = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            $i$f$mapTo = false;
            Iterator iterator = $this$mapTo$iv$iv.iterator();
            while (iterator.hasNext()) {
                String string;
                item$iv$iv2 = iterator.next();
                it2 = (ElementalType)item$iv$iv2;
                collection = destination$iv$iv;
                boolean bl = false;
                String string2 = ((ElementalType)it2).getName();
                if (((CharSequence)string2).length() > 0) {
                    void p0;
                    char c = string2.charAt(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean bl2 = false;
                    String string3 = String.valueOf((char)p0);
                    Intrinsics.checkNotNull((Object)string3, (String)"null cannot be cast to non-null type java.lang.String");
                    String string4 = string3.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"this as java.lang.String).toUpperCase(Locale.ROOT)");
                    StringBuilder stringBuilder2 = stringBuilder.append((Object)string4);
                    String string5 = string2;
                    int n7 = 1;
                    String string6 = string5.substring(n7);
                    Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"this as java.lang.String).substring(startIndex)");
                    string = stringBuilder2.append(string6).toString();
                } else {
                    string = string2;
                }
                collection.add(string);
            }
            showdownSpecies.types = (List)destination$iv$iv;
            ShowdownSpecies showdownSpecies3 = this;
            Object object2 = form2;
            if (object2 == null || (object2 = ((FormData)object2).getPreEvolution()) == null) {
                object2 = species.getPreEvolution();
            }
            if (object2 != null) {
                $this$mapTo$iv$iv = object2;
                showdownSpecies = showdownSpecies3;
                boolean bl = false;
                v12 = Intrinsics.areEqual((Object)it.getForm(), (Object)it.getSpecies().getStandardForm()) ? INSTANCE.createShowdownName(it.getSpecies()) : INSTANCE.createShowdownName(it.getSpecies()) + "-" + it.getForm().getName();
                showdownSpecies3 = showdownSpecies;
            } else {
                v12 = showdownSpecies3.preevo = null;
            }
            if ((set2 = form2) == null || (set2 = ((FormData)((Object)set2)).getEvolutions()) == null) {
                set2 = species.getEvolutions();
            }
            if (set2.isEmpty()) {
                list = CollectionsKt.emptyList();
            } else {
                $this$map$iv = new String[]{""};
                list = CollectionsKt.arrayListOf((Object[])$this$map$iv);
            }
            this.evos = list;
            this.nfe = !((Collection)this.evos).isEmpty();
            Object object3 = form2;
            if (object3 == null || (object3 = ((FormData)object3).getEggGroups()) == null) {
                object3 = species.getEggGroups();
            }
            $this$map$iv = (Iterable)object3;
            showdownSpecies = this;
            $i$f$map = false;
            it = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            $i$f$mapTo = false;
            iterator = $this$mapTo$iv$iv.iterator();
            while (iterator.hasNext()) {
                item$iv$iv2 = iterator.next();
                it2 = (EggGroup)((Object)item$iv$iv2);
                collection = destination$iv$iv;
                boolean bl = false;
                collection.add(((EggGroup)((Object)it2)).getShowdownID$common());
            }
            showdownSpecies.eggGroups = (List)destination$iv$iv;
            FormData formData3 = form2;
            float f = formData3 != null ? formData3.getMaleRatio() : species.getMaleRatio();
            String string = f == 0.0f ? "F" : (f == 1.0f ? "M" : (this.gender = (f == -1.0f ? true : f == 1.125f) ? "N" : null));
            if (this.gender == null) {
                Pair[] pairArray = new Pair[2];
                FormData formData4 = form2;
                pairArray[0] = TuplesKt.to((Object)"maleRatio", (Object)Float.valueOf(formData4 != null ? formData4.getMaleRatio() : species.getMaleRatio()));
                FormData formData5 = form2;
                pairArray[1] = TuplesKt.to((Object)"femaleRation", (Object)Float.valueOf(1.0f - (formData5 != null ? formData5.getMaleRatio() : species.getMaleRatio())));
                map = MapsKt.mapOf((Pair[])pairArray);
            } else {
                map = null;
            }
            this.genderRatio = map;
            Pair[] pairArray = new Pair[6];
            Object object4 = form2;
            if (object4 != null && (object4 = ((FormData)object4).getBaseStats()) != null && (object4 = (Integer)object4.get(Stats.HP)) != null) {
                n6 = (Integer)object4;
            } else {
                Integer n8 = species.getBaseStats().get(Stats.HP);
                n6 = n8 != null ? n8 : 1;
            }
            pairArray[0] = TuplesKt.to((Object)"hp", (Object)n6);
            Object object5 = form2;
            if (object5 != null && (object5 = ((FormData)object5).getBaseStats()) != null && (object5 = (Integer)object5.get(Stats.ATTACK)) != null) {
                n5 = (Integer)object5;
            } else {
                Integer n9 = species.getBaseStats().get(Stats.ATTACK);
                n5 = n9 != null ? n9 : 1;
            }
            pairArray[1] = TuplesKt.to((Object)"atk", (Object)n5);
            Object object6 = form2;
            if (object6 != null && (object6 = ((FormData)object6).getBaseStats()) != null && (object6 = (Integer)object6.get(Stats.DEFENCE)) != null) {
                n4 = (Integer)object6;
            } else {
                Integer n10 = species.getBaseStats().get(Stats.DEFENCE);
                n4 = n10 != null ? n10 : 1;
            }
            pairArray[2] = TuplesKt.to((Object)"def", (Object)n4);
            Object object7 = form2;
            if (object7 != null && (object7 = ((FormData)object7).getBaseStats()) != null && (object7 = (Integer)object7.get(Stats.SPECIAL_ATTACK)) != null) {
                n3 = (Integer)object7;
            } else {
                Integer n11 = species.getBaseStats().get(Stats.SPECIAL_ATTACK);
                n3 = n11 != null ? n11 : 1;
            }
            pairArray[3] = TuplesKt.to((Object)"spa", (Object)n3);
            Object object8 = form2;
            if (object8 != null && (object8 = ((FormData)object8).getBaseStats()) != null && (object8 = (Integer)object8.get(Stats.SPECIAL_DEFENCE)) != null) {
                n2 = (Integer)object8;
            } else {
                Integer n12 = species.getBaseStats().get(Stats.SPECIAL_DEFENCE);
                n2 = n12 != null ? n12 : 1;
            }
            pairArray[4] = TuplesKt.to((Object)"spd", (Object)n2);
            Object object9 = form2;
            if (object9 != null && (object9 = ((FormData)object9).getBaseStats()) != null && (object9 = (Integer)object9.get(Stats.SPEED)) != null) {
                n = (Integer)object9;
            } else {
                Integer n13 = species.getBaseStats().get(Stats.SPEED);
                n = n13 != null ? n13 : 1;
            }
            pairArray[5] = TuplesKt.to((Object)"spe", (Object)n);
            this.baseStats = MapsKt.mapOf((Pair[])pairArray);
            FormData formData6 = form2;
            this.heightm = (formData6 != null ? formData6.getHeight() : species.getHeight()) / (float)10;
            FormData formData7 = form2;
            this.weightkg = (formData7 != null ? formData7.getWeight() : species.getWeight()) / (float)10;
            this.maxHP = Intrinsics.areEqual((Object)species.showdownId(), (Object)"shedinja") ? Integer.valueOf(1) : null;
            FormData formData8 = form2;
            this.canGigantamax = (formData8 != null ? formData8.getGigantamaxMove() : null) != null ? form2.getGigantamaxMove().getName() : null;
            FormData formData9 = form2;
            this.cannotDynamax = formData9 != null ? formData9.getDynamaxBlocked() : species.getDynamaxBlocked();
            FormData formData10 = form2;
            this.requiredMove = formData10 != null ? formData10.getRequiredMove() : null;
            FormData formData11 = form2;
            this.requiredItem = formData11 != null ? formData11.getRequiredItem() : null;
            FormData formData12 = form2;
            this.requiredItems = formData12 != null ? formData12.getRequiredItems() : null;
        }

        public final int getNum() {
            return this.num;
        }

        @NotNull
        public final String getName() {
            return this.name;
        }

        @NotNull
        public final String getBaseSpecies() {
            return this.baseSpecies;
        }

        @Nullable
        public final String getForme() {
            return this.forme;
        }

        @NotNull
        public final List<String> getOtherFormes() {
            return this.otherFormes;
        }

        @NotNull
        public final List<String> getFormeOrder() {
            return this.formeOrder;
        }

        @NotNull
        public final Map<String, String> getAbilities() {
            return this.abilities;
        }

        @NotNull
        public final List<String> getTypes() {
            return this.types;
        }

        @Nullable
        public final String getPreevo() {
            return this.preevo;
        }

        @NotNull
        public final List<String> getEvos() {
            return this.evos;
        }

        public final boolean getNfe() {
            return this.nfe;
        }

        @NotNull
        public final List<String> getEggGroups() {
            return this.eggGroups;
        }

        @Nullable
        public final String getGender() {
            return this.gender;
        }

        @Nullable
        public final Map<String, Float> getGenderRatio() {
            return this.genderRatio;
        }

        @NotNull
        public final Map<String, Integer> getBaseStats() {
            return this.baseStats;
        }

        public final float getHeightm() {
            return this.heightm;
        }

        public final float getWeightkg() {
            return this.weightkg;
        }

        @Nullable
        public final Integer getMaxHP() {
            return this.maxHP;
        }

        @Nullable
        public final String getCanGigantamax() {
            return this.canGigantamax;
        }

        public final boolean getCannotDynamax() {
            return this.cannotDynamax;
        }

        @Nullable
        public final String getRequiredMove() {
            return this.requiredMove;
        }

        @Nullable
        public final String getRequiredItem() {
            return this.requiredItem;
        }

        @Nullable
        public final List<String> getRequiredItems() {
            return this.requiredItems;
        }
    }
}

