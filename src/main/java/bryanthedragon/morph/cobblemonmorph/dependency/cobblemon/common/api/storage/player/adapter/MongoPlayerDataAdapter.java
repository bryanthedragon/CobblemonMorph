/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoCollection
 *  com.mongodb.client.model.ReplaceOptions
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  kotlin.reflect.KMutableProperty
 *  kotlin.reflect.full.KClasses
 *  net.minecraft.resources.ResourceLocation
 *  org.bson.Document
 *  org.bson.conversions.Bson
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.PlayerDataExtensionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.PlayerDataStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.full.KClasses;
import net.minecraft.resources.ResourceLocation;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0017\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\t\u0010\nR8\u0010\u000e\u001a&\u0012\f\u0012\n \r*\u0004\u0018\u00010\f0\f \r*\u0012\u0012\f\u0012\n \r*\u0004\u0018\u00010\f0\f\u0018\u00010\u000b0\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/adapter/MongoPlayerDataAdapter;", "Lcom/cobblemon/mod/common/api/storage/player/adapter/PlayerDataStoreAdapter;", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "load", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "playerData", "", "save", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerData;)V", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "kotlin.jvm.PlatformType", "collection", "Lcom/mongodb/client/MongoCollection;", "Lcom/mongodb/client/MongoClient;", "mongoClient", "", "databaseName", "<init>", "(Lcom/mongodb/client/MongoClient;Ljava/lang/String;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMongoPlayerData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MongoPlayerData.kt\ncom/cobblemon/mod/common/api/storage/player/adapter/MongoPlayerDataAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,67:1\n800#2,11:68\n766#2:79\n857#2,2:80\n1855#2,2:82\n1#3:84\n*S KotlinDebug\n*F\n+ 1 MongoPlayerData.kt\ncom/cobblemon/mod/common/api/storage/player/adapter/MongoPlayerDataAdapter\n*L\n47#1:68,11\n47#1:79\n47#1:80,2\n50#1:82,2\n*E\n"})
public final class MongoPlayerDataAdapter
implements PlayerDataStoreAdapter {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final MongoCollection<Document> collection;
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter((Type)((Object)PlayerDataExtension.class), (Object)PlayerDataExtensionAdapter.INSTANCE).registerTypeAdapter((Type)((Object)ResourceLocation.class), (Object)IdentifierAdapter.INSTANCE).create();

    public MongoPlayerDataAdapter(@NotNull MongoClient mongoClient, @NotNull String databaseName) {
        Intrinsics.checkNotNullParameter((Object)mongoClient, (String)"mongoClient");
        Intrinsics.checkNotNullParameter((Object)databaseName, (String)"databaseName");
        this.collection = mongoClient.getDatabase(databaseName).getCollection("PlayerDataCollection");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public PlayerData load(@NotNull UUID uuid2) {
        PlayerData playerData;
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Document filter = new Document("uuid", (Object)uuid2.toString());
        Document document = (Document)this.collection.find((Bson)filter).first();
        if (document != null) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            void $this$filterIsInstanceTo$iv$iv;
            String jsonStr = document.toJson();
            Object object = gson.fromJson(jsonStr, PlayerData.class);
            PlayerData it = (PlayerData)object;
            boolean bl = false;
            Iterable $this$filterIsInstance$iv = KClasses.getMemberProperties((KClass)Reflection.getOrCreateKotlinClass(it.getClass()));
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof KMutableProperty)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv;
            boolean $i$f$filter = false;
            $this$filterIsInstanceTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                KMutableProperty member = (KMutableProperty)element$iv$iv;
                boolean bl2 = false;
                Object[] objectArray = new Object[]{it};
                if (!(member.getGetter().call(objectArray) == null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            List newProps = (List)destination$iv$iv;
            if (!((Collection)newProps).isEmpty()) {
                PlayerData defaultData = PlayerData.Companion.defaultData(uuid2);
                Iterable $this$forEach$iv = newProps;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    KMutableProperty member = (KMutableProperty)element$iv;
                    boolean bl3 = false;
                    Object[] objectArray = new Object[2];
                    objectArray[0] = it;
                    Object[] objectArray2 = new Object[]{defaultData};
                    objectArray[1] = member.getGetter().call(objectArray2);
                    member.getSetter().call(objectArray);
                }
            }
            Object object2 = object;
            Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"{\n            val jsonSt\u2026}\n            }\n        }");
            playerData = (PlayerData)object2;
        } else {
            PlayerData playerData2;
            PlayerData p0 = playerData2 = PlayerData.Companion.defaultData(uuid2);
            boolean bl = false;
            this.save(p0);
            playerData = playerData2;
        }
        return playerData;
    }

    @Override
    public void save(@NotNull PlayerData playerData) {
        Intrinsics.checkNotNullParameter((Object)playerData, (String)"playerData");
        String jsonStr = gson.toJson((Object)playerData);
        Document document = Document.parse((String)jsonStr);
        document.put("uuid", (Object)playerData.getUuid().toString());
        Document filter = new Document("uuid", (Object)playerData.getUuid().toString());
        this.collection.replaceOne((Bson)filter, (Object)document, new ReplaceOptions().upsert(true));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/adapter/MongoPlayerDataAdapter$Companion;", "", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGson() {
            return gson;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

