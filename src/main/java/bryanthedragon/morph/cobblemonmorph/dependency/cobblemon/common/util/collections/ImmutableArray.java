/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001b\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0007\"\u00028\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001c\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "T", "", "", "index", "get", "(I)Ljava/lang/Object;", "", "values", "[Ljava/lang/Object;", "<init>", "([Ljava/lang/Object;)V", "common"})
public final class ImmutableArray<T> {
    @NotNull
    private final T[] values;

    public ImmutableArray(T ... values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        this.values = values;
    }

    public final T get(int index) {
        return this.values[index];
    }
}

