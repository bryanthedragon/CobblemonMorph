/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtIo
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.OneToOneFileStoreAdapter;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u0012\u0006\u0010\u001a\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001b\u0010\u001cJG\u0010\f\u001a\u0004\u0018\u00018\u0001\"\u0004\b\u0000\u0010\u0003\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00010\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J1\u0010\u0014\u001a\u00020\u0002\"\b\b\u0000\u0010\u0003*\u00020\u0012\"\u000e\b\u0001\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0013\u001a\u00028\u0001H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/NBTStoreAdapter;", "Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/OneToOneFileStoreAdapter;", "Lnet/minecraft/nbt/CompoundTag;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/io/File;", "file", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "load", "(Ljava/io/File;Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "serialized", "", "save", "(Ljava/io/File;Lnet/minecraft/nbt/CompoundTag;)V", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "store", "serialize", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;)Lnet/minecraft/nbt/CompoundTag;", "", "rootFolder", "", "useNestedFolders", "folderPerClass", "<init>", "(Ljava/lang/String;ZZ)V", "common"})
public class NBTStoreAdapter
extends OneToOneFileStoreAdapter<CompoundTag> {
    public NBTStoreAdapter(@NotNull String rootFolder, boolean useNestedFolders, boolean folderPerClass) {
        Intrinsics.checkNotNullParameter((Object)rootFolder, (String)"rootFolder");
        super(rootFolder, useNestedFolders, folderPerClass, "dat");
    }

    @Override
    @NotNull
    public <E extends StorePosition, T extends PokemonStore<E>> CompoundTag serialize(@NotNull T store) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        return store.saveToNBT(new CompoundTag());
    }

    @Override
    public void save(@NotNull File file, @NotNull CompoundTag serialized) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        Intrinsics.checkNotNullParameter((Object)serialized, (String)"serialized");
        NbtIo.m_128944_((CompoundTag)serialized, (File)file);
    }

    @Override
    @Nullable
    public <E, T extends PokemonStore<E>> T load(@NotNull File file, @NotNull Class<? extends T> storeClass, @NotNull UUID uuid2) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        try {
            object = new Class[]{UUID.class};
            Constructor<T> constructor = storeClass.getConstructor((Class<?>)object);
            object = new Object[]{uuid2};
            object = (PokemonStore)constructor.newInstance((Object[])object);
        }
        catch (NoSuchMethodException exception) {
            Object[] objectArray = new Class[]{UUID.class};
            Constructor<T> constructor = storeClass.getConstructor((Class<?>[])objectArray);
            objectArray = new Object[]{uuid2};
            object = (PokemonStore)constructor.newInstance(objectArray);
        }
        Object store = object;
        try {
            CompoundTag nbt = NbtIo.m_128937_((File)file);
            Intrinsics.checkNotNullExpressionValue((Object)nbt, (String)"nbt");
            ((PokemonStore)store).loadFromNBT(nbt);
            object = store;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return (T)object;
    }
}

