/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.PrecalculationResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.RootPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningPrecalculation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00040\u0003B\u000f\u0012\u0006\u00100\u001a\u00020\n\u00a2\u0006\u0004\bN\u0010OJ)\u0010\b\u001a\u00020\u00002\u001a\u0010\u0007\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\"\u0006\u0012\u0002\b\u00030\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\f\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u000eH\u0096\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\r\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0012\u0010\u0013J#\u0010\u0017\u001a\u00020\u00112\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001b\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u001b2\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010 \u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b \u0010!R\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\"8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u001a\u0010(\u001a\u00020'8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020\u00158\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0017\u00100\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b0\u00101\u001a\u0004\b2\u00103R \u00105\u001a\b\u0012\u0004\u0012\u00020\u0000048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108R&\u0010:\u001a\u0006\u0012\u0002\b\u0003098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R!\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\"8\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010$\u001a\u0004\b@\u0010&R\"\u0010B\u001a\n A*\u0004\u0018\u00010\n0\n8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bB\u00101\u001a\u0004\bC\u00103R\u001a\u0010E\u001a\u00020D8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bG\u0010HR>\u0010J\u001a&\u0012\f\u0012\n A*\u0004\u0018\u00010\u00020\u0002 A*\u0012\u0012\f\u0012\n A*\u0004\u0018\u00010\u00020\u0002\u0018\u00010I0I8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bJ\u0010K\u001a\u0004\bL\u0010M\u00a8\u0006P"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/api/spawning/SpawnSet;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "precalculators", "addPrecalculators", "([Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;)Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "", "newName", "copy", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "", "iterator", "()Ljava/util/Iterator;", "", "precalculate", "()V", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "reload", "(Ljava/util/Map;)V", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "retrieve", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Ljava/util/List;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "details", "Ljava/util/List;", "getDetails", "()Ljava/util/List;", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "precalculation", "Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "getPrecalculation", "()Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "setPrecalculation", "(Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;)V", "getPrecalculators", "kotlin.jvm.PlatformType", "resourcePath", "getResourcePath", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "(Ljava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawnPool.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnPool.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnPool\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,110:1\n766#2:111\n857#2,2:112\n*S KotlinDebug\n*F\n+ 1 SpawnPool.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnPool\n*L\n49#1:111\n49#1:112,2\n*E\n"})
public final class SpawnPool
implements JsonDataRegistry<SpawnSet>,
Iterable<SpawnDetail>,
KMappedMarker {
    @NotNull
    private final String name;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final PackType type;
    @NotNull
    private final SimpleObservable<SpawnPool> observable;
    @NotNull
    private final Gson gson;
    private final TypeToken<SpawnSet> typeToken;
    private final String resourcePath;
    @NotNull
    private final List<SpawnDetail> details;
    @NotNull
    private PrecalculationResult<?> precalculation;
    @NotNull
    private final List<SpawningPrecalculation<?>> precalculators;

    public SpawnPool(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this.name = name;
        this.id = MiscUtilsKt.cobblemonResource("spawn_pool_" + this.name);
        this.type = PackType.SERVER_DATA;
        this.observable = new SimpleObservable();
        Gson gson2 = SpawnLoader.INSTANCE.getGson();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"SpawnLoader.gson");
        this.gson = gson2;
        this.typeToken = TypeToken.get(SpawnSet.class);
        this.resourcePath = this.getId().m_135815_();
        this.details = new ArrayList();
        this.precalculation = RootPrecalculation.INSTANCE.generate(this.details, CollectionsKt.emptyList());
        this.precalculators = new ArrayList();
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return this.type;
    }

    @NotNull
    public SimpleObservable<SpawnPool> getObservable() {
        return this.observable;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return this.gson;
    }

    @Override
    public TypeToken<SpawnSet> getTypeToken() {
        return this.typeToken;
    }

    @Override
    public String getResourcePath() {
        return this.resourcePath;
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void reload(@NotNull Map<ResourceLocation, SpawnSet> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        this.details.clear();
        for (SpawnSet set2 : data.values()) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            Iterable iterable = set2;
            List<SpawnDetail> list = this.details;
            boolean $i$f$filter = false;
            void var6_6 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                SpawnDetail it = (SpawnDetail)element$iv$iv;
                boolean bl = false;
                if (!it.isValid()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list.addAll((List)destination$iv$iv);
        }
        this.precalculate();
    }

    @NotNull
    public final List<SpawnDetail> getDetails() {
        return this.details;
    }

    @NotNull
    public final PrecalculationResult<?> getPrecalculation() {
        return this.precalculation;
    }

    public final void setPrecalculation(@NotNull PrecalculationResult<?> precalculationResult) {
        Intrinsics.checkNotNullParameter(precalculationResult, (String)"<set-?>");
        this.precalculation = precalculationResult;
    }

    @NotNull
    public final List<SpawningPrecalculation<?>> getPrecalculators() {
        return this.precalculators;
    }

    @Override
    @NotNull
    public Iterator<SpawnDetail> iterator() {
        return this.details.iterator();
    }

    @NotNull
    public final SpawnPool addPrecalculators(SpawningPrecalculation<?> ... precalculators) {
        Intrinsics.checkNotNullParameter(precalculators, (String)"precalculators");
        CollectionsKt.addAll((Collection)this.precalculators, (Object[])precalculators);
        this.precalculate();
        return this;
    }

    public final void precalculate() {
        this.precalculation = this.precalculators.isEmpty() ? RootPrecalculation.INSTANCE.generate(this.details, CollectionsKt.emptyList()) : ((SpawningPrecalculation)CollectionsKt.first(this.precalculators)).generate(this.details, this.precalculators.subList(1, this.precalculators.size()));
    }

    @NotNull
    public final List<SpawnDetail> retrieve(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return this.precalculation.retrieve(ctx);
    }

    @NotNull
    public final SpawnPool copy(@NotNull String newName) {
        Intrinsics.checkNotNullParameter((Object)newName, (String)"newName");
        SpawnPool copy = new SpawnPool(newName);
        copy.details.addAll((Collection<SpawnDetail>)this.details);
        copy.precalculators.addAll((Collection)this.precalculators);
        copy.precalculation = this.precalculation;
        return copy;
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }
}

