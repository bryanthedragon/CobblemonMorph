/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000e\u0010\u000fR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/battles/pokemon/BattleMove;", "", "", "disabled", "Z", "getDisabled", "()Z", "setDisabled", "(Z)V", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "Lcom/cobblemon/mod/common/api/moves/Move;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/Move;", "<init>", "(Lcom/cobblemon/mod/common/api/moves/Move;)V", "common"})
public final class BattleMove {
    @NotNull
    private final Move move;
    private boolean disabled;

    public BattleMove(@NotNull Move move) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        this.move = move;
    }

    @NotNull
    public final Move getMove() {
        return this.move;
    }

    public final boolean getDisabled() {
        return this.disabled;
    }

    public final void setDisabled(boolean bl) {
        this.disabled = bl;
    }
}

