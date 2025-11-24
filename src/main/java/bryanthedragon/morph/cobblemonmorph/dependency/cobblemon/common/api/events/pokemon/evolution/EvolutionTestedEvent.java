/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\u0006\u0010\u000e\u001a\u00020\b\u0012\u0006\u0010\u000f\u001a\u00020\b\u00a2\u0006\u0004\b%\u0010&J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u000b\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\nJ8\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u00022\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0014\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0017\u001a\u00020\u0016H\u00d6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u001a\u001a\u00020\u0019H\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001a\u0010\r\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u001c\u001a\u0004\b\u001d\u0010\u0007R\u0017\u0010\u000e\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001e\u001a\u0004\b\u001f\u0010\nR\u001a\u0010\f\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010 \u001a\u0004\b!\u0010\u0004R\"\u0010\u000f\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u001e\u001a\u0004\b\"\u0010\n\"\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionTestedEvent;", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionEvent;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "component2", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "", "component3", "()Z", "component4", "pokemon", "evolution", "originalResult", "result", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;ZZ)Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionTestedEvent;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "getEvolution", "Z", "getOriginalResult", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "getResult", "setResult", "(Z)V", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;ZZ)V", "common"})
public final class EvolutionTestedEvent
implements EvolutionEvent {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final Evolution evolution;
    private final boolean originalResult;
    private boolean result;

    public EvolutionTestedEvent(@NotNull Pokemon pokemon, @NotNull Evolution evolution, boolean originalResult, boolean result) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        this.pokemon = pokemon;
        this.evolution = evolution;
        this.originalResult = originalResult;
        this.result = result;
    }

    @Override
    @NotNull
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    @Override
    @NotNull
    public Evolution getEvolution() {
        return this.evolution;
    }

    public final boolean getOriginalResult() {
        return this.originalResult;
    }

    public final boolean getResult() {
        return this.result;
    }

    public final void setResult(boolean bl) {
        this.result = bl;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @NotNull
    public final Evolution component2() {
        return this.evolution;
    }

    public final boolean component3() {
        return this.originalResult;
    }

    public final boolean component4() {
        return this.result;
    }

    @NotNull
    public final EvolutionTestedEvent copy(@NotNull Pokemon pokemon, @NotNull Evolution evolution, boolean originalResult, boolean result) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        return new EvolutionTestedEvent(pokemon, evolution, originalResult, result);
    }

    public static /* synthetic */ EvolutionTestedEvent copy$default(EvolutionTestedEvent evolutionTestedEvent, Pokemon pokemon, Evolution evolution, boolean bl, boolean bl2, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = evolutionTestedEvent.pokemon;
        }
        if ((n & 2) != 0) {
            evolution = evolutionTestedEvent.evolution;
        }
        if ((n & 4) != 0) {
            bl = evolutionTestedEvent.originalResult;
        }
        if ((n & 8) != 0) {
            bl2 = evolutionTestedEvent.result;
        }
        return evolutionTestedEvent.copy(pokemon, evolution, bl, bl2);
    }

    @NotNull
    public String toString() {
        return "EvolutionTestedEvent(pokemon=" + this.pokemon + ", evolution=" + this.evolution + ", originalResult=" + this.originalResult + ", result=" + this.result + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + this.evolution.hashCode();
        int n = this.originalResult ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        int n2 = this.result ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result = result * 31 + n2;
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EvolutionTestedEvent)) {
            return false;
        }
        EvolutionTestedEvent evolutionTestedEvent = (EvolutionTestedEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)evolutionTestedEvent.pokemon)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.evolution, (Object)evolutionTestedEvent.evolution)) {
            return false;
        }
        if (this.originalResult != evolutionTestedEvent.originalResult) {
            return false;
        }
        return this.result == evolutionTestedEvent.result;
    }
}

