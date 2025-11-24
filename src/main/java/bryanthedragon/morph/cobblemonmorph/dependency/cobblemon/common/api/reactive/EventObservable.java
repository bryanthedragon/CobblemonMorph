/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ=\u0010\b\u001a\u00020\u00062\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0003\"\u00028\u00002\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u0005H\u0086\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/reactive/EventObservable;", "T", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "events", "Lkotlin/Function1;", "", "then", "post", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nEventObservables.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,48:1\n13579#2,2:49\n*S KotlinDebug\n*F\n+ 1 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n*L\n18#1:49,2\n*E\n"})
public class EventObservable<T>
extends SimpleObservable<T> {
    public final void post(@NotNull T[] events, @NotNull Function1<? super T, Unit> then) {
        Intrinsics.checkNotNullParameter(events, (String)"events");
        Intrinsics.checkNotNullParameter(then, (String)"then");
        boolean $i$f$post = false;
        this.emit(Arrays.copyOf(events, events.length));
        T[] $this$forEach$iv = events;
        boolean $i$f$forEach = false;
        for (T element$iv : $this$forEach$iv) {
            then.invoke(element$iv);
        }
    }

    public static /* synthetic */ void post$default(EventObservable $this, Object[] events, Function1 then, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: post");
        }
        if ((n & 2) != 0) {
            then = post.1.INSTANCE;
        }
        Intrinsics.checkNotNullParameter((Object)events, (String)"events");
        Intrinsics.checkNotNullParameter((Object)then, (String)"then");
        boolean $i$f$post = false;
        $this.emit(Arrays.copyOf(events, events.length));
        Object[] $this$forEach$iv = events;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            then.invoke(element$iv);
        }
    }
}

