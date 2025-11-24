/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\t\b\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/util/MergeMode;", "", "Lcom/cobblemon/mod/common/util/Merger;", "<init>", "(Ljava/lang/String;I)V", "REPLACE", "INSERT", "KEEP", "common"})
public abstract class MergeMode
extends Enum<MergeMode>
implements Merger {
    public static final /* enum */ MergeMode REPLACE = new REPLACE("REPLACE", 0);
    public static final /* enum */ MergeMode INSERT = new INSERT("INSERT", 1);
    public static final /* enum */ MergeMode KEEP = new KEEP("KEEP", 2);
    private static final /* synthetic */ MergeMode[] $VALUES;

    private MergeMode() {
    }

    public static MergeMode[] values() {
        return (MergeMode[])$VALUES.clone();
    }

    public static MergeMode valueOf(String value2) {
        return Enum.valueOf(MergeMode.class, value2);
    }

    public /* synthetic */ MergeMode(String $enum$name, int $enum$ordinal, DefaultConstructorMarker $constructor_marker) {
        this();
    }

    static {
        $VALUES = mergeModeArray = new MergeMode[]{MergeMode.REPLACE, MergeMode.INSERT, MergeMode.KEEP};
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0007\b\u00c6\u0001\u0018\u00002\u00020\u0001J=\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J+\u0010\b\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010\u00022\b\u0010\u0004\u001a\u0004\u0018\u00018\u00002\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/util/MergeMode$INSERT;", "Lcom/cobblemon/mod/common/util/MergeMode;", "T", "", "base", "other", "merge", "(Ljava/util/Collection;Ljava/util/Collection;)Ljava/util/Collection;", "mergeSingle", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "common"})
    static final class INSERT
    extends MergeMode {
        /*
         * WARNING - void declaration
         */
        INSERT() {
            void var1_1;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        @Nullable
        public <T> Collection<T> merge(@Nullable Collection<T> base, @Nullable Collection<T> other) {
            Collection collection;
            if (other == null) {
                collection = base;
            } else {
                void var3_3;
                Collection collection2 = base;
                if (collection2 == null) {
                    collection2 = new ArrayList();
                }
                Collection list = collection2;
                list.addAll(other);
                collection = var3_3;
            }
            return collection;
        }

        @Override
        @Nullable
        public <T> T mergeSingle(@Nullable T base, @Nullable T other) {
            return KEEP.mergeSingle(base, other);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0007\b\u00c6\u0001\u0018\u00002\u00020\u0001J=\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J+\u0010\b\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010\u00022\b\u0010\u0004\u001a\u0004\u0018\u00018\u00002\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/util/MergeMode$KEEP;", "Lcom/cobblemon/mod/common/util/MergeMode;", "T", "", "base", "other", "merge", "(Ljava/util/Collection;Ljava/util/Collection;)Ljava/util/Collection;", "mergeSingle", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "common"})
    static final class KEEP
    extends MergeMode {
        /*
         * WARNING - void declaration
         */
        KEEP() {
            void var1_1;
        }

        @Override
        @Nullable
        public <T> Collection<T> merge(@Nullable Collection<T> base, @Nullable Collection<T> other) {
            List list = base;
            return list != null && (list = CollectionsKt.toMutableList(list)) != null ? (Collection)list : other;
        }

        @Override
        @Nullable
        public <T> T mergeSingle(@Nullable T base, @Nullable T other) {
            T t = base;
            if (t == null) {
                t = other;
            }
            return t;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0007\b\u00c6\u0001\u0018\u00002\u00020\u0001J=\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J+\u0010\b\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010\u00022\b\u0010\u0004\u001a\u0004\u0018\u00018\u00002\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/util/MergeMode$REPLACE;", "Lcom/cobblemon/mod/common/util/MergeMode;", "T", "", "base", "other", "merge", "(Ljava/util/Collection;Ljava/util/Collection;)Ljava/util/Collection;", "mergeSingle", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "common"})
    static final class REPLACE
    extends MergeMode {
        /*
         * WARNING - void declaration
         */
        REPLACE() {
            void var1_1;
        }

        @Override
        @Nullable
        public <T> Collection<T> merge(@Nullable Collection<T> base, @Nullable Collection<T> other) {
            List list = other;
            return list != null && (list = CollectionsKt.toMutableList(list)) != null ? (Collection)list : base;
        }

        @Override
        @Nullable
        public <T> T mergeSingle(@Nullable T base, @Nullable T other) {
            T t = other;
            if (t == null) {
                t = base;
            }
            return t;
        }
    }
}

