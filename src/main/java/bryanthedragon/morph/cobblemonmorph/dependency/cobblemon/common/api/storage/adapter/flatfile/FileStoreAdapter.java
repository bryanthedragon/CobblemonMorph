/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapter;
import java.util.UUID;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002J3\u0010\n\u001a\u00020\t2\u0012\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u00032\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\n\u0010\u000bJ1\u0010\u0010\u001a\u00028\u0000\"\b\b\u0001\u0010\r*\u00020\f\"\u000e\b\u0002\u0010\u000e*\b\u0012\u0004\u0012\u00028\u00010\u00042\u0006\u0010\u000f\u001a\u00028\u0002H&\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/flatfile/FileStoreAdapter;", "S", "Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapter;", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "storeClass", "Ljava/util/UUID;", "uuid", "serialized", "", "save", "(Ljava/lang/Class;Ljava/util/UUID;Ljava/lang/Object;)V", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "T", "store", "serialize", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;)Ljava/lang/Object;", "common"})
public interface FileStoreAdapter<S>
extends CobblemonAdapter<S> {
    public <E extends StorePosition, T extends PokemonStore<E>> S serialize(@NotNull T var1);

    public void save(@NotNull Class<? extends PokemonStore<?>> var1, @NotNull UUID var2, S var3);
}

