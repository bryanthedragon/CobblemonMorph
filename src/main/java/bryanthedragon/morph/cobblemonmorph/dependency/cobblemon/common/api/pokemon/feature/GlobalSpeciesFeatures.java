/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.GlobalSpeciesFeatureSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpeciesFeatureProviderAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vec3dAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010&\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bL\u0010MJ\u001b\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\b\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\u0006J\u001b\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u00020\t\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\f\u0010\u0006J+\u0010\u0011\u001a\u00020\u00102\u001c\u0010\u000f\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u000e0\r\u00a2\u0006\u0004\b\u0011\u0010\u0012J-\u0010\u0016\u001a\u00020\u0010\"\b\b\u0000\u0010\u0013*\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J!\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u00032\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0019J+\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u00032\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u001b\u001a\u00020\u001aH\u0002\u00a2\u0006\u0004\b\u0016\u0010\u001cJ#\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001d2\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u001f\u0010 J'\u0010#\u001a\u00020\u00102\u0016\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u001d\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020!H\u0016\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010'\u001a\u00020\u00102\u0006\u0010&\u001a\u00020%H\u0016\u00a2\u0006\u0004\b'\u0010(J\u0015\u0010)\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b)\u0010*R$\u0010,\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020+8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b,\u0010-R\u001a\u0010/\u001a\u00020.8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u001a\u00103\u001a\u00020\u001d8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R \u00109\u001a\b\u0012\u0004\u0012\u000208078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<R$\u0010=\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020+8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010-R\u001a\u0010>\u001a\u00020\u00038\u0016X\u0096D\u00a2\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b@\u0010AR\u001a\u0010C\u001a\u00020B8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bE\u0010FR$\u0010H\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020G8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bH\u0010I\u001a\u0004\bJ\u0010K\u00a8\u0006N"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/GlobalSpeciesFeatures;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "", "name", "getCodeFeature", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "getFeature", "", "getFeatures", "()Ljava/util/List;", "getResourceFeature", "", "", "entries", "", "loadOnClient", "(Ljava/util/Collection;)V", "T", "Lkotlin/Function0;", "providerLambda", "register", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "provider", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;)V", "", "isCoded", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;Z)V", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "registerFromAssets", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;)V", "", "data", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "unregister", "(Ljava/lang/String;)V", "", "codeFeatures", "Ljava/util/Map;", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatures;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "resourceFeatures", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nGlobalSpeciesFeatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalSpeciesFeatures.kt\ncom/cobblemon/mod/common/api/pokemon/feature/GlobalSpeciesFeatures\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,108:1\n1855#2,2:109\n1603#2,9:111\n1855#2:120\n1856#2:122\n1612#2:123\n1549#2:124\n1620#2,3:125\n1#3:121\n1#3:128\n*S KotlinDebug\n*F\n+ 1 GlobalSpeciesFeatures.kt\ncom/cobblemon/mod/common/api/pokemon/feature/GlobalSpeciesFeatures\n*L\n61#1:109,2\n69#1:111,9\n69#1:120\n69#1:122\n69#1:123\n72#1:124\n72#1:125,3\n69#1:121\n*E\n"})
public final class GlobalSpeciesFeatures
implements JsonDataRegistry<SpeciesFeatureProvider<?>> {
    @NotNull
    public static final GlobalSpeciesFeatures INSTANCE = new GlobalSpeciesFeatures();
    @NotNull
    private static final ResourceLocation id = MiscUtils.cobblemonResource("global_species_features");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<SpeciesFeatures> observable = new SimpleObservable();
    @NotNull
    private static final Map<String, SpeciesFeatureProvider<?>> codeFeatures = new LinkedHashMap();
    @NotNull
    private static final Map<String, SpeciesFeatureProvider<?>> resourceFeatures = new LinkedHashMap();
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final TypeToken<SpeciesFeatureProvider<?>> typeToken;
    @NotNull
    private static final String resourcePath;

    private GlobalSpeciesFeatures() {
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

    @NotNull
    public SimpleObservable<SpeciesFeatures> getObservable() {
        return observable;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<SpeciesFeatureProvider<?>> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        CobblemonNetwork.INSTANCE.sendPacket(player, new GlobalSpeciesFeatureSyncPacket(MapsKt.plus(codeFeatures, resourceFeatures)));
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, ? extends SpeciesFeatureProvider<?>> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        Iterable $this$forEach$iv = CollectionsKt.toList((Iterable)resourceFeatures.keySet());
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String p0 = (String)element$iv;
            boolean bl = false;
            this.unregister(p0);
        }
        data.forEach((arg_0, arg_1) -> GlobalSpeciesFeatures.reload$lambda$0((Function2)new Function2<ResourceLocation, SpeciesFeatureProvider<?>, Unit>(this){

            public final void invoke(@NotNull ResourceLocation p0, @NotNull SpeciesFeatureProvider<?> p1) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                Intrinsics.checkNotNullParameter(p1, (String)"p1");
                GlobalSpeciesFeatures.access$registerFromAssets((GlobalSpeciesFeatures)this.receiver, p0, p1);
            }
        }, arg_0, arg_1));
    }

    @Nullable
    public final SpeciesFeatureProvider<?> getCodeFeature(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return codeFeatures.get(name);
    }

    @Nullable
    public final SpeciesFeatureProvider<?> getResourceFeature(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return resourceFeatures.get(name);
    }

    @Nullable
    public final SpeciesFeatureProvider<? extends SpeciesFeature> getFeature(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        SpeciesFeatureProvider<?> speciesFeatureProvider = this.getCodeFeature(name);
        if (speciesFeatureProvider == null) {
            speciesFeatureProvider = this.getResourceFeature(name);
        }
        return speciesFeatureProvider;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<SpeciesFeatureProvider<? extends SpeciesFeature>> getFeatures() {
        void $this$mapNotNullTo$iv$iv;
        Iterable $this$mapNotNull$iv = SetsKt.plus(resourceFeatures.keySet(), (Iterable)codeFeatures.keySet());
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            SpeciesFeatureProvider<? extends SpeciesFeature> it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            String p0 = (String)element$iv$iv;
            boolean bl2 = false;
            if (this.getFeature(p0) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    public final void loadOnClient(@NotNull Collection<? extends Map.Entry<String, ? extends SpeciesFeatureProvider<?>>> entries) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        Iterable iterable = entries;
        Map<String, SpeciesFeatureProvider<?>> map = codeFeatures;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            Map.Entry entry = (Map.Entry)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void var11_12 = it;
            collection.add(new Pair(var11_12.getKey(), var11_12.getValue()));
        }
        MapsKt.putAll(map, (Iterable)((List)destination$iv$iv));
    }

    private final void register(String name, SpeciesFeatureProvider<?> provider, boolean isCoded) {
        Map<String, SpeciesFeatureProvider<?>> mapping;
        Map<String, SpeciesFeatureProvider<?>> map = mapping = isCoded ? codeFeatures : resourceFeatures;
        if (provider instanceof AspectProvider) {
            AspectProvider.Companion.register((AspectProvider)((Object)provider));
        }
        if (provider instanceof CustomPokemonPropertyType) {
            CustomPokemonProperty.Companion.register((CustomPokemonPropertyType)((Object)provider));
        }
        mapping.put(name, provider);
    }

    public final void register(@NotNull String name, @NotNull SpeciesFeatureProvider<?> provider) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(provider, (String)"provider");
        this.register(name, provider, true);
    }

    public final <T extends SpeciesFeature> void register(@NotNull String name, @NotNull Function0<? extends T> providerLambda) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(providerLambda, (String)"providerLambda");
        this.register(name, new SpeciesFeatureProvider<T>(providerLambda){
            final /* synthetic */ Function0<T> $providerLambda;
            {
                this.$providerLambda = $providerLambda;
            }

            @NotNull
            public T invoke(@NotNull Pokemon pokemon) {
                Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
                return (T)((SpeciesFeature)this.$providerLambda.invoke());
            }

            @NotNull
            public T invoke(@NotNull CompoundTag nbt) {
                Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
                Object object = this.$providerLambda.invoke();
                SpeciesFeature $this$invoke_u24lambda_u240 = (SpeciesFeature)object;
                boolean bl = false;
                $this$invoke_u24lambda_u240.loadFromNBT(nbt);
                return (T)((SpeciesFeature)object);
            }

            @NotNull
            public T invoke(@NotNull JsonObject json) {
                Intrinsics.checkNotNullParameter((Object)json, (String)"json");
                Object object = this.$providerLambda.invoke();
                SpeciesFeature $this$invoke_u24lambda_u241 = (SpeciesFeature)object;
                boolean bl = false;
                $this$invoke_u24lambda_u241.loadFromJSON(json);
                return (T)((SpeciesFeature)object);
            }
        });
    }

    private final void registerFromAssets(ResourceLocation identifier, SpeciesFeatureProvider<?> provider) {
        String string = identifier.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.path");
        this.register(string, provider, false);
    }

    public final void unregister(@NotNull String name) {
        SpeciesFeatureProvider<?> speciesFeatureProvider;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        boolean coded = false;
        coded = true;
        SpeciesFeatureProvider<?> speciesFeatureProvider2 = this.getResourceFeature(name);
        if (speciesFeatureProvider2 != null) {
            SpeciesFeatureProvider<?> speciesFeatureProvider3;
            SpeciesFeatureProvider<?> it = speciesFeatureProvider3 = speciesFeatureProvider2;
            boolean bl = false;
            coded = false;
            speciesFeatureProvider = speciesFeatureProvider3;
        } else {
            speciesFeatureProvider = this.getCodeFeature(name);
            if (speciesFeatureProvider == null) {
                return;
            }
        }
        SpeciesFeatureProvider<?> value2 = speciesFeatureProvider;
        if (value2 instanceof AspectProvider) {
            AspectProvider.Companion.unregister((AspectProvider)((Object)value2));
        }
        if (value2 instanceof CustomPokemonPropertyType) {
            CustomPokemonProperty.Companion.unregister((CustomPokemonPropertyType)((Object)value2));
        }
        Map<String, SpeciesFeatureProvider<?>> mapping = coded ? codeFeatures : resourceFeatures;
        mapping.remove(name);
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    private static final void reload$lambda$0(Function2 $tmp0, Object p0, Object p1) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0, p1);
    }

    public static final /* synthetic */ void access$registerFromAssets(GlobalSpeciesFeatures $this, ResourceLocation identifier, SpeciesFeatureProvider provider) {
        $this.registerFromAssets(identifier, provider);
    }

    static {
        Gson gson2 = new GsonBuilder().setPrettyPrinting().registerTypeAdapter((Type)((Object)SpeciesFeatureProvider.class), (Object)SpeciesFeatureProviderAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Vec3.class), (Object)Vec3dAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GsonBuilder()\n        .s\u2026dapter)\n        .create()");
        gson = gson2;
        TypeToken typeToken = TypeToken.get(SpeciesFeatureProvider.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(SpeciesFeatureProvider::class.java)");
        GlobalSpeciesFeatures.typeToken = typeToken;
        resourcePath = "global_species_features";
    }
}

