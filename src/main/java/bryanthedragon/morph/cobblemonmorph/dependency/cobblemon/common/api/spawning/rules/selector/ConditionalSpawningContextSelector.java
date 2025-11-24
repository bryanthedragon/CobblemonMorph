/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R,\u0010\t\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR,\u0010\u000f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/spawning/rules/selector/ConditionalSpawningContextSelector;", "Lcom/cobblemon/mod/common/api/spawning/rules/selector/SpawningContextSelector;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "selects", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "anticonditions", "Ljava/util/List;", "getAnticonditions", "()Ljava/util/List;", "setAnticonditions", "(Ljava/util/List;)V", "conditions", "getConditions", "setConditions", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nConditionalSpawningContextSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConditionalSpawningContextSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/ConditionalSpawningContextSelector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,25:1\n2624#2,3:26\n1747#2,3:29\n*S KotlinDebug\n*F\n+ 1 ConditionalSpawningContextSelector.kt\ncom/cobblemon/mod/common/api/spawning/rules/selector/ConditionalSpawningContextSelector\n*L\n19#1:26,3\n22#1:29,3\n*E\n"})
public final class ConditionalSpawningContextSelector
implements SpawningContextSelector {
    @NotNull
    private List<SpawningCondition<?>> conditions = new ArrayList();
    @NotNull
    private List<SpawningCondition<?>> anticonditions = new ArrayList();

    @NotNull
    public final List<SpawningCondition<?>> getConditions() {
        return this.conditions;
    }

    public final void setConditions(@NotNull List<SpawningCondition<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.conditions = list;
    }

    @NotNull
    public final List<SpawningCondition<?>> getAnticonditions() {
        return this.anticonditions;
    }

    public final void setAnticonditions(@NotNull List<SpawningCondition<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.anticonditions = list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean selects(@NotNull SpawningContext ctx) {
        Object element$iv;
        SpawningCondition it;
        Iterator iterator;
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        if (!((Collection)this.conditions).isEmpty()) {
            Iterable $this$none$iv = this.conditions;
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                return false;
            }
            iterator = $this$none$iv.iterator();
            do {
                if (!iterator.hasNext()) return false;
                element$iv = iterator.next();
                it = (SpawningCondition)element$iv;
                boolean bl = false;
            } while (!it.isSatisfiedBy(ctx));
            boolean bl = false;
            if (bl) {
                return false;
            }
        }
        if (((Collection)this.anticonditions).isEmpty()) return true;
        boolean bl = true;
        if (!bl) return true;
        Iterable $this$any$iv = this.anticonditions;
        boolean $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return true;
        }
        iterator = $this$any$iv.iterator();
        do {
            if (!iterator.hasNext()) return true;
            element$iv = iterator.next();
            it = (SpawningCondition)element$iv;
            boolean bl2 = false;
        } while (!it.isSatisfiedBy(ctx));
        return false;
    }
}

