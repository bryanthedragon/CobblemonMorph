/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureAssignment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesFeatureAssignmentSyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b5\u00106J\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000e\u001a\u00020\r2\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000b0\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0010\u001a\u00020\r2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00020\tH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0017\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014R&\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000b0\u00158\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\u00020\u00188\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\n8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R \u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00000!8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u00068\u0016X\u0096D\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u001a\u0010+\u001a\u00020*8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R>\u00101\u001a&\u0012\f\u0012\n 0*\u0004\u0018\u00010\u00020\u0002 0*\u0012\u0012\f\u0012\n 0*\u0004\u0018\u00010\u00020\u0002\u0018\u00010/0/8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b1\u00102\u001a\u0004\b3\u00104\u00a8\u00067"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignments;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignment;", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "", "", "getFeatures", "(Lcom/cobblemon/mod/common/pokemon/Species;)Ljava/util/Set;", "", "Lnet/minecraft/resources/ResourceLocation;", "", "data", "", "loadOnClient", "(Ljava/util/Map;)V", "reload", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "assignments", "Ljava/util/Map;", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "kotlin.jvm.PlatformType", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpeciesFeatureAssignments.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureAssignments.kt\ncom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignments\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,64:1\n1855#2:65\n1855#2:66\n1856#2:74\n1856#2:75\n361#3,7:67\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureAssignments.kt\ncom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignments\n*L\n50#1:65\n51#1:66\n51#1:74\n50#1:75\n52#1:67,7\n*E\n"})
public final class SpeciesFeatureAssignments
implements JsonDataRegistry<SpeciesFeatureAssignment> {
    @NotNull
    public static final SpeciesFeatureAssignments INSTANCE = new SpeciesFeatureAssignments();
    @NotNull
    private static final ResourceLocation id = MiscUtils.cobblemonResource("species_feature_assignments");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<SpeciesFeatureAssignments> observable = new SimpleObservable();
    @NotNull
    private static final Gson gson;
    private static final TypeToken<SpeciesFeatureAssignment> typeToken;
    @NotNull
    private static final String resourcePath;
    @NotNull
    private static final Map<ResourceLocation, Set<String>> assignments;

    private SpeciesFeatureAssignments() {
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
    public SimpleObservable<SpeciesFeatureAssignments> getObservable() {
        return observable;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    public TypeToken<SpeciesFeatureAssignment> getTypeToken() {
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
        CobblemonNetwork.INSTANCE.sendPacket(player, new SpeciesFeatureAssignmentSyncPacket(assignments));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void reload(@NotNull Map<ResourceLocation, SpeciesFeatureAssignment> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        Iterable $this$forEach$iv = data.values();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpeciesFeatureAssignment it = (SpeciesFeatureAssignment)element$iv;
            boolean bl = false;
            Iterable $this$forEach$iv2 = it.getPokemon();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Object object;
                void $this$getOrPut$iv;
                String pokemon = (String)element$iv2;
                boolean bl2 = false;
                Map<ResourceLocation, Set<String>> map = assignments;
                ResourceLocation key$iv = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(pokemon, null, 1, null);
                boolean $i$f$getOrPut = false;
                Object value$iv = $this$getOrPut$iv.get(key$iv);
                if (value$iv == null) {
                    boolean bl3 = false;
                    Set answer$iv = new LinkedHashSet();
                    $this$getOrPut$iv.put(key$iv, answer$iv);
                    object = answer$iv;
                } else {
                    object = value$iv;
                }
                ((Set)object).addAll((Collection)it.getFeatures());
            }
        }
        SpeciesFeatureAssignments[] speciesFeatureAssignmentsArray = new SpeciesFeatureAssignments[]{this};
        this.getObservable().emit((SpeciesFeatureAssignments[])speciesFeatureAssignmentsArray);
    }

    public final void loadOnClient(@NotNull Map<ResourceLocation, ? extends Set<String>> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        assignments.clear();
        assignments.putAll(data);
    }

    @NotNull
    public final Set<String> getFeatures(@NotNull Species species) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Set set2 = assignments.get(species.getResourceIdentifier());
        if (set2 == null) {
            set2 = SetsKt.emptySet();
        }
        return set2;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"GsonBuilder().setPrettyPrinting().create()");
        gson = gson2;
        typeToken = TypeToken.get(SpeciesFeatureAssignment.class);
        resourcePath = "species_feature_assignments";
        assignments = new LinkedHashMap();
    }
}

