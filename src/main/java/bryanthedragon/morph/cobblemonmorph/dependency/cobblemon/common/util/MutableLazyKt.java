/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MutableLazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a'\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\"\u0004\b\u0000\u0010\u00002\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"T", "Lkotlin/Function0;", "initializer", "Lcom/cobblemon/mod/common/util/MutableLazy;", "mutableLazy", "(Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/util/MutableLazy;", "common"})
public final class MutableLazyKt {
    @NotNull
    public static final <T> MutableLazy<T> mutableLazy(@NotNull Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(initializer, (String)"initializer");
        return new MutableLazy<T>(initializer);
    }
}

