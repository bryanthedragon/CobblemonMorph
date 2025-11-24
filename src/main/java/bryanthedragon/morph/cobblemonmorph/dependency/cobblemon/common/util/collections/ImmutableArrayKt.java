/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.ImmutableArray;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0010\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\"\u0006\b\u0000\u0010\u0000\u0018\u00012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0001\"\u00028\u0000H\u0086\b\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"T", "", "values", "Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "immutableArrayOf", "([Ljava/lang/Object;)Lcom/cobblemon/mod/common/util/collections/ImmutableArray;", "common"})
public final class ImmutableArrayKt {
    public static final /* synthetic */ <T> ImmutableArray<T> immutableArrayOf(T ... values) {
        Intrinsics.checkNotNullParameter(values, (String)"values");
        boolean $i$f$immutableArrayOf = false;
        return new ImmutableArray<T>(Arrays.copyOf(values, values.length));
    }
}

