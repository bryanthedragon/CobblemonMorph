/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J=\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J+\u0010\b\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010\u00022\b\u0010\u0004\u001a\u0004\u0018\u00018\u00002\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/util/Merger;", "", "T", "", "base", "other", "merge", "(Ljava/util/Collection;Ljava/util/Collection;)Ljava/util/Collection;", "mergeSingle", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "common"})
public interface Merger {
    @Nullable
    public <T> Collection<T> merge(@Nullable Collection<T> var1, @Nullable Collection<T> var2);

    @Nullable
    public <T> T mergeSingle(@Nullable T var1, @Nullable T var2);
}

