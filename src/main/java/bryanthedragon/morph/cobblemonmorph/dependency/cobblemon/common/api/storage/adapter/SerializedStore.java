/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B+\u0012\u0012\u0010\f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00028\u0000\u00a2\u0006\u0004\b!\u0010\"J\u001c\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\b\u001a\u00020\u0007H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00028\u0000H\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ@\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0014\b\u0002\u0010\f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u00032\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00028\u0000H\u00c6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\u000e\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001b\u001a\u0004\b\u001c\u0010\u000bR#\u0010\f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u00038\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001d\u001a\u0004\b\u001e\u0010\u0006R\u0017\u0010\r\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001f\u001a\u0004\b \u0010\t\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/SerializedStore;", "S", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "component1", "()Ljava/lang/Class;", "Ljava/util/UUID;", "component2", "()Ljava/util/UUID;", "component3", "()Ljava/lang/Object;", "storeClass", "uuid", "serializedForm", "copy", "(Ljava/lang/Class;Ljava/util/UUID;Ljava/lang/Object;)Lcom/cobblemon/mod/common/api/storage/adapter/SerializedStore;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Ljava/lang/Object;", "getSerializedForm", "Ljava/lang/Class;", "getStoreClass", "Ljava/util/UUID;", "getUuid", "<init>", "(Ljava/lang/Class;Ljava/util/UUID;Ljava/lang/Object;)V", "common"})
public final class SerializedStore<S> {
    @NotNull
    private final Class<? extends PokemonStore<?>> storeClass;
    @NotNull
    private final UUID uuid;
    private final S serializedForm;

    public SerializedStore(@NotNull Class<? extends PokemonStore<?>> storeClass, @NotNull UUID uuid2, S serializedForm) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this.storeClass = storeClass;
        this.uuid = uuid2;
        this.serializedForm = serializedForm;
    }

    @NotNull
    public final Class<? extends PokemonStore<?>> getStoreClass() {
        return this.storeClass;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    public final S getSerializedForm() {
        return this.serializedForm;
    }

    @NotNull
    public final Class<? extends PokemonStore<?>> component1() {
        return this.storeClass;
    }

    @NotNull
    public final UUID component2() {
        return this.uuid;
    }

    public final S component3() {
        return this.serializedForm;
    }

    @NotNull
    public final SerializedStore<S> copy(@NotNull Class<? extends PokemonStore<?>> storeClass, @NotNull UUID uuid2, S serializedForm) {
        Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        return new SerializedStore<S>(storeClass, uuid2, serializedForm);
    }

    public static /* synthetic */ SerializedStore copy$default(SerializedStore serializedStore, Class clazz, UUID uUID, Object object, int n, Object object2) {
        if ((n & 1) != 0) {
            clazz = serializedStore.storeClass;
        }
        if ((n & 2) != 0) {
            uUID = serializedStore.uuid;
        }
        if ((n & 4) != 0) {
            object = serializedStore.serializedForm;
        }
        return serializedStore.copy(clazz, uUID, object);
    }

    @NotNull
    public String toString() {
        return "SerializedStore(storeClass=" + this.storeClass + ", uuid=" + this.uuid + ", serializedForm=" + this.serializedForm + ")";
    }

    public int hashCode() {
        int result = this.storeClass.hashCode();
        result = result * 31 + this.uuid.hashCode();
        result = result * 31 + (this.serializedForm == null ? 0 : this.serializedForm.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SerializedStore)) {
            return false;
        }
        SerializedStore serializedStore = (SerializedStore)other;
        if (!Intrinsics.areEqual(this.storeClass, serializedStore.storeClass)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.uuid, (Object)serializedStore.uuid)) {
            return false;
        }
        return Intrinsics.areEqual(this.serializedForm, serializedStore.serializedForm);
    }
}

