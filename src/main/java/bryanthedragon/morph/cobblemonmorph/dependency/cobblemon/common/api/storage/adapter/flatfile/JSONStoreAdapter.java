/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.OneToOneFileStoreAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\u0006\u0010\u001d\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJG\u0010\f\u001a\u0004\u0018\u00018\u0001\"\u0004\b\u0000\u0010\u0003\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00010\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J1\u0010\u0014\u001a\u00020\u0002\"\b\b\u0000\u0010\u0003*\u00020\u0012\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0013\u001a\u00028\u0001H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0018\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/JSONStoreAdapter;", "Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/OneToOneFileStoreAdapter;", "Lcom/google/gson/JsonObject;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/io/File;", "file", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "load", "(Ljava/io/File;Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "serialized", "", "save", "(Ljava/io/File;Lcom/google/gson/JsonObject;)V", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "store", "serialize", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;)Lcom/google/gson/JsonObject;", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "", "rootFolder", "", "useNestedFolders", "folderPerClass", "<init>", "(Ljava/lang/String;ZZLcom/google/gson/Gson;)V", "common"})
@SourceDebugExtension(value={"SMAP\nJSONStoreAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JSONStoreAdapter.kt\ncom/cobblemon/mod/common/api/storage/adapter/flatfile/JSONStoreAdapter\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,62:1\n17#2:63\n*S KotlinDebug\n*F\n+ 1 JSONStoreAdapter.kt\ncom/cobblemon/mod/common/api/storage/adapter/flatfile/JSONStoreAdapter\n*L\n49#1:63\n*E\n"})
public class JSONStoreAdapter
extends OneToOneFileStoreAdapter<JsonObject> {
    @NotNull
    private final Gson gson;

    public JSONStoreAdapter(@NotNull String rootFolder, boolean useNestedFolders, boolean folderPerClass, @NotNull Gson gson2) {
        Intrinsics.checkNotNullParameter((Object)rootFolder, (String)"rootFolder");
        Intrinsics.checkNotNullParameter((Object)gson2, (String)"gson");
        super(rootFolder, useNestedFolders, folderPerClass, "json");
        this.gson = gson2;
    }

    public /* synthetic */ JSONStoreAdapter(String string, boolean bl, boolean bl2, Gson gson2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            Gson gson3 = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            Intrinsics.checkNotNullExpressionValue((Object)gson3, (String)"GsonBuilder().setPrettyP\u2026leHtmlEscaping().create()");
            gson2 = gson3;
        }
        this(string, bl, bl2, gson2);
    }

    @Override
    @NotNull
    public <E extends StorePosition, T extends PokemonStore<E>> JsonObject serialize(@NotNull T store) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        return store.saveToJSON(new JsonObject());
    }

    @Override
    public void save(@NotNull File file, @NotNull JsonObject serialized) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        Intrinsics.checkNotNullParameter((Object)serialized, (String)"serialized");
        PrintWriter pw = new PrintWriter(file);
        String json = this.gson.toJson((JsonElement)serialized);
        pw.write(json);
        pw.flush();
        pw.close();
    }

    @Override
    @Nullable
    public <E, T extends PokemonStore<E>> T load(@NotNull File file, @NotNull Class<? extends T> storeClass, @NotNull UUID uuid2) {
        Object[] objectArray;
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        try {
            Object object;
            BufferedReader br = new BufferedReader(new FileReader(file));
            Gson $this$fromJson$iv = this.gson;
            boolean $i$f$fromJson = false;
            JsonObject json = (JsonObject)$this$fromJson$iv.fromJson((Reader)br, JsonObject.class);
            br.close();
            try {
                object = new Class[]{UUID.class, UUID.class};
                Constructor<T> constructor = storeClass.getConstructor((Class<?>[])object);
                object = new Object[]{uuid2, uuid2};
                object = (PokemonStore)constructor.newInstance(object);
            }
            catch (NoSuchMethodException exception) {
                Object[] objectArray2 = new Class[]{UUID.class};
                Constructor<T> constructor = storeClass.getConstructor((Class<?>[])objectArray2);
                objectArray2 = new Object[]{uuid2};
                object = (PokemonStore)constructor.newInstance(objectArray2);
            }
            Object[] store = object;
            Intrinsics.checkNotNullExpressionValue((Object)json, (String)"json");
            store.loadFromJSON(json);
            objectArray = store;
        }
        catch (Exception e) {
            objectArray = null;
        }
        return (T)objectArray;
    }
}

