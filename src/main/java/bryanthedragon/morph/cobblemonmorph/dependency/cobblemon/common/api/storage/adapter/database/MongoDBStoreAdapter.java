/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoCollection
 *  com.mongodb.client.model.ReplaceOptions
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.storage.LevelResource
 *  org.bson.Document
 *  org.bson.conversions.Bson
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.database;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapterParent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.JSONStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.NBTStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\u0017\u0012\u0006\u0010%\u001a\u00020$\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b)\u0010*J\u000f\u0010\u0005\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006J)\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0012\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\b0\u0007H\u0014\u00a2\u0006\u0004\b\f\u0010\rJA\u0010\u0013\u001a\u0004\u0018\u00018\u0001\"\b\b\u0000\u0010\u000f*\u00020\u000e\"\u000e\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u00028\u00000\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00072\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J3\u0010\u0017\u001a\u00020\u00162\u0012\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\b0\u00072\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J1\u0010\u001a\u001a\u00020\u0002\"\b\b\u0000\u0010\u000f*\u00020\u000e\"\u000e\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u00028\u00000\b2\u0006\u0010\u0019\u001a\u00028\u0001H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u00048\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010\u0006R\u001a\u0010%\u001a\u00020$8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/database/MongoDBStoreAdapter;", "Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapterParent;", "Lcom/google/gson/JsonObject;", "Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;", "Lcom/google/gson/Gson;", "createGson", "()Lcom/google/gson/Gson;", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "storeClass", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "getCollection", "(Ljava/lang/Class;)Lcom/mongodb/client/MongoCollection;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "T", "Ljava/util/UUID;", "uuid", "provide", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "serialized", "", "save", "(Ljava/lang/Class;Ljava/util/UUID;Lcom/google/gson/JsonObject;)V", "store", "serialize", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;)Lcom/google/gson/JsonObject;", "", "databaseName", "Ljava/lang/String;", "getDatabaseName", "()Ljava/lang/String;", "gson", "Lcom/google/gson/Gson;", "getGson", "Lcom/mongodb/client/MongoClient;", "mongoClient", "Lcom/mongodb/client/MongoClient;", "getMongoClient", "()Lcom/mongodb/client/MongoClient;", "<init>", "(Lcom/mongodb/client/MongoClient;Ljava/lang/String;)V", "common"})
public class MongoDBStoreAdapter
extends CobblemonAdapterParent<JsonObject>
implements FileStoreAdapter<JsonObject> {
    @NotNull
    private final MongoClient mongoClient;
    @NotNull
    private final String databaseName;
    @NotNull
    private final Gson gson;

    public MongoDBStoreAdapter(@NotNull MongoClient mongoClient, @NotNull String databaseName) {
        Intrinsics.checkNotNullParameter((Object)mongoClient, (String)"mongoClient");
        Intrinsics.checkNotNullParameter((Object)databaseName, (String)"databaseName");
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
        this.gson = this.createGson();
    }

    @NotNull
    protected final MongoClient getMongoClient() {
        return this.mongoClient;
    }

    @NotNull
    protected final String getDatabaseName() {
        return this.databaseName;
    }

    @NotNull
    protected final Gson getGson() {
        return this.gson;
    }

    @Override
    @NotNull
    public <E extends StorePosition, T extends PokemonStore<E>> JsonObject serialize(@NotNull T store) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        return store.saveToJSON(new JsonObject());
    }

    @Override
    public void save(@NotNull Class<? extends PokemonStore<?>> storeClass, @NotNull UUID uuid2, @NotNull JsonObject serialized) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)serialized, (String)"serialized");
        Document document = Document.parse((String)this.gson.toJson((JsonElement)serialized));
        Intrinsics.checkNotNullExpressionValue((Object)document, (String)"document");
        ((Map)document).put("uuid", uuid2.toString());
        ((Map)document).put("lastUpdated", new Date());
        MongoCollection<Document> collection = this.getCollection(storeClass);
        Document filter = new Document("uuid", (Object)uuid2.toString());
        collection.replaceOne((Bson)filter, (Object)document, new ReplaceOptions().upsert(true));
    }

    @Override
    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T provide(@NotNull Class<T> storeClass, @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        MinecraftServer minecraftServer = DistributionUtilsKt.server();
        Intrinsics.checkNotNull((Object)minecraftServer);
        MinecraftServer server = minecraftServer;
        File pokemonStoreRoot = server.m_129843_(LevelResource.f_78182_).resolve("pokemon").toFile();
        String string = pokemonStoreRoot.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"pokemonStoreRoot.absolutePath");
        JSONStoreAdapter jsonAdapter = new JSONStoreAdapter(string, true, true, null, 8, null);
        String string2 = pokemonStoreRoot.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"pokemonStoreRoot.absolutePath");
        NBTStoreAdapter nbtAdapter = new NBTStoreAdapter(string2, true, true);
        MongoCollection<Document> collection = this.getCollection(storeClass);
        Document filter = new Document("uuid", (Object)uuid2.toString());
        Document document = (Document)collection.find((Bson)filter).first();
        if (document != null) {
            Object object;
            JsonObject json = (JsonObject)this.gson.fromJson(document.toJson(), JsonObject.class);
            try {
                object = new Class[]{UUID.class, UUID.class};
                Constructor<T> constructor = storeClass.getConstructor((Class<?>[])object);
                object = new Object[]{uuid2, uuid2};
                object = (PokemonStore)constructor.newInstance(object);
            }
            catch (NoSuchMethodException exception) {
                Object[] objectArray = new Class[]{UUID.class};
                Constructor<T> constructor = storeClass.getConstructor((Class<?>[])objectArray);
                objectArray = new Object[]{uuid2};
                object = (PokemonStore)constructor.newInstance(objectArray);
            }
            Object[] store = object;
            Intrinsics.checkNotNullExpressionValue((Object)json, (String)"json");
            store.loadFromJSON(json);
            return (T)store;
        }
        T nbtStore = nbtAdapter.provide(storeClass, uuid2);
        if (nbtStore != null) {
            this.save(storeClass, uuid2, this.serialize(nbtStore));
            return nbtStore;
        }
        T jsonStore = jsonAdapter.provide(storeClass, uuid2);
        if (jsonStore != null) {
            this.save(storeClass, uuid2, this.serialize(jsonStore));
            return jsonStore;
        }
        return null;
    }

    @NotNull
    protected Gson createGson() {
        return new Gson();
    }

    @NotNull
    protected MongoCollection<Document> getCollection(@NotNull Class<? extends PokemonStore<?>> storeClass) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Class<? extends PokemonStore<?>> clazz = storeClass;
        String collectionName = Intrinsics.areEqual(clazz, PlayerPartyStore.class) ? "PlayerPartyCollection" : (Intrinsics.areEqual(clazz, PCStore.class) ? "PCCollection" : "OtherCollection");
        MongoCollection mongoCollection = this.mongoClient.getDatabase(this.databaseName).getCollection(collectionName);
        Intrinsics.checkNotNullExpressionValue((Object)mongoCollection, (String)"this.mongoClient.getData\u2026ollection(collectionName)");
        return mongoCollection;
    }
}

