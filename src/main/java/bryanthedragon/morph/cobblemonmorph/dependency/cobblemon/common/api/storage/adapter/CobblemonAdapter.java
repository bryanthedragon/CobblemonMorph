/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import java.util.UUID;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002JA\u0010\u000b\u001a\u0004\u0018\u00018\u0002\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\u000e\b\u0002\u0010\u0006*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u00072\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapter;", "S", "", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "load", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "common"})
public interface CobblemonAdapter<S> {
    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T load(@NotNull Class<T> var1, @NotNull UUID var2);
}

