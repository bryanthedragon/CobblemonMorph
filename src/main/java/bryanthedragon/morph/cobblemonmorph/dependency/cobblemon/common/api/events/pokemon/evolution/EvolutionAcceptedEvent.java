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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0006\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ$\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u00d6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012H\u00d6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u001a\u0010\n\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u0018\u001a\u0004\b\u0019\u0010\bR\u001a\u0010\t\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\u001a\u001a\u0004\b\u001b\u0010\u0005\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionAcceptedEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionEvent;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "component2", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "pokemon", "evolution", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionAcceptedEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "getEvolution", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)V", "common"})
public final class EvolutionAcceptedEvent
extends Cancelable
implements EvolutionEvent {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final Evolution evolution;

    public EvolutionAcceptedEvent(@NotNull Pokemon pokemon, @NotNull Evolution evolution) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        this.pokemon = pokemon;
        this.evolution = evolution;
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

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @NotNull
    public final Evolution component2() {
        return this.evolution;
    }

    @NotNull
    public final EvolutionAcceptedEvent copy(@NotNull Pokemon pokemon, @NotNull Evolution evolution) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        return new EvolutionAcceptedEvent(pokemon, evolution);
    }

    public static /* synthetic */ EvolutionAcceptedEvent copy$default(EvolutionAcceptedEvent evolutionAcceptedEvent, Pokemon pokemon, Evolution evolution, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = evolutionAcceptedEvent.pokemon;
        }
        if ((n & 2) != 0) {
            evolution = evolutionAcceptedEvent.evolution;
        }
        return evolutionAcceptedEvent.copy(pokemon, evolution);
    }

    @NotNull
    public String toString() {
        return "EvolutionAcceptedEvent(pokemon=" + this.pokemon + ", evolution=" + this.evolution + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + this.evolution.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EvolutionAcceptedEvent)) {
            return false;
        }
        EvolutionAcceptedEvent evolutionAcceptedEvent = (EvolutionAcceptedEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)evolutionAcceptedEvent.pokemon)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.evolution, (Object)evolutionAcceptedEvent.evolution);
    }
}

