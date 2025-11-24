/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\u000e\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u0004J>\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00022\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0004J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0019\u001a\u0004\b\u001a\u0010\u0004R\u0017\u0010\f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u0019\u001a\u0004\b\u001b\u0010\u0004R\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001c\u001a\u0004\b\u001d\u0010\tR\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0019\u001a\u0004\b\u001e\u0010\u0004\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "", "", "component1", "()I", "component2", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "component3", "()Ljava/util/Set;", "component4", "oldLevel", "newLevel", "newMoves", "experienceAdded", "copy", "(IILjava/util/Set;I)Lcom/cobblemon/mod/common/pokemon/AddExperienceResult;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getExperienceAdded", "getNewLevel", "Ljava/util/Set;", "getNewMoves", "getOldLevel", "<init>", "(IILjava/util/Set;I)V", "common"})
public final class AddExperienceResult {
    private final int oldLevel;
    private final int newLevel;
    @NotNull
    private final Set<MoveTemplate> newMoves;
    private final int experienceAdded;

    public AddExperienceResult(int oldLevel, int newLevel, @NotNull Set<? extends MoveTemplate> newMoves, int experienceAdded) {
        Intrinsics.checkNotNullParameter(newMoves, (String)"newMoves");
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
        this.newMoves = newMoves;
        this.experienceAdded = experienceAdded;
    }

    public final int getOldLevel() {
        return this.oldLevel;
    }

    public final int getNewLevel() {
        return this.newLevel;
    }

    @NotNull
    public final Set<MoveTemplate> getNewMoves() {
        return this.newMoves;
    }

    public final int getExperienceAdded() {
        return this.experienceAdded;
    }

    public final int component1() {
        return this.oldLevel;
    }

    public final int component2() {
        return this.newLevel;
    }

    @NotNull
    public final Set<MoveTemplate> component3() {
        return this.newMoves;
    }

    public final int component4() {
        return this.experienceAdded;
    }

    @NotNull
    public final AddExperienceResult copy(int oldLevel, int newLevel, @NotNull Set<? extends MoveTemplate> newMoves, int experienceAdded) {
        Intrinsics.checkNotNullParameter(newMoves, (String)"newMoves");
        return new AddExperienceResult(oldLevel, newLevel, newMoves, experienceAdded);
    }

    public static /* synthetic */ AddExperienceResult copy$default(AddExperienceResult addExperienceResult, int n, int n2, Set set2, int n3, int n4, Object object) {
        if ((n4 & 1) != 0) {
            n = addExperienceResult.oldLevel;
        }
        if ((n4 & 2) != 0) {
            n2 = addExperienceResult.newLevel;
        }
        if ((n4 & 4) != 0) {
            set2 = addExperienceResult.newMoves;
        }
        if ((n4 & 8) != 0) {
            n3 = addExperienceResult.experienceAdded;
        }
        return addExperienceResult.copy(n, n2, set2, n3);
    }

    @NotNull
    public String toString() {
        return "AddExperienceResult(oldLevel=" + this.oldLevel + ", newLevel=" + this.newLevel + ", newMoves=" + this.newMoves + ", experienceAdded=" + this.experienceAdded + ")";
    }

    public int hashCode() {
        int result = Integer.hashCode(this.oldLevel);
        result = result * 31 + Integer.hashCode(this.newLevel);
        result = result * 31 + ((Object)this.newMoves).hashCode();
        result = result * 31 + Integer.hashCode(this.experienceAdded);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AddExperienceResult)) {
            return false;
        }
        AddExperienceResult addExperienceResult = (AddExperienceResult)other;
        if (this.oldLevel != addExperienceResult.oldLevel) {
            return false;
        }
        if (this.newLevel != addExperienceResult.newLevel) {
            return false;
        }
        if (!Intrinsics.areEqual(this.newMoves, addExperienceResult.newMoves)) {
            return false;
        }
        return this.experienceAdded == addExperienceResult.experienceAdded;
    }
}

