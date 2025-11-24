/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u001e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u001aI\u0010\b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00022\u001e\u0010\u0006\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"A", "B", "", "Lkotlin/Function1;", "", "", "predicate", "", "removeIf", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V", "common"})
public final class MapExtensionsKt {
    public static final <A, B> void removeIf(@NotNull Map<A, B> $this$removeIf, @NotNull Function1<? super Map.Entry<? extends A, ? extends B>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter($this$removeIf, (String)"<this>");
        Intrinsics.checkNotNullParameter(predicate, (String)"predicate");
        List toRemove = new ArrayList();
        for (Map.Entry<A, B> entry : $this$removeIf.entrySet()) {
            if (!((Boolean)predicate.invoke(entry)).booleanValue()) continue;
            toRemove.add(entry.getKey());
        }
        for (Map.Entry<Object, Object> key : toRemove) {
            $this$removeIf.remove(key);
        }
    }
}

