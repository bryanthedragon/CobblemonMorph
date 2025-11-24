/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.IdleBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.MoveBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.RestBehaviour;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\"\u0010\b\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "", "Lcom/cobblemon/mod/common/pokemon/ai/IdleBehaviour;", "idle", "Lcom/cobblemon/mod/common/pokemon/ai/IdleBehaviour;", "getIdle", "()Lcom/cobblemon/mod/common/pokemon/ai/IdleBehaviour;", "Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "moving", "Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "getMoving", "()Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "setMoving", "(Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;)V", "Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "resting", "Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "getResting", "()Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "<init>", "()V", "common"})
public class PokemonBehaviour {
    @NotNull
    private final RestBehaviour resting = new RestBehaviour();
    @NotNull
    private MoveBehaviour moving = new MoveBehaviour();
    @NotNull
    private final IdleBehaviour idle = new IdleBehaviour();

    @NotNull
    public final RestBehaviour getResting() {
        return this.resting;
    }

    @NotNull
    public final MoveBehaviour getMoving() {
        return this.moving;
    }

    public final void setMoving(@NotNull MoveBehaviour moveBehaviour) {
        Intrinsics.checkNotNullParameter((Object)moveBehaviour, (String)"<set-?>");
        this.moving = moveBehaviour;
    }

    @NotNull
    public final IdleBehaviour getIdle() {
        return this.idle;
    }
}

