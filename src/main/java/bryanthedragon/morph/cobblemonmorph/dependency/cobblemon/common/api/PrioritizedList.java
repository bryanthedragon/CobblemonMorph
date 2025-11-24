/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010!\n\u0002\b\u0005\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u0007\u00a2\u0006\u0004\b$\u0010\nJ\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0096\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0011\u0010\bJ\u000f\u0010\u0013\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\u0013\u0010\nR#\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00150\u00148F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R*\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0019j\b\u0012\u0004\u0012\u00028\u0000`\u001a8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR,\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000 0\u001f8\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010\u0017\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/PrioritizedList;", "T", "", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "value", "", "add", "(Lcom/cobblemon/mod/common/api/Priority;Ljava/lang/Object;)V", "clear", "()V", "", "isEmpty", "()Z", "", "iterator", "()Ljava/util/Iterator;", "remove", "(Ljava/lang/Object;)V", "reorder", "", "", "getMapping", "()Ljava/util/Map;", "mapping", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "ordered", "Ljava/util/ArrayList;", "getOrdered", "()Ljava/util/ArrayList;", "", "", "priorityMap", "Ljava/util/Map;", "getPriorityMap", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPrioritizedList.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PrioritizedList.kt\ncom/cobblemon/mod/common/api/PrioritizedList\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n13579#2:60\n13580#2:62\n1#3:61\n1855#4,2:63\n*S KotlinDebug\n*F\n+ 1 PrioritizedList.kt\ncom/cobblemon/mod/common/api/PrioritizedList\n*L\n29#1:60\n29#1:62\n42#1:63,2\n*E\n"})
public class PrioritizedList<T>
implements Iterable<T>,
KMappedMarker {
    @NotNull
    private final Map<Priority, List<T>> priorityMap = new LinkedHashMap();
    @NotNull
    private final ArrayList<T> ordered = new ArrayList();

    @NotNull
    protected final Map<Priority, List<T>> getPriorityMap() {
        return this.priorityMap;
    }

    @NotNull
    protected final ArrayList<T> getOrdered() {
        return this.ordered;
    }

    @NotNull
    public final Map<Priority, List<T>> getMapping() {
        return MapsKt.toMap(this.priorityMap);
    }

    private final void reorder() {
        this.ordered.clear();
        Priority[] $this$forEach$iv = Priority.values();
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            List<T> it;
            Priority element$iv;
            Priority it2 = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            if (this.priorityMap.get((Object)it2) == null) continue;
            boolean bl2 = false;
            this.ordered.addAll((Collection)it);
        }
    }

    public final void add(@NotNull Priority priority, T value2) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        this.priorityMap.putIfAbsent(priority, new ArrayList());
        List<T> list = this.priorityMap.get((Object)priority);
        if (list != null) {
            list.add(value2);
        }
        this.reorder();
    }

    public final void remove(T value2) {
        Iterable $this$forEach$iv = this.priorityMap.values();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            List it = (List)element$iv;
            boolean bl = false;
            it.remove(value2);
        }
        this.reorder();
    }

    public final void remove(@NotNull Priority priority, T value2) {
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        List<T> list = this.priorityMap.get((Object)priority);
        if (list != null) {
            list.remove(value2);
        }
        this.reorder();
    }

    public final void clear() {
        this.priorityMap.clear();
        this.ordered.clear();
    }

    public final boolean isEmpty() {
        return this.ordered.isEmpty();
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        Iterator<T> iterator = this.ordered.iterator();
        Intrinsics.checkNotNullExpressionValue(iterator, (String)"ordered.iterator()");
        return iterator;
    }
}

