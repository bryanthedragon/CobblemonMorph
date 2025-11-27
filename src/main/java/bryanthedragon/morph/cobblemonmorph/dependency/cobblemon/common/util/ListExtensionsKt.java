/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0005\u001a+\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u00012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0005\u00a8\u0006\u0007"}, d2={"T", "", "", "amount", "random", "(Ljava/util/List;I)Ljava/util/List;", "randomNoCopy", "common"})
public final class ListExtensionsKt {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @NotNull
    public static final <T> List<T> random(@NotNull List<? extends T> $this$random, int amount) {
        Intrinsics.checkNotNullParameter($this$random, (String)"<this>");
        List values = new ArrayList();
        int i = 1;
        if (i <= amount) {
            while (true) {
                values.add(CollectionsKt.random((Collection)$this$random, (Random)((Random)Random.Default)));
                if (i == amount) break;
                ++i;
            }
        }
        return values;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @NotNull
    public static final <T> List<T> randomNoCopy(@NotNull List<? extends T> $this$randomNoCopy, int amount) {
        Intrinsics.checkNotNullParameter($this$randomNoCopy, (String)"<this>");
        List toChooseFrom = CollectionsKt.toMutableList((Collection)$this$randomNoCopy);
        List values = new ArrayList();
        int amountLeft = amount;
        while (amountLeft > 0 && !((Collection)toChooseFrom).isEmpty()) {
            Object random = CollectionsKt.random((Collection)toChooseFrom, (Random)((Random)Random.Default));
            toChooseFrom.remove(random);
            if (values.contains(random)) continue;
            values.add(random);
            --amountLeft;
        }
        return values;
    }
}

