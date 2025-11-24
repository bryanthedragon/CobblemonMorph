/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0002\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0006\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R#\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/CachedLevelThresholds;", "", "", "experience", "getLevel", "(I)I", "Lkotlin/Function1;", "experienceToLevel", "Lkotlin/jvm/functions/Function1;", "getExperienceToLevel", "()Lkotlin/jvm/functions/Function1;", "levelLimit", "I", "getLevelLimit", "()I", "", "savedThresholds", "Ljava/util/List;", "getSavedThresholds", "()Ljava/util/List;", "<init>", "(ILkotlin/jvm/functions/Function1;)V", "common"})
public final class CachedLevelThresholds {
    private final int levelLimit;
    @NotNull
    private final Function1<Integer, Integer> experienceToLevel;
    @NotNull
    private final List<Integer> savedThresholds;

    public CachedLevelThresholds(int levelLimit, @NotNull Function1<? super Integer, Integer> experienceToLevel) {
        Intrinsics.checkNotNullParameter(experienceToLevel, (String)"experienceToLevel");
        this.levelLimit = levelLimit;
        this.experienceToLevel = experienceToLevel;
        this.savedThresholds = new ArrayList();
    }

    public /* synthetic */ CachedLevelThresholds(int n, Function1 function1, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            n = 1000;
        }
        this(n, (Function1<? super Integer, Integer>)function1);
    }

    public final int getLevelLimit() {
        return this.levelLimit;
    }

    @NotNull
    public final Function1<Integer, Integer> getExperienceToLevel() {
        return this.experienceToLevel;
    }

    @NotNull
    public final List<Integer> getSavedThresholds() {
        return this.savedThresholds;
    }

    public final int getLevel(int experience) {
        int threshold;
        int level;
        for (level = 1; level <= this.savedThresholds.size(); ++level) {
            threshold = ((Number)this.savedThresholds.get(level - 1)).intValue();
            if (experience >= threshold) continue;
            return level - 1;
        }
        while (level < this.levelLimit) {
            threshold = ((Number)this.experienceToLevel.invoke((Object)level)).intValue();
            this.savedThresholds.add(threshold);
            if (experience < threshold) {
                return level - 1;
            }
            ++level;
        }
        return 1;
    }
}

