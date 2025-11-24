/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.GlobalSpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureAssignments;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.StandardSpeciesFeatureSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpeciesFeatureProviderAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vec3dAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0010&\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bO\u0010PJ\u001b\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\b\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\u0006J\u001b\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u00020\t\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\t2\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0010\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0006J+\u0010\u0015\u001a\u00020\u00142\u001c\u0010\u0013\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00120\u0011\u00a2\u0006\u0004\b\u0015\u0010\u0016J!\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u00032\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J+\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u00032\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u001b\u001a\u00020\u001aH\u0002\u00a2\u0006\u0004\b\u0018\u0010\u001cJ#\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u001d2\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u001f\u0010 J'\u0010#\u001a\u00020\u00142\u0016\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020\u001d\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020!H\u0016\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010'\u001a\u00020\u00142\u0006\u0010&\u001a\u00020%H\u0016\u00a2\u0006\u0004\b'\u0010(J\u0015\u0010)\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b)\u0010*R$\u0010,\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020+8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b,\u0010-R\u001a\u0010/\u001a\u00020.8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u001a\u00103\u001a\u00020\u001d8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R \u00108\u001a\b\u0012\u0004\u0012\u00020\u0000078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b8\u00109\u001a\u0004\b:\u0010;R$\u0010<\u001a\u0012\u0012\u0004\u0012\u00020\u0003\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020+8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010-R\u001a\u0010=\u001a\u00020\u00038\u0016X\u0096D\u00a2\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@R\u001a\u0010B\u001a\u00020A8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bB\u0010C\u001a\u0004\bD\u0010ER$\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020F8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bG\u0010H\u001a\u0004\bI\u0010JR/\u0010L\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00020K0+8\u0006\u00a2\u0006\f\n\u0004\bL\u0010-\u001a\u0004\bM\u0010N\u00a8\u0006Q"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatures;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "", "name", "getCodeFeature", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "getFeature", "", "getFeatures", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "getFeaturesFor", "(Lcom/cobblemon/mod/common/pokemon/Species;)Ljava/util/List;", "getResourceFeature", "", "", "entries", "", "loadOnClient", "(Ljava/util/Collection;)V", "provider", "register", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;)V", "", "isCoded", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;Z)V", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "registerFromAssets", "(Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;)V", "", "data", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "unregister", "(Ljava/lang/String;)V", "", "codeFeatures", "Ljava/util/Map;", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "resourceFeatures", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "Ljava/lang/Class;", "types", "getTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpeciesFeatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatures.kt\ncom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatures\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,113:1\n1855#2,2:114\n1549#2:116\n1620#2,3:117\n1603#2,9:120\n1855#2:129\n1856#2:131\n1612#2:132\n1603#2,9:133\n1855#2:142\n1856#2:144\n1612#2:145\n1603#2,9:146\n1855#2:155\n1856#2:157\n1612#2:158\n1#3:130\n1#3:143\n1#3:156\n1#3:159\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatures.kt\ncom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatures\n*L\n67#1:114,2\n75#1:116\n75#1:117,3\n78#1:120,9\n78#1:129\n78#1:131\n78#1:132\n80#1:133,9\n80#1:142\n80#1:144\n80#1:145\n82#1:146,9\n82#1:155\n82#1:157\n82#1:158\n78#1:130\n80#1:143\n82#1:156\n*E\n"})
public final class SpeciesFeatures
implements JsonDataRegistry<SpeciesFeatureProvider<?>> {
    @NotNull
    public static final SpeciesFeatures INSTANCE = new SpeciesFeatures();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("species_features");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<SpeciesFeatures> observable = new SimpleObservable();
    @NotNull
    private static final Map<String, Class<? extends SpeciesFeatureProvider<?>>> types = new LinkedHashMap();
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

    private SpeciesFeatures() {
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

    @NotNull
    public final Map<String, Class<? extends SpeciesFeatureProvider<?>>> getTypes() {
        return types;
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
        CobblemonNetwork.INSTANCE.sendPacket(player, new StandardSpeciesFeatureSyncPacket(MapsKt.plus(codeFeatures, resourceFeatures)));
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
        data.forEach((arg_0, arg_1) -> SpeciesFeatures.reload$lambda$0((Function2)new Function2<ResourceLocation, SpeciesFeatureProvider<?>, Unit>(this){

            public final void invoke(@NotNull ResourceLocation p0, @NotNull SpeciesFeatureProvider<?> p1) {
                Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
                Intrinsics.checkNotNullParameter(p1, (String)"p1");
                SpeciesFeatures.access$registerFromAssets((SpeciesFeatures)this.receiver, p0, p1);
            }
        }, arg_0, arg_1));
    }

    @Nullable
    public final SpeciesFeatureProvider<?> getCodeFeature(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return resourceFeatures.get(name);
    }

    @Nullable
    public final SpeciesFeatureProvider<?> getResourceFeature(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return codeFeatures.get(name);
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
    @NotNull
    public final List<SpeciesFeatureProvider<?>> getFeaturesFor(@NotNull Species species) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNullTo$iv$iv2;
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Iterable $this$mapNotNull$iv = species.getFeatures();
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo22 = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv2;
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
        List mentionedFeatures = (List)destination$iv$iv;
        List<SpeciesFeatureProvider<? extends SpeciesFeature>> globalFeatures = GlobalSpeciesFeatures.INSTANCE.getFeatures();
        Iterable $this$mapNotNull$iv2 = SpeciesFeatureAssignments.INSTANCE.getFeatures(species);
        boolean $i$f$mapNotNull2 = false;
        Iterable $i$f$mapNotNullTo22 = $this$mapNotNull$iv2;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv2 = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach2 = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv2.iterator();
        while (iterator2.hasNext()) {
            SpeciesFeatureProvider<? extends SpeciesFeature> it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            String p0 = (String)element$iv$iv;
            boolean bl4 = false;
            if (this.getFeature(p0) == null) continue;
            boolean bl5 = false;
            destination$iv$iv2.add(it$iv$iv);
        }
        List assignedFeatures = (List)destination$iv$iv2;
        return CollectionsKt.distinct((Iterable)CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)mentionedFeatures, (Iterable)globalFeatures), (Iterable)assignedFeatures));
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

    public static final /* synthetic */ void access$registerFromAssets(SpeciesFeatures $this, ResourceLocation identifier, SpeciesFeatureProvider provider) {
        $this.registerFromAssets(identifier, provider);
    }

    static {
        Gson gson2 = new GsonBuilder().setPrettyPrinting().registerTypeAdapter((Type)((Object)SpeciesFeatureProvider.class), (Object)SpeciesFeatureProviderAdapter.INSTANCE).registerTypeAdapter((Type)((Object)Vec3.class), (Object)Vec3dAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GsonBuilder()\n        .s\u2026dapter)\n        .create()");
        gson = gson2;
        TypeToken typeToken = TypeToken.get(SpeciesFeatureProvider.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(SpeciesFeatureProvider::class.java)");
        SpeciesFeatures.typeToken = typeToken;
        resourcePath = "species_features";
    }
}

