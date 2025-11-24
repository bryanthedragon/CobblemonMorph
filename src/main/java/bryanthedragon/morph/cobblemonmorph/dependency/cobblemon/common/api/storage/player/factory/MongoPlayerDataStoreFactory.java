/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.client.MongoClient
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.MongoPlayerDataAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory.PlayerDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MapExtensionsKt;
import com.mongodb.client.MongoClient;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R \u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/factory/MongoPlayerDataStoreFactory;", "Lcom/cobblemon/mod/common/api/storage/player/factory/PlayerDataStoreFactory;", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "load", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "", "onPlayerDisconnect", "(Ljava/util/UUID;)V", "data", "save", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerData;)V", "saveAll", "()V", "Lcom/cobblemon/mod/common/api/storage/player/adapter/MongoPlayerDataAdapter;", "adapter", "Lcom/cobblemon/mod/common/api/storage/player/adapter/MongoPlayerDataAdapter;", "", "cache", "Ljava/util/Map;", "Lcom/mongodb/client/MongoClient;", "mongoClient", "", "databaseName", "<init>", "(Lcom/mongodb/client/MongoClient;Ljava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nMongoPlayerDataAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MongoPlayerDataAdapter.kt\ncom/cobblemon/mod/common/api/storage/player/factory/MongoPlayerDataStoreFactory\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,46:1\n215#2,2:47\n*S KotlinDebug\n*F\n+ 1 MongoPlayerDataAdapter.kt\ncom/cobblemon/mod/common/api/storage/player/factory/MongoPlayerDataStoreFactory\n*L\n38#1:47,2\n*E\n"})
public final class MongoPlayerDataStoreFactory
implements PlayerDataStoreFactory {
    @NotNull
    private final Map<UUID, PlayerData> cache;
    @NotNull
    private final MongoPlayerDataAdapter adapter;

    @SuppressWarnings("rawtypes")
    public MongoPlayerDataStoreFactory(@NotNull MongoClient mongoClient, @NotNull String databaseName) {
        Intrinsics.checkNotNullParameter((Object)mongoClient, (String)"mongoClient");
        Intrinsics.checkNotNullParameter((Object)databaseName, (String)"databaseName");
        this.cache = new LinkedHashMap();
        this.adapter = new MongoPlayerDataAdapter(mongoClient, databaseName);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public PlayerData load(@NotNull UUID uuid2) {
        PlayerData playerData;
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        if (this.cache.containsKey(uuid2)) {
            PlayerData playerData2 = this.cache.get(uuid2);
            Intrinsics.checkNotNull((Object)playerData2);
            playerData = playerData2;
        } else {
            void var2_2;
            PlayerData data = this.adapter.load(uuid2);
            this.cache.put(uuid2, data);
            playerData = var2_2;
        }
        return playerData;
    }

    @Override
    public void save(@NotNull PlayerData data) {
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        this.adapter.save(data);
    }

    @Override
    public void saveAll() {
        Map<UUID, PlayerData> $this$forEach$iv = this.cache;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<UUID, PlayerData>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, PlayerData> element$iv;
            Map.Entry<UUID, PlayerData> entry = element$iv = iterator.next();
            boolean bl = false;
            PlayerData pd = entry.getValue();
            this.adapter.save(pd);
        }
        MapExtensionsKt.removeIf(this.cache, saveAll.2.INSTANCE);
    }

    @Override
    public void onPlayerDisconnect(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this.cache.remove(uuid2);
    }
}

