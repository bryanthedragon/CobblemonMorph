/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 $2\u00020\u0001:\u0001$B\u0007\u00a2\u0006\u0004\b\"\u0010#J!\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\t\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0004\b\t\u0010\nJ!\u0010\r\u001a\u00020\u00052\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u0002\"\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ)\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00002\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u0002\"\u00020\u000b\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001d\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0018\u0010\u0019J)\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00002\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u0002\"\u00020\u000b\u00a2\u0006\u0004\b\u001a\u0010\u0011R?\u0010\u001e\u001a*\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u001c0\u001bj\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u001c`\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;", "", "", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "contexts", "", "add", "([Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;)V", "context", "addUnique", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "bucketTypes", "clear", "([Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;)V", "with", "copy", "(Lcom/cobblemon/mod/common/battles/interpreter/ContextManager;[Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;)V", "bucketType", "", "get", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;)Ljava/util/Collection;", "", "contextID", "remove", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;)V", "swap", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "buckets", "Ljava/util/HashMap;", "getBuckets", "()Ljava/util/HashMap;", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nContextManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,104:1\n13579#2:105\n13580#2:120\n13579#2,2:122\n13579#2:124\n13580#2:129\n13579#2:130\n13580#2:133\n361#3,7:106\n361#3,7:113\n1#4:121\n37#5,2:125\n37#5,2:127\n37#5,2:131\n*S KotlinDebug\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager\n*L\n26#1:105\n26#1:120\n58#1:122,2\n66#1:124\n66#1:129\n78#1:130\n78#1:133\n28#1:106,7\n33#1:113,7\n71#1:125,2\n72#1:127,2\n81#1:131,2\n*E\n"})
public final class ContextManager {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final HashMap<BattleContext.Type, Collection<BattleContext>> buckets = new HashMap();

    @NotNull
    public final HashMap<BattleContext.Type, Collection<BattleContext>> getBuckets() {
        return this.buckets;
    }

    /*
     * WARNING - void declaration
     */
    public final void add(BattleContext ... contexts) {
        Intrinsics.checkNotNullParameter((Object)contexts, (String)"contexts");
        BattleContext[] $this$forEach$iv = contexts;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            Object object;
            void $this$getOrPut$iv;
            Object bucket;
            BattleContext element$iv;
            BattleContext context = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            if (context.getType().getExclusive()) {
                Object object2;
                void $this$getOrPut$iv2;
                Map map = this.buckets;
                BattleContext.Type key$iv = context.getType();
                boolean $i$f$getOrPut = false;
                Object value$iv = $this$getOrPut$iv2.get((Object)key$iv);
                if (value$iv == null) {
                    boolean bl2 = false;
                    Collection answer$iv = new ArrayList();
                    $this$getOrPut$iv2.put(key$iv, answer$iv);
                    object2 = answer$iv;
                } else {
                    object2 = value$iv;
                }
                bucket = (Collection)object2;
                bucket.clear();
                bucket.add(context);
                continue;
            }
            bucket = this.buckets;
            BattleContext.Type key$iv = context.getType();
            boolean $i$f$getOrPut = false;
            Object value$iv = $this$getOrPut$iv.get((Object)key$iv);
            if (value$iv == null) {
                boolean bl3 = false;
                Collection answer$iv = new ArrayList();
                $this$getOrPut$iv.put(key$iv, answer$iv);
                object = answer$iv;
            } else {
                object = value$iv;
            }
            ((Collection)object).add(context);
        }
    }

    public final void addUnique(@NotNull BattleContext context) {
        BattleContext battleContext;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Collection<BattleContext> collection = this.buckets.get((Object)context.getType());
        if (collection != null) {
            Object v1;
            block4: {
                Iterable iterable = collection;
                for (Object t : iterable) {
                    BattleContext it = (BattleContext)t;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getId(), (Object)context.getId())) continue;
                    v1 = t;
                    break block4;
                }
                v1 = null;
            }
            battleContext = v1;
        } else {
            battleContext = null;
        }
        if (battleContext == null) {
            BattleContext[] battleContextArray = new BattleContext[]{context};
            this.add(battleContextArray);
        }
    }

    public final void remove(@NotNull String contextID, @NotNull BattleContext.Type bucketType) {
        block1: {
            block0: {
                Intrinsics.checkNotNullParameter((Object)contextID, (String)"contextID");
                Intrinsics.checkNotNullParameter((Object)((Object)bucketType), (String)"bucketType");
                if (!bucketType.getExclusive()) break block0;
                Collection<BattleContext> collection = this.buckets.get((Object)bucketType);
                if (collection == null) break block1;
                collection.clear();
                break block1;
            }
            Collection<BattleContext> collection = this.buckets.get((Object)bucketType);
            if (collection == null) break block1;
            collection.removeIf(arg_0 -> ContextManager.remove$lambda$4((Function1)new Function1<BattleContext, Boolean>(contextID){
                final /* synthetic */ String $contextID;
                {
                    this.$contextID = $contextID;
                    super(1);
                }

                @NotNull
                public final Boolean invoke(@NotNull BattleContext it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    return Intrinsics.areEqual((Object)it.getId(), (Object)this.$contextID);
                }
            }, arg_0));
        }
    }

    public final void clear(BattleContext.Type ... bucketTypes) {
        Intrinsics.checkNotNullParameter((Object)bucketTypes, (String)"bucketTypes");
        BattleContext.Type[] $this$forEach$iv = bucketTypes;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            BattleContext.Type element$iv;
            BattleContext.Type bucketType = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            Collection<BattleContext> collection = this.buckets.get((Object)bucketType);
            if (collection == null) continue;
            collection.clear();
        }
    }

    public final void swap(@NotNull ContextManager with, BattleContext.Type ... bucketTypes) {
        Intrinsics.checkNotNullParameter((Object)with, (String)"with");
        Intrinsics.checkNotNullParameter((Object)bucketTypes, (String)"bucketTypes");
        BattleContext.Type[] $this$forEach$iv = bucketTypes;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            BattleContext[] battleContextArray;
            Collection thisCollection$iv;
            boolean $i$f$toTypedArray;
            Collection $this$toTypedArray$iv;
            List it;
            List list;
            BattleContext.Type[] typeArray;
            List oldContexts;
            Collection<BattleContext> collection;
            BattleContext.Type element$iv;
            BattleContext.Type bucketType = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            Collection<BattleContext> collection2 = collection = this.buckets.get((Object)bucketType);
            if (collection2 != null) {
                Intrinsics.checkNotNullExpressionValue(collection2, (String)"this.buckets[bucketType]");
                v1 = CollectionsKt.toMutableList(collection);
            } else {
                v1 = oldContexts = null;
            }
            if (typeArray != null) {
                typeArray = with.buckets.get((Object)bucketType);
                Intrinsics.checkNotNullExpressionValue(typeArray, (String)"with.buckets[bucketType]");
                list = CollectionsKt.toMutableList(typeArray);
            } else {
                list = null;
            }
            List newContexts = list;
            typeArray = new BattleContext.Type[]{bucketType};
            this.clear(typeArray);
            typeArray = new BattleContext.Type[]{bucketType};
            with.clear(typeArray);
            if (oldContexts != null) {
                boolean bl2 = false;
                $this$toTypedArray$iv = it;
                $i$f$toTypedArray = false;
                thisCollection$iv = $this$toTypedArray$iv;
                battleContextArray = thisCollection$iv.toArray(new BattleContext[0]);
                with.add(Arrays.copyOf(battleContextArray, battleContextArray.length));
            }
            if (newContexts == null) continue;
            boolean bl3 = false;
            $this$toTypedArray$iv = it;
            $i$f$toTypedArray = false;
            thisCollection$iv = $this$toTypedArray$iv;
            battleContextArray = thisCollection$iv.toArray(new BattleContext[0]);
            this.add(Arrays.copyOf(battleContextArray, battleContextArray.length));
        }
    }

    public final void copy(@NotNull ContextManager with, BattleContext.Type ... bucketTypes) {
        Intrinsics.checkNotNullParameter((Object)with, (String)"with");
        Intrinsics.checkNotNullParameter((Object)bucketTypes, (String)"bucketTypes");
        BattleContext.Type[] $this$forEach$iv = bucketTypes;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            List it;
            List list;
            BattleContext.Type[] typeArray;
            BattleContext.Type element$iv;
            BattleContext.Type bucketType = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            if (typeArray != null) {
                typeArray = with.buckets.get((Object)bucketType);
                Intrinsics.checkNotNullExpressionValue(typeArray, (String)"with.buckets[bucketType]");
                list = CollectionsKt.toMutableList(typeArray);
            } else {
                list = null;
            }
            List newContexts = list;
            typeArray = new BattleContext.Type[]{bucketType};
            this.clear(typeArray);
            if (newContexts == null) continue;
            boolean bl2 = false;
            Collection $this$toTypedArray$iv = it;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            BattleContext[] battleContextArray = thisCollection$iv.toArray(new BattleContext[0]);
            this.add(Arrays.copyOf(battleContextArray, battleContextArray.length));
        }
    }

    @Nullable
    public final Collection<BattleContext> get(@NotNull BattleContext.Type bucketType) {
        Intrinsics.checkNotNullParameter((Object)((Object)bucketType), (String)"bucketType");
        return this.buckets.get((Object)bucketType);
    }

    private static final boolean remove$lambda$4(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ;\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0003\u001a\u00020\u00022\"\u0010\u0007\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00050\u0004\"\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/ContextManager$Companion;", "", "", "contextID", "", "", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "contextBuckets", "scoop", "(Ljava/lang/String;[Ljava/util/Collection;)Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nContextManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,104:1\n1855#2:105\n1856#2:107\n1#3:106\n*S KotlinDebug\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager$Companion\n*L\n98#1:105\n98#1:107\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @Nullable
        public final BattleContext scoop(@NotNull String contextID, Collection<? extends BattleContext> ... contextBuckets) {
            Intrinsics.checkNotNullParameter((Object)contextID, (String)"contextID");
            Intrinsics.checkNotNullParameter(contextBuckets, (String)"contextBuckets");
            Iterable $this$forEach$iv = ArraysKt.filterNotNull((Object[])contextBuckets);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Collection bucket = (Collection)element$iv;
                boolean bl = false;
                Iterable iterable = bucket;
                Object var10_10 = null;
                for (Object t : iterable) {
                    BattleContext it = (BattleContext)t;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual((Object)it.getId(), (Object)contextID)) continue;
                    var10_10 = t;
                }
                BattleContext battleContext = var10_10;
                if (battleContext == null) continue;
                BattleContext it = battleContext;
                boolean bl3 = false;
                return it;
            }
            return null;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

