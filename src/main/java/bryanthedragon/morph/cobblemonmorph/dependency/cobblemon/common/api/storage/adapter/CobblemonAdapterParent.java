/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0006\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u0007\u00a2\u0006\u0004\b\u0016\u0010\u0017JA\u0010\u000b\u001a\u0004\u0018\u00018\u0002\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\u000e\b\u0002\u0010\u0006*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJA\u0010\r\u001a\u0004\u0018\u00018\u0002\"\b\b\u0001\u0010\u0004*\u00020\u0003\"\u000e\b\u0002\u0010\u0006*\b\u0012\u0004\u0012\u00028\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u00072\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\r\u0010\fJ/\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u001a\u0010\u000f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00020\u000e\"\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R!\u0010\u000f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapterParent;", "S", "Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapter;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "E", "Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "T", "Ljava/lang/Class;", "storeClass", "Ljava/util/UUID;", "uuid", "load", "(Ljava/lang/Class;Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/storage/PokemonStore;", "provide", "", "children", "with", "([Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapter;)Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapter;", "", "Ljava/util/List;", "getChildren", "()Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobbledAdapterParent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledAdapterParent.kt\ncom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapterParent\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"})
public abstract class CobblemonAdapterParent<S>
implements CobblemonAdapter<S> {
    @NotNull
    private final List<CobblemonAdapter<?>> children = new ArrayList();

    @NotNull
    public final List<CobblemonAdapter<?>> getChildren() {
        return this.children;
    }

    @NotNull
    public final CobblemonAdapter<S> with(CobblemonAdapter<?> ... children) {
        Intrinsics.checkNotNullParameter(children, (String)"children");
        CollectionsKt.addAll((Collection)this.children, (Object[])children);
        return this;
    }

    @Override
    @Nullable
    public <E extends StorePosition, T extends PokemonStore<E>> T load(@NotNull Class<T> storeClass, @NotNull UUID uuid2) {
        T t;
        block2: {
            Intrinsics.checkNotNullParameter(storeClass, (String)"storeClass");
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            t = this.provide(storeClass, uuid2);
            if (t == null) {
                for (CobblemonAdapter it : (Iterable)this.children) {
                    boolean bl = false;
                    T t2 = it.load(storeClass, uuid2);
                    if (t2 == null) continue;
                    t = t2;
                    break block2;
                }
                t = null;
            }
        }
        return t;
    }

    @Nullable
    public abstract <E extends StorePosition, T extends PokemonStore<E>> T provide(@NotNull Class<T> var1, @NotNull UUID var2);
}

