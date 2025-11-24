/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.IdleBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.MoveBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.PokemonBehaviour;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.RestBehaviour;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0016\u0010\t\u001a\u0004\u0018\u00010\b8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0011\u0010\r\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0010\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\"\u0010\u0012\u001a\u00020\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u001a\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/pokemon/ai/FormPokemonBehaviour;", "", "Lcom/cobblemon/mod/common/pokemon/ai/IdleBehaviour;", "_idle", "Lcom/cobblemon/mod/common/pokemon/ai/IdleBehaviour;", "Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "_moving", "Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "_resting", "Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "getIdle", "()Lcom/cobblemon/mod/common/pokemon/ai/IdleBehaviour;", "idle", "getMoving", "()Lcom/cobblemon/mod/common/pokemon/ai/MoveBehaviour;", "moving", "Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "parent", "Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "getParent", "()Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;", "setParent", "(Lcom/cobblemon/mod/common/pokemon/ai/PokemonBehaviour;)V", "getResting", "()Lcom/cobblemon/mod/common/pokemon/ai/RestBehaviour;", "resting", "<init>", "()V", "common"})
public final class FormPokemonBehaviour {
    public transient PokemonBehaviour parent;
    @SerializedName(value="resting")
    @Nullable
    private final RestBehaviour _resting;
    @SerializedName(value="moving")
    @Nullable
    private final MoveBehaviour _moving;
    @SerializedName(value="idle")
    @Nullable
    private final IdleBehaviour _idle;

    @NotNull
    public final PokemonBehaviour getParent() {
        PokemonBehaviour pokemonBehaviour = this.parent;
        if (pokemonBehaviour != null) {
            return pokemonBehaviour;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"parent");
        return null;
    }

    public final void setParent(@NotNull PokemonBehaviour pokemonBehaviour) {
        Intrinsics.checkNotNullParameter((Object)pokemonBehaviour, (String)"<set-?>");
        this.parent = pokemonBehaviour;
    }

    @NotNull
    public final RestBehaviour getResting() {
        RestBehaviour restBehaviour = this._resting;
        if (restBehaviour == null) {
            restBehaviour = this.getParent().getResting();
        }
        return restBehaviour;
    }

    @NotNull
    public final MoveBehaviour getMoving() {
        MoveBehaviour moveBehaviour = this._moving;
        if (moveBehaviour == null) {
            moveBehaviour = this.getParent().getMoving();
        }
        return moveBehaviour;
    }

    @NotNull
    public final IdleBehaviour getIdle() {
        IdleBehaviour idleBehaviour = this._idle;
        if (idleBehaviour == null) {
            idleBehaviour = this.getParent().getIdle();
        }
        return idleBehaviour;
    }
}

