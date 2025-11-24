/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapterParent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B'\u0012\u0006\u0010\u001f\u001a\u00020\u0019\u0012\u0006\u0010 \u001a\u00020\u001c\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b!\u0010\"J)\u0010\n\u001a\u00020\t2\u0012\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJG\u0010\u000f\u001a\u0004\u0018\u00018\u0002\"\u0004\b\u0001\u0010\f\"\u000e\b\u0002\u0010\r*\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u000e\u001a\u00020\t2\u000e\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u000f\u0010\u0010JA\u0010\u0012\u001a\u0004\u0018\u00018\u0002\"\b\b\u0001\u0010\f*\u00020\u0011\"\u000e\b\u0002\u0010\r*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001f\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0016\u0010\u0017J3\u0010\u0016\u001a\u00020\u00152\u0012\u0010\u0006\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0018R\u0014\u0010\u001a\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001d\u001a\u00020\u001c8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\u001bR\u0014\u0010 \u001a\u00020\u001c8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\u001e\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/OneToOneFileStoreAdapter;", "S", "Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;", "Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapterParent;", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "storeClass", "Ljava/util/UUID;", "uuid", "Ljava/io/File;", "getFile", "(Ljava/lang/Class;Ljava/util/UUID;)Ljava/io/File;", "E", "T", "file", "load", "(Ljava/io/File;Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "provide", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "serialized", "", "save", "(Ljava/io/File;Ljava/lang/Object;)V", "(Ljava/lang/Class;Ljava/util/UUID;Ljava/lang/Object;)V", "", "fileExtension", "Ljava/lang/String;", "", "folderPerClass", "Z", "rootFolder", "useNestedFolders", "<init>", "(Ljava/lang/String;ZZLjava/lang/String;)V", "common"})
public abstract class OneToOneFileStoreAdapter<S>
extends CobblemonAdapterParent<S>
implements FileStoreAdapter<S> {
    @NotNull
    private final String rootFolder;
    private final boolean useNestedFolders;
    private final boolean folderPerClass;
    @NotNull
    private final String fileExtension;

    public OneToOneFileStoreAdapter(@NotNull String rootFolder, boolean useNestedFolders, boolean folderPerClass, @NotNull String fileExtension) {
        Intrinsics.checkNotNullParameter((Object)rootFolder, (String)"rootFolder");
        Intrinsics.checkNotNullParameter((Object)fileExtension, (String)"fileExtension");
        this.rootFolder = rootFolder;
        this.useNestedFolders = useNestedFolders;
        this.folderPerClass = folderPerClass;
        this.fileExtension = fileExtension;
    }

    public abstract void save(@NotNull File var1, S var2);

    @Nullable
    public abstract <E, T extends PokemonStore<E>> T load(@NotNull File var1, @NotNull Class<? extends T> var2, @NotNull UUID var3);

    @NotNull
    public final File getFile(@NotNull Class<? extends PokemonStore<?>> storeClass, @NotNull UUID uuid2) {
        Object object;
        Object subfolder1;
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        String string = storeClass.getSimpleName();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"storeClass.simpleName");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String className = string2;
        Object object2 = subfolder1 = this.folderPerClass ? className + "/" : "";
        if (this.useNestedFolders) {
            String string3 = uuid2.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"uuid.toString()");
            String string4 = string3;
            int n = 0;
            int n2 = 2;
            String string5 = string4.substring(n, n2);
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
            object = string5 + "/";
        } else {
            object = "";
        }
        String subfolder2 = object;
        Object folder = !StringsKt.endsWith$default((String)this.rootFolder, (String)"/", (boolean)false, (int)2, null) ? this.rootFolder + "/" : this.rootFolder;
        String fileName = this.folderPerClass ? uuid2 + "." + this.fileExtension : uuid2 + "-" + className + "." + this.fileExtension;
        File file = new File((String)folder + (String)subfolder1 + subfolder2, fileName);
        file.getParentFile().mkdirs();
        return file;
    }

    @Override
    public void save(@NotNull Class<? extends PokemonStore<?>> storeClass, @NotNull UUID uuid2, S serialized) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        File file = this.getFile(storeClass, uuid2);
        File tempFile = new File(file.getAbsolutePath() + ".temp");
        tempFile.createNewFile();
        this.save(tempFile, serialized);
        FilesKt.copyTo$default((File)tempFile, (File)file, (boolean)true, (int)0, (int)4, null);
        tempFile.delete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T provide(@NotNull Class<T> storeClass, @NotNull UUID uuid2) {
        PokemonStore pokemonStore;
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        File file = this.getFile(storeClass, uuid2);
        File tempFile = new File(file.getAbsolutePath() + ".temp");
        if (tempFile.exists()) {
            try {
                T tempLoaded = this.load(tempFile, storeClass, uuid2);
                if (tempLoaded != null) {
                    this.save(file, this.serialize(tempLoaded));
                    T t = tempLoaded;
                    return t;
                }
            }
            finally {
                tempFile.delete();
            }
        }
        if (file.exists()) {
            T t = this.load(file, storeClass, uuid2);
            pokemonStore = (PokemonStore)t;
            if (t == null) {
                OneToOneFileStoreAdapter it = this;
                boolean bl = false;
                Cobblemon.INSTANCE.getLOGGER().error("Pok\u00e9mon save file for " + storeClass.getSimpleName() + " (" + uuid2 + ") was corrupted. A fresh file will be created.");
                Object[] objectArray = new Class[]{UUID.class};
                Constructor<T> constructor = storeClass.getConstructor((Class<?>[])objectArray);
                objectArray = new Object[]{uuid2};
                pokemonStore = (PokemonStore)constructor.newInstance(objectArray);
            }
        } else {
            pokemonStore = null;
        }
        return (T)pokemonStore;
    }
}

