/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u001d\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\n\u001a\u00028\u0000\u00a2\u0006\u0004\b%\u0010&J\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u0007\u001a\u00028\u0000H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ0\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\b\u0002\u0010\n\u001a\u00028\u0000H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0017\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0015\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u001f\u001a\u00020\u001eH\u00d6\u0001\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010\n\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\n\u0010!\u001a\u0004\b\"\u0010\bR\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0006\u00a2\u0006\f\n\u0004\b\t\u0010#\u001a\u0004\b$\u0010\u0006\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "T", "", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "component1", "()Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "component2", "()Lcom/cobblemon/mod/common/api/storage/StorePosition;", "store", "position", "copy", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;Lcom/cobblemon/mod/common/api/storage/StorePosition;)Lcom/cobblemon/mod/common/api/storage/StoreCoordinates;", "other", "", "equals", "(Ljava/lang/Object;)Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "get", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "hashCode", "()I", "remove", "()Z", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "getPosition", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "getStore", "<init>", "(Lcom/cobblemon/mod/common/api/storage/PokemonStore;Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "common"})
public final class StoreCoordinates<T extends StorePosition> {
    @NotNull
    private final PokemonStore<T> store;
    @NotNull
    private final T position;

    public StoreCoordinates(@NotNull PokemonStore<T> store, @NotNull T position) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        Intrinsics.checkNotNullParameter(position, (String)"position");
        this.store = store;
        this.position = position;
    }

    @NotNull
    public final PokemonStore<T> getStore() {
        return this.store;
    }

    @NotNull
    public final T getPosition() {
        return this.position;
    }

    public final void saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.store.savePositionToNBT(this.position, nbt);
    }

    @Nullable
    public final Pokemon get() {
        return this.store.get(this.position);
    }

    public final boolean remove() {
        return this.store.remove(this.position);
    }

    @NotNull
    public final PokemonStore<T> component1() {
        return this.store;
    }

    @NotNull
    public final T component2() {
        return this.position;
    }

    @NotNull
    public final StoreCoordinates<T> copy(@NotNull PokemonStore<T> store, @NotNull T position) {
        Intrinsics.checkNotNullParameter(store, (String)"store");
        Intrinsics.checkNotNullParameter(position, (String)"position");
        return new StoreCoordinates<T>(store, position);
    }

    public static /* synthetic */ StoreCoordinates copy$default(StoreCoordinates storeCoordinates, PokemonStore pokemonStore, StorePosition storePosition, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonStore = storeCoordinates.store;
        }
        if ((n & 2) != 0) {
            storePosition = storeCoordinates.position;
        }
        return storeCoordinates.copy(pokemonStore, storePosition);
    }

    @NotNull
    public String toString() {
        return "StoreCoordinates(store=" + this.store + ", position=" + this.position + ")";
    }

    public int hashCode() {
        int result = this.store.hashCode();
        result = result * 31 + this.position.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StoreCoordinates)) {
            return false;
        }
        StoreCoordinates storeCoordinates = (StoreCoordinates)other;
        if (!Intrinsics.areEqual(this.store, storeCoordinates.store)) {
            return false;
        }
        return Intrinsics.areEqual(this.position, storeCoordinates.position);
    }
}

